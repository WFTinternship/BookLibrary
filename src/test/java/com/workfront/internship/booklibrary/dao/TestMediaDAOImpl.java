package com.workfront.internship.booklibrary.dao;


import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.common.Media;
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
    private BookDAO bookDAO;
    private GenreDAO genreDAO;

    private Media expectedMedia = null;
    private Book expectedBook = null;
    private Genre expectedGenre = null;

    DataSource dataSource = DataSource.getInstance();

    @Before
    public void setup() throws Exception {
        init();
        expectedGenre = getRandomGenre();
        expectedGenre.setId(genreDAO.add(expectedGenre));

        expectedBook = getRandomBook(expectedGenre);
        expectedBook.setId(bookDAO.add(expectedBook));
    }

    @After
    public void tearDown(){
        mediaDAO.deleteAll();
        bookDAO.deleteAll();
        genreDAO.deleteAll();
    }

    private void init() throws Exception {
        mediaDAO = new MediaDAOImpl(dataSource);
        bookDAO = new BookDAOImpl(dataSource);
        genreDAO = new GenreDAOImpl(dataSource);
    }


    @Test
    public void add(){
        expectedMedia = getRandomMedia(expectedBook);

        //Test method add()
        int mediaId = mediaDAO.add(expectedMedia);

        Media actualMedia = mediaDAO.getMediaByID(mediaId);
        checkAssertions(expectedMedia, actualMedia);
    }

    @Test
    public void getMediaByID(){
        expectedMedia = getRandomMedia(expectedBook);
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
            Media media = getRandomMedia(expectedBook);
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
        expectedMedia = getRandomMedia(expectedBook);
        int id = mediaDAO.add(expectedMedia);

        expectedMedia.setType("video");

        //Test method updateMedia()
        mediaDAO.updateMedia(expectedMedia);

        Media actualMedia = mediaDAO.getMediaByID(id);
        checkAssertions(expectedMedia, actualMedia);
    }

    @Test
    public void deleteMedia(){
        expectedMedia = getRandomMedia(expectedBook);
        int id = mediaDAO.add(expectedMedia);

        //Test method deleteMedia()
        mediaDAO.deleteMedia(id);

        Media actualMedia = mediaDAO.getMediaByID(id);
        assertNull(actualMedia);
    }

    @Test
    public void deleteAll(){
        expectedMedia = getRandomMedia(expectedBook);
        mediaDAO.add(expectedMedia);

        //Test method deleteAll()
        mediaDAO.deleteAll();

        assertTrue(mediaDAO.getAllMedia().isEmpty());
    }


    private void checkAssertions(Media expectedMedia, Media actualMedia){
        assertEquals(expectedMedia.getLink(), actualMedia.getLink());
        assertEquals(expectedMedia.getType(), actualMedia.getType());
        assertEquals(expectedMedia.getBook(), actualMedia.getBook());
    }
}
