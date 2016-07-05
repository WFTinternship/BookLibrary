package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;
import java.util.List;

/**
 * Created by Workfront on 7/1/2016.
 */
public interface AuthorDAO {

    //insert all data fields of an author to the database
    void createAuthor(Author author);

    //get all data fields of an author entry by 'author_id' field
    Author getAuthorByID(int id);

    //get all data fields of all the authors in the database
    List<Author> getAllAuthors();

    //update all fields in a row
    void updateAuthor(Author a);

    void deleteAuthor(int id);
}
