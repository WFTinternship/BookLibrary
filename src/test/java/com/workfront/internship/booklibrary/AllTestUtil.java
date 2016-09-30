package com.workfront.internship.booklibrary;

import com.workfront.internship.booklibrary.business.ManagerTestConfig;
import com.workfront.internship.booklibrary.business.UserManager;
import com.workfront.internship.booklibrary.common.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by ${Sona} on 9/30/2016.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ManagerTestConfig.class)
@ActiveProfiles("Development")
public class AllTestUtil {
    @Autowired
    private static UserManager userManager;
    public static User user;

    public static User makeRandomUser(){
//        User user = new User();
        user.setName("name");
        user.setSurname("surname");
        user.setUsername("username" + uuid());
        user.setPassword("password");
        user.setAddress("address");
        user.seteMail("sona" + uuid() + "@yahoo.com");
        user.setPhone("phone number");
        user.setAccessPrivilege("user");

        return user;
    }

    public static User registerRandomUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int id = userManager.register(makeRandomUser());

        user = userManager.findUserByID(id);
        return user;
    }

    public static String uuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
