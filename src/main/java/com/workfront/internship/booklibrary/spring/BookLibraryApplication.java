package com.workfront.internship.booklibrary.spring;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

/**
 * Created by ${Sona} on 8/19/2016.
 */
@Configuration
@ComponentScan(basePackages = "com.workfront")
public class BookLibraryApplication {
    public static final String SPRING_CONTEXT_KEY = "springContextKey";

    public static void init(ServletContext servletContext) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BookLibraryApplication.class);
        servletContext.setAttribute(SPRING_CONTEXT_KEY, applicationContext);
    }

    public static ApplicationContext getApplicationContext(ServletContext servletContext) {
        return (ApplicationContext)servletContext.getAttribute(SPRING_CONTEXT_KEY);
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("sonadb"); //mydb
        dataSource.setUrl("jdbc:mysql://localhost/book_library");

        // the settings below are optional -- dbcp can work with defaults
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(20);
        dataSource.setMaxOpenPreparedStatements(180);

        return dataSource;
    }
}
