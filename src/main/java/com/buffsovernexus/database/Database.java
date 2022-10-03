package com.buffsovernexus.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Database {
    public static SessionFactory sessionFactory;

    public static void setUp() throws Exception {
        // A SessionFactory is set up once for an application!

        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            e.printStackTrace();
        }
    }
}
