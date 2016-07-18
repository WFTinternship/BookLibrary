package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


public class MediaDAOImpl extends General implements MediaDAO{

    private static final Logger LOGGER = Logger.getLogger(BookDAOImpl.class);

    private DataSource dataSource;
    private BookDAO bookDAO;

    public MediaDAOImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        this.bookDAO = new BookDAOImpl(dataSource);
    }

    @Override
    public int add(Media media) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        int lastId = 0;

        try{
            connection = dataSource.getConnection();
            String sql = "INSERT INTO Media(media, media_type, book_id) VALUES(?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, media.getLink());
            preparedStatement.setString(2, media.getType());
            preparedStatement.setInt(3, media.getBook().getId());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            media.setId(lastId);

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(preparedStatement, connection);
        }
        return media.getId();
    }

    @Override
    public Media getMediaByID(int id) {
        Media media = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();

            String sql = "SELECT * FROM Media LEFT JOIN Book " +
                    "ON Media.book_id = Book.book_id " +
                    "where Media.book_id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                media = new Media();
                Book book = new Book();

                setMediaDetails(resultSet, media);

                book.setId(resultSet.getInt(4)).setTitle(resultSet.getString("title"));
                media.setBook(book);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return media;
    }

    @Override
    public List<Media> getAllMedia() {
        List<Media> mediaList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            mediaList = new ArrayList<Media>();
            String sql = "SELECT * FROM Media LEFT JOIN Book " +
                    "ON Media.book_id = Book.book_id";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Media media = new Media();
                Book book = new Book();

                setMediaDetails(resultSet, media);
                media.setBook(book);

                mediaList.add(media);
            }
            return mediaList;

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement,connection);
        }
        return null;
    }

    @Override
    public void updateMedia(Media media) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(media.getId() != 0){
                connection = dataSource.getConnection();
                String sql = "UPDATE Media SET " +
                        "media=?, media_type=?, book_id=?" +
                        " WHERE media_id=?";

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, media.getLink());
                preparedStatement.setString(2, media.getType());
                preparedStatement.setInt(3, media.getBook().getId());
                preparedStatement.setInt(4, media.getId());

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
    public void deleteMedia(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM Media WHERE media_id=?";

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
            mediaList = new ArrayList<Media>();
            String sql = "SELECT * FROM media left join book on media.book_id = book.book_id where media.book_id =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Media media = new Media();
                Book book = new Book();
                book = bookDAO.getBookByID(resultSet.getInt(4));

                setMediaDetails(resultSet, media);
                media.setBook(book);

                mediaList.add(media);
            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return mediaList;
    }

    @Override
    public void deleteAll(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM Media";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection( preparedStatement, connection);
        }
    }

    private void setMediaDetails(ResultSet resultSet, Media media) throws SQLException {
        media.setId(resultSet.getInt("media_id"));
        media.setLink(resultSet.getString("media"));
        media.setType(resultSet.getString("media_type"));
    }
}
