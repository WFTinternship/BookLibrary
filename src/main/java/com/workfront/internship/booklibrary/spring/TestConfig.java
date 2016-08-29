package com.workfront.internship.booklibrary.spring;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ${Sona} on 8/29/2016.
 */
@Configuration
@ComponentScan(basePackages = "com.workfront")
@Profile("Test")
public class TestConfig {
    @Bean
    public DataSource getDataSource() throws IOException {

        BasicDataSource dataSource = new BasicDataSource();
        Properties properties = new Properties();
        properties.load(DevelopmentConfig.class.getClassLoader().getResourceAsStream("ConnectionResources.properties"));

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("UserName");
        dataSource.setPassword("Password"); //mydb
        dataSource.setUrl("Url");

        return dataSource;
    }
}
