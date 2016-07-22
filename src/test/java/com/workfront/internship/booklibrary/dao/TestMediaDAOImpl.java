package com.workfront.internship.booklibrary.dao;


import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.common.Media;
import com.workfront.internship.booklibrary.common.MediaType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

public class TestMediaDAOImpl {
    private MediaDAO mediaDAO;
    private MediaTypeDAO mediaTypeDAO;
    private BookDAO bookDAO;
    private GenreDAO genreDAO;

    private Media expectedMedia = null;
    private MediaType expectedMediaType = null;
    private Book expectedBook = null;
    private Genre expectedGenre = null;

    DataSource dataSource = DataSource.getInstance();

    @Before
    public void setup() throws Exception {
        init();
        expectedMediaType = getRandomMediaType();
        expectedMediaType.setId(mediaTypeDAO.add(expectedMediaType));

        expectedGenre = getRandomGenre();
        expectedGenre.setId(genreDAO.add(expectedGenre));

        expectedBook = getRandomBook(expectedGenre);
        expectedBook.setId(bookDAO.add(expectedBook));
    }

    @After
    public void tearDown(){
        mediaDAO.deleteAll();
        mediaTypeDAO.deleteAll();
        bookDAO.deleteAll();
        genreDAO.deleteAll();
    }

    private void init() throws Exception {
        mediaDAO = new MediaDAOImpl(dataSource);
        mediaTypeDAO = new MediaTypeDAOImpl(dataSource);
        bookDAO = new BookDAOImpl(dataSource);
        genreDAO = new GenreDAOImpl(dataSource);
    }


    @Test
    public void add(){
        expectedMedia = getRandomMedia(expectedMediaType, expectedBook);

        //Test method add()
        int mediaId = mediaDAO.add(expectedMedia);

        Media actualMedia = mediaDAO.getMediaByID(mediaId);
        checkAssertions(expectedMedia, actualMedia);
    }

    @Test
    public void getMediaByID(){
        expectedMedia = getRandomMedia(expectedMediaType, expectedBook);
        int mediaId = mediaDAO.add(expectedMedia);

        //Test method getMediaByID
        Media actualMedia = mediaDAO.getMediaByID(mediaId);
        checkAssertions(expectedMedia, actualMedia);
    }

    @Test
    public void getAllMedia(){
        mediaDAO.deleteAll();

        List<Media> expectedMediaList = new ArrayList<>();
        List<Media> actualMediaList = new ArrayList<>();
        int mediaCount = 2;

        for(int i = 0; i < mediaCount; i++){
            Media media = getRandomMedia(expectedMediaType, expectedBook);
            mediaDAO.add(media);
            expectedMediaList.add(media);
        }

        //Test method getAllMedia()
        actualMediaList = mediaDAO.getAllMedia();

        assertEquals(expectedMediaList.size(), actualMediaList.size());
        for(int i = 0; i < mediaCount; i++){
            checkAssertions(expectedMediaList.get(i), actualMediaList.get(i));
        }

        expectedMediaList.clear();
        actualMediaList.clear();
    }

    @Test
    public void updateMedia(){
        expectedMedia = getRandomMedia(expectedMediaType, expectedBook);
        int id = mediaDAO.add(expectedMedia);

        expectedMedia.setLink("LinkToVideo");

        //Test method updateMedia()
        mediaDAO.updateMedia(expectedMedia);

        Media actualMedia = mediaDAO.getMediaByID(id);
        checkAssertions(expectedMedia, actualMedia);
    }

    @Test
    public void deleteMedia(){
        expectedMedia = getRandomMedia(expectedMediaType, expectedBook);
        int id = mediaDAO.add(expectedMedia);

        //Test method deleteMedia()
        mediaDAO.deleteMedia(id);

        Media actualMedia = mediaDAO.getMediaByID(id);
        assertNull(actualMedia);
    }

    @Test
    public void deleteAll(){
        expectedMedia = getRandomMedia(expectedMediaType, expectedBook);
        mediaDAO.add(expectedMedia);

        //Test method deleteAll()
        mediaDAO.deleteAll();

        assertTrue(mediaDAO.getAllMedia().isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void add_duplicateEntry(){
        Media media = getRandomMedia(expectedMediaType, expectedBook);
        mediaDAO.add(media);
        Media duplicateMedia = getRandomMedia(expectedMediaType, expectedBook);
        duplicateMedia.setLink(media.getLink());
        mediaDAO.add(duplicateMedia);
    }

    @Test(expected = RuntimeException.class)
    public void update_duplicateEntry(){
        Media media = getRandomMedia(expectedMediaType, expectedBook);
        Media duplicateMedia = media;
        mediaDAO.add(media);
        duplicateMedia.setLink("NewLink");
        mediaDAO.add(duplicateMedia);

        duplicateMedia.setLink(media.getLink());
        mediaDAO.add(duplicateMedia);
    }


    private void checkAssertions(Media expectedMedia, Media actualMedia){
        assertEquals(expectedMedia.getLink(), actualMedia.getLink());
        assertEquals(expectedMedia.getType(), actualMedia.getType());
        assertEquals(expectedMedia.getBook(), actualMedia.getBook());
    }
}
