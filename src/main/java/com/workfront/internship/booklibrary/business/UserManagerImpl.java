package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.dao.DataSource;
import com.workfront.internship.booklibrary.dao.UserDAO;
import com.workfront.internship.booklibrary.dao.UserDAOImpl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.*;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public class UserManagerImpl implements UserManager {
    private UserDAO userDAO;
    private DataSource dataSource;

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public UserManagerImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        this.userDAO = new UserDAOImpl(dataSource);
    }

    /**
     * Registering a new user. All mandatory fields are checked to be filled.
     *
     * @param user
     * @return user id or 0 if registration failed.
     */
    @Override
    public int registration(User user) {
        if(user != null) {
            if (emailFormatValidator(user.geteMail())) {
                if (user.getUsername() != null && user.geteMail() != null &&
                        user.getName() != null && user.getSurname() != null && user.getPassword() != null &&
                        user.getAddress() != null && user.getPhone() != null && user.getAccessPrivilege() != null) {
                    user.setPassword(getHashedPassword(user.getPassword()));
                    userDAO.add(user);
                    // todo send message to user's email asking to confirm the registration
                    return user.getId();
                }
            }

        }
        return 0;
    }

    @Override
    public User loginWithUsername(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if(user != null) {
            if (user.getPassword() == getHashedPassword(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User loginWithEMail(String email, String password) {
        User user = userDAO.getUserByeMail(email);
        if(user != null){
            if(user.getPassword() == getHashedPassword(password)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findUserByID(int id) {
        if(id < 0){
            throw new IllegalArgumentException("Wrong id is entered.");
        }
        User user = userDAO.getUserByID(id);
        if(user != null) {
            return user;
        }
        return null;
    }


    @Override
    public User findUserByUserName(String username) {
        if(username == null){
            throw new IllegalArgumentException("No username is entered");
        }
        User user = userDAO.getUserByUsername(username);
        if(user != null){
            return user;
        }
        return null;
    }

    @Override
    public User findUserByeMail(String email) {
        if(email == null){
            throw new IllegalArgumentException("No email is entered");
        }
        User user = userDAO.getUserByeMail(email);
        if(user != null){
            return user;
        }
        return null;
    }

    @Override
    public User update(User user) {
        userDAO.updateUser(user);
        return user;
    }

    @Override
    public boolean deleteAccount(User user) {
        int id = user.getId();
        userDAO.deleteUser(id);
        if(userDAO.getUserByID(id) == null){
            return true;
        }else {
            return false;
        }
    }

    private String getHashedPassword(String password){
        return hashString(password, "SHA-256");
    }

    private String hashString(String message, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));

            return convertByteArrayToHexString(hashedBytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new RuntimeException("Could not generate hash from String", ex);
        }
    }

    private String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    private boolean emailFormatValidator(String email){
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }



/**
 import static org.junit.Assert.*;
 import java.util.Arrays;
 import java.util.Collection;
 import org.junit.BeforeClass;
 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.junit.runners.Parameterized;
 import org.junit.runners.Parameterized.Parameters;

  @RunWith(Parameterized.class)

    public class EmailFormatValidatorTest {

        private String arg;
        private static EmailFormatValidator emailFormatValidator;//
        private Boolean expectedValidation;

        public EmailFormatValidatorTest(String str, Boolean expectedValidation) {//
            this.arg = str;//
            this.expectedValidation = expectedValidation;//
        }


        @BeforeClass//
        public static void initialize() {//
            emailFormatValidator = new EmailFormatValidator();//
        }



        @Parameters//
        public static Collection<Object[]> data() {

            Object[][] data = new Object[][] {

                    { "javacodegeeks@gmail.com.2j",false },    // it's not allowed to have a digit in the second level tld

                    { "java@java@oracle.com", false },         // you cannot have @ twice in the address

                    { "java!!!@example.com", false },          // you cannot the have special character '!' in the address

                    { "mysite@.com", false },                  // tld cannot start with a dot

                    { "javacodegees.com", false },             // must contain a @ character and a tld

                    { ".javacodegees.com@at.com", false },     // the address cannot start with a dot

                    { "javacodegees..javacom@at.com", false }, // you cannot have double dots in your address

                    { "javacodegeeks@gmail.com",true },

                    { "nikos+mylist@gmail.com", true },

                    { "abc.efg-900@gmail-list.com", true },

                    { "abc123@example.com.gr", true } };

            return Arrays.asList(data);

        }

        @Test
        public void test() {
            Boolean res = emailFormatValidator.validate(this.arg);
            String validv = (res) ? "valid" : "invalid";
            System.out.println("Hex Color Code "+arg+ " is " + validv);
            assertEquals("Result", this.expectedValidation, res);
        }
    }
*/

}
