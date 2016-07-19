package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;

import org.junit.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.TestCase.*;

public class TestAuthorDAOImpl {
    private AuthorDAO authorDAO;
    private Author expectedAuthor = null;
    DataSource dataSource = DataSource.getInstance();

    @Before
    public void setup() throws Exception {
        init();
    }

    @After
    public void tearDown() {
        authorDAO.deleteAllAuthors();
    }

    private void init() throws Exception {
        authorDAO = new AuthorDAOImpl(dataSource);
    }


    @Test
    public void add(){
        expectedAuthor = getRandomAuthor();

        // test method add()
        int id = authorDAO.add(expectedAuthor);

        assertTrue(id > 0);

        Author actualAuthor = authorDAO.getAuthorByID(id);
        checkAssertions(expectedAuthor, actualAuthor);
    }

    @Test
    public void getAuthorByID() {
        expectedAuthor = getRandomAuthor();
        int id = authorDAO.add(expectedAuthor);

        // Test method getAuthorByID()
        Author actualAuthor = authorDAO.getAuthorByID(id);

        checkAssertions(expectedAuthor, actualAuthor);
    }

    @Test
    public void getAuthorByName() {
        expectedAuthor = getRandomAuthor();
        authorDAO.add(expectedAuthor);

        // Test method getAuthorByName()
        Author actualAuthor = authorDAO.getAuthorByName(expectedAuthor.getName());

        checkAssertions(expectedAuthor, actualAuthor);
    }

    @Test
    public void getAllAuthors(){
        authorDAO.deleteAllAuthors();

        List<Author> expectedAuthorList = new ArrayList<>();
        List<Author> actualAuthorList = new ArrayList<>();
        int authorCount = 2;

        for(int i = 0; i < authorCount; i++){
            Author author = getRandomAuthor();
            authorDAO.add(author);
            expectedAuthorList.add(author);
        }

        //Test method getAllAuthors()
        actualAuthorList = authorDAO.getAllAuthors();

        assertEquals(expectedAuthorList.size(), actualAuthorList.size());
        for(int i = 0; i < authorCount; i++){
            checkAssertions(expectedAuthorList.get(i), actualAuthorList.get(i));
        }

        expectedAuthorList.clear();
        actualAuthorList.clear();
    }

    @Test
    public void updateAuthor(){
        expectedAuthor = getRandomAuthor();
        int id = authorDAO.add(expectedAuthor);

        expectedAuthor.setSurname("Mikayelyan");
        expectedAuthor.setWebPage("authorWebPage");

        //Test method updateAuthor()
        authorDAO.updateAuthor(expectedAuthor);

        Author actualAuthor = authorDAO.getAuthorByID(id);
        checkAssertions(expectedAuthor, actualAuthor);
    }

    @Test
    public void deleteAuthorByID(){
        authorDAO.deleteAllAuthors();

        expectedAuthor = getRandomAuthor();
        int id = authorDAO.add(expectedAuthor);

        //Test method deleteAuthor()
        authorDAO.deleteAuthor(id);

        Author actualAuthor = authorDAO.getAuthorByID(id);
        assertNull(actualAuthor);
    }

    @Test
    public void deleteAllAuthors(){
        expectedAuthor = getRandomAuthor();
        authorDAO.add(expectedAuthor);

        //Test method deleteAllAuthors()
        authorDAO.deleteAllAuthors();

        assertTrue(authorDAO.getAllAuthors().isEmpty());
    }


    private void checkAssertions(Author expectedAuthor, Author actualAuthor){
        assertEquals(expectedAuthor.getName(), actualAuthor.getName());
        assertEquals(expectedAuthor.getSurname(), actualAuthor.getSurname());
        assertEquals(expectedAuthor.geteMail(), actualAuthor.geteMail());
        assertEquals(expectedAuthor.getWebPage(), actualAuthor.getWebPage());
        assertEquals(expectedAuthor.getBiography(), actualAuthor.getBiography());
    }
}
