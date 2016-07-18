package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Pending;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.workfront.internship.booklibrary.common.PickBook;
import com.workfront.internship.booklibrary.common.User;
import org.apache.log4j.Logger;

public class PendingDAOImpl extends General implements PendingDAO{
    private static final Logger LOGGER = Logger.getLogger(BookDAOImpl.class);

    private DataSource dataSource;
    private BookDAO bookDAO;
    private UserDAO userDAO;

    public PendingDAOImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        this.bookDAO = new BookDAOImpl(dataSource);
        this.userDAO = new UserDAOImpl(dataSource);
    }

    public int add(Pending pending) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int lastId = 0;

        try{
            connection = dataSource.getConnection();
            String sql = "INSERT INTO Pending(user_id, book_id, pending_time) VALUES(?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            //preparedStatement.setInt(1, pending.getPendingId());
            preparedStatement.setInt(1, pending.getUser().getId());
            preparedStatement.setInt(2, pending.getBook().getId());
            preparedStatement.setTimestamp(3, new Timestamp(pending.getPendingDate().getTime()));

            preparedStatement.executeUpdate();
            resultSet =preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            pending.setId(lastId);

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
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
            connection = dataSource.getConnection();

            String sql = "select * from pending where pending.pending_id =" + id; //pending.getPendingId();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                pending = new Pending();
                User user = new User();
                Book book = new Book();

                setPendingDetails(resultSet, pending);

                user.setId(resultSet.getInt("user_id"));
                book.setId(resultSet.getInt("book_id"));
                pending.setUser(user);
                pending.setBook(book);

            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
        return pending;
    }

    public List<Pending> getAllPendingsByBookID(int bookId){
        List<Pending> pendingList = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            pendingList = new ArrayList<Pending>();

            String sql = "SELECT * FROM pending" +
                    "where pending.book_id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Pending pending = new Pending();
                pending = new Pending();
                User user = new User();
                Book book = new Book();

                setPendingDetails(resultSet, pending);

                user.setId(resultSet.getInt("user_id"));
                book.setId(resultSet.getInt("book_id"));
                pending.setUser(user);
                pending.setBook(book);

                pendingList.add(pending);
            }
            return pendingList;
        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public List<Pending> getAllPendingsByUserID(int userId){
        List<Pending> pendingList = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            pendingList = new ArrayList<Pending>();

            String sql = "SELECT * FROM pending" +
                    "where pending.user_id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Pending pending = new Pending();

                pending = new Pending();
                User user = new User();
                Book book = new Book();

                setPendingDetails(resultSet, pending);

                user.setId(resultSet.getInt("user_id"));
                book.setId(resultSet.getInt("book_id"));
                pending.setUser(user);
                pending.setBook(book);

                pendingList.add(pending);
            }
            return pendingList;
        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
    }

    public void deletePending(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE * FROM Pending where pending_id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }

    @Override
    public void deleteAllPendings() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM Pending";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }

    private void setPendingDetails(ResultSet rs, Pending pending) throws SQLException {
        pending.setId(rs.getInt("pick_id"));
        pending.setPendingDate(rs.getTimestamp("pending_time"));
    }
}
