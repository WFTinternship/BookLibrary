package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.common.Media;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MediaDAOImpl extends General implements MediaDAO{
    public int add(Media media) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int lastId = 0;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql = "INSERT INTO Media(media, media_type, book_id) VALUES(?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            //preparedStatement.setInt(1,media.getMediaId());
            preparedStatement.setString(1, media.getLink());
            preparedStatement.setString(2, media.getType());
            preparedStatement.setInt(3, media.getBookId());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            media.setId(lastId);

        } catch (SQLException e){
            e.printStackTrace();
            //todo use log4j
        }finally {
            closeConnection(preparedStatement, connection);
        }
        return media.getId();
    }

    public Media getMediaByID(int id) {
        Media media = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            media = new Media();
            Book book = new Book();
            Genre genre = new Genre();
            String sql = "SELECT * FROM Media LEFT JOIN Book " +
                    "ON Media.book_id = Book.book_id " +
                    "where Media.book_id = " + id;

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                media.setId(resultSet.getInt("media_id"));
                media.setLink(resultSet.getString("media"));
                media.setType(resultSet.getString("media_type"));
                book.setId(resultSet.getInt("book_id"));
                book.setISBN(resultSet.getString("ISBN"));
                book.setTitle(resultSet.getString("title"));
                genre.setId(resultSet.getInt("genre_id"));
                book.setVolume(resultSet.getInt("volume"));
                book.setBookAbstract(resultSet.getString("abstract"));
                book.setLanguage(resultSet.getString("language"));
                book.setCount(resultSet.getInt("count"));
                book.setEditionYear(resultSet.getString("edition_year"));
                book.setPages(resultSet.getInt("pages"));
                book.setCountryOfEdition(resultSet.getString("country_of_edition"));

                //book.setGenre(genre);
                //media.setBook(book);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return media;
    }

    public List<Media> getAllMedia() {
        List<Media> medias = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            medias = new ArrayList<Media>();
            String sql = "SELECT * FROM Media LEFT JOIN Book " +
                    "ON Media.book_id = Book.book_id";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Media media = new Media();
                Book book = new Book();
                Genre genre = new Genre();

                media.setId(resultSet.getInt("media_id"));
                media.setLink(resultSet.getString("media"));
                media.setType(resultSet.getString("media_type"));
                book.setId(resultSet.getInt("book_id"));
                book.setISBN(resultSet.getString("ISBN"));
                book.setTitle(resultSet.getString("title"));
                genre.setId(resultSet.getInt("genre_id"));
                book.setVolume(resultSet.getInt("volume"));
                book.setBookAbstract(resultSet.getString("abstract"));
                book.setLanguage(resultSet.getString("language"));
                book.setCount(resultSet.getInt("count"));
                book.setEditionYear(resultSet.getString("edition_year"));
                book.setPages(resultSet.getInt("pages"));
                book.setCountryOfEdition(resultSet.getString("country_of_edition"));

                //book.setGenre(genre);
                //media.setBook(book);

                medias.add(media);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement,connection);
        }


        return null;
    }

    public void updateMedia(Media media) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(media.getId() != 0){
                connection = DataSource.getInstance().getConnection();
                String sql = "UPDATE Media SET " +
                        "media_id=?, media=?, media_type=?, book_id=?" +
                        " WHERE media_id=" + media.getId();

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, media.getId());
                preparedStatement.setString(2, media.getLink());
                preparedStatement.setString(3, media.getType());
                preparedStatement.setInt(4, media.getBookId());

                preparedStatement.executeUpdate();

            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }

    public void deleteMedia(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql = "DELETE FROM Media WHERE media_id=" + id;

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(preparedStatement, connection);
        }
    }

    public List<Media> getAllMediaByBookId(int bookId){
        List<Media> mediaList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            mediaList = new ArrayList<Media>();
            String sql = "SELECT * FROM media where media.book_id = " + bookId;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Media media = new Media();

                media.setId(resultSet.getInt("media_id"));
                media.setLink(resultSet.getString("media"));
                media.setType(resultSet.getString("media_type"));
                media.setBookId(resultSet.getInt("book_id"));

                mediaList.add(media);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return mediaList;
    }
}
