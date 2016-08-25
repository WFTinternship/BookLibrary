package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomAuthor;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Sona Mikayelyan on 8/1/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ManagerTestConfig.class)
public class AuthorManagerIntegrationTest {
    @Autowired
    private AuthorManager authorManager;

    private Author testAuthor;

    @Before
    public void setup() throws Exception {
        testAuthor = getRandomAuthor();
    }

    @After
    public void tearDown(){
        authorManager.delete(testAuthor.getId());
    }

    @Test
    public void uploadAuthorInfo(){
        //test
        authorManager.uploadAuthorInfo(testAuthor);

        Author actualAuthor = authorManager.findAuthorByID(testAuthor.getId());
        assertNotNull(actualAuthor);
    }
}
