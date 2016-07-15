package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Pending;
import com.workfront.internship.booklibrary.dao.DataSource;
import com.workfront.internship.booklibrary.dao.General;
import com.workfront.internship.booklibrary.dao.PendingDAO;
import com.workfront.internship.booklibrary.dao.PendingDAOImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class TestPendingDAOImpl {
    Pending pending = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Before
    public void setUp(){
        try{
            connection = DataSource.getInstance().getConnection();
            pending = new Pending();
            pending.setId(4);
            pending.setUserId(3);
            pending.setBookId(2);
            pending.setPendingDate(Timestamp.valueOf("2016-08-12 01:02:03"));

            String sql;
            sql = "INSERT INTO Pending VALUES(?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, pending.getId());
            preparedStatement.setInt(2, pending.getUserId());
            preparedStatement.setInt(3, pending.getBookId());
            preparedStatement.setTimestamp(4, new Timestamp(pending.getPendingDate().getTime()));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown(){
        try{
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
            if (resultSet != null) resultSet.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void TestCreatePending() {
        PendingDAO pendingDao = new PendingDAOImpl();
        Pending actualPending = pendingDao.getPendingByID(pending.getId());


        assertNotNull(actualPending);
        assertEquals(pending.getId(), actualPending.getId());
        assertEquals(pending.getUserId(), actualPending.getUserId());
        assertEquals(pending.getBookId(), actualPending.getBookId());
        assertEquals(pending.getPendingDate(), actualPending.getPendingDate());

    }


/**
    @Test
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



    @Test
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



    @Test
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
    */
}
