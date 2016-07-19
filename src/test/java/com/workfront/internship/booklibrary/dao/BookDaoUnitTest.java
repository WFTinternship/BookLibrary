package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for BookDAO class
 */
public class BookDaoUnitTest {
    DataSource dataSource;
    BookDAO realBookDAO;

    BookDAO bookDAO;
    GenreDAO genreDAO;

    @SuppressWarnings("unchecked")
    @Before
    public void beforeTest() throws Exception {
        dataSource = Mockito.mock(DataSource.class);

        Connection connection = Mockito.mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenThrow(SQLException.class);

        realBookDAO = new BookDAOImpl(DataSource.getInstance());
        bookDAO = new BookDAOImpl(dataSource);
        genreDAO = new GenreDAOImpl(dataSource);
    }

    @After
    public void afterTest() {

    }

    // region <TEST CASES>

    @Test(expected = RuntimeException.class)
    public void add_dbError() {
        bookDAO.add(new Book());
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
