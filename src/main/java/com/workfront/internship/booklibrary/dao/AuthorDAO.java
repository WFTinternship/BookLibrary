package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;
import java.util.List;

public interface AuthorDAO {

    //insert all data fields of an author to the database
    int add(Author author);

    //get all data fields of an author entry by 'author_id' field
    Author getAuthorByID(int id);

    Author getAuthorByName(String name);

    //get all data fields of all the authors in the database
    List<Author> getAllAuthors();

    //update all fields in a row
    void updateAuthor(Author author);

    void deleteAuthor(int id);
}
