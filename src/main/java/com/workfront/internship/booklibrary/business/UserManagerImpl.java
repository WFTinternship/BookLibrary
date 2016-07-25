package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.dao.DataSource;
import com.workfront.internship.booklibrary.dao.UserDAO;
import com.workfront.internship.booklibrary.dao.UserDAOImpl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public class UserManagerImpl implements UserManager {
    private UserDAO userDAO;
    private DataSource dataSource;

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
        if(user != null){
            if(user.getUsername() != null && user.geteMail() != null &&
                    user.getName() != null && user.getSurname() != null && user.getPassword() != null &&
                    user.getAddress() != null && user.getPhone() != null && user.getAccessPrivilege() != null){
                user.setPassword(getHashedPassword(user.getPassword()));
                userDAO.add(user);
                // todo send message to user's email asking to confirm the registration
                return user.getId();
            }
        }
        return 0;
    }

    @Override
    public boolean loginWithUsername(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if(user != null){
            if(user.getPassword() == getHashedPassword(password)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean loginWithEMail(String email, String password) {
        User user = userDAO.getUserByeMail(email);
        if(user != null){
            if(user.getPassword() == getHashedPassword(password)){
                return true;
            }
        }
        return false;
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
        return hashString(password, "SHA-1");
    } //todo implement method body and use in registration() method

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
}
