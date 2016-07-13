package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.User;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl extends General implements UserDAO {

    public int add(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int lastId = 0;

        try{
            connection = DataSource.getInstance().getConnection();

            String sql;
            sql = "INSERT INTO User(name, surname, username, password, address, e_mail, phone, access_privelege) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
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
            // TODO add log
           throw new RuntimeException(e);
        } finally{
            closeConnection( preparedStatement, connection);
        }
        return user.getId();

    }

    public User getUserByID(int id) {
        User user = new User();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DataSource.getInstance().getConnection();
            String sql;
            sql = "SELECT * FROM User WHERE user_id=" + id;

            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setUsername(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setAddress(rs.getString(6));
                user.seteMail(rs.getString(7));
                user.setPhone(rs.getString(8));
                user.setAccessPrivilege(rs.getString(9));
            }

        } catch (IOException | SQLException e){
            e.printStackTrace();
        }  finally{
            closeConnection( preparedStatement, connection);
        }

        return user;
    }

    public User getUserByName(String name) {
        User user = new User();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DataSource.getInstance().getConnection();
            String sql;
            sql = "SELECT * FROM User WHERE name=" + name;

            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setUsername(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setAddress(rs.getString(6));
                user.seteMail(rs.getString(7));
                user.setPhone(rs.getString(8));
                user.setAccessPrivilege(rs.getString(9));
            }

        } catch (IOException | SQLException e){
            e.printStackTrace();
        } finally{
            closeConnection( preparedStatement, connection);
        }

        return user;
    }

    public User getUserByUsername(String userName) {
        User user = new User();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DataSource.getInstance().getConnection();
            String sql;
            sql = "SELECT * FROM User WHERE username=" + userName;

            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setUsername(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setAddress(rs.getString(6));
                user.seteMail(rs.getString(7));
                user.setPhone(rs.getString(8));
                user.setAccessPrivilege(rs.getString(9));
            }

        } catch (IOException | SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection( preparedStatement, connection);
        }

        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = null;
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSets = null;
        try{
            connection = DataSource.getInstance().getConnection();
            users = new ArrayList<User>();
            String sql;
            sql = "SELECT * FROM User";
            preparedStatement = connection.prepareStatement(sql);
            resultSets = preparedStatement.executeQuery();
            //ResultSetMetaData rsmd = rs.getMetaData();
            while (resultSets.next()) {
                user = new User();
                user.setId(resultSets.getInt(1));
                user.setName(resultSets.getString(2));
                user.setSurname(resultSets.getString(3));
                user.setUsername(resultSets.getString(4));
                user.setPassword(resultSets.getString(5));
                user.setAddress(resultSets.getString(6));
                user.seteMail(resultSets.getString(7));
                user.setPhone(resultSets.getString(8));
                user.setAccessPrivilege(resultSets.getString(9));

                users.add(user);
            }

        } catch (IOException | SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(resultSets, preparedStatement, connection);
        }

        return users;
    }

    public void updateUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(user.getId()!=0) {
                connection = DataSource.getInstance().getConnection();
                String sql;
                sql = "UPDATE User SET " +
                        "name = ?, surname = ?, username = ?, password = ?, address =?, e_mail = ?, phone =?, access_privilege = ?" +
                        " WHERE user_id=" + user.getId();

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getSurname());
                preparedStatement.setString(3, user.getUsername());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setString(5, user.getAddress());
                preparedStatement.setString(6, user.geteMail());
                preparedStatement.setString(7, user.getPhone());
                preparedStatement.setString(8, user.getAccessPrivilege());

                preparedStatement.executeUpdate();
            }

        } catch (IOException | SQLException e){
            e.printStackTrace();
        } finally{
            closeConnection( preparedStatement, connection);
        }

    }

    public void deleteUser(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DataSource.getInstance().getConnection();

            String sql;
            sql ="DELETE FROM User WHERE user_id="  + id;
            //  sql ="DELETE FROM User WHERE user_id=?";
            // preparedStatement.setInt(1, id);

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (IOException | SQLException e){
            e.printStackTrace();
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
            String sql;
            sql = "SELECT * FROM user left join pick_book" +
                    "on user.user_id = pick_book.user_id" +
                    "where pick_book.book_id =" + bookId;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User user = new User();

                user.setId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAddress(resultSet.getString("address"));
                user.seteMail(resultSet.getString("e_mail"));
                user.setPhone(resultSet.getString("phone"));
                user.setAccessPrivilege(resultSet.getString("access_privilege"));

                userList.add(user);
            }


        } catch (IOException | SQLException e){
            e.printStackTrace();
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
            String sql;
            sql = "SELECT * FROM user left join pending" +
                    "on user.user_id = pending.user_id" +
                    "where pending.book_id =" + bookId;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User user = new User();

                user.setId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAddress(resultSet.getString("address"));
                user.seteMail(resultSet.getString("e_mail"));
                user.setPhone(resultSet.getString("phone"));
                user.setAccessPrivilege(resultSet.getString("access_privilege"));

                userList.add(user);
            }


        }catch (IOException | SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return userList;
    }
}
