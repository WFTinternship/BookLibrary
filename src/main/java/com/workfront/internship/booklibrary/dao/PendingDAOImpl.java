package com.workfront.internship.booklibrary.dao;

 import com.workfront.internship.booklibrary.common.Pending;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PendingDAOImpl extends General implements PendingDAO{

    public void createPending(Pending pending) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql;
            sql = "INSERT INTO Pending VALUES(?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, pending.getPendingId());
            preparedStatement.setInt(2, pending.getUserId());
            preparedStatement.setInt(3, pending.getBookId());
            preparedStatement.setTimestamp(4, new Timestamp(pending.getPendingDate().getTime()));

            preparedStatement.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }finally {
            closeConnection(preparedStatement, connection);
        }
    }

    public Pending getPendingByID(int id) {
        Pending pending = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            pending = new Pending();
            String sql;
            sql = "select * from pending where pending.pending_id =" + pending.getPendingId();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                pending.setPendingId(resultSet.getInt("pending_id"));
                pending.setUserId(resultSet.getInt("user_id"));
                pending.setBookId(resultSet.getInt("book_id"));
                pending.setPendingDate(resultSet.getTimestamp("pending_time"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
        return pending;
    }

    public List<Pending> getAllPendingsByBookID(int bookid){
        List<Pending> pendingList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            pendingList = new ArrayList<Pending>();
            String sql;
            sql = "select * from pending left join book" +
                    "on pending.book_id = book.book_id" +
                    "where pending.book_id =" + bookid;

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Pending pending = new Pending();

                pending.setPendingId(resultSet.getInt("pending_id"));
                pending.setUserId(resultSet.getInt("user_id"));
                pending.setBookId(resultSet.getInt("book_id"));
                pending.setPendingDate(resultSet.getTimestamp("pending_time"));

                pendingList.add(pending);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
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
            String sql;
            sql = "DELETE * FROM Pending where pending_id=" + id;

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }
}
