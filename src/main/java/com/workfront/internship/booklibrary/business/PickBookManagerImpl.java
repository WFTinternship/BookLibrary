package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Pending;
import com.workfront.internship.booklibrary.common.PickBook;
import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.dao.PickBookDAO;
import com.workfront.internship.booklibrary.dao.PickBookDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.workfront.internship.booklibrary.controller.ControllerUtil.getDateTime;
import static com.workfront.internship.booklibrary.controller.ControllerUtil.getIntegerFromString;

/**
 * Created by ${Sona} on 7/21/2016.
 */

@Component
public class PickBookManagerImpl implements PickBookManager{
    @Autowired
    private PickBookDAO pickBookDAO;
    @Autowired
    private BookManager bookManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private PendingsManager pendingsManager;

    @Override
    public int add(PickBook pickBook) {
        if(pickBookValidator(pickBook) && pickBook.getBook().getCount() > 0){
            pickBookDAO.add(pickBook);
            if(pickBook.getId() > 0){
                return pickBook.getId();
            }
        }
        return 0;
    }

    @Override
    public String addPickOrPend(int bookId, int userId) {
        String message=null;
        PickBook pickBook = new PickBook();
        Pending pend = new Pending();

        Book book = bookManager.findBookByID(bookId);
        User user = userManager.findUserByID(userId);

        pickBook.setPickingDate(Timestamp.valueOf(getDateTime(0)));
        pickBook.setReturnDate(Timestamp.valueOf(getDateTime(10)));
        pickBook.setBook(book);
        pickBook.setUser(user);

        try{
            if(book.getCount() == 0){
                if(pendingsManager.isPended(userId, bookId)){
                    message = "{\"success\":false, \"message\":\"You already pend for the book\"}";
//                    writer.write("{\"success\":false, \"message\":\"You already pend for the book\"}");
                }else if(isPicked(userId, bookId)) {
                    message = "{\"success\":false, \"message\":\"You already picked the book\"}";
                }else {
                    pend.setPendingDate(Timestamp.valueOf(getDateTime(0)));
                    pend.setBook(book);
                    pend.setUser(user);
                    pendingsManager.add(pend);
                    message = "{\"success\":false, \"message\":\"The book is not available now. You are set to pend for it.\"}";
                }

            }
            else if(lastTimePickedSoonerThanNow(userId, bookId)) {
                add(pickBook);
                message = "{\"success\":true, \"book\":{\"count\":" + book.getCount() + "}}";
            } else {
                message = "{\"success\":false, \"message\":\"the book is already picked\"}";
            }

        }catch (Exception e) {
            message = "No book available at this moment.\nYou can pend for it now";
        }

        return message;
    }

    @Override
    public PickBook getPickBookByID(int id) {
        if(id < 1){
//            throw new IllegalArgumentException("Invalid id is entered");
            return null;
        }
        PickBook pickBook = pickBookDAO.getPickedBookByID(id);
        if(pickBookValidator(pickBook)){
            return pickBook;
        }
        return null;
    }

    @Override
    public PickBook getPickBookByBookID(int id){
        if(id < 1){
//            throw new IllegalArgumentException("Invalid id is entered");
            return null;
        }
        PickBook pickBook = pickBookDAO.getPickedBookByBookID(id);
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

    @Override
    public boolean lastTimePickedSoonerThanNow(int userId, int bookId){
        List<PickBook> pickedBooksOfUser = viewAllPickedBooksByUser(userId);
        for(PickBook pickedBook : pickedBooksOfUser){
            if(pickedBook.getBook().getId() == bookId){
                DateFormat dateFormat =
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 0);
                Date now = cal.getTime();
                String nowDate = dateFormat.format(now);
                if(pickedBook.getReturnDate().compareTo(Timestamp.valueOf(nowDate)) <= 0){ //yete return  date-y aveli shut e qan stugman pahy
                    return true;
                } else{return false;}
            } //ays girqy pick chi arac
        }
        return true;
    }

    @Override
    public boolean isPicked(int userId, int bookId){
        List<PickBook> pickBookList = viewAllPickedBooksByUser(userId);
        for(PickBook pickBook : pickBookList){
            if(pickBook.getBook().getId() == bookId){
                return true;
            }
        }
        return false;
    }

    private boolean pickBookValidator(PickBook pickBook){
        return pickBook != null && pickBook.getBook() != null && pickBook.getUser()!= null &&
                pickBook.getPickingDate() != null && pickBook.getReturnDate() != null;
    }
}
