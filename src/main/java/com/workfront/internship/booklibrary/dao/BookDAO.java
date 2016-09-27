package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;

import java.sql.Connection;
import java.util.List;

public interface BookDAO {

    int add(Book book, List<Integer> authorsIdList);

    Book getBookByID(int id);

    Book getBookByTitle(String title);

    List<Book> getAllBooks();

    List<Book> getAllBooksWithCondition(String string);

    void updateBook(Book book);

    void updateBook(Connection connection, Book book);

    void deleteBook(int id);

    void deleteAll();

    boolean isExist(int id);

    void addAuthorToBook(int bookId, int authorID);
}
