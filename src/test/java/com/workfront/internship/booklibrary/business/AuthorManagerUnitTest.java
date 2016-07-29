package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

/**
 * Created by ${Sona} on 7/29/2016.
 */
public class AuthorManagerUnitTest {
    DataSource dataSource;
    private Author testAuthor;

    private AuthorDAO authorDAO;
    private AuthorManager authorManager;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        authorManager = new AuthorManagerImpl(dataSource);

        authorDAO = Mockito.mock(AuthorDAOImpl.class);
        Whitebox.setInternalState(authorManager, "authorDAO", authorDAO);

        testAuthor = new Author();
        testAuthor = TestUtil.getRandomAuthor();
    }

    @After
    public void tearDown(){
        testAuthor = null;
        authorDAO = null;
    }

    @Test
    public void uploadAuthorInfo(){}
}
