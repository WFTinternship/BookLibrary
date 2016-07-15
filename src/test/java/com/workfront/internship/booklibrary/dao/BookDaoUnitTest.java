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
import static org.mockito.Mockito.when;

/**
 *
 */
public class BookDaoUnitTest {

    DataSource dataSource;

    BookDAO bookDAO;
    GenreDAO genreDAO;

    @SuppressWarnings("unchecked")
    @Before
    public void beforeTest() throws Exception {
        dataSource = Mockito.mock(DataSource.class);

        Connection connection = Mockito.mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenThrow(SQLException.class);

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

    @Test
    public void getBookByID_dbError() {

    }

    // endregion

}
