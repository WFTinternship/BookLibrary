package com.workfront.internship.booklibrary.dao;

 import com.workfront.internship.booklibrary.common.Book;
 import com.workfront.internship.booklibrary.common.Pending;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PendingDAOImpl extends General implements PendingDAO{

    public int add(Pending pending) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int lastId = 0;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql = "INSERT INTO Pending(user_id, book_id, pending_time) VALUES(?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            //preparedStatement.setInt(1, pending.getPendingId());
            preparedStatement.setInt(1, pending.getUserId());
            preparedStatement.setInt(2, pending.getBookId());
            preparedStatement.setTimestamp(3, new Timestamp(pending.getPendingDate().getTime()));

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            pending.setUserId(lastId);

        } catch (IOException | SQLException e){
            e.printStackTrace();
            //todo use log4j
        }finally {
            closeConnection(preparedStatement, connection);
        }
        return pending.getId();
    }

    public Pending getPendingByID(int id) {
        Pending pending = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            pending = new Pending();
            String sql = "select * from pending where pending.pending_id =" + id; //pending.getPendingId();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                pending.setId(resultSet.getInt("pending_id"));
                pending.setUserId(resultSet.getInt("user_id"));
                pending.setBookId(resultSet.getInt("book_id"));
                pending.setPendingDate(resultSet.getTimestamp("pending_time"));
            }

        } catch (IOException | SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
        return pending;
    }

    public List<Pending> getAllPendingsByBookID(int bookId){
        List<Pending> pendingList = null;
        Book book = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            pendingList = new ArrayList<Pending>();

            String sql = "SELECT * FROM pending" +
                    "where pending.book_id" + bookId;

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Pending pending = new Pending();

                pending.setId(resultSet.getInt("pending_id"));
                pending.setUserId(resultSet.getInt("user_id"));
                pending.setBookId(resultSet.getInt("book_id"));
                pending.setPendingDate(resultSet.getTimestamp("pending_time"));

                pendingList.add(pending);
            }


        } catch (IOException | SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return pendingList;
    }

    public List<Pending> getAllPendingsByUserID(int userId){
        List<Pending> pendingList = null;
        Book book = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            pendingList = new ArrayList<Pending>();

            String sql = "SELECT * FROM pending" +
                    "where pending.user_id" + userId;

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Pending pending = new Pending();

                pending.setId(resultSet.getInt("pending_id"));
                pending.setUserId(resultSet.getInt("user_id"));
                pending.setBookId(resultSet.getInt("book_id"));
                pending.setPendingDate(resultSet.getTimestamp("pending_time"));

                pendingList.add(pending);
            }


        } catch (IOException | SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return pendingList;
    }

    public void deletePending(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql = "DELETE * FROM Pending where pending_id=" + id;

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (IOException | SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }
}
