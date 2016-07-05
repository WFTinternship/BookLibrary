package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import java.util.List;

/**
 * Created by Workfront on 6/30/2016.
 */
public interface BookDAO {
    void createBook(Book book);

    Book getBookByID(int id);

    List<Book> getAllBooks();

    void updateBook(Book b);

    void deleteBook(int id);
}
