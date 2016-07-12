import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.dao.AuthorDAO;
import com.workfront.internship.booklibrary.dao.AuthorDAOImpl;
import com.workfront.internship.booklibrary.dao.DataSource;
import org.junit.*;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class TestAuthorDAOImpl {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    AuthorDAO authorDAO = new AuthorDAOImpl();
    Author author = null;

    @Before
    public void setUp() throws PropertyVetoException, SQLException, IOException {
        connection = DataSource.getInstance().getConnection();

    //    author = getRandomAuthor();
//        saveAuthorToDatabase(author);
    }

    @After
    public void tearDown() throws PropertyVetoException, IOException, SQLException {
        deleteCreatedAuthor(author);

        closeResource(resultSet);
        closeResource(preparedStatement);
        closeResource(connection);
    }

    @Test
    public void insertAuthor() throws SQLException, PropertyVetoException, IOException {
        Author newAuthor = getRandomAuthor();
        authorDAO.add(newAuthor);
        // test method
        newAuthor.setId(newAuthor.getId()) ;

        Author author = getAuthorByIdHelper(newAuthor.getId());
        assertNotNull(newAuthor);
        assertTrue(newAuthor.getId() > 0);
        assertEquals( newAuthor.getName()     , author.getName()    );
        assertEquals( newAuthor.getSurname()  , author.getSurname()  );
        assertEquals( newAuthor.geteMail()    , author.geteMail()   );
        assertEquals( newAuthor.getWebPage()  , author.getWebPage()  );
        assertEquals( newAuthor.getBiography(), author.getBiography());

        deleteCreatedAuthor(newAuthor);

    }

    @Test
    public void getAuthorByID() throws SQLException {
        author = getRandomAuthor();
        assertEquals(0, author.getId());

        authorDAO.add(author);

        // test method
        Author actualAuthor = authorDAO.getAuthorByID(author.getId());

        assertNotNull(author);
        assertTrue(author.getId() > 0);
        assertEquals(author.getName(), actualAuthor.getName());
        assertEquals(author.getSurname(), actualAuthor.getSurname());
        assertEquals(author.geteMail(), actualAuthor.geteMail());
        assertEquals(author.getWebPage(), actualAuthor.getWebPage());
        assertEquals(author.getBiography(), actualAuthor.getBiography());

    }

    @Test
    public void getAuthorByName() throws SQLException {
        Author newAuthor = getRandomAuthor();
        assertEquals(0, newAuthor.getId());

        // test method
        newAuthor = authorDAO.getAuthorByName(author.getName());

        assertNotNull(newAuthor);
        assertTrue(newAuthor.getId() > 0);
        assertEquals(author.getName(), newAuthor.getName());
        assertEquals(author.getSurname(), newAuthor.getSurname());
        assertEquals(author.geteMail(), newAuthor.geteMail());
        assertEquals(author.getWebPage(), newAuthor.getWebPage());
        assertEquals(author.getBiography(), newAuthor.getBiography());
    }




    private void closeResource(AutoCloseable closeable){
        try{
            if (closeable != null ) closeable.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Author saveAuthorToDatabase(Author author) throws SQLException {
        String sql;
        sql = "INSERT INTO Author VALUES(?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, author.getId());
        preparedStatement.setString(2, author.getName());
        preparedStatement.setString(3, author.getSurname());
        preparedStatement.setString(4, author.geteMail());
        preparedStatement.setString(5, author.getWebPage());
        preparedStatement.setString(6, author.getBiography());

        // Execute insert statement
        preparedStatement.executeUpdate();

        // Acquire generated key(ID)
        ResultSet rsKey = preparedStatement.getGeneratedKeys();
        int lastId = 0;
        if (rsKey.next()){
            lastId = rsKey.getInt(1);
        }
        rsKey.close();

        // Assign generated ID to original object
        author.setId(lastId);

        return author;
    }

    private Author getRandomAuthor() throws SQLException {
        Author author = new Author();

        author.setName("Hermann");
        author.setSurname("Hesse");
        author.seteMail("hehe@yahoo.com");
        author.setBiography("HermannHesseBioLink");

        /**
        author.setName("Herbert");
        author.setSurname("Shildt");
        author.seteMail("shildt@yahoo.com");
        author.setBiography("ShildtBioLink");*/

        return author;
    }

    private void deleteCreatedAuthor(Author author) throws PropertyVetoException, SQLException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int isExecuted = 0;

        connection = DataSource.getInstance().getConnection();
        String sql;
        sql = "DELETE FROM Author WHERE author_id = " + author.getId();
        preparedStatement = connection.prepareStatement(sql);
        isExecuted = preparedStatement.executeUpdate();
        if (isExecuted == 0) {
            throw new SQLException("Delete not executed");
        }
    }

    private Author getAuthorByIdHelper(int id) throws PropertyVetoException, SQLException, IOException {
        author = new Author();

        connection = DataSource.getInstance().getConnection();
        String sql;
        sql = "SELECT * FROM Author WHERE author_id=" + id;
        preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            author = new Author();
            author.setId(rs.getInt(1));
            author.setName(rs.getString(2));
            author.setSurname(rs.getString(3));
            author.seteMail(rs.getString(4));
            author.setWebPage(rs.getString(5));
            author.setBiography(rs.getString(6));
        }

        return author;
    }

}
