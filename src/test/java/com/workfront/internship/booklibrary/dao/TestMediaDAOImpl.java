package com.workfront.internship.booklibrary.dao;


import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.*;
import com.workfront.internship.booklibrary.spring.DevelopmentConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DevelopmentConfig.class)
@ActiveProfiles("Development")
public class TestMediaDAOImpl {
    @Autowired
    private MediaDAO mediaDAO;
    @Autowired
    private MediaTypeDAO mediaTypeDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private GenreDAO genreDAO;
    List<Author> authorList;
    List<Integer> authorsIdList;

    private Media expectedMedia = null;
    private MediaType expectedMediaType = null;
    private Book expectedBook = null;
    private Genre expectedGenre = null;

//    LegacyDataSource dataSource = LegacyDataSource.getInstance();

    @Before
    public void setup() throws Exception {
        authorsIdList = init();
        expectedMediaType = getRandomMediaType();
        expectedMediaType.setId(mediaTypeDAO.add(expectedMediaType));

        expectedGenre = getRandomGenre();
        expectedGenre.setId(genreDAO.add(expectedGenre));

        expectedBook = getRandomBook(expectedGenre);
        expectedBook.setId(bookDAO.add(expectedBook, authorsIdList));
    }

    @After
    public void tearDown(){
        mediaDAO.deleteAll();
        mediaTypeDAO.deleteAll();
        bookDAO.deleteAll();
        genreDAO.deleteAll();
    }

    private List<Integer> init() throws Exception {
        List<Integer> authorsIdList = new ArrayList<>();
        for(int i = 0; i < authorList.size(); i++){
            authorsIdList.add(authorList.get(i).getId());
        }
        return authorsIdList;
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

/**    @Test
    public void getAllMediaByBook() {
        mediaTypeDAO.deleteAll();
        List<Media> expectedMediaList = new ArrayList<>();
        List<Media> actualMediaList = new ArrayList<>();
        int count = 2;

        for(int i = 0; i < count; i++) {
            expectedMedia = getRandomMedia(expectedMediaType, expectedBook);
            mediaDAO.add(expectedMedia);
            assertNotNull(expectedMedia);
            expectedMediaList.add(expectedMedia);
        }

        //Test method getAllPBookID()
        actualMediaList = mediaDAO.getAllMediaByBook(expectedBook.getId());

        for(int i = 0; i < count; i++){
            checkAssertions(expectedMediaList.get(i), actualMediaList.get(i));
        }

        expectedMediaList.clear();
        actualMediaList.clear();
    }
 */

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
