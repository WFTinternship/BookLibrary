package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Pending;
import com.workfront.internship.booklibrary.dao.DataSource;
import com.workfront.internship.booklibrary.dao.PendingDAO;
import com.workfront.internship.booklibrary.dao.PendingDAOImpl;

import java.util.List;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public class PendingsManagerImpl implements PendingsManager{
    private PendingDAO pendingDAO;
    private DataSource dataSource;

    public PendingsManagerImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        pendingDAO = new PendingDAOImpl(dataSource);
    }

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
        ;
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

    private boolean pendingsValidator(Pending pending){
        return pending != null && pending.getUser() != null && pending.getBook() != null && pending.getPendingDate() != null;
    }
}
