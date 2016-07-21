package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Book;
import java.util.List;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public interface BookManager {

    int add(Book book);

    //get book list ordered by title
    List<Book> showAll();

    //get book list ordered by author name, surname
    List<Book> showAllBooksByAuthor();

    //find by title
    Book findBookByTitle(String title);

    //update book info
    Book update(Book book);

    //delete book
    boolean delete(Book book);
}
