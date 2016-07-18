package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class GenreDAOImpl extends General implements GenreDAO {
    private static final Logger LOGGER = Logger.getLogger(GenreDAOImpl.class);

    private DataSource dataSource;

    public GenreDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int add(Genre genre) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        int lastId = 0;

        try{
            connection = dataSource.getConnection();
            String sql = "INSERT INTO Genre(genre) VALUES(?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, genre.getGenre());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            genre.setId(lastId);

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(preparedStatement, connection);
        }
        return genre.getId();
    }

    @Override
    public Genre getGenreByID(int id) {
        Genre genre = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();

            String sql = "SELECT FROM Genre WHERE genre_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setGenre(resultSet.getString(2));
            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
        return genre;
    }

    @Override
    public Genre getGenreByGenreName(String genreName) {
        Genre genre = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();

            String sql = "SELECT FROM Genre WHERE genre=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, genreName);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setGenre(resultSet.getString(2));
            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return genre;
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
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

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return genres;
    }

    @Override
    public void updateGenre(Genre genre) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(genre.getId() != 0){
                connection = dataSource.getConnection();
                String sql = "UPDATE Genre SET genre=?" +
                        " WHERE genre_id=?";

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, genre.getGenre());
                preparedStatement.setInt(2, genre.getId());

                preparedStatement.executeUpdate();

            }
        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(preparedStatement, connection);
        }
    }

    @Override
    public void deleteGenre(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM Gener WHERE book_id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(preparedStatement, connection);
        }
    }

    @Override
    public void deleteAll(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM Genre";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection( preparedStatement, connection);
        }
    }
}
