package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.PickBook;
import java.util.List;

public interface PickBookDAO {
    int add(PickBook pickedBook);

    PickBook getPickedBookByID(int id);

    List<PickBook> getAllPickedBooks();

    List<PickBook> getAllPickedBooksByUserId(int userId);

    void updatePickedBook(PickBook pickedBook);

    void deletePickedBook(int id);

    void deleteAllPickedBooks();
}
