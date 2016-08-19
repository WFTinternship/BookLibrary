package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.*;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.mockito.internal.util.reflection.Whitebox;

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

    LegacyDataSource dataSource = LegacyDataSource.getInstance();
    MediaDAO mediaDAO;
    MediaTypeDAO mediaTypeDAO;
    GenreDAO genreDAO;
    BookDAO bookDAO;

    @Before
    public void setup() throws Exception {
        Whitebox.setInternalState(mediaDAO, "dataSource", dataSource);
        mediaManager = new MediaManagerImpl();
        mediaTypeManager = new MediaTypeManagerImpl();
        genreManager = new GenreManagerImpl();
        bookManager = new BookManagerImpl();

        testGenre = getRandomGenre();
        testBook = getRandomBook(testGenre);
        testMediaType = getRandomMediaType();

        testMedia = getRandomMedia(testMediaType, testBook);
        mediaDAO = new MediaDAOImpl();
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
