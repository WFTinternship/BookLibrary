package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.PickBook;
import com.workfront.internship.booklibrary.dao.DataSource;
import com.workfront.internship.booklibrary.dao.PickBookDAO;
import com.workfront.internship.booklibrary.dao.PickBookDAOImpl;

import java.util.List;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public class PickBookManagerImpl implements PickBookManager{
    private PickBookDAO pickBookDAO;
    private DataSource dataSource;

    public PickBookManagerImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        pickBookDAO = new PickBookDAOImpl(dataSource);
    }

    @Override
    public int add(PickBook pickBook) {
        if(pickBookValidator(pickBook)){
            pickBookDAO.add(pickBook);
            if(pickBook.getId() > 0){
                return pickBook.getId();
            }
        }
        return 0;
    }

    @Override
    public PickBook getPickBookByID(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        PickBook pickBook = pickBookDAO.getPickedBookByID(id);
        if(pickBookValidator(pickBook)){
            return pickBook;
        }
        return null;
    }

    @Override
    public List<PickBook> viewAllPickedBooks() {
        List<PickBook> pickedBookList = pickBookDAO.getAllPickedBooks();
        if(pickedBookList != null){
            return pickedBookList;
        }
        return null;
    }

    @Override
    public List<PickBook> viewAllPickedBooksByUser(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        if(id > 0){
            List<PickBook> pickedBookList = pickBookDAO.getAllPickedBooksByUserId(id);
            if(pickedBookList != null){
                return pickedBookList;
            }
        }
        return null;
    }

    @Override
    public PickBook update(PickBook pickBook) {
        if(pickBookValidator(pickBook)){
            pickBookDAO.updatePickedBook(pickBook);
            return pickBook;
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        if(id > 0){
            pickBookDAO.deletePickedBook(id);
            if(pickBookDAO.getPickedBookByID(id) == null){
                return true;
            }
        }
        return false;
    }

    private boolean pickBookValidator(PickBook pickBook){
        return pickBook.getBook() != null && pickBook.getUser()!= null &&
                pickBook.getPickingDate() != null && pickBook.getReturnDate() != null;
    }
}
