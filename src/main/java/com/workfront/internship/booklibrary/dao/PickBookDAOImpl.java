package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.PickBook;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PickBookDAOImpl extends General implements PickBookDAO {

    @Override
    public int add(PickBook pickedBook) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementUpdateBookCount = null;
        ResultSet resultSet = null;
        Book book = null;
        int lastId = 0;

        try{
            connection = DataSource.getInstance().getConnection();
            //connection.setAutoCommit(false);

            String sql = "INSERT INTO Pick_Book(book_id, user_id, picking_date, return_date) VALUES(?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);
            //preparedStatement.setInt(1, pickedBook.getId());
            preparedStatement.setInt(1, pickedBook.getBookId());
            preparedStatement.setInt(2, pickedBook.getUserId());
            preparedStatement.setTimestamp(3, new Timestamp(pickedBook.getPickingDate().getTime()));
            preparedStatement.setTimestamp(4, new Timestamp(pickedBook.getReturnDate().getTime()));

            String sqlBook = "UPDATE book SET count=? WHERE book_id=?";
            preparedStatementUpdateBookCount = connection.prepareStatement(sqlBook);
            book.setId(pickedBook.getId());
            preparedStatementUpdateBookCount.setInt(1, (book.getCount()-1));
            preparedStatementUpdateBookCount.setInt(2, book.getId());

            if(book.getCount()>0){
                preparedStatement.executeUpdate();
                preparedStatementUpdateBookCount.executeUpdate();
                connection.commit();
            }
            else{
                connection.rollback();
            }



            /**int changeMade = preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            pickedBook.setId(lastId);

            if(changeMade == 1){  //todo transaction sarqel

            } */

        } catch (IOException | SQLException e){
            e.printStackTrace();

        }finally {
            closeConnection(preparedStatement, connection);
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lastId; //todo sxal e !!!
    }

    @Override
    public PickBook getPickedBookByID(int id) {
        PickBook pickedBook = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            pickedBook = new PickBook();
            String sql = "select * from Pick_Book where Pick_Book.pick_id =" + pickedBook.getId();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                pickedBook.setId(resultSet.getInt("pick_id"));
                pickedBook.setBookId(resultSet.getInt("book_id"));
                pickedBook.setUserId(resultSet.getInt("user_id"));
                pickedBook.setPickingDate(resultSet.getTimestamp("picking_date"));
                pickedBook.setReturnDate(resultSet.getTimestamp("return_date"));
            }

        } catch (IOException | SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
        return pickedBook;
    }

    @Override
    public List<PickBook> getAllPickedBooks() {
        List<PickBook> pickedBookList = null;
        Book book = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            pickedBookList = new ArrayList<PickBook>();

            String sql = "SELECT * FROM pick_book";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                PickBook pickedBook = new PickBook();

                pickedBook.setId(resultSet.getInt("pick_id"));
                pickedBook.setBookId(resultSet.getInt("book_id"));
                pickedBook.setUserId(resultSet.getInt("user_id"));
                pickedBook.setPickingDate(resultSet.getTimestamp("picking_date"));
                pickedBook.setReturnDate(resultSet.getTimestamp("return_date"));

                pickedBookList.add(pickedBook);
            }


        } catch (IOException | SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return pickedBookList;
    }

    @Override
    public void updatePickedBook(PickBook pickedBook) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(pickedBook.getId() != 0){
                connection = DataSource.getInstance().getConnection();
                String sql = "UPDATE Pick_Book SET " +
                        "pick_id = ?, book_id = ?, user_id = ?, picking_date = ?, return_date = ? " +
                        "WHERE pick_id = " + pickedBook.getId();
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, pickedBook.getId());
                preparedStatement.setInt(2, pickedBook.getBookId());
                preparedStatement.setInt(3, pickedBook.getUserId());
                //preparedStatement.setDate(4, pickedBook.getPickingDate()); // TODO
                //preparedStatement.setDate(5, pickedBook.getReturnDate()); // TODO

                preparedStatement.executeUpdate();
            }

        } catch (IOException | SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }

    @Override
    public void deletePickedBook(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DataSource.getInstance().getConnection();
            String sql = "DELETE * FROM Pick_Book where pick_id=" + id;

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (IOException | SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }

    @Override
    public List<PickBook> getAllPickedBooksByUserId(int userId) {
        List<PickBook> pickedBookList = null;
        //Book book = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DataSource.getInstance().getConnection();
            pickedBookList = new ArrayList<PickBook>();

            String sql = "SELECT * FROM pick_book" +
                    "where pick_book.user_id =?";

            preparedStatement.setInt(1, userId);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                PickBook pickedBook = new PickBook();

                pickedBook.setId(resultSet.getInt("pick_id"));
                pickedBook.setBookId(resultSet.getInt("book_id"));
                pickedBook.setUserId(resultSet.getInt("user_id"));
                pickedBook.setPickingDate(resultSet.getTimestamp("picking_date"));
                pickedBook.setReturnDate(resultSet.getTimestamp("return_date"));

                pickedBookList.add(pickedBook);
            }


        } catch (IOException | SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }

        return pickedBookList;
    }
}
