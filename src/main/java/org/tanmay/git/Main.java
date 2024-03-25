package org.tanmay.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {

        final String username = args[0];
        final String password = args[1];
        final String path = args[2];

        if (isEmpty(username) || isEmpty(password)) {
            throw new RuntimeException("Username or password not provided");
        }

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