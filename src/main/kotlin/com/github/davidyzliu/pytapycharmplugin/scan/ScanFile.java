package com.github.davidyzliu.pytapycharmplugin.scan;

import java.io.*;

public class ScanFile {

    public static void main(String[] args) throws IOException {

        ProcessBuilder builder = new ProcessBuilder( "/bin/bash" );
        Process process = null;
        try {
            process = builder.start();
        }
        catch (IOException e) {
            System.out.println(e);
        }
        //get stdin of shell
        BufferedWriter p_stdin =
                new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

        // List of commands
        // TODO: get PyTA path
        String[] commands = {"cd ..", "cd pyta", "cd pyta", "cd python_ta", "python3 __main__.py -h"};

        for (String command : commands) {
            try {
                //single execution
                p_stdin.write(command);
                p_stdin.newLine();
                p_stdin.flush();
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }

        // close the shell by execution exit command
        try {
            p_stdin.write("exit");
            p_stdin.newLine();
            p_stdin.flush();
        }
        catch (IOException e) {
            System.out.println(e);
        }

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(process.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(process.getErrorStream()));

        // Read the output & errors from the command
        String output = null;
        while ((output = stdInput.readLine()) != null) {
            System.out.println(output);
        }

        String errors = null;
        while ((errors = stdError.readLine()) != null) {
            System.out.println(errors);
        }
    }
}
