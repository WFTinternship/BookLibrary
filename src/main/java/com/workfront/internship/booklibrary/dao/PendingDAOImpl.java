package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

            preparedStatement.setInt(1, pending.getUser().getId());
            preparedStatement.setInt(2, pending.getBook().getId());
            preparedStatement.setTimestamp(3, new Timestamp(pending.getPendingDate().getTime()));

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
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

            String sql = "SELECT * FROM Pending inner join User ON pending.user_id = User.user_id" +
                    " inner join Book ON Pending.book_id = Book.book_id inner join Genre " +
                    " ON Book.genre_id = Genre.genre_id" +
                    " where pending.pending_id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                pending = new Pending();

                setPendingDetails(resultSet, pending);
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            List<Pending> pendingList = new ArrayList<Pending>();

            String sql = "SELECT * FROM Pending inner join User ON pending.user_id = User.user_id" +
                    " inner join Book ON Pending.book_id = Book.book_id inner join Genre " +
                    " ON Book.genre_id = Genre.genre_id " +
                    "where pending.book_id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Pending pending = new Pending();

                setPendingDetails(resultSet, pending);

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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            List<Pending> pendingList = new ArrayList<Pending>();

            String sql = "SELECT * FROM Pending inner join User ON pending.user_id = User.user_id" +
                    " inner join Book ON Pending.book_id = Book.book_id inner join Genre " +
                    " ON Book.genre_id = Genre.genre_id " +
                    "where pending.user_id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Pending pending = new Pending();

                setPendingDetails(resultSet, pending);

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
            String sql = "DELETE FROM Pending where pending_id=?";

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

    private void setPendingDetails(ResultSet resultSet, Pending pending) throws SQLException {
        Book book = new Book();
        Genre genre = new Genre();
        User user = new User();

        pending.setId(resultSet.getInt("pending_id"));
        pending.setPendingDate(resultSet.getTimestamp("pending_time"));

        genre.setId(resultSet.getInt("genre_id")).setGenre(resultSet.getString("genre"));

        book.setId(resultSet.getInt("book_id")).setISBN(resultSet.getString("ISBN")).setTitle(resultSet.getString("title"));
        book.setGenre(genre).setVolume(resultSet.getInt("volume")).setBookAbstract(resultSet.getString("abstract"));
        book.setLanguage(resultSet.getString("language")).setCount(resultSet.getInt("count"));
        book.setEditionYear(resultSet.getString("edition_year")).setPages(resultSet.getInt("pages"));
        book.setCountryOfEdition(resultSet.getString("country_of_edition"));
        book.setGenre(genre);

        user.setId(resultSet.getInt("user_id")).setName(resultSet.getString("name")).setSurname(resultSet.getString("surname"));
        user.setUsername(resultSet.getString("username")).setPassword(resultSet.getString("password"));
        user.setAddress(resultSet.getString("address")).seteMail(resultSet.getString("e_mail"));
        user.setPhone(resultSet.getString("phone")).setAccessPrivilege(resultSet.getString("access_privilege"));

        pending.setBook(book);
        pending.setUser(user);
    }
}
