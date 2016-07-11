package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookDAOImpl extends General implements BookDAO {
    public void createBook(Book book) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql;
            sql = "INSERT INTO Book VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, book.getBookId());
            preparedStatement.setString(2, book.getISBN());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setInt(4,book.getGenreId());
            preparedStatement.setInt(5, book.getVolume());
            preparedStatement.setString(6, book.getBookAbstract());
            preparedStatement.setString(7, book.getLanguage());
            preparedStatement.setInt(8, book.getCount());
            preparedStatement.setString(9, book.getEditionYear());
            preparedStatement.setInt(10, book.getPages());
            preparedStatement.setString(11, book.getCountryOfEdition());

            preparedStatement.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }finally {
            closeConnection(preparedStatement, connection);
        }
    }

    public Book getBookByID(int id) {
        Book book = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            book = new Book();

            String sql;
            sql = "SELECT * FROM Book LEFT JOIN Genre " +
                    "ON Book.genre_id = Genre.genre_id " +
                    "where Book.book_id = " + id;

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                book.setBookId(resultSet.getInt(1));
                book.setISBN(resultSet.getString(2));
                book.setTitle(resultSet.getString(3));
                book.setVolume(resultSet.getInt(5));
                book.setBookAbstract(resultSet.getString(6));
                book.setLanguage(resultSet.getString(7));
                book.setCount(resultSet.getInt(8));
                book.setEditionYear(resultSet.getString(9));
                book.setPages(resultSet.getInt(10));
                book.setCountryOfEdition(resultSet.getString(11));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return book;
    }

    public Book getBookByTitle(String title) {
        Book book = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            book = new Book();

            String sql;
            sql = "SELECT * FROM Book LEFT JOIN Genre " +
                    "ON Book.genre_id = Genre.genre_id " +
                    "where Book.title = " + title;

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                book.setBookId(resultSet.getInt(1));
                book.setISBN(resultSet.getString(2));
                book.setTitle(resultSet.getString(3));
                book.setVolume(resultSet.getInt(5));
                book.setBookAbstract(resultSet.getString(6));
                book.setLanguage(resultSet.getString(7));
                book.setCount(resultSet.getInt(8));
                book.setEditionYear(resultSet.getString(9));
                book.setPages(resultSet.getInt(10));
                book.setCountryOfEdition(resultSet.getString(11));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return book;
    }

    public List<Book> getAllBooks() {
        List<Book> books = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            books = new ArrayList<Book>();
            String sql;
            sql = "SELECT * FROM Book LEFT JOIN Genre ON Book.genre_id = Genre.genre_id";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Book book = new Book();

                book.setBookId(resultSet.getInt(1));
                book.setISBN(resultSet.getString(2));
                book.setTitle(resultSet.getString(3));
                book.setGenreId(resultSet.getInt(4));
                book.setVolume(resultSet.getInt(5));
                book.setBookAbstract(resultSet.getString(6));
                book.setLanguage(resultSet.getString(7));
                book.setCount(resultSet.getInt(8));
                book.setEditionYear(resultSet.getString(9));
                book.setPages(resultSet.getInt(10));
                book.setCountryOfEdition(resultSet.getString(11));

                books.add(book);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return books;
    }

    public void updateBook(Book book) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(book.getBookId() != 0){
                connection = DataSource.getInstance().getConnection();
                String sql;
                sql = "UPDATE Book SET " +
                        "ISBN=?, title=?, genre_id=?, volume=?, abstract=?, language=?, count=?, edition_year=?, pages=?, country_of_edition=?" +
                        " WHERE book_id=" + book.getBookId();

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, book.getISBN());
                preparedStatement.setString(2, book.getTitle());
                preparedStatement.setInt(3, book.getGenreId());
                preparedStatement.setInt(4, book.getVolume());
                preparedStatement.setString(5, book.getBookAbstract());
                preparedStatement.setString(6, book.getLanguage());
                preparedStatement.setInt(7, book.getCount());
                preparedStatement.setString(8, book.getEditionYear());
                preparedStatement.setInt(9, book.getPages());
                preparedStatement.setString(10, book.getCountryOfEdition());

                preparedStatement.executeUpdate();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }finally {
            closeConnection(preparedStatement, connection);
        }
    }

    public void deleteBook(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql;
            sql = "DELETE FROM Book WHERE book_id=" + id;

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }finally {
            closeConnection(preparedStatement, connection);
        }
    }

    public List<Book> getAllBooksByAuthorId(int authorId){
        List<Book> bookList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            bookList = new ArrayList<Book>();
            String sql;
            sql = "select * from book left join book_author on book.book_id = book_author.book_id" +
                    "where book_author.author_id" + authorId;

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Book book = new Book();

                book.setBookId(resultSet.getInt("book_id"));
                book.setISBN(resultSet.getString("ISBN"));
                book.setTitle(resultSet.getString("title"));
                book.setGenreId(resultSet.getInt("genre_id"));
                book.setVolume(resultSet.getInt("volume"));
                book.setBookAbstract(resultSet.getString("abstract"));
                book.setLanguage(resultSet.getString("language"));
                book.setCount(resultSet.getInt("count"));
                book.setEditionYear(resultSet.getString("edition_year"));
                book.setPages(resultSet.getInt("pages"));
                book.setCountryOfEdition(resultSet.getString("country_of_edition"));

                bookList.add(book);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }finally {
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
            connection = DataSource.getInstance().getConnection();
            bookList = new ArrayList<Book>();
            String sql;
            sql = "select * from book left join genre" +
                    "on book.genre_id = genre.genre_id" +
                    "where book.genre_id=" + genreId;

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Book book = new Book();

                book.setBookId(resultSet.getInt("book_id"));
                book.setISBN(resultSet.getString("ISBN"));
                book.setTitle(resultSet.getString("title"));
                book.setGenreId(resultSet.getInt("genre_id"));
                book.setVolume(resultSet.getInt("volume"));
                book.setBookAbstract(resultSet.getString("abstract"));
                book.setLanguage(resultSet.getString("language"));
                book.setCount(resultSet.getInt("count"));
                book.setEditionYear(resultSet.getString("edition_year"));
                book.setPages(resultSet.getInt("pages"));
                book.setCountryOfEdition(resultSet.getString("country_of_edition"));

                bookList.add(book);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return bookList;
    }

}
