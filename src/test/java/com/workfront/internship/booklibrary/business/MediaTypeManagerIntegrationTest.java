package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.MediaType;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomMediaType;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Sona Mikayelyan on 7/31/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ManagerTestConfig.class)
@ActiveProfiles("Development")
public class MediaTypeManagerIntegrationTest {

    @Autowired
    private MediaTypeManager mediaTypeManager;

    private MediaType testMediaType;

    @Before
    public void setup() throws Exception {
        testMediaType = getRandomMediaType();
    }

    @After
    public void tearDown(){
        mediaTypeManager.delete(testMediaType.getId());
    }

    @Test
    public void add(){
        //test
        mediaTypeManager.add(testMediaType);

        MediaType actualMediaType = mediaTypeManager.getMediaTypeByID(testMediaType.getId());
        assertNotNull(actualMediaType);
    }

}
