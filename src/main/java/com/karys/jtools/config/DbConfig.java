package com.karys.jtools.config;

import lombok.RequiredArgsConstructor;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.SimpleTransactionStatus;

@Configuration
public class DbConfig {

    @Bean
    public DB mapDb() {
        return DBMaker.fileDB("jTools.db").transactionEnable().fileMmapEnable().fileMmapEnableIfSupported().fileMmapPreclearDisable().closeOnJvmShutdown().make();
    }

    @Bean
    @ConditionalOnMissingBean(PlatformTransactionManager.class)
    public PlatformTransactionManager mapDbTransactionManager(DB mapDb) {
        return new MapDbTransactionManager(mapDb);
    }

    @RequiredArgsConstructor
    public static class MapDbTransactionManager implements PlatformTransactionManager {

        private final DB db;

        @Override
        public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
            return new SimpleTransactionStatus();
        }

        @Override
        public void commit(TransactionStatus status) throws TransactionException {
            db.commit();
        }

        @Override
        public void rollback(TransactionStatus status) throws TransactionException {
            db.rollback();
        }
    }
}
