package org.tanmay.git;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import java.io.File;

public class GitHelper {

    private String username, password, path;

    public static void main(String[] args) throws Exception {

        if (args.length != 4) {
            System.out.println("Please provide inputs in the following format.\n" +
                    " java -jar <generated_jar> <username> <password> <repo_path> <mode>");
            return;
        }
        final GitHelper helper = new GitHelper();
        helper.username = args[0];
        helper.password = args[1];
        helper.path = args[2];

        System.out.println(String.format("PWD : %s", helper.path));
        final int mode = Integer.parseInt(args[3]);

        if (isEmpty(helper.username) || isEmpty(helper.password)) {
            throw new RuntimeException("Username or password not provided");
        }

        switch(mode) {
            case 1:
                System.out.println("Attempting push");
                helper.push();
                break;
            case 2:
                System.out.println("Attempting pull");
                helper.pull();
                break;
            case 3:
                System.out.println("Attempting fetch");
                helper.fetch();
                break;
            default:
                throw new IllegalArgumentException("Invalid mode has been passed. Mode 1 = push and 2 = pull");
        }
    }

    private void push() throws Exception {
        try (Git git  = Git.open(new File(path))) {
            PushCommand pushCommand = git.push();
            for (PushResult result : processTransport(pushCommand))  {
                result.getRemoteUpdates().forEach(System.out::println);
            }
        }
    }

    private void pull() throws Exception {
        printLastLog();
        try (Git git  = Git.open(new File(path))) {
            PullCommand command = git.pull();
            PullResult result = processTransport(command);
            result.getFetchResult().submoduleResults().forEach(
                    (key, value) -> System.out.printf("FETCHED RESULT : %s, %s%n", key , value.getMessages()));
            System.out.printf("MERGE RESULTS %s", result.getMergeResult().getMergeStatus());
            printLastLog();
        }
    }
    private void fetch() throws Exception {
        printLastLog();
        try (Git git  = Git.open(new File(path))) {
            final FetchCommand command = git.fetch();
            final FetchResult result = processTransport(command);
            result.submoduleResults().forEach(
                    (key, value) -> System.out.printf("FETCHED RESULT : %s, %s%n", key , value.getMessages()));
            printLastLog();
        }
    }

    private <T extends TransportCommand<T, Y>, Y> Y processTransport(TransportCommand<T, Y> command) throws Exception {
        command.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
        return command.call();
    }
    private static boolean isEmpty(final String st) {
        return st == null || st.isEmpty();
    }

    private void printLastLog() throws Exception {
        try (Git git  = Git.open(new File(path))) {
            final LogCommand command = git.log().setMaxCount(2);
            for (final RevCommit commit : command.call()) {
                printMessage(commit.toString());
            }
        }
    }

    private static void printMessage(final String s) {
        System.out.printf("\n----------%s---------\n", s);
    }
}
