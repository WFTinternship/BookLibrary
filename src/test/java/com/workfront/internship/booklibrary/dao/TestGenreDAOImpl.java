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
    public void addGenre(){
        expectedGenre = getRandomGenre();

        //Test method add()
        int id = genreDAO.add(expectedGenre);

        assertTrue(id > 0);
        Genre actualGenre = genreDAO.getGenreByID(id);
        checkAssertions(expectedGenre, actualGenre);
    }

    @Test
    public void getGenreByID(){
        expectedGenre = getRandomGenre();
        int id = genreDAO.add(expectedGenre);

        //Test method getGenreByID()
        Genre actualGenre = genreDAO.getGenreByID(id);

        checkAssertions(expectedGenre, actualGenre);
    }

    @Test
    public void getGenreByGenreName(){
        expectedGenre = getRandomGenre();
        genreDAO.add(expectedGenre);

        //Test method getGenreByGenreName()
        Genre actualGenre = genreDAO.getGenreByGenreName(expectedGenre.getGenre());

        checkAssertions(expectedGenre, actualGenre);
    }

    @Test
    public void getAllGenres(){
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
    public void updateGenre(){
        expectedGenre = getRandomGenre();
        int id = genreDAO.add(expectedGenre);

        expectedGenre.setGenre("OOPS!");

        //Test method updateGenre()
        genreDAO.updateGenre(expectedGenre);

        Genre actualGenre = genreDAO.getGenreByID(id);
        checkAssertions(expectedGenre, actualGenre);
    }

    @Test
    public void deleteGenreByID(){
        genreDAO.deleteAll();

        expectedGenre = getRandomGenre();
        int id = genreDAO.add(expectedGenre);

        //Test method deleteGenre()
        genreDAO.deleteGenre(id);

        Genre actualGenre = genreDAO.getGenreByID(id);
        assertNull(actualGenre);
    }

    @Test
    public void deleteAllGenres(){
        expectedGenre = getRandomGenre();
        genreDAO.add(expectedGenre);

        //Test method deleteAll()
        genreDAO.deleteAll();

        assertTrue(genreDAO.getAllGenres().isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void addDuplcateEntry(){
        Genre genre = getRandomGenre();
        genreDAO.add(genre);
        Genre duplicateGenre = getRandomGenre();
        duplicateGenre.setGenre(genre.getGenre());
        genreDAO.add(duplicateGenre);
    }

    @Test(expected = RuntimeException.class)
    public void update_duplicateEntry(){
        Genre genre = getRandomGenre();
        Genre duplicateGenre = genre;
        genreDAO.add(genre);
        duplicateGenre.setGenre("NewGenre");
        genreDAO.add(duplicateGenre);

        duplicateGenre.setGenre(genre.getGenre());
        genreDAO.add(duplicateGenre);
    }


    private void checkAssertions(Genre expectedGenre, Genre actualGenre){
        assertEquals(expectedGenre.getGenre(), actualGenre.getGenre());
    }

}
