package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.PickBook;
import com.workfront.internship.booklibrary.common.User;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class PickBookDAOImpl extends General implements PickBookDAO {

    private static final Logger LOGGER = Logger.getLogger(BookDAOImpl.class);

    private DataSource dataSource;
    private BookDAO bookDAO;
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
            //connection.setAutoCommit(false);

            String sql = "INSERT INTO Pick_Book(book_id, user_id, picking_date, return_date) VALUES(?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, pickedBook.getBook().getId());
            preparedStatement.setInt(2, pickedBook.getUser().getId());
            preparedStatement.setTimestamp(3, new Timestamp(pickedBook.getPickingDate().getTime()));
            preparedStatement.setTimestamp(4, new Timestamp(pickedBook.getReturnDate().getTime()));

            preparedStatement.executeUpdate();
            resultSet =preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
            pickedBook.setId(lastId);

/**            String sqlBook = "UPDATE book SET count=? WHERE book_id=?";
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
 */

        } catch (SQLException e){
            LOGGER.error("SQL exception occurred!");
            throw new RuntimeException(e);
        }finally {
            closeConnection(preparedStatement, connection);
/**            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
 */
        }
        return pickedBook.getId(); //todo
    }

    @Override
    public PickBook getPickedBookByID(int id) {
        PickBook pickedBook = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();

            String sql = "select * from Pick_Book where Pick_Book.pick_id =?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                pickedBook = new PickBook();
                Book book = new Book();
                User user = new User();

                setPickBookDetails(resultSet, pickedBook);

                book.setId(resultSet.getInt("book_id"));
                user.setId(resultSet.getInt("user_id"));
                pickedBook.setBook(book);
                pickedBook.setUser(user);
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
        List<PickBook> pickedBookList = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            pickedBookList = new ArrayList<PickBook>();

            String sql = "SELECT * FROM pick_book INNER JOIN User ON Pick_Book.user_id = User.user_id " +
                    "INNER JOIN Book on Pick_Book.book_id = Book.book_id " +
                    "INNER JOIN Genre ON Book.genre_id = Genre.genre_id";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                PickBook pickedBook = new PickBook();
                Book book = new Book();
                User user = new User();

                setPickBookDetails(resultSet, pickedBook);

                book.setId(resultSet.getInt("book_id"));
                user.setId(resultSet.getInt("user_id"));
                pickedBook.setBook(book);
                pickedBook.setUser(user);

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
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                String dateString = dateFormat.format(pickedBook.getPickingDate().getTime());
//                Date date = new Date();
//                Date sqlDate = new Date(0000-00-00);

                preparedStatement.setInt(1, pickedBook.getBook().getId());
                preparedStatement.setInt(2, pickedBook.getUser().getId());
                //preparedStatement.setDate(3, pickedBook.getPickingDate()); // TODO
                //preparedStatement.setDate(4, pickedBook.getReturnDate()); // TODO
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
            String sql = "DELETE * FROM Pick_Book where pick_id=?";

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
        List<PickBook> pickedBookList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();
            pickedBookList = new ArrayList<PickBook>();

            String sql = "SELECT * FROM Pick_Book INNER JOIN User ON Pick_Book.user_id = User.user_id " +
                    "INNer JOIN Book ON Pick_Book.book_id = Book.book_id INNER JOIN Genre " +
                    "ON Book.genre_id = Genre.genre_id " +
                    "where pick_book.user_id =?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                PickBook pickedBook = new PickBook();
                Book book = new Book();
                User user = new User();

                setPickBookDetails(resultSet, pickedBook);

                book.setId(resultSet.getInt("book_id"));
                user.setId(resultSet.getInt("user_id"));
                pickedBook.setBook(book);
                pickedBook.setUser(user);

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

        pickBook.setBook(book);
        pickBook.setUser(user);
    }

    private void setPickBookDetails(ResultSet rs, PickBook pickBook) throws SQLException {
        pickBook.setId(rs.getInt("pick_id"));
        pickBook.setPickingDate(rs.getTimestamp("picking_date"));
        pickBook.setReturnDate(rs.getTimestamp("return_date"));
    }
}
