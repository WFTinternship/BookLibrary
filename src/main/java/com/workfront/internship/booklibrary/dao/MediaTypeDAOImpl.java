package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Media;
import com.workfront.internship.booklibrary.common.MediaType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Sona} on 7/22/2016.
 */
public class MediaTypeDAOImpl extends General implements MediaTypeDAO {
    private static final Logger LOGGER = Logger.getLogger(GenreDAOImpl.class);

    private DataSource dataSource;

    public MediaTypeDAOImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public int add(MediaType mediaType) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int lastId = 0;

        try{
            connection = dataSource.getConnection();
            String sql = "INSERT INTO media_type(media_type) VALUES(?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, mediaType.getType());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId =resultSet.getInt(1);
            }
            mediaType.setId(lastId);

        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(preparedStatement, connection);
        }

        return mediaType.getId();
    }

    @Override
    public MediaType getMediaTypeByID(int id) {
        MediaType mediaType = null;
        Connection connection= null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            String sql = "SELECT * FROM media_type WHERE mediaType_id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                mediaType = new MediaType();
                mediaType.setId(resultSet.getInt(1));
                mediaType.setType(resultSet.getString(2));
            }

        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return mediaType;
    }

    @Override
    public MediaType getMediaByType(String mediaType) {
        MediaType mediaTypeObject = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection= dataSource.getConnection();
            String sql = "SELECT * FROM media_type WHERE media_type = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mediaType);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                mediaTypeObject = new MediaType();
                mediaTypeObject.setId(resultSet.getInt(1));
                mediaTypeObject.setType(resultSet.getString(2));
            }

        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
        return mediaTypeObject;
    }

    @Override
    public List<MediaType> getAllMediaTypes() {
        List<MediaType> mediaTypeList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            mediaTypeList = new ArrayList<>();
            String sql = "SELECT * FROM media_type";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                MediaType mediaType = new MediaType();
                mediaType.setId(resultSet.getInt("mediaType_id"));
                mediaType.setType(resultSet.getString("media_type"));

                mediaTypeList.add(mediaType);
            }

        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return mediaTypeList;
    }

    @Override
    public void updateMediaType(MediaType mediaType) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            if(mediaType.getId() != 0){
                connection = dataSource.getConnection();
                String sql = "UPDATE media_type SET media_type = ?" +
                        "WHERE mediaType_id = ?";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, mediaType.getType());
                preparedStatement.setInt(2, mediaType.getId());

                preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(preparedStatement, connection);
        }
    }

    @Override
    public void deleteMediaType(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM media_type WHERE mediaType_id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(preparedStatement, connection);
        }
    }

    @Override
    public void deleteAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM media_type";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(preparedStatement, connection);
        }
    }
}
