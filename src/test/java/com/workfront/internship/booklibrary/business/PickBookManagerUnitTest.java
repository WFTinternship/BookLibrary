package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.*;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by ${Sona} on 8/1/2016.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = ManagerTestConfig.class)
public class PickBookManagerUnitTest {
//    @Autowired
    private PickBookManager pickBookManager;


    private PickBook testPickBook;

    private Genre testGenre;
    private Book testBook;
    private User testUser;

    private PickBookDAO pickBookDAO;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        pickBookManager = new PickBookManagerImpl();

        pickBookDAO = Mockito.mock(PickBookDAOImpl.class);
        Whitebox.setInternalState(pickBookManager, "pickBookDAO", pickBookDAO);

        testGenre = getRandomGenre();
        testBook = getRandomBook(testGenre);

        testUser = getRandomUser();

        testPickBook = getRandomPickBook(testBook, testUser);
    }

    @After
    public void tearDown(){
        testPickBook = null;
        testUser = null;
        testBook = null;
        testGenre = null;
        pickBookDAO = null;
    }

    @Test
    public void add_nullPickBook() {
        int id;

        //test
        id = pickBookManager.add(null);

        assertEquals("Wrong id for null pickBook", 0, id);
    }

    @Test
    public void add_nonNullPickBook() {
        int id;
        testPickBook.setId(5);

        //test
        id = pickBookManager.add(testPickBook);

        assertTrue("pickBook is not added", id > 0);
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void getPickBookByID_idLessThan1(){
//        testPickBook.setId(0);
//        pickBookDAO.add(testPickBook);
//        int id = testPickBook.getId();
//
//        //test
//        pickBookManager.getPickBookByID(id);
//    }

    @Test
    public void getPickBookByID_nullPickBook(){
        when(pickBookDAO.getPickedBookByID(5)).thenReturn(null);
        PickBook pickBook;

        //test method
        pickBook = pickBookManager.getPickBookByID(5);
        assertNull(pickBook);
    }

    @Test
    public void getPickBookByID_nonNullPickBook(){
        testPickBook.setId(5);
        when(pickBookDAO.getPickedBookByID(5)).thenReturn(testPickBook);

        PickBook pickBook;
        //test
        pickBook = pickBookManager.getPickBookByID(testPickBook.getId());

        assertEquals("Null pickBook returned", pickBook, testPickBook);
    }

    @Test
    public void viewAllPickedBooks_nullList(){
        when(pickBookDAO.getAllPickedBooks()).thenReturn(null);

        //test
        List<PickBook> pickBookList = pickBookManager.viewAllPickedBooks();
        assertEquals("pickBookList is not null", null, pickBookList);
    }

    @Test
    public void viewAllPickedBooks_nonNullList(){
        //test
        pickBookManager.viewAllPickedBooks();

        Mockito.verify(pickBookDAO, Mockito.atLeast(1)).getAllPickedBooks();
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewAllPickedBooksByUser_idLessThan1(){
        //test
        pickBookManager.viewAllPickedBooksByUser(0);
    }

    @Test
    public void viewAllPickedBooksByUser_nullList(){
        when(pickBookDAO.getAllPickedBooksByUserId(5)).thenReturn(null);

        //test
        List<PickBook> pickBookList = pickBookManager.viewAllPickedBooksByUser(5);
        assertEquals("pickBookList is not null", null, pickBookList);
    }

    @Test
    public void viewAllPickedBooksByUser_nonNullList(){
        List<PickBook> pickBookList = new ArrayList<>();
        pickBookList.add(testPickBook);
        when(pickBookDAO.getAllPickedBooksByUserId(5)).thenReturn(pickBookList);

        //test
        pickBookManager.viewAllPickedBooksByUser(5);
    }

    @Test
    public void update_nullPickBook() {
        PickBook pickBook;
        //test
        pickBook = pickBookManager.update(null);

        assertEquals("pickBook is not null", null, pickBook);
    }

    @Test
    public void update_valid_nonNullPickBook(){
        DateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        Date todate1 = cal.getTime();
        String fromdate = dateFormat.format(todate1);
        testPickBook.setPickingDate(Timestamp.valueOf(fromdate));

        PickBook pickBook;
        pickBook = pickBookManager.update(testPickBook);

        assertEquals(testPickBook.getPickingDate(), pickBook.getPickingDate());
    }

    @Test
    public void update_nonNull_invalidPickBook() {
        testPickBook.setReturnDate(null);

        //test
        pickBookManager.update(testPickBook);

        //test
        Mockito.verify(pickBookDAO, Mockito.never()).updatePickedBook(testPickBook);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_nullPickBook(){
        boolean b;
        //test
        b = pickBookManager.delete(0);

        assertEquals("pickBook is not null", true, b);
    }

    @Test
    public void delete_successOn_nonNullPickBook() {
        testPickBook.setId(2);
        boolean b;

        //Test
        b = pickBookManager.delete(testPickBook.getId());

        assertEquals("null pickBook", true, b);
    }

    @Test
    public void delete_cannotDeleteNonNullPickBook() {
        testPickBook.setId(3);
        PickBook pickBook = TestUtil.getRandomPickBook(testBook, testUser);
        when(pickBookDAO.getPickedBookByID(anyInt())).thenReturn(pickBook);

        boolean b;
        //test
        b = pickBookManager.delete(testPickBook.getId());

        assertEquals(false, b);
    }
}
