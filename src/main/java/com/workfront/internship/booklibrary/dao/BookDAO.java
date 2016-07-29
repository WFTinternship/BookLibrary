package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Book;

import java.sql.Connection;
import java.util.List;

public interface BookDAO {

    int add(Book book);

    Book getBookByID(int id);

    Book getBookByTitle(String title);

    List<Book> getAllBooks();

    void updateBook(Book book);

    void updateBook(Connection connection, Book book);

    void deleteBook(int id);

    void deleteAll();

}
