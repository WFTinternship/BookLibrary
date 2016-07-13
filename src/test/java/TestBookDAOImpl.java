import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


public class TestBookDAOImpl {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    BookDAO bookDAO = new BookDAOImpl();
    Book book = null;
    Genre genre = null;

    @Before
    public void setup() throws PropertyVetoException, SQLException, IOException {
        connection = DataSource.getInstance().getConnection();
    }

    @After
    public void tearDown() throws PropertyVetoException, IOException, SQLException {
        //deleteAddedBook(book);

        closeResource(resultSet);
        closeResource(preparedStatement);
        closeResource(connection);
    }

    @Test
    public void add_author() throws SQLException, PropertyVetoException, IOException {
        Book expectedBook = getRandomBook();
        Genre expectedGenre = getRandomGenre();

        int genre_ID = addGenre(expectedGenre);
        expectedGenre.setId(genre_ID);
        expectedBook.setGenre(expectedGenre);
        // test method
        bookDAO.add(expectedBook);

        Book book = getBookByIdHelper(expectedBook.getId());
        Genre genre = getGenreByIdHelper(expectedGenre.getId());
        book.setGenre(genre);

        assertNotNull(expectedBook);
        assertNotNull(expectedGenre);

        assertTrue(expectedBook.getId() > 0);
        assertEquals(expectedBook.getISBN(), book.getISBN());
        assertEquals( expectedBook.getTitle(), book.getTitle());
        assertEquals( expectedBook.getGenre(), book.getGenre());
        assertEquals( expectedBook.getVolume(), book.getVolume());
        assertEquals( expectedBook.getBookAbstract(), book.getBookAbstract());
        assertEquals( expectedBook.getLanguage(), book.getLanguage());
        assertEquals( expectedBook.getCount(), book.getCount());
        assertEquals( expectedBook.getEditionYear(), book.getEditionYear());
        assertEquals( expectedBook.getPages(), book.getPages());
        assertEquals( expectedBook.getCountryOfEdition(), book.getCountryOfEdition());


        //deleteAddedBook(expectedBook);
    }




    private Book getRandomBook() throws SQLException {
        Book book = new Book();

        book.setISBN("012540654");
        book.setTitle("New Book");
        //book.setGenreId(1);
        book.setVolume(1);
        book.setBookAbstract("Abstract");
        book.setLanguage("English");
        book.setCount(4);
        book.setEditionYear("2015");
        book.setPages(100);
        book.setCountryOfEdition("Armenia");

        return book;
    }

    private Genre getRandomGenre(){
        Genre genre = new Genre();
        genre.setGenre("education");
        return genre;
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

    private void closeResource(AutoCloseable closeable){
        try{
            if (closeable != null ) closeable.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Book getBookByIdHelper(int id) throws PropertyVetoException, SQLException, IOException {
        book = new Book();

        connection = DataSource.getInstance().getConnection();
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
        }

        return book;
    }

    private Genre getGenreByIdHelper(int id) throws PropertyVetoException, SQLException, IOException {
        genre = new Genre();
        connection = DataSource.getInstance().getConnection();
        String sql;
        sql = "SELECT * FROM Genre WHERE genre_id=" + id;
        preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            //genre = new Genre();
            genre.setId(rs.getInt(1));
            genre.setGenre(rs.getString(2));
        }

        return genre;
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
}
