package com.moon.labs.database.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory implements AutoCloseable {

    private final HikariDataSource dataSource;

    public ConnectionFactory(ApplicationProperties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getDbUrl());
        config.setUsername(properties.getDbUsername());
        config.setPassword(properties.getDbPassword());
        config.setMaximumPoolSize(properties.getPoolSize());
        config.setSchema(properties.getDbSchema());
        config.setPoolName("DepartmentPool");

        this.dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}