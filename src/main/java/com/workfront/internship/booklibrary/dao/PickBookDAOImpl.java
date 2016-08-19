package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PickBookDAOImpl extends General implements PickBookDAO {

    private static final Logger LOGGER = Logger.getLogger(BookDAOImpl.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private UserDAO userDAO;

    public PickBookDAOImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        this.bookDAO = new BookDAOImpl(dataSource);
        this.userDAO = new UserDAOImpl(dataSource);
    }

    @Override
    public int add(PickBook pickedBook) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int lastId = 0;

        try{
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            bookDAO.updateBook(connection, pickedBook.getBook());

            String sql = "INSERT INTO Pick_Book(book_id, user_id, picking_date, return_date) VALUES(?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, pickedBook.getBook().getId());
            preparedStatement.setInt(2, pickedBook.getUser().getId());
            preparedStatement.setTimestamp(3, new Timestamp(pickedBook.getPickingDate().getTime()));
            preparedStatement.setTimestamp(4, new Timestamp(pickedBook.getReturnDate().getTime()));

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            pickedBook.setId(lastId);

            connection.commit();


        } catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(preparedStatement, connection);
        }
        return pickedBook.getId();
    }

    @Override
    public PickBook getPickedBookByID(int id) {
        PickBook pickedBook = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();

            String sql = "SELECT * FROM Pick_Book inner join User ON Pick_Book.user_id = User.user_id" +
                    " inner join Book ON Pick_Book.book_id = Book.book_id inner join Genre " +
                    " ON Book.genre_id = Genre.genre_id" +
                    " where Pick_Book.pick_id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                pickedBook = new PickBook();

                setPickBookDetails(resultSet, pickedBook);
            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
        return pickedBook;
    }

    @Override
    public List<PickBook> getAllPickedBooks() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            List<PickBook> pickedBookList = new ArrayList<PickBook>();

            String sql = "SELECT * FROM pick_book INNER JOIN User ON Pick_Book.user_id = User.user_id " +
                    "INNER JOIN Book on Pick_Book.book_id = Book.book_id " +
                    "INNER JOIN Genre ON Book.genre_id = Genre.genre_id";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                PickBook pickedBook = new PickBook();

                setPickBookDetails(resultSet, pickedBook);

                pickedBookList.add(pickedBook);
            }
            return pickedBookList;

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void updatePickedBook(PickBook pickedBook) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            if(pickedBook.getId() != 0){
                connection = dataSource.getConnection();
                String sql = "UPDATE Pick_Book SET " +
                        "book_id = ?, user_id = ?, picking_date = ?, return_date = ? " +
                        "WHERE pick_id =?";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, pickedBook.getBook().getId());
                preparedStatement.setInt(2, pickedBook.getUser().getId());
                preparedStatement.setTimestamp(3, new Timestamp(pickedBook.getPickingDate().getTime()));
                preparedStatement.setTimestamp(4, new Timestamp(pickedBook.getReturnDate().getTime()));
                preparedStatement.setInt(5, pickedBook.getId());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }


    @Override
    public void deletePickedBook(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM Pick_Book where pick_id=?";

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
    public void deleteAllPickedBooks() {
        Connection connection = null;
        PreparedStatement preparedStatement =null;

        try{
            connection = dataSource.getConnection();
            String sql = "DELETE FROM Pick_Book";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(preparedStatement, connection);
        }
    }

    @Override
    public List<PickBook> getAllPickedBooksByUserId(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            List<PickBook> pickedBookList = new ArrayList<PickBook>();

            String sql = "SELECT * FROM Pick_Book INNER JOIN User ON Pick_Book.user_id = User.user_id " +
                    "INNer JOIN Book ON Pick_Book.book_id = Book.book_id INNER JOIN Genre " +
                    "ON Book.genre_id = Genre.genre_id " +
                    "where pick_book.user_id =?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                PickBook pickedBook = new PickBook();

                setPickBookDetails(resultSet, pickedBook);

                pickedBookList.add(pickedBook);
            }
            return pickedBookList;
        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        } finally {
            closeConnection(resultSet, preparedStatement, connection);
        }
    }

    private void setPickBookDetails(ResultSet resultSet, PickBook pickBook) throws SQLException {
        Book book = new Book();
        Genre genre = new Genre();
        User user = new User();

        pickBook.setId(resultSet.getInt("pick_id"));
        pickBook.setPickingDate(resultSet.getTimestamp("picking_date"));
        pickBook.setReturnDate(resultSet.getTimestamp("return_date"));

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
        user.setConfirmationStatus(resultSet.getBoolean("confirmation_status"));

        pickBook.setBook(book);
        pickBook.setUser(user);
    }
}
