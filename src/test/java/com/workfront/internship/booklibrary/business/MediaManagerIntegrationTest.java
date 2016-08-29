package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.*;
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

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Sona Mikayelyan on 7/31/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ManagerTestConfig.class)
@ActiveProfiles("Development")
public class MediaManagerIntegrationTest {

    @Autowired
    private MediaManager mediaManager;

    @Autowired
    private MediaTypeManager mediaTypeManager;

    @Autowired
    private GenreManager genreManager;

    @Autowired
    private BookManager bookManager;

    private Media testMedia;
    private MediaType testMediaType;
    private Genre testGenre;
    private Book testBook;

    @Before
    public void setup() throws Exception {
        testGenre = getRandomGenre();
        testBook = getRandomBook(testGenre);
        testMediaType = getRandomMediaType();

        testMedia = getRandomMedia(testMediaType, testBook);
    }

    @After
    public void tearDown(){
//        mediaManager.delete(testMedia.getId());
//        mediaTypeManager.delete(testMediaType.getId());
//        bookManager.delete(testBook.getId());
//        genreManager.delete(testGenre.getId());
    }

    @Test
    public void add(){
//        genreManager.add(testGenre);
//        bookManager.add(testBook);
//        mediaTypeManager.add(testMediaType);
//
//        //test
//        mediaManager.add(testMedia);
//
//        Media actualMedia = mediaManager.getMediaByID(testMedia.getId());
//        assertNotNull(actualMedia);
    }
}
