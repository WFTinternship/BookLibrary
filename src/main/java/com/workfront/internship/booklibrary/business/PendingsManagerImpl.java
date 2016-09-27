package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Pending;
import com.workfront.internship.booklibrary.dao.PendingDAO;
import com.workfront.internship.booklibrary.dao.PendingDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ${Sona} on 7/21/2016.
 */

@Component
public class PendingsManagerImpl implements PendingsManager{

    @Autowired
    private PendingDAO pendingDAO;

    @Override
    public int add(Pending pending) {
        if(pendingsValidator(pending)){
            pendingDAO.add(pending);
            if(pending.getId() > 0){
                return pending.getId();
            }
        }
        return 0;
    }

    @Override
    public Pending getPendingByID(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        Pending pending = pendingDAO.getPendingByID(id);
        if(pendingsValidator(pending)){
            return pending;
        }
        return null;
    }

    @Override
    public List<Pending> viewAllPendingByBook(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        if(id > 0){
            List<Pending> pendingList = pendingDAO.getAllPendingsByBookID(id);
            if(pendingList != null){
                return pendingList;
            }
        }
        return null;
    }

    @Override
    public List<Pending> viewAllPendingByUser(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }

        if(id > 0){
            List<Pending> pendingList = pendingDAO.getAllPendingsByUserID(id);
            if(pendingList != null){
                return pendingList;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        if(id > 0){
            pendingDAO.deletePending(id);
            if(pendingDAO.getPendingByID(id) == null){
                return true;
            }
        }
        return false;
    }

    public boolean isPended(int userId, int bookId){
        List<Pending> pendingList = viewAllPendingByUser(userId);
        for(Pending pendingBook : pendingList){
            if(pendingBook.getBook().getId() == bookId){
                return true;
            }
        }
        return false;
    }

    private boolean pendingsValidator(Pending pending){
        return pending != null && pending.getUser() != null && pending.getBook() != null && pending.getPendingDate() != null;
    }
}
