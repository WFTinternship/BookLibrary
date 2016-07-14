package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.dao.TestUtil;
import com.workfront.internship.booklibrary.dao.UserDAO;
import com.workfront.internship.booklibrary.dao.UserDAOImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static com.workfront.internship.booklibrary.dao.TestUtil.*;

public class TestUserDAOImpl {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private UserDAO userDAO = new UserDAOImpl();
    private List<User> userList = new ArrayList<>();
    //private User user = null;

    @Before
    public void setup() {
        //create mocked user
        User user = getRandomUser();

        userDAO.add(user);
        userList.add(user);
    }

    @After
    public void tearDown() {
        // delete all User objects created during this test deleteAllUsers()
        //deleteAllUsers();

        //close resources
        closeResource(resultSet);
        closeResource(preparedStatement);
        closeResource(connection);
    }

    @Test
    public void add_user(){
        User user = getRandomUser();
        assertEquals(0, user.getId());

        //test method add(user)
        userDAO.add(user);

        userList.add(user);

        assertNotNull(user);
        assertTrue(user.getId() > 0);

        User actualUser = userDAO.getUserByID(user.getId());

        checkAssertions(user, actualUser);
    }

    @Test
    public void get_user_by_id(){
        User user = userList.get(0);
        System.out.println(user.toString());
        //userDAO.add(user);
        //userList.add(user);

        //test method getUserById
        User actualUser = userDAO.getUserByID(user.getId());
        System.out.println(actualUser.toString());

        checkAssertions(user, actualUser);
    }

    @Test
    public void get_user_by_email(){
        User user = userList.get(0);

        //test method getUserByeMail
        User actualUser = userDAO.getUserByeMail(user.geteMail());

        checkAssertions(user, actualUser);
    }

    @Test
    public void get_user_by_username(){
        User user = userList.get(0);

        //test method getUserByUsername
        User actualUser = userDAO.getUserByUsername(user.getUsername());

        checkAssertions(user, actualUser);
    }

    @Test
    public void get_all_users(){
        userDAO.deleteAll();
        deleteAllUsers();
        List<User> users = null;
        int userCount = 2;
        User user;
        for(int i = 0; i < userCount; i++){
            user = getRandomUser();
            userDAO.add(user);
            userList.add(user);
        }

        //test method getAllUsers()
        users = userDAO.getAllUsers();

        assertEquals(userList.size(), users.size());
        for(int i = 0; i < userCount; i++){
            checkAssertions(userList.get(i), users.get(i));
        }
    }

  /**  @Test // TODO: 7/14/2016
    public void get_all_users_by_pickedBookId(){
        userDAO.deleteAll();
        deleteAllUsers();
        List<User> users = null;
        int userCount = 2;
        User user;
        for(int i = 0; i < userCount; i++){
            user = getRandomUser();
            userDAO.add(user);
            userList.add(user);
        }

        users = userDAO.getAllUsersByPickedBookId(1);
        assertEquals(userList.size(), users.size());
        for(int i = 0; i < userCount; i++){
            checkAssertions(userList.get(i), users.get(i));
        }
    }*/

  /**  @Test // TODO: 7/14/2016
    public void get_all_users_by_pendingBookId(){} */

    @Test
    public void update_user(){
        User user = userList.get(0);

        user.setName("Sona");
        user.setUsername("sonamik");

        // Test method updateUser()
        userDAO.updateUser(user);

        User newUser = userDAO.getUserByID(user.getId());

        checkAssertions(user, newUser);
    }

    @Test
    public void delete_user_by_id(){
        User user = userList.get(0);
        int id = user.getId();

        // Test method deleteUser()
        userDAO.deleteUser(id);

        User actualUser = userDAO.getUserByID(id);

        assertNull(actualUser);
    }

    @Test
    public void delete_all(){
        // Test method deleteAllUsers()
        userDAO.deleteAll();

        assertTrue(userDAO.getAllUsers().isEmpty());
    }





    private boolean deleteAllUsers(){
        for(User user : userList){
            userDAO.deleteUser(user.getId());
        }
        userList.clear();
        return userList.isEmpty();
    }

    private User getRandomUser(){
        User user = new User();
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

    private void checkAssertions(User user, User actualUser){
        assertEquals(user.getName(), actualUser.getName());
        assertEquals(user.getSurname(), actualUser.getSurname());
        assertEquals(user.getUsername(), actualUser.getUsername());
        assertEquals(user.getPassword(), actualUser.getPassword());
        assertEquals(user.getAddress(), actualUser.getAddress());
        assertEquals(user.geteMail(), actualUser.geteMail());
        assertEquals(user.getPhone(), actualUser.getPhone());
        assertEquals(user.getAccessPrivilege(), actualUser.getAccessPrivilege());
    }
}
