package com.workfront.internship.booklibrary.spring;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by ${Sona} on 8/29/2016.
 */
@Configuration
@ComponentScan(basePackages = "com.workfront")
@Profile("Development")
public class DevelopmentConfig {
    @Bean
    public DataSource getDataSource() throws IOException, SQLException {

        BasicDataSource dataSource = new BasicDataSource();
        Properties properties = new Properties();
        InputStream fis = DevelopmentConfig.class.getClassLoader().getResourceAsStream("ConnectionResources.properties");
        properties.load(fis);

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(properties.getProperty("UserName"));
        dataSource.setPassword(properties.getProperty("Password")); //mydb
        dataSource.setUrl(properties.getProperty("Url"));

        return dataSource;
    }
}