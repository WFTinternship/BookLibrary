package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.common.Genre;
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

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomGenre;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Sona Mikayelyan on 7/31/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ManagerTestConfig.class)
@ActiveProfiles("Development")
public class GenreManagerIntegrationTest {
    @Autowired
    private GenreManager genreManager;
    private Genre testGenre;

    @Before
    public void setup() throws Exception {
        testGenre = getRandomGenre();
    }

    @After
    public void tearDown(){
        genreManager.delete(testGenre.getId());
    }

    @Test
    public void add(){
        //test
        genreManager.add(testGenre);

        Genre actualGenre = genreManager.findGenreByID(testGenre.getId());
        assertNotNull(actualGenre);
    }
}
