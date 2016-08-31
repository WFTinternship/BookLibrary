package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomAuthor;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by ${Sona} on 7/29/2016.
 */

public class AuthorManagerUnitTest {

    private AuthorManager authorManager;

    private Author testAuthor;
    private AuthorDAO authorDAO;


    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        authorManager = new AuthorManagerImpl();

        authorDAO = Mockito.mock(AuthorDAOImpl.class);
        Whitebox.setInternalState(authorManager, "authorDAO", authorDAO);

        testAuthor = getRandomAuthor();
    }

    @After
    public void tearDown(){
        testAuthor = null;
        authorDAO = null;
    }

    @Test
    public void uploadAuthorInfo_nullAuthor(){
        int id;

        //test
        id = authorManager.uploadAuthorInfo(null);

        assertEquals("Wrong id for null author", 0, id);
    }

    @Test
    public void uploadAuthorInfo_nonNullAuthor() {
        int id;
        testAuthor.setId(5);

        //test
        id = authorManager.uploadAuthorInfo(testAuthor);

        assertTrue("author is not added", id > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAuthorByID_idLessThan1(){
        testAuthor.setId(0);
        authorDAO.add(testAuthor);
        int id = testAuthor.getId();

        //test
        authorManager.findAuthorByID(id);
    }

    @Test
    public void findAuthorByID_nullAuthor(){
        when(authorDAO.getAuthorByID(5)).thenReturn(null);
        Author author;

        //test method
        author = authorManager.findAuthorByID(5);
        assertNull(author);
    }

    @Test
    public void findAuthorByID_nonNullAuthor(){
        testAuthor.setId(5);
        when(authorDAO.getAuthorByID(5)).thenReturn(testAuthor);

        Author author;
        //test
        author = authorManager.findAuthorByID(testAuthor.getId());

        assertEquals("Null author returned", author, testAuthor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAuthorByName_noName(){
        testAuthor.setName(null);
        authorDAO.add(testAuthor);
        String name = testAuthor.getName();

        //test
        authorManager.findAuthorByName(name);
    }

    @Test
    public void findAuthorByName_nullAuthor(){
        when(authorDAO.getAuthorByName("Shildt")).thenReturn(null);
        Author author;

        //test method
        author = authorManager.findAuthorByName("Shildt");
        assertNull(author);
    }

    @Test
    public void findAuthorByName_nonNullAuthor(){
        testAuthor.setName("John");
        when(authorDAO.getAuthorByName("John")).thenReturn(testAuthor);

        Author author;
        //test
        author = authorManager.findAuthorByName(testAuthor.getName());

        assertEquals("Null author returned", author, testAuthor);
    }

    @Test
    public void viewAllAuthors_nullList(){
        when(authorDAO.getAllAuthors()).thenReturn(null);

        //test
        List<Author> authorList = authorManager.viewAllAuthors();
        assertEquals("authorList is not null", null, authorList);
    }

    @Test
    public void viewAllAuthors_nonNullList(){
        //test
        authorManager.viewAllAuthors();

        Mockito.verify(authorDAO, Mockito.atLeast(1)).getAllAuthors();
    }

    @Test
    public void update_nullAuthor() {
        Author author;
        //test
        author = authorManager.update(null);

        assertEquals("author is not null", null, author);
    }

    @Test
    public void update_valid_nonNullAuthor(){
        testAuthor.setSurname("Deitel");
        Author author;
        author = authorManager.update(testAuthor);

        assertEquals(testAuthor.getSurname(), author.getSurname());
    }

    @Test
    public void update_nonNull_invalidAuthor() {
        testAuthor.setName(null);

        //test
        authorManager.update(testAuthor);

        //test
        Mockito.verify(authorDAO, Mockito.never()).updateAuthor(testAuthor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_nullAuthor(){
        boolean b;
        //test
        b = authorManager.delete(0);

        assertEquals("author is not null", true, b);
    }

    @Test
    public void delete_successOn_nonNullAuthor() {
        testAuthor.setId(2);
        boolean b;

        //Test
        b = authorManager.delete(testAuthor.getId());

        assertEquals("null author", true, b);
    }

    @Test
    public void delete_cannotDeleteNonNullAuthor() {
        testAuthor.setId(3);
        Author author = getRandomAuthor();
        when(authorDAO.getAuthorByID(anyInt())).thenReturn(author);

        boolean b;
        //test
        b = authorManager.delete(testAuthor.getId());

        assertEquals(false, b);
    }
}
