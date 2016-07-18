package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class AuthorDAOImpl extends General implements AuthorDAO {
    private static final Logger LOGGER = Logger.getLogger(BookDAOImpl.class);

    private DataSource dataSource;

    public AuthorDAOImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
    }

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
            String sql = "SELECT * FROM Author";
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

    public List<Author> getAllAuthorsByBookId(int bookId){
        List<Author> authorList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            authorList = new ArrayList<Author>();
            String sql = "SELECT * FROM author left join book_author" +
                    "on author.author_id = book_author.author_id" +
                    "where book_author.book_id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Author author = new Author();
                setAuthorDetails(resultSet, author);

                authorList.add(author);
            }


        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return authorList;
    }

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

    private void setAuthorDetails(ResultSet rs, Author author) throws SQLException {
        author.setId(rs.getInt("author_id"));
        author.setName(rs.getString("name"));
        author.setSurname(rs.getString("surname"));
        author.seteMail(rs.getString("email"));
        author.setWebPage(rs.getString("web_page"));
        author.setBiography(rs.getString("biography"));
    }

}
