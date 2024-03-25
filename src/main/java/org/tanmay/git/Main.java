package org.tanmay.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static final String PATH = "C:\\Users\\tanmay.majumdar\\Downloads\\Code_Dependencies\\Personal\\GitHelper";
    public static void main(String[] args) throws Exception {

        final String username = args[0];
        final String password = args[1];

        if (isEmpty(username) || isEmpty(password)) {
            throw new RuntimeException("Username or password not provided");
        }

        try (Git git  = Git.open(new File(PATH))) {
            PushCommand pushCommand = git.push();
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
            pushCommand.call();
        }
    }

    private static boolean isEmpty(final String st) {
        return st == null || st.isEmpty();
    }
}