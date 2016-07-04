package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sona on 7/1/2016.
 */
public class UserDAOImpl extends General implements UserDAO {

    public void createUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try{
            connection = DataSource.getInstance().getConnection();

            String sql;
            sql = "INSERT INTO User VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.geteMail());
            preparedStatement.setString(8, user.getPhone());
            preparedStatement.setString(9, user.getAccessPrivilege());

            preparedStatement.executeUpdate();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closeConnection( preparedStatement, connection);
        }

    }

    public User getUserByID(int id) {
        User user = new User();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DataSource.getInstance().getConnection();
            String sql;
            sql = "SELECT * FROM User WHERE user_id=" + id + "";

            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            // ResultSet rs = statement.executeQuery();
            // int columns = rs.getColumnCount();
            while(rs.next()){
                user.setUserId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setUsername(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setAddress(rs.getString(6));
                user.seteMail(rs.getString(7));
                user.setPhone(rs.getString(8));
                user.setAccessPrivilege(rs.getString(9));
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection( preparedStatement, connection);
        }

        return user;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<User>();
        return null;
    }// TODO

    public void updateUser(User u) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(u.getUserId()!=0) {
                connection = DataSource.getInstance().getConnection();
                String sql;
                sql = "UPDATE User SET " +
                        "name = ?, surname = ?, username = ?, password = ?, address =?, e_mail = ?, phone =?, access_privilege = ?" +
                        " WHERE user_id=" + u.getUserId();

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, u.getName());
                preparedStatement.setString(2, u.getSurname());
                preparedStatement.setString(3, u.getUsername());
                preparedStatement.setString(4, u.getPassword());
                preparedStatement.setString(5, u.getAddress());
                preparedStatement.setString(6, u.geteMail());
                preparedStatement.setString(7, u.getPhone());
                preparedStatement.setString(8, u.getAccessPrivilege());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            closeConnection( preparedStatement, connection);
        }

    }

    public void deleteUser(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DataSource.getInstance().getConnection();

            String sql;
            sql ="DELETE FROM User WHERE user_id="  + id + "";
            //  sql ="DELETE FROM User WHERE user_id=?";
            // preparedStatement.setInt(1, id);

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            closeConnection( preparedStatement, connection);
        }
    }

}
