package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.User;
import java.util.List;

/**
 * Created by Sona on 6/30/2016.
 */
public interface UserDAO {

    //insert all data fields of a user to the database
    void createUser(User user);

    //get all data fields of a user entry by 'user_id' field
    User getUserByID(int id);

    //get all data fields of all the users in the database
    List<User> getAllUsers();

    //update all fields in a row
    void updateUser(User user);

    void deleteUser(int id);
}
