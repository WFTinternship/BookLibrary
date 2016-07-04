package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.User;

import java.sql.*;

/**
 * Created by Sona on 7/1/2016.
 */
public class Test {
    public static void main(String[] args) {
        // createUserTest();
        //deleteUserTest();
        //getUserByIDTest();
        updateUserTest();
    }

    public static void createUserTest(){
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

    public static void deleteUserTest(){
        UserDAOImpl userDao = new UserDAOImpl();
        userDao.deleteUser(8);
    }

    public static void getUserByIDTest(){
        UserDAOImpl userDao  = new UserDAOImpl();
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

    public static void getAllAuthorsTest(){} // TODO

    public static void updateUserTest(){
        UserDAOImpl userDAO = new UserDAOImpl();
        User user = userDAO.getUserByID(1);
        user.setName("Sonaki");
        userDAO.updateUser(user);
    } // TODO





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
