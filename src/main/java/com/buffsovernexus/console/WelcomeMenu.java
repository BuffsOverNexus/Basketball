package com.buffsovernexus.console;

import com.buffsovernexus.Runner;

public class WelcomeMenu {

    public WelcomeMenu() {
        System.out.println();
        System.out.println(">>> WELCOME TO BASKETBALL <<<");
        System.out.println("In order to access your scenarios, you need to login to an account.");
        System.out.println("If you do not have an account, you may create one.");
        System.out.println("Press any key to continue or press (C) to create an account...");
        System.out.println();
        System.out.print("Press any key (or C to create account): ");

        boolean currentUser = Runner.in.next().toLowerCase().contains("c");

        if (!currentUser) {
            new LoginMenu();
        } else {
            new CreateAccount();
        }
    }
}
