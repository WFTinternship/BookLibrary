package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.*;
import com.workfront.internship.booklibrary.spring.DevelopmentConfig;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DevelopmentConfig.class)
@ActiveProfiles("Development")
public class TestPickBookDAOImpl {
    @Autowired
    private PickBookDAO pickBookDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private GenreDAO genreDAO;
    @Autowired
    private AuthorDAO authorDAO;

    private PickBook expectedPickBook = null;
    private User expectedUser = null;
    private Book expectedBook = null;
    private Author expectedAuthor = null;

    private List<Author> authorList = null;

//    private LegacyDataSource dataSource = LegacyDataSource.getInstance();

    @Before
    public void setup() throws Exception {
        init();
        expectedUser = getRandomUser();
        userDAO.add(expectedUser);

        Genre expectedGenre = getRandomGenre();
        genreDAO.add(expectedGenre);

        expectedBook = getRandomBook(expectedGenre);
        bookDAO.add(expectedBook, authorList);

    }

    @After
    public void tearDown() {
        pickBookDAO.deleteAllPickedBooks();
        bookDAO.deleteAll();
        genreDAO.deleteAll();
        userDAO.deleteAll();
    }

    private void init() throws Exception {
        authorList = new ArrayList<>();
        authorList.add(expectedAuthor);
//        Whitebox.setInternalState(pickBookDAO, "dataSource", dataSource);
//        pickBookDAO = new PickBookDAOImpl();
//        userDAO = new UserDAOImpl();
//        bookDAO = new BookDAOImpl();
//        genreDAO = new GenreDAOImpl();
    }


    @Test
    public void add(){
        expectedPickBook = getRandomPickBook(expectedBook, expectedUser);

        //Test method add()
        int id = pickBookDAO.add(expectedPickBook);


        assertNotNull(expectedPickBook);

        PickBook actualPickBookending = pickBookDAO.getPickedBookByID(id);
        checkAssertions(expectedPickBook, actualPickBookending);
    }

    @Test
    public void getPickedBookByID(){
        expectedPickBook = getRandomPickBook(expectedBook, expectedUser);
        int id = pickBookDAO.add(expectedPickBook);

        assertNotNull(expectedPickBook);

        //Test method getPickedBookByID()
        PickBook actualPickBookending = pickBookDAO.getPickedBookByID(id);
        checkAssertions(expectedPickBook, actualPickBookending);
    }

    @Test
    public void getAllPickedBooks(){
        pickBookDAO.deleteAllPickedBooks();
        List<PickBook> expectedPickedBookList = new ArrayList<>();
        List<PickBook> actualPickedBookList = new ArrayList<>();
        int count = 2;
        for(int i = 0; i < count; i++){
            PickBook pickBook = getRandomPickBook(expectedBook, expectedUser);
            pickBookDAO.add(pickBook);
            expectedPickedBookList.add(pickBook);
        }

        //Test method getAllPickedBooks()
        actualPickedBookList = pickBookDAO.getAllPickedBooks();

        TestCase.assertEquals(expectedPickedBookList.size(), actualPickedBookList.size());
        for(int i = 0; i < count; i++){
            checkAssertions(expectedPickedBookList.get(i), actualPickedBookList.get(i));
        }

        expectedPickedBookList.clear();
        actualPickedBookList.clear();
    }

    @Test
    public void getAllPickedBooksByUserId(){
        pickBookDAO.deleteAllPickedBooks();
        List<PickBook> expectedPickedBookList = new ArrayList<>();
        List<PickBook> actualPickedBookList = new ArrayList<>();
        int count = 2;

        for(int i = 0; i < count; i++) {
            expectedPickBook = getRandomPickBook(expectedBook, expectedUser);
            pickBookDAO.add(expectedPickBook);
            assertNotNull(expectedPickBook);
            expectedPickedBookList.add(expectedPickBook);
        }

        //Test method getAllPBookID()
        actualPickedBookList = pickBookDAO.getAllPickedBooksByUserId(expectedUser.getId());

        for(int i = 0; i < count; i++){
            checkAssertions(expectedPickedBookList.get(i), actualPickedBookList.get(i));
        }

        expectedPickedBookList.clear();
        actualPickedBookList.clear();
    }

       @Test
    public void updatePickedBook(){
        expectedPickBook = getRandomPickBook(expectedBook, expectedUser);
        int id = pickBookDAO.add(expectedPickBook);

        expectedPickBook.setReturnDate(Timestamp.valueOf("2016-07-20 01:02:03"));

        //Test method updatePickedBook()
        pickBookDAO.updatePickedBook(expectedPickBook);

        PickBook actualPickedBook = pickBookDAO.getPickedBookByID(id);
        checkAssertions(expectedPickBook, actualPickedBook);
    }


    @Test
    public void deletePickedBook(){
        expectedPickBook = getRandomPickBook(expectedBook, expectedUser);
        int id = pickBookDAO.add(expectedPickBook);

        //Test method deletePickedBook()
        pickBookDAO.deletePickedBook(id);

        PickBook actualPickedBook = pickBookDAO.getPickedBookByID(id);
        assertNull(actualPickedBook);
    }

    @Test
    public void deleteAllPickedBooks(){
        expectedPickBook = getRandomPickBook(expectedBook, expectedUser);
        pickBookDAO.add(expectedPickBook);

        //Test method deleteAllPickedBooks()
        pickBookDAO.deleteAllPickedBooks();

        assertTrue(pickBookDAO.getAllPickedBooks().isEmpty());
    }


    private void checkAssertions(PickBook expectedPickBook, PickBook actualPickBook){
        assertEquals(expectedPickBook.getId(), actualPickBook.getId());
        assertEquals(expectedPickBook.getUser(), actualPickBook.getUser());
        assertEquals(expectedPickBook.getBook(), actualPickBook.getBook());
        assertEquals(expectedPickBook.getPickingDate(), actualPickBook.getPickingDate());
        assertEquals(expectedPickBook.getReturnDate(), actualPickBook.getReturnDate());
    }

}
