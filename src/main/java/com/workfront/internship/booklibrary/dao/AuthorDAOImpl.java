package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ${Sona} on 7/5/2016.
 */
public class AuthorDAOImpl extends General implements AuthorDAO {

    public void createAuthor(Author author) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DataSource.getInstance().getConnection();

            String sql;
            sql = "INSERT INTO Author VALUES(?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, author.getAuthorId());
            preparedStatement.setString(2, author.getName());
            preparedStatement.setString(3, author.getSurname());
            preparedStatement.setString(4, author.geteMail());
            preparedStatement.setString(5, author.getWebPage());
            preparedStatement.setString(6, author.getBiography());

            preparedStatement.executeUpdate();

        }catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closeConnection( preparedStatement, connection);
        }
    }

    public Author getAuthorByID(int id) {
        Author author = new Author();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql;
            sql = "SELECT * FROM Author WHERE author_id=" + id;
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                author.setAuthorId(rs.getInt(1));
                author.setName(rs.getString(2));
                author.setSurname(rs.getString(3));
                author.seteMail(rs.getString(4));
                author.setWebPage(rs.getString(5));
                author.setBiography(rs.getString(6));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            closeConnection(preparedStatement, connection);
        }

        return author;
    }

    public List<Author> getAllAuthors() {
        return null;
    } // TODO

    public void updateAuthor(Author a) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(a.getAuthorId() != 0){
                connection = DataSource.getInstance().getConnection();
                String sql;
                sql = "UPDATE Author SET " +
                        "name = ?, surname = ?, email = ?, web_page = ?, biography = ? " +
                        "WHERE author_id = " + a.getAuthorId();
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, a.getName());
                preparedStatement.setString(2, a.getSurname());
                preparedStatement.setString(3, a.geteMail());
                preparedStatement.setString(4, a.getWebPage());
                preparedStatement.setString(5, a.getBiography());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeConnection(preparedStatement, connection);
        }

    }

    public void deleteAuthor(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql;
            sql = "DELETE FROM Author WHERE author_id = " + id;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeConnection(preparedStatement, connection);
        }
    }
}
