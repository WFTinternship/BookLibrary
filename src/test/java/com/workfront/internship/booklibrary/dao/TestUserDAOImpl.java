package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.util.ArrayList;
import java.util.List;
import static com.workfront.internship.booklibrary.dao.TestUtil.*;

public class TestUserDAOImpl {

    private UserDAO userDAO;

    private User expectedUser = null;

    DataSource dataSource = DataSource.getInstance();

    @Before
    public void setup() throws Exception{
        init();
    }

    @After
    public void tearDown() {
        userDAO.deleteAll();
    }

    private void init() throws Exception {
        userDAO = new UserDAOImpl(dataSource);
    }

    @Test
    public void add_user(){
        expectedUser = getRandomUser();

        //test method add(user)
        int id = userDAO.add(expectedUser);

        assertTrue(id > 0);

        User actualUser = userDAO.getUserByID(id);
        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void get_user_by_id(){
        expectedUser = getRandomUser();
        int id = userDAO.add(expectedUser);

        //test method getUserById()
        User actualUser = userDAO.getUserByID(id);

        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void get_user_by_email(){
        expectedUser = getRandomUser();
        userDAO.add(expectedUser);

        //test method getUserByeMail
        User actualUser = userDAO.getUserByeMail(expectedUser.geteMail());

        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void get_user_by_username(){
        expectedUser = getRandomUser();
        userDAO.add(expectedUser);

        //test method getUserByUsername
        User actualUser = userDAO.getUserByUsername(expectedUser.getUsername());

        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void get_all_users(){
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
    public void update_user(){
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
    public void delete_user_by_id(){
        userDAO.deleteAll();

        expectedUser = getRandomUser();
        int id = userDAO.add(expectedUser);

        // TmpTest method deleteUser()
        userDAO.deleteUser(id);

        User actualUser = userDAO.getUserByID(id);
        assertNull(actualUser);
    }

    @Test
    public void delete_all(){
        expectedUser = getRandomUser();
        userDAO.add(expectedUser);

        // Test method deleteAllUsers()
        userDAO.deleteAll();

        assertTrue(userDAO.getAllUsers().isEmpty());
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
