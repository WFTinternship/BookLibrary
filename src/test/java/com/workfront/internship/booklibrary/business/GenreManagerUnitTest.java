package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by Sona Mikayelyan on 7/31/2016.
 */
public class GenreManagerUnitTest {
    LegacyDataSource dataSource;
    private Genre testGenre;

    private GenreDAO genreDAO;
    private GenreManager genreManager;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        genreManager = new GenreManagerImpl();

        genreDAO = Mockito.mock(GenreDAOImpl.class);
        Whitebox.setInternalState(genreManager, "genreDAO", genreDAO);

        testGenre = new Genre();
        testGenre = TestUtil.getRandomGenre();
    }

    @After
    public void tearDown(){
        testGenre = null;
        genreDAO = null;
    }

    @Test
    public void add_nullGenre() {
        int id;

        //test
        id = genreManager.add(null);

        assertEquals("Wrong id for null genre", 0, id);
    }

    @Test
    public void add_nonNullGenre() {
        int id;
        testGenre.setId(5);

        //test
        id = genreManager.add(testGenre);

        assertTrue("genre is not added", id > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findGenreByID_idLessThan1(){
        testGenre.setId(0);
        genreDAO.add(testGenre);
        int id = testGenre.getId();

        //test
        genreManager.findGenreByID(id);

    }

    @Test
    public void findGenreByID_nullGenre(){
        when(genreDAO.getGenreByID(5)).thenReturn(null);
        Genre genre;

        //test method
        genre = genreManager.findGenreByID(5);
        assertNull(genre);
    }

    @Test
    public void findGenreByID_nonNullGenre(){
        testGenre.setId(5);
        when(genreDAO.getGenreByID(5)).thenReturn(testGenre);

        Genre genre;
        //test
        genre = genreManager.findGenreByID(testGenre.getId());

        assertEquals("Null genre returned", genre, testGenre);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findGenreByGenreName_noGenreName(){
        testGenre.setGenre(null);
        String genreName = testGenre.getGenre();

        //test
        genreManager.findGenreByGenreName(genreName);
    }

    @Test
    public void findGenreByGenreName_nullGenre(){
        when(genreDAO.getGenreByGenreName("genreName")).thenReturn(null);
        Genre genre;

        //test method
        genre = genreManager.findGenreByGenreName("genreName");
        assertNull(genre);
    }

    @Test
    public void findGenreByGenreName_non_NullGenre(){
        testGenre.setGenre("genreName");
        when(genreDAO.getGenreByGenreName("genreName")).thenReturn(testGenre);

        Genre genre;
        //test
        genre = genreManager.findGenreByGenreName(testGenre.getGenre());

        assertEquals("Null user returned", genre, testGenre);
    }

    @Test
    public void viewAll_nullList(){
        when(genreDAO.getAllGenres()).thenReturn(null);

        //test
        List<Genre> genreList= genreManager.viewAll();
        assertEquals("genreList is not null", null, genreList);
    }

    @Test
    public void viewAll_nonNullList(){
        //test
        genreManager.viewAll();

        Mockito.verify(genreDAO, Mockito.atLeast(1)).getAllGenres();
    }

    @Test
    public void update_nullGenre() {
        Genre genre;
        //test
        genre = genreManager.update(null);

        assertEquals("genre is not null", null, genre);
    }

    @Test
    public void update_valid_nonNullGenre(){
        testGenre.setGenre("Java");
        Genre genre;
        genre = genreManager.update(testGenre);

        assertEquals(testGenre.getGenre(), genre.getGenre());
    }

    @Test
    public void update_nonNull_invalidGenre() {
        testGenre.setGenre(null);

        //test
        genreManager.update(testGenre);

        //test
        Mockito.verify(genreDAO, Mockito.never()).updateGenre(testGenre);

    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_nullGenre(){
        boolean b;
        //test
        b = genreManager.delete(0);

        assertEquals("genre is not null", true, b);
    }

    @Test
    public void delete_successOn_nonNullGenre() {
        testGenre.setId(2);
        boolean b;

        //Test
        b = genreManager.delete(testGenre.getId());

        assertEquals("null genre", true, b);
    }

    @Test
    public void delete_cannotDeleteNonNullGenre() {
        testGenre.setId(3);
        Genre genre = TestUtil.getRandomGenre();
        when(genreDAO.getGenreByID(anyInt())).thenReturn(genre);

        boolean b;
        //test
        b = genreManager.delete(testGenre.getId());

        assertEquals(false, b);
    }
}
