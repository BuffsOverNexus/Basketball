package com.buffsovernexus;

import com.buffsovernexus.console.WelcomeMenu;
import com.buffsovernexus.database.Database;

import java.util.Scanner;

public class Runner {

    public static Scanner in;

    public static void main(String[] args) {
        try {
            in = new Scanner(System.in);
            // Setup Hibernate
            Database.setUp();

            // Start the welcome message.
            new WelcomeMenu();

            in.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
