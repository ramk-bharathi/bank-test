package com.bank.test.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;
    static {
        try {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml"); //hibernate config xml file name
            sessionFactory = cfg.buildSessionFactory();
        } catch (Throwable th) {
            System.err.println("Enitial SessionFactory creation failed" + th);
            throw new ExceptionInInitializerError(th);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
