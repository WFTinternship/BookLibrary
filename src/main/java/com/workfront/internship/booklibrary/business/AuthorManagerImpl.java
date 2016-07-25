package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.dao.AuthorDAO;
import com.workfront.internship.booklibrary.dao.AuthorDAOImpl;
import com.workfront.internship.booklibrary.dao.DataSource;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public class AuthorManagerImpl {
    private AuthorDAO authorDAO;
    private DataSource dataSource;

    public AuthorManagerImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        authorDAO = new AuthorDAOImpl(dataSource);
    }


}
