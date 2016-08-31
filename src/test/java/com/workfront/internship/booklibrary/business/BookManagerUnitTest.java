package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by Sona Mikayelyan on 8/1/2016.
 */

public class BookManagerUnitTest {

    private BookManager bookManager;

    private Book testBook;
    private Genre testGenre;
    private List<Author> authorList;

    private static BookDAO bookDAO;
    private GenreDAO genreDAO;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        bookManager = new BookManagerImpl();

        bookDAO = Mockito.mock(BookDAOImpl.class);
        Whitebox.setInternalState(bookManager, "bookDAO", bookDAO);

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
        bookDAO.add(testBook, authorList);
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

    @Test
    public void findBookByID_nonNullBook(){
        testBook.setId(5);
        testBook.setCount(1);
        when(bookDAO.getBookByID(5)).thenReturn(testBook);

        Book book;

        //test
        book = bookManager.findBookByID(testBook.getId());

        assertEquals("Null media returned", book, testBook);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findBookByTitle_idLessThan1(){
        testBook.setTitle(null);
        bookDAO.add(testBook, authorList);
        String title = testBook.getTitle();

        //test
        bookManager.findBookByTitle(title);
    }

    @Test
    public void findBookByTitle_nullBook(){
        when(bookDAO.getBookByTitle("title")).thenReturn(null);
        Book book;

        //test method
        book = bookManager.findBookByTitle("title");
        assertNull(book);
    }

    @Test
    public void findBookByTitle_nonNullBook(){
        testBook.setTitle("Java");
        testBook.setCount(1);
        when(bookDAO.getBookByTitle("Java")).thenReturn(testBook);

        Book book;

        //test
        book = bookManager.findBookByTitle(testBook.getTitle());

        assertEquals("Null media returned", book, testBook);
    }

    @Test
    public void viewAll_nullList(){
        when(bookDAO.getAllBooks()).thenReturn(null);

        //test
        List<Book> bookList = bookManager.viewAll();
        assertEquals("bookList is not null", null, bookList);
    }

    @Test
    public void viewAll_nonNullList(){
        //test
        bookManager.viewAll();

        Mockito.verify(bookDAO, Mockito.atLeast(1)).getAllBooks();
    }

    @Test
    public void update_nullBook() {
        Book book;
        //test
        book = bookManager.update(null);

        assertEquals("book is not null", null, book);
    }

    @Test
    public void update_valid_nonNullBook(){
        testBook.setCount(1);
        testBook.setTitle("Title");
        Book book;
        book = bookManager.update(testBook);

        assertEquals(testBook.getTitle(), book.getTitle());
    }

    @Test
    public void update_nonNull_invalidMedia() {
        testBook.setTitle(null);

        //test
        bookManager.update(testBook);

        //test
        Mockito.verify(bookDAO, Mockito.never()).updateBook(testBook);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_nullBook(){
        boolean b;
        //test
        b = bookManager.delete(0);

        assertEquals("book is not null", true, b);
    }

    @Test
    public void delete_successOn_nonNullBook() {
        testBook.setId(2);
        boolean b;

        //Test
        b = bookManager.delete(testBook.getId());

        assertEquals("null book", true, b);
    }

    @Test
    public void delete_cannotDeleteNonNullBook() {
        testBook.setId(3);
        Book book = TestUtil.getRandomBook(testGenre);
        when(bookDAO.getBookByID(anyInt())).thenReturn(book);

        boolean b;
        //test
        b = bookManager.delete(testBook.getId());

        assertEquals(false, b);
    }

}