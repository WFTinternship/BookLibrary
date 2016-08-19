package com.workfront.internship.booklibrary.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by ${Sona} on 8/19/2016.
 */
public class BookLibraryApplication {
    public static final String SPRING_CONTEXT_KEY = "springContextKey";

    public static void init(ServletContext servletContext) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.workfront");
        servletContext.setAttribute(SPRING_CONTEXT_KEY, applicationContext);
    }

    public static ApplicationContext getApplicationContext(ServletContext servletContext) {
        return (ApplicationContext)servletContext.getAttribute(SPRING_CONTEXT_KEY);
    }
}
