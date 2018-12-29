/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobinesh.helidon.mp.demo.hr.entity;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
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

    private static final Logger logger = Logger.getLogger(PersistenceManager.class.getName());

    private DataSource createDataSource() {
        //See the following e.g for more options 
        //https://www.stubbornjava.com/posts/database-connection-pooling-in-java-with-hikaricp
        DataSource dataSource = null;
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:/Users/jmpurush/mywork/sqlite/demo.db");
        config.setDriverClassName("org.sqlite.JDBC");

        try {
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "setupPool", e);
        }
        return dataSource;
    }

    private PersistenceManager() {
        // "jpa-example" was the value of the name attribute of the
        // persistence-unit element.
        Map properties = new HashMap();
        // Ensure RESOURCE_LOCAL transactions is used.
        properties.put(TRANSACTION_TYPE,
                PersistenceUnitTransactionType.RESOURCE_LOCAL.name());

        // Configure the internal  data source
        properties.put("javax.persistence.nonJtaDataSource", createDataSource());
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
