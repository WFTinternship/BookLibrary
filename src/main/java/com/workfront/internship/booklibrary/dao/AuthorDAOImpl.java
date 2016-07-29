package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.workfront.internship.booklibrary.common.Book;
import org.apache.log4j.Logger;

public class AuthorDAOImpl extends General implements AuthorDAO {
    private static final Logger LOGGER = Logger.getLogger(BookDAOImpl.class);

    private DataSource dataSource;
//    private BookDAO bookDAO;

    public AuthorDAOImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
//        this.bookDAO = new BookDAOImpl(dataSource);
    }

    public AuthorDAOImpl(DataSource dataSource, Book book) {}

    @Override
    public int add(Author author) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int lastId = 0;

        try{
            connection = dataSource.getConnection();

            String sql = "INSERT INTO Author(name, surname, email, web_page, biography) VALUES(?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getSurname());
            preparedStatement.setString(3, author.geteMail());
            preparedStatement.setString(4, author.getWebPage());
            preparedStatement.setString(5, author.getBiography());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            author.setId(lastId);

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection( preparedStatement, connection);
        }
        return author.getId();
    }

    @Override
    public Author getAuthorByID(int id) {
        Author author = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "SELECT * FROM Author WHERE author_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                author = new Author();
                setAuthorDetails(resultSet, author);
            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection(preparedStatement, connection);
        }

        return author;
    }

    @Override
    public Author getAuthorByName(String name){
        Author author = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            String sql = "SELECT * FROM author WHERE name =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                author = new Author();
                setAuthorDetails(resultSet, author);
            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection(resultSet, preparedStatement, connection);
        }

        return author;
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            authors = new ArrayList<Author>();
            String sql = "SELECT * FROM Author ORDER BY name, surname";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Author author = new Author();
                setAuthorDetails(resultSet, author);

                authors.add(author);
            }
        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
        return authors;
    }

 /**
    public List<Author> getAllAuthorsByBookId(int bookId){
//        List<Author> authorList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            List<Author> authorList = new ArrayList<>();
//            authorList = new ArrayList<Author>();
            String sql = "SELECT * FROM book_author where book_author.book_id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
//                Author author = new Author();
//                setAuthorDetails(resultSet, author);
                int id = resultSet.getInt("author_id");
                Author author = getAuthorByID(id);

//                author.setId(resultSet.getInt("author_id"));
//                author.setName(resultSet.getString("name"));
//                author.setSurname(resultSet.getString("surname"));
//                author.seteMail(resultSet.getString("email"));
//                author.setWebPage(resultSet.getString("web_page"));
//                author.setBiography(resultSet.getString("biography"));

                authorList.add(author);
            }
            return authorList;
        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
    }
  */

    @Override
    public void updateAuthor(Author author) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(author.getId() != 0){
                connection = dataSource.getConnection();
                String sql = "UPDATE Author SET " +
                        "name = ?, surname = ?, email = ?, web_page = ?, biography = ? " +
                        "WHERE author_id = ?";

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, author.getName());
                preparedStatement.setString(2, author.getSurname());
                preparedStatement.setString(3, author.geteMail());
                preparedStatement.setString(4, author.getWebPage());
                preparedStatement.setString(5, author.getBiography());
                preparedStatement.setInt(6, author.getId());

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
    public void deleteAuthor(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM Author WHERE author_id = ?";

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
    public void deleteAllAuthors() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM Author";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }

    @Override
    public boolean isExist(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            String sql = "SELECT * FROM author WHERE id =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection(resultSet, preparedStatement, connection);
        }
        return false;
    }

    private void setAuthorDetails(ResultSet rs, Author author) throws SQLException {
        author.setId(rs.getInt("author_id"));
        author.setName(rs.getString("name"));
        author.setSurname(rs.getString("surname"));
        author.seteMail(rs.getString("email"));
        author.setWebPage(rs.getString("web_page"));
        author.setBiography(rs.getString("biography"));
    }

}
