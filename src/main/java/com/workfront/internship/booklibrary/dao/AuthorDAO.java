package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Author;
import java.util.List;

/**
 * Created by Workfront on 7/1/2016.
 */
public interface AuthorDAO {

    void createAuthor(Author author); //insert all data fields of an author to the database

    Author getAuthorByID(int id); //get all data fields of an author entry by 'author_id' field
    Author getAuthorByName(String name); //get all data fields of an author entry by 'name' field
    List<Author> getAllAuthors(); //get all data fields of all the authors in the database

    void updateAuthor(Author a); //update all fields in a row
    void updateAuthor(int id); //update author_id field of an author
    void updateAuthor(String s); //update one of name, surname, email, web_page, biography fields of an author

    void deleteAuthor(Author a);
}
