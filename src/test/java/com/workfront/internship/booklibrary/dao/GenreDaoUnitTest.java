package com.workfront.internship.booklibrary.dao;


import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
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

public class GenreDaoUnitTest {
    DataSource dataSource;
    GenreDAO genreDAO;

    @SuppressWarnings("unchecked")
    @Before
    public void beforeTest() throws Exception {
        dataSource = Mockito.mock(DataSource.class);

        Connection connection = Mockito.mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenThrow(SQLException.class);

        genreDAO = new GenreDAOImpl(dataSource);
    }

    @After
    public void afterTest() {

    }

    @Test(expected = RuntimeException.class)
    public void add_dbError() {
        genreDAO.add(new Genre());
    }

    @Test(expected = RuntimeException.class)
    public void getGenreByID_dbError() {
        genreDAO.getGenreByID(5);
    }

    @Test(expected = RuntimeException.class)
    public void getGenreByName_dbError(){
        genreDAO.getGenreByGenreName("Java");
    }

    @Test(expected = RuntimeException.class)
    public void getAllGenres_dbError(){
        genreDAO.getAllGenres();
    }

    @Test(expected = RuntimeException.class)
    public void deleteGenre_dbError(){
        genreDAO.deleteGenre(3);
    }

    @Test(expected = RuntimeException.class)
    public void deleteAll_dbError(){
        genreDAO.deleteAll();
    }
}
