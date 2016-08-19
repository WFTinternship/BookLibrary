package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.*;
import static com.workfront.internship.booklibrary.dao.TestUtil.*;


public class TestPendingDAOImpl {
    private PendingDAO pendingDAO;
    private UserDAO userDAO;
    private BookDAO bookDAO;
    private GenreDAO genreDAO;

    private Pending expectedPending = null;
    private User expectedUser = null;
    private Book expectedBook = null;

    private LegacyDataSource dataSource = LegacyDataSource.getInstance();

    @Before
    public void setup() throws Exception {
        init();
        expectedUser = getRandomUser();
        userDAO.add(expectedUser);

        Genre expectedGenre = getRandomGenre();
        genreDAO.add(expectedGenre);

        expectedBook = getRandomBook(expectedGenre);
        bookDAO.add(expectedBook);

    }

    @After
    public void tearDown() {
        pendingDAO.deleteAllPendings();
        bookDAO.deleteAll();
        genreDAO.deleteAll();
        userDAO.deleteAll();
    }

    private void init() throws Exception {
        Whitebox.setInternalState(pendingDAO, "dataSource", dataSource);
        pendingDAO = new PendingDAOImpl();
        userDAO = new UserDAOImpl();
        bookDAO = new BookDAOImpl();
        genreDAO = new GenreDAOImpl();
    }


    @Test
    public void add() {

        expectedPending = getRandomPending(expectedUser, expectedBook);

        //Test method add()
        int id = pendingDAO.add(expectedPending);


        assertNotNull(expectedPending);

        Pending actualPending = pendingDAO.getPendingByID(id);
        checkAssertions(expectedPending, actualPending);

    }

    @Test
    public void getPendingByID(){
        expectedPending = getRandomPending(expectedUser, expectedBook);
        int id = pendingDAO.add(expectedPending);
        assertNotNull(expectedPending);

        //Test method getPendingByID()
        Pending actualPending = pendingDAO.getPendingByID(id);
        checkAssertions(expectedPending, actualPending);
    }

    @Test
    public void getAllPendingsByBookID(){
        pendingDAO.deleteAllPendings();
        List<Pending> expectedPendingList = new ArrayList<>();
        List<Pending> actualPendingList = new ArrayList<>();
        int count = 2;

        for(int i = 0; i < count; i++) {
            expectedPending = getRandomPending(expectedUser, expectedBook);
            pendingDAO.add(expectedPending);
            assertNotNull(expectedPending);
            expectedPendingList.add(expectedPending);
        }

        //Test method getAllPBookID()
        actualPendingList = pendingDAO.getAllPendingsByBookID(expectedBook.getId());

        for(int i = 0; i < count; i++){
            checkAssertions(expectedPendingList.get(i), actualPendingList.get(i));
        }

        expectedPendingList.clear();
        actualPendingList.clear();
    }

    @Test
    public void getAllPendingsByUserID() {
        pendingDAO.deleteAllPendings();
        List<Pending> expectedPendingList = new ArrayList<>();
        List<Pending> actualPendingList = new ArrayList<>();
        int count = 2;

        for(int i = 0; i < count; i++) {
            expectedPending = getRandomPending(expectedUser, expectedBook);
            pendingDAO.add(expectedPending);
            assertNotNull(expectedPending);
            expectedPendingList.add(expectedPending);
        }

        //Test method getAllPBookID()
        actualPendingList = pendingDAO.getAllPendingsByUserID(expectedUser.getId());

        for(int i = 0; i < count; i++){
            checkAssertions(expectedPendingList.get(i), actualPendingList.get(i));
        }

        expectedPendingList.clear();
        actualPendingList.clear();
    }


    @Test
    public void deletePending(){
        expectedPending = getRandomPending(expectedUser, expectedBook);
        int id = pendingDAO.add(expectedPending);

        //Test method deletePickedBook()
        pendingDAO.deletePending(id);

        Pending actualPending = pendingDAO.getPendingByID(id);
        assertNull(actualPending);
    }

    @Test
    public void deleteAllPendings(){
        pendingDAO.deleteAllPendings();
        expectedPending = getRandomPending(expectedUser, expectedBook);
        int id = pendingDAO.add(expectedPending);

        //Test method deleteAllPickedBooks()
        pendingDAO.deleteAllPendings();

        Pending actualPending = pendingDAO.getPendingByID(id);
        assertNull(actualPending);
    }


    private void checkAssertions(Pending expectedPending, Pending actualPending){
        assertEquals(expectedPending.getId(), actualPending.getId());
        assertEquals(expectedPending.getUser(), actualPending.getUser());
        assertEquals(expectedPending.getBook(), actualPending.getBook());
        assertEquals(expectedPending.getPendingDate(), actualPending.getPendingDate());
    }
}
