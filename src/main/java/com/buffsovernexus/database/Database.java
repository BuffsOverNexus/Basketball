package com.buffsovernexus.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Database {
    public static SessionFactory sessionFactory;

    public static void setUp(String environment) {
        // A SessionFactory is set up once for an application!
        try {
            if (environment.toLowerCase().contains("dev")) {
                sessionFactory = new Configuration().configure("hibernate_dev.cfg.xml").buildSessionFactory();
            } else {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            }
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            e.printStackTrace();
        }
    }
}
