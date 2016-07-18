package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.User;
import java.util.List;


public interface UserDAO {

    //insert all data fields of a user to the database
    int add(User user);

    //get all data fields of a user entry by 'user_id' field
    User getUserByID(int id);

    User getUserByeMail(String email);

    User getUserByUsername(String userName);

    //get all data fields of all the users in the database
    List<User> getAllUsers();

//    List<User> getAllUsersByPickedBookId(int bookId);

//    List<User> getAllUsersByPendingBookId(int bookId);

    //update all fields in a row
    void updateUser(User user);

    void deleteUser(int id);

    void deleteAll();
}
