package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.User;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public interface UserManager {

    //registering a user first time and returning its id
    int registration(User user);

    //logging into existing user account
    boolean login(String username, String password);

    //find by username
    User findUserByUserName(String username);

    //find by e-mail
    User findUserByeMail(String email);

    //update user info
    User update(User user);

    //delete user account
    boolean deleteAccount(User user);
}
