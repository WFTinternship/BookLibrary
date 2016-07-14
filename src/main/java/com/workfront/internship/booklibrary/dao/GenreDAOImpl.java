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
    public int add(Genre genre) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int lastId = 0;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql = "INSERT INTO Genre(genre) VALUES(?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            //preparedStatement.setInt(1, genre.getId());
            preparedStatement.setString(1, genre.getGenre());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            genre.setId(lastId);

        } catch (IOException | SQLException e){
            e.printStackTrace();
            //todo use log4j
        } finally {
            closeConnection(preparedStatement, connection);
        }
        return genre.getId();
    }

    public Genre getGenreByID(int id) {
        Genre genre = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            genre = new Genre();
            String sql = "SELECT FROM Genre WHERE genre_id=" + id;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                genre.setId(resultSet.getInt(1));
                genre.setGenre(resultSet.getString(2));
            }

        } catch (IOException | SQLException e){
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
            String sql = "SELECT FROM Genre WHERE genre=" + genreName;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                genre.setId(resultSet.getInt(1));
                genre.setGenre(resultSet.getString(2));
            }

        } catch (IOException | SQLException e){
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
            String sql = "SELECT * FROM Genre";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Genre genre = new Genre();

                genre.setId(resultSet.getInt(1));
                genre.setGenre(resultSet.getString(2));

                genres.add(genre);
            }

        } catch (IOException | SQLException e){
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
            if(genre.getId() != 0){
                connection = DataSource.getInstance().getConnection();
                String sql = "UPDATE Genre SET " +
                        "genre=?" +
                        " WHERE genre_id=" + genre.getId();

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, genre.getGenre());

                preparedStatement.executeUpdate();

            }
        } catch (IOException | SQLException e){
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
            String sql = "DELETE FROM Gener WHERE book_id=" + id;

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (IOException | SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(preparedStatement, connection);
        }
    }
}
