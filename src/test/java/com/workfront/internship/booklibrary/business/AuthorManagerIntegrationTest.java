package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.MediaType;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomAuthor;
import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomMediaType;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Sona Mikayelyan on 8/1/2016.
 */
public class AuthorManagerIntegrationTest {
    private AuthorManager authorManager;
    private Author testAuthor;
    DataSource dataSource = DataSource.getInstance();
    AuthorDAO authorDAO;

    @Before
    public void setup() throws Exception {
        authorManager = new AuthorManagerImpl(dataSource);
        testAuthor = getRandomAuthor();
        authorDAO = new AuthorDAOImpl(dataSource);
    }

    @After
    public void tearDown(){
        testAuthor = null;
        authorDAO.deleteAllAuthors();
    }

    @Test
    public void uploadAuthorInfo(){
        //test
        authorManager.uploadAuthorInfo(testAuthor);

        Author actualAuthor = authorManager.findAuthorByID(testAuthor.getId());
        assertNotNull(actualAuthor);
    }
}
