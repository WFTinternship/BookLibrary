package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


public class TestBookDAOImpl {

    private BookDAO bookDAO;
    private GenreDAO genreDAO;

    private Book expectedBook = null;
    private Genre expectedGenre = null;

    DataSource dataSource = DataSource.getInstance();

    @Before
    public void setup() throws Exception {
        init();

        expectedGenre = getRandomGenre();
        genreDAO.add(expectedGenre);

        expectedBook = getRandomBook(expectedGenre);
        bookDAO.add(expectedBook);
    }

    @After
    public void tearDown() {
        //bookDAO.deleteAll();
        //genreDAO.deleteAll();
    }

    private void init() throws Exception {
        bookDAO = new BookDAOImpl(dataSource);
        genreDAO = new GenreDAOImpl(dataSource);
    }

    // region <TEST CASES>

    @Test
    public void add_book() {
        Genre expectedGenre = getRandomGenre();
        genreDAO.add(expectedGenre);
        Book expectedBook = getRandomBook(expectedGenre);

        //TmpTest method add()
        bookDAO.add(expectedBook);
        assertNotNull(expectedGenre);
        assertNotNull(expectedBook);
        assertTrue(expectedGenre.getId()>0);
        assertTrue(expectedBook.getId()>0);

        Book actualBook = bookDAO.getBookByID(expectedBook.getId());
        checkAssertions(expectedBook, actualBook);
    }

    @Test
    public void get_book_by_ID (){
        Book expectedBook = this.expectedBook;

        //TmpTest method getBookByID()
        Book actualBook = bookDAO.getBookByID(expectedBook.getId());

        checkAssertions(expectedBook, actualBook);
    }

    @Test
    public void get_book_by_title (){
        Book expectedBook = this.expectedBook;

        //TmpTest method getBookByTitle()
        Book actualBook = bookDAO.getBookByTitle(expectedBook.getTitle());

        checkAssertions(expectedBook, actualBook);
    }

    @Test
    public void get_all_books(){
        bookDAO.deleteAll();
        List<Book> expectedBookList = new ArrayList<>();
        List<Book> actualBookList = new ArrayList<>();
        Book book;
        int bookCount = 2;
        for(int i = 0; i < bookCount; i++){
            book = getRandomBook(expectedGenre);
            bookDAO.add(book);
            expectedBookList.add(book);
        }

        //Test method getAllBooks()
        actualBookList = bookDAO.getAllBooks();

        assertEquals(expectedBookList.size(), actualBookList.size());
        for(int i = 0; i < bookCount; i++){
            checkAssertions(expectedBookList.get(i), actualBookList.get(i));
        }
    }

    @Test
    public void get_all_books_by_author_Id(){
        bookDAO.deleteAll();
        List<Book> expectedBookList = new ArrayList<>();
        List<Book> actualBookList = new ArrayList<>();
    }

    @Test
    public void update_book(){
        bookDAO.deleteAll();
        Book expectedBook = getRandomBook(expectedGenre);
        bookDAO.add(expectedBook);

        expectedBook.setCountryOfEdition("US");
        expectedBook.setVolume(2);

        //Test method updateBook()
        bookDAO.updateBook(expectedBook);

        Book actualBook = bookDAO.getBookByID(expectedBook.getId());
        checkAssertions(expectedBook, actualBook);
    }

    @Test
    public void delete_book_by_id(){
        bookDAO.deleteAll();
        Book expectedBook = getRandomBook(expectedGenre);
        bookDAO.add(expectedBook);
        int id = expectedBook.getId();

        //Test method deleteBook()
        bookDAO.deleteBook(id);

        Book actualBook = bookDAO.getBookByID(id);
        assertNull(actualBook);
    }

    @Test
    public void delete_all(){
        bookDAO.deleteAll();

        assertTrue(bookDAO.getAllBooks().isEmpty());
    }




    private Book getRandomBook(Genre genre) {
        Book book = new Book();

        book.setISBN("0123456789");
        book.setTitle("New Book" + uuid());
        book.setGenre(genre);
        book.setVolume(1);
        book.setBookAbstract("BookAbstract");
        book.setLanguage("English");
        book.setCount(4);
        book.setEditionYear("2015");
        book.setPages(100);
        book.setCountryOfEdition("Armenia");

        return book;
    }

    private Genre getRandomGenre(){
        Genre genre = new Genre();
        genre.setGenre("education" + uuid());
        return genre;
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











    private void deleteAddedBook(Book book) throws PropertyVetoException, SQLException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int isExecuted = 0;

        connection = DataSource.getInstance().getConnection();
        String sql;
        sql = "DELETE FROM Book WHERE book_id = " + book.getId();
        preparedStatement = connection.prepareStatement(sql);
        isExecuted = preparedStatement.executeUpdate();
        if (isExecuted == 0) {
            throw new SQLException("Delete not executed");
        }
    }


    private Book getBookByIdHelper(int id) throws PropertyVetoException, SQLException, IOException {
        expectedBook = new Book();

        /**connection = DataSource.getInstance().getConnection();
        String sql;
        sql = "SELECT * FROM Book WHERE book_id=" + id;
        preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            book = new Book();
            book.setId(rs.getInt(1));
            book.setISBN(rs.getString(2));
            book.setTitle(rs.getString(3));
            //book.setGenreId(rs.getInt(4));
            book.setVolume(rs.getInt(5));
            book.setBookAbstract(rs.getString(6));
            book.setLanguage(rs.getString(7));
            book.setCount(rs.getInt(8));
            book.setEditionYear(rs.getString(9));
            book.setPages(rs.getInt(10));
            book.setCountryOfEdition(rs.getString(11));
        }*/

        return expectedBook;
    }

    private Genre getGenreByIdHelper(int id) throws PropertyVetoException, SQLException, IOException {
        expectedGenre = new Genre();
        /**connection = DataSource.getInstance().getConnection();
        String sql;
        sql = "SELECT * FROM Genre WHERE genre_id=" + id;
        preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            //genre = new Genre();
            genre.setId(rs.getInt(1));
            genre.setGenre(rs.getString(2));
        }*/

        return expectedGenre;
    }

    private int addGenre(Genre genre) throws PropertyVetoException, SQLException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        //try{
            connection = DataSource.getInstance().getConnection();
            String sql;
            sql = "INSERT INTO Genre VALUES(?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, genre.getId());
            preparedStatement.setString(2, genre.getGenre());

            preparedStatement.executeUpdate();

        return genre.getId();

    //    }
    /**finally {
            closeResource(preparedStatement);
            closeResource(connection);
        }*/
    }

    // endregion
}
