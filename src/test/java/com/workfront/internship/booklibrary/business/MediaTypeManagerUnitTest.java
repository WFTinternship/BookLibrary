package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.MediaType;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomMediaType;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by Sona Mikayelyan on 7/31/2016.
 */

public class MediaTypeManagerUnitTest {

    private MediaTypeManager mediaTypeManager;

    private MediaType testMediaType;
    private MediaTypeDAO mediaTypeDAO;


    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        mediaTypeManager = new MediaTypeManagerImpl();

        mediaTypeDAO = Mockito.mock(MediaTypeDAOImpl.class);
        Whitebox.setInternalState(mediaTypeManager, "mediaTypeDAO", mediaTypeDAO);

        testMediaType = getRandomMediaType();
    }

    @After
    public void tearDown(){
        testMediaType = null;
        mediaTypeDAO = null;
    }

    @Test
    public void add_nullMediaType() {
        int id;

        //test
        id = mediaTypeManager.add(null);

        assertEquals("Wrong id for null mediaType", 0, id);
    }

    @Test
    public void add_nonNullMediaType() {
        int id;
        testMediaType.setId(5);

        //test
        id = mediaTypeManager.add(testMediaType);

        assertTrue("media type is not added", id > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMediaTypeByID_idLessThan1(){
        testMediaType.setId(0);
        mediaTypeDAO.add(testMediaType);
        int id = testMediaType.getId();

        //test
        mediaTypeManager.getMediaTypeByID(id);
    }

    @Test
    public void getMediaTypeByID_nullMediaType(){
        when(mediaTypeDAO.getMediaTypeByID(5)).thenReturn(null);
        MediaType mediaType;

        //test method
        mediaType = mediaTypeManager.getMediaTypeByID(5);
        assertNull(mediaType);
    }

    @Test
    public void getMediaTypeByID_nonNullMediaType(){
        testMediaType.setId(5);
        when(mediaTypeDAO.getMediaTypeByID(5)).thenReturn(testMediaType);

        MediaType mediaType;
        //test
        mediaType = mediaTypeManager.getMediaTypeByID(testMediaType.getId());

        assertEquals("Null media Type returned", mediaType, testMediaType);
    }

    @Test
    public void viewAllMediaTypes_nullList(){
        when(mediaTypeDAO.getAllMediaTypes()).thenReturn(null);

        //test
        List<MediaType> mediaTypeList = mediaTypeManager.viewAllMediaTypes();
        assertEquals("mediaTypeList is not null", null, mediaTypeList);
    }

    @Test
    public void viewAllMediaTypes_nonNullList(){
        //test
        mediaTypeManager.viewAllMediaTypes();

        Mockito.verify(mediaTypeDAO, Mockito.atLeast(1)).getAllMediaTypes();
    }

    @Test
    public void update_nullMediaType() {
        MediaType mediaType;
        //test
        mediaType = mediaTypeManager.update(null);

        assertEquals("media type is not null", null, mediaType);
    }

    @Test
    public void update_valid_nonNullMediaType(){
        testMediaType.setType("Photo");
        MediaType mediaType;
        mediaType = mediaTypeManager.update(testMediaType);

        assertEquals(testMediaType.getType(), mediaType.getType());
    }

    @Test
    public void update_nonNull_invalidMediaType() {
        testMediaType.setType(null);

        //test
        mediaTypeManager.update(testMediaType);

        //test
        Mockito.verify(mediaTypeDAO, Mockito.never()).updateMediaType(testMediaType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_nullMediaType(){
        boolean b;
        //test
        b = mediaTypeManager.delete(0);

        assertEquals("media type is not null", true, b);
    }

    @Test
    public void delete_successOn_nonNullMediaType() {
        testMediaType.setId(2);
        boolean b;

        //Test
        b = mediaTypeManager.delete(testMediaType.getId());

        assertEquals("null genre", true, b);
    }

    @Test
    public void delete_cannotDeleteNonNullMediaType() {
        testMediaType.setId(3);
        MediaType mediaType = getRandomMediaType();
        when(mediaTypeDAO.getMediaTypeByID(anyInt())).thenReturn(mediaType);

        boolean b;
        //test
        b = mediaTypeManager.delete(testMediaType.getId());

        assertEquals(false, b);
    }
}
