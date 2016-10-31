/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nanosolution.aecontrol.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Jessica
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static Session session;

    private static SessionFactory buildSessionFactory() {

        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            SessionFactory sessionFactory = configuration
                    .buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            System.err.println(ex.getCause());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {

        if (session == null || !session.isConnected()) {
            session = sessionFactory.getCurrentSession();
        }

        return session;
    }
}
