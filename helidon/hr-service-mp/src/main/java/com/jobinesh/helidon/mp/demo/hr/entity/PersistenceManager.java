/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobinesh.helidon.mp.demo.hr.entity;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;
import static org.eclipse.persistence.config.EntityManagerProperties.JDBC_DRIVER;
import static org.eclipse.persistence.config.EntityManagerProperties.JDBC_URL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_LEVEL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_SESSION;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_THREAD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_TIMESTAMP;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TARGET_SERVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TRANSACTION_TYPE;
import org.eclipse.persistence.config.TargetServer;

public enum PersistenceManager {
    INSTANCE;
    private EntityManagerFactory emFactory;

    private PersistenceManager() {
        // "jpa-example" was the value of the name attribute of the
        // persistence-unit element.
        Map properties = new HashMap();

        // Ensure RESOURCE_LOCAL transactions is used.
        properties.put(TRANSACTION_TYPE,
                PersistenceUnitTransactionType.RESOURCE_LOCAL.name());

        // Configure the internal EclipseLink connection pool
        properties.put(JDBC_DRIVER, "org.sqlite.JDBC");
        properties.put(JDBC_URL, "jdbc:sqlite:/Users/jmpurush/mywork/sqlite/demo.db");

        // Configure logging. FINE ensures all SQL is shown
        properties.put(LOGGING_LEVEL, "FINE");
        properties.put(LOGGING_TIMESTAMP, "false");
        properties.put(LOGGING_THREAD, "false");
        properties.put(LOGGING_SESSION, "false");

        // Ensure that no server-platform is configured
        properties.put(TARGET_SERVER, TargetServer.None);
        emFactory = Persistence.createEntityManagerFactory("demo-pu", properties);
    }

    public EntityManager getEntityManager() {

        return emFactory.createEntityManager();
    }

    public void close() {
        emFactory.close();
    }
}
