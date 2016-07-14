package com.workfront.internship.booklibrary.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSource {
    private static DataSource datasource;
    private BasicDataSource ds;

    private DataSource() throws IOException, SQLException {
        ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("sonadb");
        ds.setUrl("jdbc:mysql://localhost/book_library");

        // the settings below are optional -- dbcp can work with defaults
        ds.setMinIdle(5);
        ds.setMaxIdle(20);
        ds.setMaxOpenPreparedStatements(180);

        ds.getConnection();
    }

    public static DataSource getInstance() throws IOException, SQLException {
        if (datasource == null) {
            datasource = new DataSource();
        }
        return datasource;
    }

    public Connection getConnection() throws SQLException {
        return this.ds.getConnection();
    }
} //todo get connection from ConnectionResources.properties file

