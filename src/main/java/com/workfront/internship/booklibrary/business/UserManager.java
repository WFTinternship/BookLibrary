package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public interface UserManager {

    //registering a user first time and returning its id
    int registration(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    //logging into existing user account
    User loginWithUsername(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    User loginWithEMail(String email, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    //find user by id
    User findUserByID(int id);

    //find by username
    User findUserByUserName(String username);

    //find by e-mail
    User findUserByeMail(String email);

    //update user info
    User update(User user);

    //delete user account
    boolean deleteAccount(int id);

    String getHashedPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
