package org.tanmay.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
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

        final int mode = Integer.parseInt(args[3]);

        if (isEmpty(helper.username) || isEmpty(helper.password)) {
            throw new RuntimeException("Username or password not provided");
        }

        switch(mode) {
            case 1:
                helper.push();
                break;
            case 2:
                helper.pull();
                break;
            default:
                throw new IllegalArgumentException("Invalid mode has been passed. Mode 1 = push and 2 = pull");
        }
    }

    private void push() throws Exception {
        try (Git git  = Git.open(new File(path))) {
            PushCommand pushCommand = git.push();
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
            for (PushResult result : pushCommand.call()) {
                result.getRemoteUpdates().forEach(System.out::println);
            }
        }
    }

    private void pull() throws Exception {
        try (Git git  = Git.open(new File(path))) {
            PushCommand pushCommand = git.push();
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
            for (PushResult result : pushCommand.call()) {
                result.getRemoteUpdates().forEach(System.out::println);
            }
        }
    }

    private static boolean isEmpty(final String st) {
        return st == null || st.isEmpty();
    }
}
