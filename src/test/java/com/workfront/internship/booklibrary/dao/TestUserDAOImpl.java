package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.*;
import com.workfront.internship.booklibrary.dao.TestUtil;
import com.workfront.internship.booklibrary.dao.UserDAO;
import com.workfront.internship.booklibrary.dao.UserDAOImpl;

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
    private PickBookDAO pickBookDAO;
    private PendingDAO pendingDAO;
    private UserDAOImpl userDAOImpl;

    private User expectedUser = null;
    private PickBook expectedPickBook = null;
    private Pending expectedPending = null;

    DataSource dataSource = DataSource.getInstance();

    @Before
    public void setup() throws Exception{
        init();
        expectedUser = getRandomUser();
        userDAO.add(expectedUser);

 //       expectedPickBook = getRandomPickBook();
        pickBookDAO.add(expectedPickBook);

 //       expectedPending = getRandomPending();
        pendingDAO.add(expectedPending);

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
        User expectedUser = getRandomUser();
        assertEquals(0, expectedUser.getId());

        //test method add(user)
        userDAO.add(expectedUser);

        assertNotNull(expectedUser);
        assertTrue(expectedUser.getId() > 0);

        User actualUser = userDAO.getUserByID(expectedUser.getId());

        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void get_user_by_id(){

        User expectedUser = this.expectedUser;

        //test method getUserById
        User actualUser = userDAO.getUserByID(expectedUser.getId());
        System.out.println(actualUser.toString());

        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void get_user_by_email(){

        User expectedUser = this.expectedUser;

        //test method getUserByeMail
        User actualUser = userDAO.getUserByeMail(expectedUser.geteMail());

        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void get_user_by_username(){

        User expectedUser = this.expectedUser;

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

        }

        //test method getAllUsers()
        actualUserList = userDAO.getAllUsers();

        assertEquals(expectedUserList.size(), actualUserList.size());
        for(int i = 0; i < userCount; i++){
            checkAssertions(expectedUserList.get(i), actualUserList.get(i));
        }

/**    @Test // TODO: 7/14/2016
    public void get_all_users_by_pickedBookId(){
        userDAO.deleteAll();

        List<User> users = null;
        int userCount = 2;
        User user;
        for(int i = 0; i < userCount; i++){
            user = getRandomUser();
            userDAO.add(user);

        }

        users = userDAO.getAllUsersByPickedBookId(1);
        assertEquals(userList.size(), users.size());
        for(int i = 0; i < userCount; i++){
            checkAssertions(userList.get(i), users.get(i));
        }
    }
*/

  /**  @Test // TODO: 7/14/2016
    public void get_all_users_by_pendingBookId(){} */

    @Test
    public void update_user(){
        userDAO.deleteAll();
        User expectedUser = getRandomUser();
        userDAO.add(expectedUser);

        expectedUser.setName("Sona");
        expectedUser.setUsername("sonamik");

        // TmpTest method updateUser()
        userDAO.updateUser(expectedUser);

        User actualUser = userDAO.getUserByID(expectedUser.getId());

        checkAssertions(expectedUser, actualUser);
    }

    @Test
    public void delete_user_by_id(){
        userDAO.deleteAll();

        User expectedUser = getRandomUser();
        userDAO.add(expectedUser);
        int id = expectedUser.getId();

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

    private Book getRandomBook(Genre genre) {
        Book book = new Book();

        book.setISBN("0123456789");
        book.setTitle("New Book" + uuid());
        book.setGenre(genre);
        book.setVolume(1);
        book.setBookAbstract("BookAbstract");
        book.setLanguage("English");
        book.setCount(4);
        book.setEditionYear("2015");
        book.setPages(100);
        book.setCountryOfEdition("Armenia");

        return book;
    }

    private Genre getRandomGenre(){
        Genre genre = new Genre();
        genre.setGenre("education" + uuid());
        return genre;
    }

/**    private PickBook getRandomPickBook(){
        PickBook pickBook = new PickBook();
    }

    private Pending getRandomPending(){}
 */

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
