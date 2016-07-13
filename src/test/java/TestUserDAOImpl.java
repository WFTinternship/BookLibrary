import com.workfront.internship.booklibrary.common.User;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestUserDAOImpl {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Before
    public void setup() {
        User user = new User();
        user.setName("name");
        user.setSurname("surname");
        user.setUsername("username");
        user.setPassword("password");
        user.setAddress("address");
        user.seteMail("email");
        user.setPhone("phone number");
        user.setAccessPrivilege("user");

    }

    @After
    public void tearDown() {

    }

}
