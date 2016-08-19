package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.MediaType;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomMediaType;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Sona Mikayelyan on 7/31/2016.
 */
public class MediaTypeManagerIntegrationTest {
    private static MediaTypeManager mediaTypeManager;
    private MediaType testMediaType;
    LegacyDataSource dataSource = LegacyDataSource.getInstance();
    MediaTypeDAO mediaTypeDAO;

    @Before
    public void setup() throws Exception {
        Whitebox.setInternalState(mediaTypeDAO, "dataSource", dataSource);
        mediaTypeManager = new MediaTypeManagerImpl();
        testMediaType = getRandomMediaType();
        mediaTypeDAO = new MediaTypeDAOImpl();
    }

    @After
    public void tearDown(){
        testMediaType = null;
        mediaTypeDAO.deleteAll();
    }

    @Test
    public void add(){
        //test
        mediaTypeManager.add(testMediaType);

        MediaType actualMediaType = mediaTypeManager.getMediaTypeByID(testMediaType.getId());
        assertNotNull(actualMediaType);
    }

}
