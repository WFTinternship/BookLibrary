package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class BookDAOImpl extends General implements BookDAO {
    private static final Logger LOGGER = Logger.getLogger(BookDAOImpl.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private AuthorDAO authorDAO;

    @Override
    public int add(Book book, List<Integer> authorsIdList) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int lastId = 0;
        try{
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO Book(ISBN, title, genre_id, volume, abstract, language, count, edition_year, pages, country_of_edition) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

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

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            book.setId(lastId);

            for(int i= 0; i < authorsIdList.size(); i++){
                addAuthorToBook(connection, book, authorsIdList.get(i));
            }
            connection.commit();
        }catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
        return book.getId();
    }

    private void addAuthorToBook(Connection connection, Book book, int authorID) {
        PreparedStatement preparedStatement = null;
        try{
            String sqlCrossTable = "INSERT INTO book_author(book_id, author_id) VALUES(?, ?)";
            preparedStatement = connection.prepareStatement(sqlCrossTable);

            preparedStatement.setInt(1, book.getId());
            preparedStatement.setInt(2, authorID);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            closeConnection(preparedStatement, connection);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addAuthorToBook(int bookId, int authorId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = dataSource.getConnection();
            String sqlCrossTable = "INSERT INTO book_author(book_id, author_id) VALUES(?, ?)";
            preparedStatement = connection.prepareStatement(sqlCrossTable);

            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, authorId);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }

    @Override
    public void addMediaToBook(int bookId, int mediaId) {

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

                genre.setId(resultSet.getInt("genre_id")).setGenre(resultSet.getString("genre"));
                book.setGenre(genre);
            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
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
            LOGGER.error("SQL exception occurred!");
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
            String sql = "SELECT * FROM Book LEFT JOIN Genre ON Book.genre_id = Genre.genre_id ORDER BY book.title";
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
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return books;
    }

    @Override
    public List<Book> getAllBooksWithCondition(String string) {
        List<Book> books = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            books = new ArrayList<Book>();
            String sql = "SELECT * FROM Book LEFT JOIN Genre ON Book.genre_id = Genre.genre_id WHERE title LIKE ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, '%' + string + '%');
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
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return books;
    }

    @Override
    public void updateBook(Connection connection, Book book) {
        PreparedStatement preparedStatement = null;

        try{
            if(book.getId() != 0){
                if(connection== null){
                    connection = dataSource.getConnection();
                }
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
            LOGGER.error("SQL exception occurred!");
            closeConnection(preparedStatement, connection);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBook(Book book){
//        updateBook(null, book);

        PreparedStatement preparedStatement = null;
        Connection connection = null;

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
            LOGGER.error("SQL exception occurred!");
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
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(preparedStatement, connection);
        }
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
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection( preparedStatement, connection);
        }
    }

    @Override
    public boolean isExist(int id) {
        if(getBookByID(id) != null) {
            return true;
        }
        return false;
    }

    private void setBookDetails(ResultSet rs, Book book) throws SQLException {
        book.setId(rs.getInt("book_id"));
        book.setISBN(rs.getString("ISBN"));
        book.setTitle(rs.getString("title"));
        book.setVolume(rs.getInt("volume"));
        book.setBookAbstract(rs.getString("abstract"));
        book.setLanguage(rs.getString("language"));
        book.setCount(rs.getInt("count"));
        book.setEditionYear(rs.getString("edition_year"));
        book.setPages(rs.getInt("pages"));
        book.setCountryOfEdition(rs.getString("country_of_edition"));

    }

}
