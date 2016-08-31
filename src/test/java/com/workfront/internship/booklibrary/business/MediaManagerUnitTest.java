package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.common.Media;
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

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by Sona Mikayelyan on 7/31/2016.
 */

public class MediaManagerUnitTest {

    private MediaManager mediaManager;

    private Media testMedia;

    private MediaType testMediaType;
    private Genre testGenre;
    private Book testBook;

    private MediaDAO mediaDAO;


    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        mediaManager = new MediaManagerImpl();

        mediaDAO = Mockito.mock(MediaDAOImpl.class);
        Whitebox.setInternalState(mediaManager, "mediaDAO", mediaDAO);

        testGenre = getRandomGenre();
        testBook = getRandomBook(testGenre);

        testMediaType = getRandomMediaType();

        testMedia = getRandomMedia(testMediaType, testBook);
    }

    @After
    public void tearDown(){
        testMedia = null;
        testMediaType = null;
        testBook = null;
        testGenre = null;
        mediaDAO = null;
    }

    @Test
    public void add_nullMedia() {
        int id;

        //test
        id = mediaManager.add(null);

        assertEquals("Wrong id for null media", 0, id);
    }

    @Test
    public void add_nonNullMedia() {
        int id;
        testMedia.setId(5);

        //test
        id = mediaManager.add(testMedia);

        assertTrue("media is not added", id > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMediaByID_idLessThan1(){
        testMedia.setId(0);
        mediaDAO.add(testMedia);
        int id = testMedia.getId();

        //test
        mediaManager.getMediaByID(id);
    }

    @Test
    public void getMediaByID_nullMedia(){
        when(mediaDAO.getMediaByID(5)).thenReturn(null);
        Media media;

        //test method
        media = mediaManager.getMediaByID(5);
        assertNull(media);
    }

    @Test
    public void getMediaByID_nonNullMedia(){
        testMedia.setId(5);
        when(mediaDAO.getMediaByID(5)).thenReturn(testMedia);

        Media media;
        //test
        media = mediaManager.getMediaByID(testMedia.getId());

        assertEquals("Null media returned", media, testMedia);
    }

    @Test
    public void viewAllMedia_nullList(){
        when(mediaDAO.getAllMedia()).thenReturn(null);

        //test
        List<Media> mediaList = mediaManager.viewAllMedia();
        assertEquals("mediaList is not null", null, mediaList);
    }

    @Test
    public void viewAllMedia_nonNullList(){
        //test
        mediaManager.viewAllMedia();

        Mockito.verify(mediaDAO, Mockito.atLeast(1)).getAllMedia();
    }

    @Test
    public void update_nullMedia() {
        Media media;
        //test
        media = mediaManager.update(null);

        assertEquals("media is not null", null, media);
    }

    @Test
    public void update_valid_nonNullMedia(){
        testMedia.setLink("mediaLink");
        Media media;
        media = mediaManager.update(testMedia);

        assertEquals(testMedia.getLink(), media.getLink());
    }

    @Test
    public void update_nonNull_invalidMedia() {
        testMedia.setLink(null);

        //test
        mediaManager.update(testMedia);

        //test
        Mockito.verify(mediaDAO, Mockito.never()).updateMedia(testMedia);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_nullMedia(){
        boolean b;
        //test
        b = mediaManager.delete(0);

        assertEquals("media is not null", true, b);
    }

    @Test
    public void delete_successOn_nonNullMedia() {
        testMedia.setId(2);
        boolean b;

        //Test
        b = mediaManager.delete(testMedia.getId());

        assertEquals("null media", true, b);
    }

    @Test
    public void delete_cannotDeleteNonNullMedia() {
        testMedia.setId(3);
        Media media = TestUtil.getRandomMedia(testMediaType, testBook);
        when(mediaDAO.getMediaByID(anyInt())).thenReturn(media);

        boolean b;
        //test
        b = mediaManager.delete(testMedia.getId());

        assertEquals(false, b);
    }
}
