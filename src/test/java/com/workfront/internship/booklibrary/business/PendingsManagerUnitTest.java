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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
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

public class PendingsManagerUnitTest {

    private PendingsManager pendingsManager;

    private Pending testPending;

    private Genre testGenre;
    private Book testBook;
    private User testUser;

    private PendingDAO pendingDAO;


    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        pendingsManager = new PendingsManagerImpl();

        pendingDAO = Mockito.mock(PendingDAOImpl.class);
        Whitebox.setInternalState(pendingsManager, "pendingDAO", pendingDAO);

        testGenre = getRandomGenre();
        testBook = getRandomBook(testGenre);

        testUser = getRandomUser();

        testPending = getRandomPending(testUser, testBook);
    }

    @After
    public void tearDown(){
        testPending = null;
        testUser = null;
        testBook = null;
        testGenre = null;
        pendingDAO = null;
    }

    @Test
    public void add_nullPending() {
        int id;

        //test
        id = pendingsManager.add(null);

        assertEquals("Wrong id for null pending", 0, id);
    }

    @Test
    public void add_nonNullPending() {
        int id;
        testPending.setId(5);

        //test
        id = pendingsManager.add(testPending);

        assertTrue("pending is not added", id > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPendingByID_idLessThan1(){
        testPending.setId(0);
        pendingDAO.add(testPending);
        int id = testPending.getId();

        //test
        pendingsManager.getPendingByID(id);
    }

    @Test
    public void getPendingByID_nullPending(){
        when(pendingDAO.getPendingByID(5)).thenReturn(null);
        Pending pending;

        //test method
        pending = pendingsManager.getPendingByID(5);
        assertNull(pending);
    }

    @Test
    public void getPendingByID_nonNullPending(){
        testPending.setId(5);
        when(pendingDAO.getPendingByID(5)).thenReturn(testPending);

        Pending pending;
        //test
        pending = pendingsManager.getPendingByID(testPending.getId());

        assertEquals("Null pending returned", pending, testPending);
    }

    ////////////////////////////////////////// viewAllPendingByBook,  viewAllPendingByUser

    @Test(expected = IllegalArgumentException.class)
    public void viewAllPendingByBook_idLessThan1() {
        //test
        pendingsManager.viewAllPendingByBook(0);
    }

    @Test
    public void viewAllPendingByBook_nullList(){
        when(pendingDAO.getAllPendingsByBookID(5)).thenReturn(null);

        //test
        List<Pending> pendingList = pendingsManager.viewAllPendingByBook(5);
        assertEquals("pendingList is not null", null, pendingList);
    }

    @Test
    public void viewAllPendingByBook_nonNullUser(){
        List<Pending> pendingList = new ArrayList<>();
        pendingList.add(testPending);
        when(pendingDAO.getAllPendingsByBookID(5)).thenReturn(pendingList);

        //test
        pendingsManager.viewAllPendingByBook(5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewAllPendingByUser_idLessThan1(){
        //test
        pendingsManager.viewAllPendingByUser(0);
    }

    @Test
    public void viewAllPendingByUser_nullList(){
        when(pendingDAO.getAllPendingsByUserID(5)).thenReturn(null);

        //test
        List<Pending> pendingList = pendingsManager.viewAllPendingByUser(5);
        assertEquals("pendingList is not null", null, pendingList);
    }

    @Test
    public void viewAllPendingByUser_nonNullList(){
        List<Pending> pendingList = new ArrayList<>();
        pendingList.add(testPending);
        when(pendingDAO.getAllPendingsByUserID(5)).thenReturn(pendingList);

        //test
        pendingsManager.viewAllPendingByUser(5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_nullPending(){
        boolean b;
        //test
        b = pendingsManager.delete(0);

        assertEquals("pending is not null", true, b);
    }

    @Test
    public void delete_successOn_nonNullPending() {
        testPending.setId(2);
        boolean b;

        //Test
        b = pendingsManager.delete(testPending.getId());

        assertEquals("null pending", true, b);
    }

    @Test
    public void delete_cannotDeleteNonNullPending() {
        testPending.setId(3);
        Pending pending = TestUtil.getRandomPending(testUser, testBook);
        when(pendingDAO.getPendingByID(anyInt())).thenReturn(pending);

        boolean b;
        //test
        b = pendingsManager.delete(testPending.getId());

        assertEquals(false, b);
    }
}
