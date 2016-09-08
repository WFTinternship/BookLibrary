package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.*;

import com.workfront.internship.booklibrary.spring.DevelopmentConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.util.ArrayList;
import java.util.List;
import static com.workfront.internship.booklibrary.dao.TestUtil.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DevelopmentConfig.class)
@ActiveProfiles("Development")
public class TestUserDAOImpl {

    @Autowired
    private UserDAO userDAO;

    private User expectedUser = null;

//    LegacyDataSource dataSource = LegacyDataSource.getInstance();

    @Before
    public void setup() throws Exception{
        init();
    }

    @After
    public void tearDown() {
        userDAO.deleteAll();
    }

    private void init() throws Exception {
//        userDAO = new UserDAOImpl();
//        Whitebox.setInternalState(userDAO, "dataSource", dataSource);
    }

    @Test
    public void addUser(){
        expectedUser = getRandomUser();

        //test method add(user)
        int id = userDAO.add(expectedUser);

        assertTrue(id > 0);

        User actualUser = userDAO.getUserByID(id);
        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void getUserByID(){
        expectedUser = getRandomUser();
        int id = userDAO.add(expectedUser);

        //test method getUserById()
        User actualUser = userDAO.getUserByID(id);

        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void getUserByEmail(){
        expectedUser = getRandomUser();
        userDAO.add(expectedUser);

        //test method getUserByeMail
        User actualUser = userDAO.getUserByeMail(expectedUser.geteMail());

        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void getUserByUsername(){
        expectedUser = getRandomUser();
        userDAO.add(expectedUser);

        //test method getUserByUsername
        User actualUser = userDAO.getUserByUsername(expectedUser.getUsername());

        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void getAllUsers(){
        userDAO.deleteAll();

        List<User> expectedUserList = new ArrayList<>();
        List<User> actualUserList = new ArrayList<>();
        int userCount = 2;

        for(int i = 0; i < userCount; i++){
            User user = getRandomUser();
            userDAO.add(user);
            expectedUserList.add(user);
        }

        //test method getAllUsers()
        actualUserList = userDAO.getAllUsers();

        assertEquals(expectedUserList.size(), actualUserList.size());
        for(int i = 0; i < userCount; i++){
            checkAssertions(expectedUserList.get(i), actualUserList.get(i));
        }

        expectedUserList.clear();
        actualUserList.clear();
    }

    @Test
    public void updateUser(){
        expectedUser = getRandomUser();
        int id = userDAO.add(expectedUser);

        expectedUser.setName("Sona");
        expectedUser.setUsername("sonamik");

        // Test method updateUser()
        userDAO.updateUser(expectedUser);

        User actualUser = userDAO.getUserByID(id);
        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void deleteUserByID(){
        userDAO.deleteAll();

        expectedUser = getRandomUser();
        int id = userDAO.add(expectedUser);

        // TmpTest method deleteUser()
        userDAO.deleteUser(id);

        User actualUser = userDAO.getUserByID(id);
        assertNull(actualUser);
    }

    @Test
    public void deleteAll(){
        expectedUser = getRandomUser();
        userDAO.add(expectedUser);

        // Test method deleteAllUsers()
        userDAO.deleteAll();

        assertTrue(userDAO.getAllUsers().isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void add_duplicateUsernameEntry(){
        User user = getRandomUser();
        userDAO.add(user);
        User duplicateUser = getRandomUser();

        duplicateUser.setUsername("NewUsername");
        userDAO.add(duplicateUser);

        duplicateUser.setUsername(user.getUsername());
        userDAO.add(duplicateUser);
    }

    @Test(expected = RuntimeException.class)
    public void update_duplicateUsernameEntry(){
        User user = getRandomUser();
        User duplicateUser = user;
        userDAO.add(user);

        duplicateUser.setUsername("NewUsername");
        userDAO.add(duplicateUser);

        duplicateUser.setUsername(user.getUsername());
        userDAO.add(duplicateUser);
    }

    @Test(expected = RuntimeException.class)
    public void add_duplicateEMailEntry(){
        User user = getRandomUser();
        userDAO.add(user);
        User duplicateUser = getRandomUser();

        duplicateUser.seteMail("NeweMail");
        userDAO.add(duplicateUser);

        duplicateUser.seteMail(user.geteMail());
        userDAO.add(duplicateUser);
    }

    @Test(expected = RuntimeException.class)
    public void update_duplicateEMailEntry(){
        User user = getRandomUser();
        User duplicateUser = user;
        userDAO.add(user);

        duplicateUser.seteMail("NeweMail");
        userDAO.add(duplicateUser);

        duplicateUser.seteMail(user.geteMail());
        userDAO.add(duplicateUser);
    }


    private void checkAssertions(User user, User actualUser){
        assertEquals(user.getName(), actualUser.getName());
        assertEquals(user.getSurname(), actualUser.getSurname());
        assertEquals(user.getUsername(), actualUser.getUsername());
        assertEquals(user.getPassword(), actualUser.getPassword());
        assertEquals(user.getAddress(), actualUser.getAddress());
        assertEquals(user.geteMail(), actualUser.geteMail());
        assertEquals(user.getPhone(), actualUser.getPhone());
        assertEquals(user.getAccessPrivilege(), actualUser.getAccessPrivilege());
        assertEquals(user.getConfirmationStatus(), actualUser.getConfirmationStatus());
    }
}
