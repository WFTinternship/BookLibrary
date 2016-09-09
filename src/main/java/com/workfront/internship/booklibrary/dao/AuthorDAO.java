package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;

import java.sql.Connection;
import java.util.List;

public interface AuthorDAO {

    //insert all data fields of an author to the database
    int add(Author author);

    public List<Integer> checkAndAdd(List<Author> authorList);

    //get all data fields of an author entry by 'author_id' field
    Author getAuthorByID(int id);

    Author getAuthorByName(String name);

    //get all data fields of all the authors in the database
    List<Author> getAllAuthors();

    List<Author> getAllAuthorsByBookId(int bookId);

//    void updateAuthor(Connection connection, Author author);

    //update all fields in a row
    void updateAuthor(Author author);

//    void checkAndUpdateAuthor(Connection connection, Author author);

//    void checkAndUpdateAuthorList(Connection connection, List<Author> authorList);

    void deleteAuthor(int id);

    void deleteAllAuthors();

    boolean isExist(int id);
}
