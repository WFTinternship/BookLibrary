package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class UserDAOImpl extends General implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public int add(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int lastId = 0;

        try{
            connection = dataSource.getConnection();

            String sql = "INSERT INTO User(name, surname, username, password, address, e_mail, phone, access_privilege) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.geteMail());
            preparedStatement.setString(7, user.getPhone());
            preparedStatement.setString(8, user.getAccessPrivilege());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            user.setId(lastId);

        } catch(SQLIntegrityConstraintViolationException e){
            LOGGER.error("Duplicate entry exception!");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }
        finally{
            closeConnection( preparedStatement, connection);
        }
        return user.getId();
    }

    @Override
    public User getUserByID(int id) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            String sql = "SELECT * FROM User WHERE user_id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                user = new User();
                setUserDetails(resultSet, user);
            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException("SQL exception occurred!", e);
        }  finally{
            closeConnection( preparedStatement, connection);
        }

        return user;
    }

    @Override
    public User getUserByeMail(String email) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            String sql = "SELECT * FROM User WHERE e_mail=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                user = new User();
                setUserDetails(resultSet, user);
            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection( preparedStatement, connection);
        }
        return user;
    }

    @Override
    public User getUserByUsername(String userName) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            String sql = "SELECT * FROM user WHERE username=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                user = new User();
                setUserDetails(resultSet, user);
            }
            return user;
        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally{
            closeConnection( preparedStatement, connection);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            users = new ArrayList<User>();
            String sql = "SELECT * FROM User ORDER BY name, surname";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                setUserDetails(resultSet, user);
                users.add(user);
            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return users;
    }

    @Override
    public void updateUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(user.getId()!=0) {
                connection = dataSource.getConnection();
                String sql = "UPDATE User SET " +
                        "name = ?, surname = ?, username = ?, password = ?, address =?, e_mail = ?, phone =?, access_privilege = ?" +
                        " WHERE user_id=?";

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getSurname());
                preparedStatement.setString(3, user.getUsername());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setString(5, user.getAddress());
                preparedStatement.setString(6, user.geteMail());
                preparedStatement.setString(7, user.getPhone());
                preparedStatement.setString(8, user.getAccessPrivilege());
                preparedStatement.setInt(9, user.getId());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection( preparedStatement, connection);
        }
    }

    @Override
    public void deleteUser(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();

            String sqlUser ="DELETE FROM user WHERE user_id=?";
            preparedStatement = connection.prepareStatement(sqlUser);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection(connection);
        }
    }

    @Override
    public void deleteAll(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM User";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection(preparedStatement, connection);
        }
    }

    private void setUserDetails(ResultSet rs, User user) throws SQLException {
        user.setId(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setAddress(rs.getString("address"));
        user.seteMail(rs.getString("e_mail"));
        user.setPhone(rs.getString("phone"));
        user.setAccessPrivilege(rs.getString("access_privilege"));
    }
}
