package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import java.util.List;

public interface BookDAO {
    void createBook(Book book);

    Book getBookByID(int id);

    Book getBookByTitle(String title);

    List<Book> getAllBooks();

    void updateBook(Book book);

    void deleteBook(int id);
}
