package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.common.Media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.workfront.internship.booklibrary.common.MediaType;
import org.apache.log4j.Logger;


public class MediaDAOImpl extends General implements MediaDAO{

    private static final Logger LOGGER = Logger.getLogger(BookDAOImpl.class);

    private DataSource dataSource;
    private BookDAO bookDAO;
    private MediaTypeDAO mediaTypeDAO;

    public MediaDAOImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        this.bookDAO = new BookDAOImpl(dataSource);
        this.mediaTypeDAO = new MediaTypeDAOImpl(dataSource);
    }

    @Override
    public int add(Media media) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        int lastId = 0;

        try{
            connection = dataSource.getConnection();
            String sql = "INSERT INTO Media(media, media_type_id, book_id) VALUES(?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, media.getLink());
            preparedStatement.setInt(2, media.getType().getId());
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

            String sql = "SELECT * FROM Media LEFT JOIN media_type ON media.media_type_id = media_type.mediaType_id" +
                    " inner join Book ON Media.book_id = Book.book_id inner join Genre" +
                    " ON Book.genre_id = Genre.genre_id where Media.media_id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                media = new Media();
                setMediaDetails(resultSet, media);
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            List<Media> mediaList = new ArrayList<Media>();
            String sql = "SELECT * FROM Media LEFT JOIN media_type ON media.media_type_id = media_type.mediaType_id" +
                    " LEFT JOIN Book ON Media.book_id = Book.book_id " +
                    "inner join Genre ON Book.genre_id = Genre.genre_id";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Media media = new Media();

                setMediaDetails(resultSet, media);

                mediaList.add(media);
            }
            return mediaList;

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement,connection);
        }

    }

    @Override
    public void updateMedia(Media media) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(media.getId() != 0){
                connection = dataSource.getConnection();
                String sql = "UPDATE Media SET " +
                        "media=?, media_type_id=?, book_id=?" +
                        " WHERE media_id=?";

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, media.getLink());
                preparedStatement.setInt(2, media.getType().getId());
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
        Book book = new Book();
        Genre genre = new Genre();
        MediaType mediaType = new MediaType();

        media.setId(resultSet.getInt("media_id"));
        media.setLink(resultSet.getString("media"));
        mediaType.setId(resultSet.getInt("mediaType_id")).setType(resultSet.getString("media_type"));
        genre.setId(resultSet.getInt("genre_id")).setGenre(resultSet.getString("genre"));

        book.setId(resultSet.getInt("book_id")).setISBN(resultSet.getString("ISBN")).setTitle(resultSet.getString("title"));
        book.setGenre(genre).setVolume(resultSet.getInt("volume")).setBookAbstract(resultSet.getString("abstract"));
        book.setLanguage(resultSet.getString("language")).setCount(resultSet.getInt("count"));
        book.setEditionYear(resultSet.getString("edition_year")).setPages(resultSet.getInt("pages"));
        book.setCountryOfEdition(resultSet.getString("country_of_edition"));
        media.setBook(book);
        media.setType(mediaType);
    }
}
