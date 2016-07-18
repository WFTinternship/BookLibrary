package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Genre;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomGenre;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

public class TestGenreDAOImpl {
    private GenreDAO genreDAO;
    private Genre expectedGenre = null;
    DataSource dataSource = DataSource.getInstance();

    @Before
    public void setup() throws Exception {
        init();
    }

    @After
    public void tearDown(){
        genreDAO.deleteAll();
    }

    private void init() throws Exception {
        genreDAO = new GenreDAOImpl(dataSource);
    }


    @Test
    public void add_genre(){
        expectedGenre = getRandomGenre();

        //Test method add()
        int id = genreDAO.add(expectedGenre);

        assertTrue(id > 0);
        Genre actualGenre = genreDAO.getGenreByID(id);
        checkAssertions(expectedGenre, actualGenre);
    }

    @Test
    public void get_genre_by_id(){
        expectedGenre = getRandomGenre();
        int id = genreDAO.add(expectedGenre);

        //Test method getGenreByID()
        Genre actualGenre = genreDAO.getGenreByID(id);

        checkAssertions(expectedGenre, actualGenre);
    }

    @Test
    public void get_genre_by_genre_name(){
        expectedGenre = getRandomGenre();
        genreDAO.add(expectedGenre);

        //Test method getGenreByGenreName()
        Genre actualGenre = genreDAO.getGenreByGenreName(expectedGenre.getGenre());

        checkAssertions(expectedGenre, actualGenre);
    }

    @Test
    public void get_all_genres(){
        genreDAO.deleteAll();

        List<Genre> expectedGenreList = new ArrayList<>();
        List<Genre> actualGenreList = new ArrayList<>();
        int genreCount = 2;

        for(int i = 0; i < genreCount; i++){
            Genre genre = getRandomGenre();
            genreDAO.add(genre);
            expectedGenreList.add(genre);
        }

        //Test method getAllGenres()
        actualGenreList = genreDAO.getAllGenres();

        assertEquals(expectedGenreList.size(), actualGenreList.size());
        for(int i = 0; i < genreCount; i++){
            checkAssertions(expectedGenreList.get(i), actualGenreList.get(i));
        }

        expectedGenreList.clear();
        actualGenreList.clear();

    }

    @Test
    public void update_genre(){
        expectedGenre = getRandomGenre();
        int id = genreDAO.add(expectedGenre);

        expectedGenre.setGenre("OOPS!");

        //Test method updateGenre()
        genreDAO.updateGenre(expectedGenre);

        Genre actualGenre = genreDAO.getGenreByID(id);
        checkAssertions(expectedGenre, actualGenre);
    }

    @Test
    public void delete_genre_by_id(){
        genreDAO.deleteAll();

        expectedGenre = getRandomGenre();
        int id = genreDAO.add(expectedGenre);

        //Test method deleteGenre()
        genreDAO.deleteGenre(id);

        Genre actualGenre = genreDAO.getGenreByID(id);
        assertNull(actualGenre);
    }

    @Test
    public void delete_all_genres(){
        expectedGenre = getRandomGenre();
        genreDAO.add(expectedGenre);

        //Test method deleteAll()
        genreDAO.deleteAll();

        assertTrue(genreDAO.getAllGenres().isEmpty());
    }




    private void checkAssertions(Genre expectedGenre, Genre actualGenre){
        assertEquals(expectedGenre.getGenre(), actualGenre.getGenre());
    }

}
