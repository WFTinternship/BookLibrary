package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.common.Media;
import com.workfront.internship.booklibrary.common.MediaType;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.when;

/**
 * Created by Sona Mikayelyan on 8/1/2016.
 */
public class BookManagerUnitTest {
    DataSource dataSource;
    private Book testBook;
    private Genre testGenre;

    private BookDAO bookDAO;
    private BookManager bookManager;

    private GenreDAO genreDAO;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        bookManager = new BookManagerImpl(dataSource);

        bookDAO = Mockito.mock(BookDAOImpl.class);
        Whitebox.setInternalState(bookManager, "bookDAO", bookDAO);

        genreDAO = Mockito.mock(GenreDAOImpl.class);
//        Whitebox.setInternalState(genreDAO);

        testGenre = getRandomGenre();
        testBook = getRandomBook(testGenre);
    }

    @After
    public void tearDown(){
        testBook = null;
        testGenre = null;
        bookDAO = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void findBookByID_idLessThan1(){
        testBook.setId(0);
        bookDAO.add(testBook);
        int id = testBook.getId();

        //test
        bookManager.findBookByID(id);
    }

    @Test
    public void findBookByID_nullBook(){
        when(bookDAO.getBookByID(5)).thenReturn(null);
        Book book;

        //test method
        book = bookManager.findBookByID(5);
        assertNull(book);
    }

//    @Test
//    public void findBookByID_nonNullBook(){
//        testBook.setId(5);
//        when(bookDAO.getBookByID(5)).thenReturn(testBook);
//
//        Book book;
//        //test
//        book = bookManager.findBookByID(testBook.getId());
//
//        assertEquals("Null media returned", book, testBook);
//    }

}