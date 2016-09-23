package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.PickBook;
import java.util.List;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public interface PickBookManager {
    int add(PickBook pickBook);

    PickBook getPickBookByID(int id);

    PickBook getPickBookByBookID(int id);

    List<PickBook> viewAllPickedBooks();

    List<PickBook> viewAllPickedBooksByUser(int id);

    PickBook update(PickBook pickBook);

    boolean delete(int id);
}
