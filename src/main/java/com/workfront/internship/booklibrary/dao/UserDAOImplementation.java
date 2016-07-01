package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.User;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.List;


/**
 * Created by Workfront on 7/1/2016.
 */
public class UserDAOImplementation extends General implements UserDAO {

    public void createUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try{
            DataSource ds = DataSource.getInstance();
            connection = ds.getConnection();

            String sql;
            sql = "INSERT INTO User VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUser_id());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getE_mail());
            preparedStatement.setString(8, user.getPhone());
            preparedStatement.setString(9, user.getAccess_privilage());

            preparedStatement.executeUpdate();

            closeConnection(null, preparedStatement, connection);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closeConnection(null, preparedStatement, connection);
        }

    }

    public User getUserByID(int id) {
        return null;
    }

    public List<User> getAllAuthors() {
        return null;
    }

    public void updateUser(User u) {

    }

    public void deleteUser(User u) {

    }

}
