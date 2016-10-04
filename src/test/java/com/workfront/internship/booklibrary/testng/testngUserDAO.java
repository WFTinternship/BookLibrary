package com.workfront.internship.booklibrary.testng;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.dao.UserDAO;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ${Sona} on 10/4/2016.
 */
public class testngUserDAO {
    @Mock
    LegacyDataSource mockLegacyDataSource;

    @Mock
    Connection mockConnection;

    @Mock
    User mockUser;

    @Spy
    UserDAO userDAO;

    @BeforeMethod
    public void setup() throws SQLException {
        Mockito.when(mockLegacyDataSource.getConnection()).thenReturn(mockConnection);

    }

    @Test
//            (groups = unit-single_threaded)
    public void onAdd_AddedInDB() {
        Mockito.when(userDAO.add(mockUser)).thenReturn(1);
        int id = userDAO.add(mockUser);

        Assert.assertEquals(id, 1, "user Is not added properly");


//        int beforeCount = aceTestCountFilter.currentTest.get();
//        Assert.assertEquals(beforeCount, 0, "Default value of currentTest was unexpected.");
//
//        aceTestCountFilter.onTestStart(mockTestResult);
//
//        int afterCount = aceTestCountFilter.currentTest.get();
//        Assert.assertEquals(afterCount, 1, "CurrentTest value wasn't incremented with onTestStart call.");
    }
}
