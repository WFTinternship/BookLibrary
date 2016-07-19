package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;
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
import static org.mockito.Mockito.when;

/**
 * Unit test for BookDAO class
 */
public class AuthorDaoUnitTest {
    DataSource dataSource;

    AuthorDAO authorDAO;

    @SuppressWarnings("unchecked")
    @Before
    public void beforeTest() throws Exception {
        dataSource = Mockito.mock(DataSource.class);

        Connection connection = Mockito.mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenThrow(SQLException.class);

        authorDAO = new AuthorDAOImpl(dataSource);
    }

    @After
    public void afterTest() {

    }

    // region <TEST CASES>

    @Test(expected = RuntimeException.class)
    public void add_dbError() {
        authorDAO.add(new Author());
    }

    @Test(expected = RuntimeException.class)
    public void getBookByID_dbError() {
        authorDAO.getAuthorByID(5);
    }

    @Test(expected = RuntimeException.class)
    public void getAuthorByName_dbError(){
        authorDAO.getAuthorByName("MyName");
    }

    @Test(expected = RuntimeException.class)
    public void getAllAuthors_dbError(){
        authorDAO.getAllAuthors();
    }

    @Test(expected = RuntimeException.class)
    public void deleteAuthor_dbError(){
        authorDAO.deleteAuthor(5);
    }

    @Test(expected = RuntimeException.class)
    public void deleteAllAuthors_dbError(){
        authorDAO.deleteAllAuthors();
    }


    // endregion
}
