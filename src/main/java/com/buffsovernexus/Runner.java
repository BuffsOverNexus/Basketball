package com.buffsovernexus;

import com.buffsovernexus.console.WelcomeMenu;
import com.buffsovernexus.database.Database;

import java.util.Scanner;

public class Runner {

    public static Scanner in;

    public static void main(String[] args) {
        try {
            // Grab the first argument
            if (args.length == 0) {
                System.out.println("Please add an 'env' variable");
                return;
            }

            String environment = args[0];
            in = new Scanner(System.in);
            // Setup Hibernate
            Database.setUp(environment);

            // Start the welcome message.
            new WelcomeMenu();

            in.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
