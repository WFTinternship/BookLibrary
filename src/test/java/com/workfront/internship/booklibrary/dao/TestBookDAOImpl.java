package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;

import com.workfront.internship.booklibrary.spring.DevelopmentConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DevelopmentConfig.class)
@ActiveProfiles("Development")
public class TestBookDAOImpl {

    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private GenreDAO genreDAO;
    @Autowired
    private AuthorDAO authorDAO;

//    private List<Author> authorList;
    private List<Integer> authorsIdList = new ArrayList<>();

    private Book expectedBook = null;
    private Genre expectedGenre = null;
    private Author expectedAuthor = null;

//    LegacyDataSource dataSource = LegacyDataSource.getInstance();

    @Before
    public void setup() throws Exception {
//        authorsIdList = init();
        expectedGenre = getRandomGenre();
        genreDAO.add(expectedGenre);

        expectedAuthor = getRandomAuthor();
        authorDAO.add(expectedAuthor);
        authorsIdList.add(expectedAuthor.getId());
    }

    @After
    public void tearDown() {
        bookDAO.deleteAll();
        genreDAO.deleteAll();
        authorDAO.deleteAllAuthors();
    }

//    private List<Integer> init() throws Exception {
//        List<Integer> authorsIdList = new ArrayList<>();
//        for(int i = 0; i < authorList.size(); i++){
//            authorsIdList.add(authorList.get(i).getId());
//        }
//        return authorsIdList;
////        bookDAO = new BookDAOImpl();
////        genreDAO = new GenreDAOImpl();
//    }

    // region <TEST CASES>

    @Test
    public void add() {
        expectedBook = getRandomBook(expectedGenre);

        //Test method add()
        int bookId = bookDAO.add(expectedBook, authorsIdList);
        assertNotNull(expectedGenre);
        assertNotNull(expectedBook);

        Book actualBook = bookDAO.getBookByID(bookId);
        checkAssertions(expectedBook, actualBook);
    }

    @Test
    public void addAuthorToBook() throws Exception {
        Author author = getRandomAuthor();
        authorDAO.add(author);
        expectedBook = getRandomBook(expectedGenre);
        bookDAO.add(expectedBook, authorsIdList);

        //test
        bookDAO.addAuthorToBook(expectedBook.getId(), author.getId());
        assertNotNull(expectedBook);
        assertNotNull(author);

        authorDAO.deleteAllAuthors();
    }

    @Test
    public void getBookByID (){
        expectedBook = getRandomBook(expectedGenre);
        int bookId = bookDAO.add(expectedBook, authorsIdList);

        //Test method getBookByID()
        Book actualBook = bookDAO.getBookByID(bookId);

        checkAssertions(expectedBook, actualBook);
    }

    @Test
    public void getBookByTitle (){
        expectedBook = getRandomBook(expectedGenre);
        bookDAO.add(expectedBook, authorsIdList);

        //TmpTest method getBookByTitle()
        Book actualBook = bookDAO.getBookByTitle(expectedBook.getTitle());

        checkAssertions(expectedBook, actualBook);
    }

    @Test
    public void getAllBooks(){
        bookDAO.deleteAll();

        List<Book> expectedBookList = new ArrayList<>();
        List<Book> actualBookList = new ArrayList<>();

        int bookCount = 2;
        for(int i = 0; i < bookCount; i++){
            Book book = getRandomBook(expectedGenre);
            bookDAO.add(book, authorsIdList);
            expectedBookList.add(book);
        }

        //Test method getAllBooks()
        actualBookList = bookDAO.getAllBooks();

        assertEquals(expectedBookList.size(), actualBookList.size());
        for(int i = 0; i < bookCount; i++){
            checkAssertions(expectedBookList.get(i), actualBookList.get(i));
        }

        expectedBookList.clear();
        actualBookList.clear();
    }

    @Test
    public void updateBook(){
        expectedBook = getRandomBook(expectedGenre);
        int bookId = bookDAO.add(expectedBook, authorsIdList);

        expectedBook.setCountryOfEdition("US");
        expectedBook.setVolume(2);

        //Test method updateBook()
        bookDAO.updateBook(expectedBook);

        Book actualBook = bookDAO.getBookByID(bookId);
        checkAssertions(expectedBook, actualBook);
    }

    @Test
    public void deleteBook(){
        expectedBook = getRandomBook(expectedGenre);
        int id = bookDAO.add(expectedBook, authorsIdList);

        //Test method deleteBook()
        bookDAO.deleteBook(id);

        Book actualBook = bookDAO.getBookByID(id);
        assertNull(actualBook);
    }

    @Test
    public void deleteAll(){
        expectedBook = getRandomBook(expectedGenre);
        bookDAO.add(expectedBook, authorsIdList);

        //Test method deleteAll()
        bookDAO.deleteAll();

        assertTrue(bookDAO.getAllBooks().isEmpty());
    }

    @Test (expected = RuntimeException.class)
    public void add_duplicateEntry(){
        Book book = getRandomBook(expectedGenre);
        bookDAO.add(book, authorsIdList);
        Book duplicateBook = getRandomBook(expectedGenre);
        duplicateBook.setTitle(book.getTitle());
        bookDAO.add(duplicateBook, authorsIdList);
    }

    @Test(expected = RuntimeException.class)
    public void update_duplicateEntry() {
        Book book = getRandomBook(expectedGenre);
        Book duplicateBook = book;
        bookDAO.add(book, authorsIdList);

        duplicateBook.setTitle("NewTitle");
        bookDAO.add(duplicateBook, authorsIdList);

        duplicateBook.setTitle(book.getTitle());
        bookDAO.add(duplicateBook, authorsIdList);
    }


    private void checkAssertions(Book expectedBook, Book actualBook){
        assertEquals(expectedBook.getISBN(), actualBook.getISBN());
        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getGenre(), actualBook.getGenre());
        assertEquals(expectedBook.getVolume(), actualBook.getVolume());
        assertEquals(expectedBook.getBookAbstract(), actualBook.getBookAbstract());
        assertEquals(expectedBook.getLanguage(), actualBook.getLanguage());
        assertEquals(expectedBook.getCount(), actualBook.getCount());
        assertEquals(expectedBook.getEditionYear(), actualBook.getEditionYear());
        assertEquals(expectedBook.getPages(), actualBook.getPages());
        assertEquals(expectedBook.getCountryOfEdition(), actualBook.getCountryOfEdition());
    }

    // endregion
}
