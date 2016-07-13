package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl extends General implements GenreDAO {
    public void add(Genre genre) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql;
            sql = "INSERT INTO Genre VALUES(?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, genre.getGenreId());
            preparedStatement.setString(2, genre.getGenre());

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

    public Genre getGenreByID(int id) {
        Genre genre = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            genre = new Genre();
            String sql;
            sql = "SELECT FROM Genre WHERE genre_id=" + id;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                genre.setGenreId(resultSet.getInt(1));
                genre.setGenre(resultSet.getString(2));
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

        return genre;
    }

    public Genre getGenreByGenreName(String genreName) {
        Genre genre = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            genre = new Genre();
            String sql;
            sql = "SELECT FROM Genre WHERE genre=" + genreName;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                genre.setGenreId(resultSet.getInt(1));
                genre.setGenre(resultSet.getString(2));
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

        return genre;
    }

    public List<Genre> getAllGenres() {
        List<Genre> genres = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            genres = new ArrayList<Genre>();
            String sql;
            sql = "SELECT * FROM Genre";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Genre genre = new Genre();

                genre.setGenreId(resultSet.getInt(1));
                genre.setGenre(resultSet.getString(2));

                genres.add(genre);
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

        return genres;
    }

    public void updateGenre(Genre genre) {
        Book book = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(genre.getGenreId() != 0){
                connection = DataSource.getInstance().getConnection();
                String sql;
                sql = "UPDATE Genre SET " +
                        "genre=?" +
                        " WHERE genre_id=" + genre.getGenreId();

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, genre.getGenre());

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

    public void deleteGenre(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql;
            sql = "DELETE FROM Gener WHERE book_id=" + id;

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
}
