package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Book;
import java.util.List;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public interface BookManager {

    int add(Book book, List<Author> authorList) throws Exception;

//    int add(Book book, List<Integer> authorsIdList) throws Exception;

    void addAuthorToBook(int bookId, int authorId);



    //find by id
    Book findBookByID(int id);

    //find by title
    Book findBookByTitle(String title);

    //get book list ordered by title
    List<Book> viewAll();

    List<Book> viewAllWithCondition(String string);

    //get book list ordered by author name, surname
//    List<Book> viewAllBooksByAuthor(int id);
//
//    List<Book> viewAllBooksByGenre(int id);

    //update book info
    Book update(Book book);

    //delete book
    boolean delete(int id);
}
