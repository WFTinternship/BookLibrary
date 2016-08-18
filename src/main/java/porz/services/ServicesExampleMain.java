package porz.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServicesExampleMain {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ServiceApplication.class);
        UserService userService = context.getBean(UserService.class);
        //
        User user = userService.getById("someID");
        System.out.printf("User is " + user.getName());
    }
}
