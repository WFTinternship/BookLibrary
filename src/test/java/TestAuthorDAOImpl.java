import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.dao.AuthorDAO;
import com.workfront.internship.booklibrary.dao.AuthorDAOImpl;
import com.workfront.internship.booklibrary.dao.DataSource;
import org.junit.*;

import java.beans.PropertyVetoException;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class TestAuthorDAOImpl {
    Author author = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Before
    public void setUp() throws PropertyVetoException, SQLException, IOException {
        connection = DataSource.getInstance().getConnection();
    }

    @After
    public void tearDown(){
        // TODO: 7/11/2016 delete created objects

        closeResource(resultSet);
        closeResource(preparedStatement);
        closeResource(connection);

    }

    @Test
    public void createAuthor() throws SQLException {
        author = createRegisteredAuthor();
        AuthorDAO authorDAO = new AuthorDAOImpl();

        Author actualAuthor = authorDAO.getAuthorByID(author.getAuthorId());

        assertNotNull(actualAuthor);
        assertEquals("expected and actual values do not match", author.getAuthorId(), actualAuthor.getAuthorId());
        assertEquals(author.getName(), actualAuthor.getName());
        assertEquals(author.getSurname(), actualAuthor.getSurname());
        assertEquals(author.geteMail(), actualAuthor.geteMail());
        assertEquals(author.getWebPage(), actualAuthor.getWebPage());
        assertEquals(author.getBiography(), actualAuthor.getBiography());
    }

    //@Test
   // public void getAuthorByID(){}


    private void closeResource(AutoCloseable closeable){
        try{
            if (closeable != null ) closeable.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    private Author registerAuthor(Author author) throws SQLException {
        String sql;
        sql = "INSERT INTO Author VALUES(?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, author.getAuthorId());
        preparedStatement.setString(2, author.getName());
        preparedStatement.setString(3, author.getSurname());
        preparedStatement.setString(4, author.geteMail());
        preparedStatement.setString(5, author.getWebPage());
        preparedStatement.setString(6, author.getBiography());

        preparedStatement.executeUpdate();

        // TODO: 7/11/2016 getLast id and add into returnable author


        return author;
    }
    private Author createRegisteredAuthor() throws SQLException {
        Author author = new Author();
        //author.setAuthorId(2);
        author.setName("Herbert");
        author.setSurname("Shildt");
        author.seteMail("shildt@yahoo.com");
        author.setBiography("ShildtBioLink");

        registerAuthor(author);

        return author;
    }


}
