package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for BookDAO class
 */
public class BookDaoUnitTest {
    LegacyDataSource dataSource;
    BookDAO realBookDAO;

    BookDAO bookDAO;
    GenreDAO genreDAO;
    List<Integer> authorsIdList = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Before
    public void beforeTest() throws Exception {
        dataSource = Mockito.mock(LegacyDataSource.class);

        Connection connection = Mockito.mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenThrow(SQLException.class);

        realBookDAO = new BookDAOImpl();
        bookDAO = new BookDAOImpl();
        Whitebox.setInternalState(bookDAO, "dataSource", dataSource);
        bookDAO = new BookDAOImpl();
        genreDAO = new GenreDAOImpl();
    }

    @After
    public void afterTest() {

    }

    // region <TEST CASES>

    @Test(expected = RuntimeException.class)
    public void add_dbError() {
        bookDAO.add((new Book()), authorsIdList);
    }

    @Test(expected = RuntimeException.class)
    public void getBookByID_dbError() {
        bookDAO.getBookByID(5);
    }

    @Test(expected = RuntimeException.class)
    public void getBookByTitle_dbError(){
        bookDAO.getBookByTitle("Java");
    }

    @Test(expected = RuntimeException.class)
    public void getAllBooks_dbError(){
        bookDAO.getAllBooks();
    }

    @Test(expected = RuntimeException.class)
    public void updateBook_dbErrorID0(){
        bookDAO.updateBook(new Book());
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        verify(((BookDAOImpl)bookDAO)).closeConnection(Mockito.eq(preparedStatement), Mockito.eq(connection));
    }

    @Test(expected = RuntimeException.class)
    public void updateBook_dbErrorID1(){
        Book realBook = realBookDAO.getBookByID(212);
        bookDAO.updateBook(realBook);
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        verify(((BookDAOImpl)bookDAO)).closeConnection(Mockito.eq(preparedStatement), Mockito.eq(connection));
    }

    @Test(expected = RuntimeException.class)
    public void deleteBook_dbError(){
        bookDAO.deleteBook(3);
    }

    @Test(expected = RuntimeException.class)
    public void deleteAll_dbError(){
        bookDAO.deleteAll();
    }

    // endregion

}
