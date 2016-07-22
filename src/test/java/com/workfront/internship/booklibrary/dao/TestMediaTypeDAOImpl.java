package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.MediaType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomAuthor;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static com.workfront.internship.booklibrary.dao.TestUtil.*;

/**
 * Created by ${Sona} on 7/22/2016.
 */
public class TestMediaTypeDAOImpl {
    DataSource dataSource = DataSource.getInstance();
    private MediaTypeDAO mediaTypeDAO;
    private MediaType expectedMediaType = null;

    @Before
    public void setup() throws Exception {
        init();
    }

    @After
    public void tearDown() {
        mediaTypeDAO.deleteAll();
    }

    private void init() throws Exception {
        mediaTypeDAO = new MediaTypeDAOImpl(dataSource);
    }

    @Test
    public void add(){
        expectedMediaType = getRandomMediaType();

        // test method add()
        int id = mediaTypeDAO.add(expectedMediaType);

        assertTrue(id > 0);

        MediaType actualMediaType = mediaTypeDAO.getMediaTypeByID(id);
        checkAssertions(expectedMediaType, actualMediaType);
    }

    @Test
    public void getMediaTypeByID(){
        expectedMediaType = getRandomMediaType();

        int id = mediaTypeDAO.add(expectedMediaType);

        assertTrue(id > 0);

        // Test method getMediaTypeByID()
        MediaType actualMediaType = mediaTypeDAO.getMediaTypeByID(id);
        checkAssertions(expectedMediaType, actualMediaType);
    }

    @Test
    public void getMediaByType(){
        expectedMediaType = getRandomMediaType();

        mediaTypeDAO.add(expectedMediaType);

        // Test method getMediaTypeByType()
        MediaType actualMediaType = mediaTypeDAO.getMediaByType(expectedMediaType.getType());
        checkAssertions(expectedMediaType, actualMediaType);
    }

    @Test
    public void getAllMediaTypes(){
        mediaTypeDAO.deleteAll();

        List<MediaType> expectedMediaTypeList = new ArrayList<>();
        List<MediaType> actualMediaTypeList = new ArrayList<>();
        int mediaTypeCount = 2;

        for(int i = 0; i < mediaTypeCount; i++){
            MediaType mediaType = getRandomMediaType();
            mediaTypeDAO.add(mediaType);
            expectedMediaTypeList.add(mediaType);
        }

        //Test method getAllMediaTypes()
        actualMediaTypeList = mediaTypeDAO.getAllMediaTypes();

        assertEquals(expectedMediaTypeList.size(), actualMediaTypeList.size());

//        assertEquals(expectedMediaTypeList.get(0), actualMediaTypeList.get(1));
//        assertEquals(expectedMediaTypeList.get(1), actualMediaTypeList.get(0));
        for(int i = 0; i < mediaTypeCount; i++){
            checkAssertions(expectedMediaTypeList.get(i), actualMediaTypeList.get(i));
        }

        expectedMediaTypeList.clear();
        actualMediaTypeList.clear();
    }

    @Test
    public void updateMediaType(){
        expectedMediaType = getRandomMediaType();
        int id = mediaTypeDAO.add(expectedMediaType);

        expectedMediaType.setType("NewType");
        //expectedMediaType.setWebPage("authorWebPage");

        //Test method updateMediaType()
        mediaTypeDAO.updateMediaType(expectedMediaType);

        MediaType actualMediaType = mediaTypeDAO.getMediaTypeByID(id);
        checkAssertions(expectedMediaType, actualMediaType);
    }

    @Test
    public void deleteMediaType(){
        mediaTypeDAO.deleteAll();

        expectedMediaType = getRandomMediaType();
        int id = mediaTypeDAO.add(expectedMediaType);

        //Test method deleteMediaType() by id
        mediaTypeDAO.deleteMediaType(id);

        MediaType actualMediaType = mediaTypeDAO.getMediaTypeByID(id);
        assertNull(actualMediaType);
    }

    @Test
    public void deleteAll(){
        expectedMediaType = getRandomMediaType();
        mediaTypeDAO.add(expectedMediaType);

        //Test method deleteAll()
        mediaTypeDAO.deleteAll();

        assertTrue(mediaTypeDAO.getAllMediaTypes().isEmpty());
    }


    private void checkAssertions(MediaType expectedMediaType, MediaType actualMediaType){
        assertEquals(expectedMediaType.getType(), actualMediaType.getType());
    }
}
