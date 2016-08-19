package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomAuthor;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Sona Mikayelyan on 8/1/2016.
 */
public class AuthorManagerIntegrationTest {
    private AuthorManager authorManager;
    private Author testAuthor;
    LegacyDataSource dataSource = LegacyDataSource.getInstance();
    AuthorDAO authorDAO;

    @Before
    public void setup() throws Exception {
        authorManager = new AuthorManagerImpl();
        Whitebox.setInternalState(authorDAO, "dataSource", dataSource);
        testAuthor = getRandomAuthor();
        authorDAO = new AuthorDAOImpl();
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
