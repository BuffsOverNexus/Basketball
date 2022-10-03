package com.buffsovernexus.console;

import com.buffsovernexus.Runner;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.Account;
import org.hibernate.Session;

public class CreateAccount {

    public CreateAccount() {
        System.out.println();
        System.out.println(">> CREATE NEW ACCOUNT");
        System.out.print("Enter desired username: ");
        String username = Runner.in.next();
        System.out.print("Enter in password: ");
        String password = Runner.in.next();

        System.out.println();
        System.out.println(">>> CREATING ACCOUNT...");

        // Being transaction and add to the database.
        Session session = Database.sessionFactory.openSession();
        session.beginTransaction();

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        session.save(account);

        session.getTransaction().commit();
        session.close();

        // Prompt the user to login.
        new LoginMenu();
    }
}

