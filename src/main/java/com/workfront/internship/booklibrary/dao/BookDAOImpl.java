package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


public class BookDAOImpl extends General implements BookDAO {
    private static final Logger LOGGER = Logger.getLogger(BookDAOImpl.class);

    private DataSource dataSource;
    private GenreDAO genreDAO;

    public BookDAOImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        this.genreDAO = new GenreDAOImpl(dataSource);
    }

    @Override
    public int add(Book book) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        int lastId = 0;

        try{

            connection = dataSource.getConnection();
            String sql = "INSERT INTO Book(ISBN, title, genre_id, volume, abstract, language, count, edition_year, pages, country_of_edition) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, book.getISBN());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setInt(3,book.getGenre().getId());
            preparedStatement.setInt(4, book.getVolume());
            preparedStatement.setString(5, book.getBookAbstract());
            preparedStatement.setString(6, book.getLanguage());
            preparedStatement.setInt(7, book.getCount());
            preparedStatement.setString(8, book.getEditionYear());
            preparedStatement.setInt(9, book.getPages());
            preparedStatement.setString(10, book.getCountryOfEdition());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            book.setId(lastId);

        } catch (SQLException e){
            LOGGER.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(preparedStatement, connection);
        }
        return book.getId();
    }

    @Override
    public Book getBookByID(int id) {
        Book book = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();

            String sql = "SELECT * FROM Book LEFT JOIN Genre " +
                    "ON Book.genre_id = Genre.genre_id " +
                    "where Book.book_id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                book = new Book();
                Genre genre = new Genre();

                setBookDetails(resultSet, book);

                genre.setId(resultSet.getInt(4)).setGenre(resultSet.getString("genre"));
                book.setGenre(genre);
            }

        } catch (SQLException e){
            LOGGER.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return book;
    }

    @Override
    public Book getBookByTitle(String title) {
        Book book = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();

            String sql = "SELECT * FROM Book LEFT JOIN Genre " +
                    "ON Book.genre_id = Genre.genre_id " +
                    "where Book.title = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                book = new Book();
                Genre genre = new Genre();

                setBookDetails(resultSet, book);

                genre.setId(resultSet.getInt(4)).setGenre(resultSet.getString("genre"));
                book.setGenre(genre);
            }

        } catch (SQLException e){
            LOGGER.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            books = new ArrayList<Book>();
            String sql = "SELECT * FROM Book LEFT JOIN Genre ON Book.genre_id = Genre.genre_id";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Book book = new Book();
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(4)).setGenre(resultSet.getString("genre"));

                setBookDetails(resultSet, book);
                book.setGenre(genre);

                books.add(book);
            }

        } catch (SQLException e){
            LOGGER.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return books;
    }

    @Override
    public void updateBook(Book book) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(book.getId() != 0){
                connection = dataSource.getConnection();
                String sql = "UPDATE Book SET " +
                        "ISBN=?, title=?, genre_id=?, volume=?, abstract=?, language=?, count=?, edition_year=?, pages=?, country_of_edition=?" +
                        " WHERE book_id=?";


                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, book.getISBN());
                preparedStatement.setString(2, book.getTitle());
                preparedStatement.setInt(3, book.getGenre().getId());
                preparedStatement.setInt(4, book.getVolume());
                preparedStatement.setString(5, book.getBookAbstract());
                preparedStatement.setString(6, book.getLanguage());
                preparedStatement.setInt(7, book.getCount());
                preparedStatement.setString(8, book.getEditionYear());
                preparedStatement.setInt(9, book.getPages());
                preparedStatement.setString(10, book.getCountryOfEdition());
                preparedStatement.setInt(11, book.getId());

                preparedStatement.executeUpdate();

            }
        } catch (SQLException e){
            LOGGER.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }

    @Override
    public void deleteBook(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM Book WHERE book_id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            LOGGER.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }

    public List<Book> getAllBooksByAuthorId(int authorId){
        List<Book> bookList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            bookList = new ArrayList<Book>();
            String sql = "select * from book left join book_author" +
                    "on book.book_id = book_author.book_id" +
                    "where book_author.author_id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, authorId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Book book = new Book();
                Genre genre = new Genre();
                genre = genreDAO.getGenreByID(resultSet.getInt(4));

                setBookDetails(resultSet, book);
                book.setGenre(genre);

                bookList.add(book);
            }


        } catch (SQLException e){
            LOGGER.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return bookList;
    }

    public List<Book> getAllBooksByGenreId(int genreId){
        List<Book> bookList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            bookList = new ArrayList<Book>();
            String sql = "select * from book left join genre" +
                    "on book.genre_id = genre.genre_id" +
                    "where book.genre_id= ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, genreId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Book book = new Book();
                Genre genre = new Genre();
                genre = genreDAO.getGenreByID(resultSet.getInt(4));

                setBookDetails(resultSet, book);
                book.setGenre(genre);

                bookList.add(book);
            }


        } catch (SQLException e){
            LOGGER.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return bookList;
    }

    @Override
    public void deleteAll(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM Book";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            LOGGER.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection( preparedStatement, connection);
        }
    }

    private void setBookDetails(ResultSet rs, Book book) throws SQLException {
        book.setId(rs.getInt("book_id"));
        book.setISBN(rs.getString("ISBN"));
        book.setTitle(rs.getString("title"));
        //book.setGenre(genre);
        book.setVolume(rs.getInt("volume"));
        book.setBookAbstract(rs.getString("abstract"));
        book.setLanguage(rs.getString("language"));
        book.setCount(rs.getInt("count"));
        book.setEditionYear(rs.getString("edition_year"));
        book.setPages(rs.getInt("pages"));
        book.setCountryOfEdition(rs.getString("country_of_edition"));
    }

}
