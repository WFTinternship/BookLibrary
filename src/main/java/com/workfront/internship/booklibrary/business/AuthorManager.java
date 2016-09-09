package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Author;

import java.util.List;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public interface AuthorManager {
    int uploadAuthorInfo(Author author);



    Author findAuthorByID(int id);

    Author findAuthorByName(String name);

    List<Author> viewAllAuthors();

 //   List<Author> viewAllAuthorsByBook(int id);

    Author update(Author author);

    boolean delete(int id);
}
