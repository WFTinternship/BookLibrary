package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserDaoUnitTest {
    LegacyDataSource dataSource;
    UserDAO userDAO;

    @SuppressWarnings("unchecked")
    @Before
    public void beforeTest() throws Exception {
        dataSource = Mockito.mock(LegacyDataSource.class);

        Connection connection = Mockito.mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenThrow(SQLException.class);

        userDAO = new UserDAOImpl();
    }

    @After
    public void afterTest() {

    }

    @Test(expected = RuntimeException.class)
    public void add_dbError() {
        userDAO.add(new User());
    }

    @Test(expected = RuntimeException.class)
    public void getUserByID_dbError() {
        userDAO.getUserByID(5);
    }

    @Test(expected = RuntimeException.class)
    public void getUserByeMail_dbError() {
        userDAO.getUserByeMail("MyEmail");
    }

    @Test(expected = RuntimeException.class)
    public void getUserByUsername_dbError() {
        userDAO.getUserByUsername("MyUsername");
    }

    @Test(expected = RuntimeException.class)
    public void getAllUsers_dbError() {
        userDAO.getAllUsers();
    }

    @Test(expected = RuntimeException.class)
    public void deleteUser_dbError() {
        userDAO.deleteUser(3);
    }

    @Test(expected = RuntimeException.class)
    public void deleteAll_dbError() {
        userDAO.deleteAll();
    }

}
