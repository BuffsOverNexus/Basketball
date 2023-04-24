package com.buffsovernexus.console;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.Runner;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.Account;
import org.hibernate.Session;

public class LoginMenu {
    public LoginMenu() {
        // Ensure login is valid before continuing.
        boolean isValidLogin = false;

        System.out.println();
        System.out.println(">> ACCESS YOUR ACCOUNT");
        System.out.println();
        while (!isValidLogin) {

            System.out.print("USERNAME: ");
            String username = Runner.in.next();
            System.out.print("PASSWORD: ");
            String password = Runner.in.next();

            Session session = Database.sessionFactory.openSession();
            session.beginTransaction();
            String query = String.format("FROM Account WHERE username = '%s' AND password = '%s'", username, password);
            Account account = session.createQuery(query, Account.class).uniqueResult();

            if (account != null) {
                isValidLogin = true;
                System.out.printf("Welcome back, %s!", account.getUsername());
                CurrentSession.accountId = account.getId();
                new ChooseScenario();
            } else {
                System.out.println();
                System.out.println("(ERROR) Incorrect credentials. Try again!");
                System.out.println();
                System.out.println("Try again?");
                System.out.print("Yes or No (y/n): ");
                char response = Runner.in.next().toLowerCase().charAt(0);
                if (response == 'n') {
                    break;
                }
            }

            session.getTransaction().commit();
            session.close();

        }
    }
}
