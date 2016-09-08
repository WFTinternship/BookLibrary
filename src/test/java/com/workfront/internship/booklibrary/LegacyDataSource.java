package com.workfront.internship.booklibrary;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class LegacyDataSource {
    private static LegacyDataSource datasource;
    private BasicDataSource ds;

    private LegacyDataSource() throws IOException, SQLException {
        ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("mydb"); //sonadb
        ds.setUrl("jdbc:mysql://localhost/book_library");

        // the settings below are optional -- dbcp can work with defaults
        ds.setMinIdle(5);
        ds.setMaxIdle(20);
        ds.setMaxOpenPreparedStatements(180);

        ds.getConnection();
    }

    public static LegacyDataSource getInstance() {
        if (datasource == null) {
            try {
                datasource = new LegacyDataSource();
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }
        }
        return datasource;
    }

    public Connection getConnection() throws SQLException {
        return this.ds.getConnection();
    }
}