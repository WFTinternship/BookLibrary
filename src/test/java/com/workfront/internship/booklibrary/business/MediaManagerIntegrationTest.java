package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.*;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Sona Mikayelyan on 7/31/2016.
 */
public class MediaManagerIntegrationTest {
    private static MediaManager mediaManager;
    private MediaTypeManager mediaTypeManager;
    private GenreManager genreManager;
    private BookManager bookManager;

    private Media testMedia;

    private MediaType testMediaType;
    private Genre testGenre;
    private Book testBook;

    DataSource dataSource = DataSource.getInstance();
    MediaDAO mediaDAO;
    MediaTypeDAO mediaTypeDAO;
    GenreDAO genreDAO;
    BookDAO bookDAO;

    @Before
    public void setup() throws Exception {
        mediaManager = new MediaManagerImpl(dataSource);
        mediaTypeManager = new MediaTypeManagerImpl(dataSource);
        genreManager = new GenreManagerImpl(dataSource);
        bookManager = new BookManagerImpl(dataSource);

        testGenre = getRandomGenre();
        testBook = getRandomBook(testGenre);
        testMediaType = getRandomMediaType();

        testMedia = getRandomMedia(testMediaType, testBook);
        mediaDAO = new MediaDAOImpl(dataSource);
    }

    @After
    public void tearDown(){
        testMedia = null;
        mediaDAO.deleteAll();
        mediaTypeDAO.deleteAll();
        bookDAO.deleteAll();
        genreDAO.deleteAll();
    }

//    @Test
//    public void add(){
//        //test
//        mediaManager.add(testMedia);
//
//        Media actualMedia = mediaManager.getMediaByID(testMedia.getId());
//        assertNotNull(actualMedia);
//    }
}
