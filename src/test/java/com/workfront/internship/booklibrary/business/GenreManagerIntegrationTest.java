package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomGenre;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Sona Mikayelyan on 7/31/2016.
 */
public class GenreManagerIntegrationTest {
    private static GenreManager genreManager;
    private Genre testGenre;
    DataSource dataSource = DataSource.getInstance();
    GenreDAO genreDAO;

    @Before
    public void setup() throws Exception {
        genreManager = new GenreManagerImpl(dataSource);
        testGenre = getRandomGenre();
        genreDAO = new GenreDAOImpl(dataSource);
    }

    @After
    public void tearDown(){
        testGenre = null;
        genreDAO.deleteAll();
    }

    @Test
    public void add(){
        //test
        genreManager.add(testGenre);

        Genre actualGenre = genreManager.findGenreByID(testGenre.getId());
        assertNotNull(actualGenre);
    }
}
