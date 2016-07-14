package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.User;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl extends General implements UserDAO {

    final static Logger logger = Logger.getLogger("User logger");

    @Override
    public int add(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int lastId = 0;

        try{
            connection = DataSource.getInstance().getConnection();

            String sql = "INSERT INTO User(name, surname, username, password, address, e_mail, phone, access_privilege) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);
            //preparedStatement.setInt(1, user.getId());
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

        } catch (IOException | SQLException e) {
            //todo
            logger.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection( preparedStatement, connection);
        }
        return user.getId();

    }

    @Override
    public User getUserByID(int id) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DataSource.getInstance().getConnection();
            String sql = "SELECT * FROM User WHERE user_id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                user = new User();
                setUserDetails(rs, user);
            }

        } catch (IOException | SQLException e){
            logger.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
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
        try{
            connection = DataSource.getInstance().getConnection();
            String sql = String.format("SELECT * FROM User WHERE e_mail= '%s'", email);

            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                user = new User();
                setUserDetails(rs, user);
            }

        } catch (IOException | SQLException e){
            logger.error("IO exception or SQL exception occurred!");
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
        try{
            connection = DataSource.getInstance().getConnection();
            String sql = String.format("SELECT * FROM User WHERE username= '%s'", userName);

            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                user = new User();
                setUserDetails(rs, user);
            }

        } catch (IOException | SQLException e){
            logger.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        }finally{
            closeConnection( preparedStatement, connection);
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSets = null;
        try{
            connection = DataSource.getInstance().getConnection();
            users = new ArrayList<User>();
            String sql = "SELECT * FROM User";
            preparedStatement = connection.prepareStatement(sql);
            resultSets = preparedStatement.executeQuery();
            //ResultSetMetaData rsmd = rs.getMetaData();
            while (resultSets.next()) {
                user = new User();
                setUserDetails(resultSets, user);
                users.add(user);
            }

        } catch (IOException | SQLException e){
            logger.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSets, preparedStatement, connection);
        }

        return users;
    }

    @Override
    public void updateUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(user.getId()!=0) {
                connection = DataSource.getInstance().getConnection();
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

        } catch (IOException | SQLException e){
            logger.error("IO exception or SQL exception occurred!");
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
            connection = DataSource.getInstance().getConnection();

            //String sql ="DELETE FROM User WHERE user_id="  + id;
            String sqlUser ="DELETE FROM user WHERE user_id=?";
            preparedStatement = connection.prepareStatement(sqlUser);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (IOException | SQLException e){
            logger.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection(connection);
        }
    }

    public void deleteAll(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql = "DELETE FROM User";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (IOException | SQLException e){
            logger.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally{
            closeConnection( preparedStatement, connection);
        }
    }

    public List<User> getAllUsersByPickedBookId(int bookId){
        List<User> userList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            userList = new ArrayList<User>();
            String sql = "SELECT * FROM user left join pick_book" +
                    "on user.user_id = pick_book.user_id" +
                    "where pick_book.book_id =" + bookId;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User user = new User();
                setUserDetails(resultSet, user);
                userList.add(user);
            }


        } catch (IOException | SQLException e){
            logger.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
        return userList;
    }

    public List<User> getAllUsersByPendingBookId(int bookId){
        List<User> userList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            userList = new ArrayList<User>();
            String sql = "SELECT * FROM user left join pending" +
                    "on user.user_id = pending.user_id" +
                    "where pending.book_id =" + bookId;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User user = new User();
                setUserDetails(resultSet, user);
                userList.add(user);
            }


        }catch (IOException | SQLException e){
            logger.error("IO exception or SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return userList;
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
