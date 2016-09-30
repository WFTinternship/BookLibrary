package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.*;

/**
 * Created by ${Sona} on 7/21/2016.
 */

@Component
public class UserManagerImpl implements UserManager {
    @Autowired
    private UserDAO userDAO;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * Registering a new user. All mandatory fields are checked to be filled.
     *
     * @param user
     * @return user id or 0 if registration failed.
     */
    @Override
    public int register(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(user != null) {
            if (isValidEmail(user.geteMail())) {
                if (isValidUser(user)) {
                    user.setPassword(getHashedPassword(user.getPassword()));
                    userDAO.add(user);
                    return user.getId();
                }
            }
        }
        return 0;
    }

    @Override
    public User loginWithUsername(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = userDAO.getUserByUsername(username);
        if(user != null) {
            if (user.getPassword().equals(getHashedPassword(password))) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User loginWithEMail(String email, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = userDAO.getUserByeMail(email);
        if(user != null){
            if(user.getPassword().equals(getHashedPassword(password))){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findUserByID(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered.");
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
        if(user != null) {
            if(isValidUser(user)) {
                userDAO.updateUser(user);
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean deleteAccount(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        if(id > 0){
            userDAO.deleteUser(id);
            if(userDAO.getUserByID(id) == null){
                return true;
            }
        }
        return false;
    }


    public String getHashedPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return hashString(password, "SHA-256");
    }

    private String hashString(String message, String algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));

        return convertByteArrayToHexString(hashedBytes);
    }

    private String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    private boolean isValidEmail(String email){
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidUser(User user){
        return user.getUsername() != null && user.geteMail() != null && isValidEmail(user.geteMail()) &&
                user.getName() != null && user.getSurname() != null && user.getPassword() != null &&
                user.getAddress() != null && user.getPhone() != null && user.getAccessPrivilege() != null;
    }
}
