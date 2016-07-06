package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.common.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sona on 7/1/2016.
 */
public class Test {
    public static void main(String[] args) {
        // createUserTest();
        //deleteUserTest();
        //getUserByIDTest();
        //getAllUsersTest();
        //updateUserTest();
        createBookTest();
        //getBookByIDTest();
    }

    public static void createUserTest() {
        User user = new User();

        user.setName("Artur");
        user.setSurname("Margaryan");
        user.setUsername("arturmar");
        user.setPassword("arturmarpass");
        user.setAddress("Yerevan, str, home");
        user.seteMail("arturmargaryan@workfront.com");
        user.setPhone("095243841");
        user.setAccessPrivilege("admin");

        UserDAOImpl userDao = new UserDAOImpl();
        userDao.createUser(user);
    }
    public static void createBookTest() {
        Book book = new Book();

        Genre genre = new Genre();
        genre.setGenreId(1);

        book.setISBN("165465416541");
        book.setTitle("MYSQL workbench");

        book.setGenre(genre);
        book.setVolume(0);
        book.setBookAbstract("Working with workbench");
        book.setLanguage("English");
        book.setCount(4);
        book.setEditionYear("2015");
        book.setPages(120);
        book.setCountryOfEdition("UK");

        BookDAOImpl bookDAO = new BookDAOImpl();
        bookDAO.createBook(book);
    }

    public static void deleteUserTest() {
        UserDAOImpl userDao = new UserDAOImpl();
        userDao.deleteUser(8);
    }

    public static void getUserByIDTest() {
        UserDAOImpl userDao = new UserDAOImpl();
        User user = userDao.getUserByID(1);
        System.out.println("user_id: " + user.getUserId());
        System.out.println("name: " + user.getName());
        System.out.println("surname: " + user.getSurname());
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("address: " + user.getAddress());
        System.out.println("e-mail: " + user.geteMail());
        System.out.println("phone: " + user.getPhone());
        System.out.println("access privilege: " + user.getAccessPrivilege());
    }

    public static void getBookByIDTest() {
        BookDAOImpl bookDao = new BookDAOImpl();
        Book book = bookDao.getBookByID(1);
        System.out.println("book_id: " + book.getBookId());
        System.out.println("ISBN: " + book.getISBN());
        System.out.println("title: " + book.getTitle());

        //System.out.println("genreId: " + book.getGenre());
        System.out.println("genreId: " + book.getGenre().getGenreId());
        System.out.println("genre: " +book.getGenre().getGenre());

        System.out.println("volume: " + book.getVolume());
        System.out.println("abstract: " + book.getBookAbstract());
        System.out.println("language: " + book.getLanguage());
        System.out.println("count: " + book.getCount());
        System.out.println("edition year: " + book.getEditionYear());
        System.out.println("pages: " + book.getPages());
        System.out.println("country of edition: " + book.getCountryOfEdition());
    }



    public static void getAllBooksTest() {
        BookDAOImpl bookDAO = new BookDAOImpl();
        List<Book> books = new ArrayList<Book>();
        books = bookDAO.getAllBooks();
        System.out.println(books.toString());

    }

    public static void updateUserTest() {
        UserDAOImpl userDAO = new UserDAOImpl();
        User user = userDAO.getUserByID(1);
        user.setName("Sonaki");
        userDAO.updateUser(user);
    }





    /**
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/book_library";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "sonadb";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");

            conn = DataSource.getInstance().getConnection();
            //conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT user_id, name, surname, access_privilage FROM user";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("user_id");
                int name = rs.getInt("name");
                String surname = rs.getString("surname");
                String access = rs.getString("access_privilage");

                //Display values
                System.out.println("ID: " + id);
                System.out.println("name: " + name);
                System.out.println("surname: " + surname);
                System.out.println("access privilage: " + access);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main

     */
}
