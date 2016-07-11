package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.PickBook;
import java.util.List;

public interface PickBookDAO {
    void createPickBook(PickBook pickedBook);

    PickBook getPickedBookByID(int id);

    List<PickBook> getAllPickedBooks();

    void updatePickedBook(PickBook pickedBook);

    void deletePickedBook(int id);
}
