package porz.basic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BasicExample {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.workfront.springexample.basic");

        ExampleBean exampleBean = applicationContext.getBean(ExampleBean.class);
        exampleBean.doSomething();
    }
}
