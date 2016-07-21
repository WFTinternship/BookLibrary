package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.dao.UserDAO;
import com.workfront.internship.booklibrary.dao.UserDAOImpl;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public class UserManagerImpl implements UserManager {
    private UserDAO userDAO;

    /**
     * Registering a new user. All mandatory fields are checked to be filled.
     *
     * @param user
     * @return
     */
    @Override
    public int registration(User user) {
        if(user.getUsername() != null & user.geteMail() != null){
            userDAO.add(user);
            return user.getId();
        }
        return 0;
    } //todo password-i het kapvac coding petq e arvi yev veradarzvi?

    @Override
    public boolean login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if(user.getId() != 0 & user.getName() != null & user.getSurname() != null & user.getUsername() != null & user.getPassword() != null &
                user.getAddress() != null & user.geteMail()!= null & user.getPhone() != null & user.getAccessPrivilege() != null){
            return true;
        }
        return false;
    }

    @Override
    public User findUserByUserName(String username) {
        return null;
    }

    @Override
    public User findUserByeMail(String email) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean deleteAccount(User user) {
        return false;
    }
}
