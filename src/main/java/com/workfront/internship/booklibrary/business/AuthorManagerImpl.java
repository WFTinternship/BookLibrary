package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.dao.AuthorDAO;
import com.workfront.internship.booklibrary.dao.AuthorDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ${Sona} on 7/21/2016.
 */

@Component
public class AuthorManagerImpl implements AuthorManager{

    @Autowired
    private AuthorDAO authorDAO;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public int uploadAuthorInfo(Author author) {
        if(isValidAuthor(author)) {
            authorDAO.add(author);
            return author.getId();
        }
        return 0;
    }

    @Override
    public Author findAuthorByID(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered.");
        }
        Author author = authorDAO.getAuthorByID(id);
        if(isValidAuthor(author)){
            return author;
        }
        return null;
    }

    @Override
    public Author findAuthorByName(String name) {
        if(name == null){
            throw new IllegalArgumentException("No username is entered");
        }
        Author author = authorDAO.getAuthorByName(name);
        if(isValidAuthor(author)){
            return author;
        }
        return null;
    }

    @Override
    public List<Author> viewAllAuthors() {
        List<Author> authorList = authorDAO.getAllAuthors();
        if(authorList != null){
            return authorList;
        }
        return null;
    }

    @Override
    public List<Author> viewAllAuthorsByBook(int id) {
        List<Author> authorList = new ArrayList<>();
        authorList = authorDAO.getAllAuthorsByBookId(id);

        return authorList;
    }

    @Override
    public Author update(Author author) {
        if(isValidAuthor(author)){
            authorDAO.updateAuthor(author);
            return author;
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        if(id > 0){
            authorDAO.deleteAuthor(id);
            if(authorDAO.getAuthorByID(id) == null) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidEmail(String email){
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidAuthor(Author author){
        return author != null && author.getName() != null && author.getSurname() != null ;  //&&(author.geteMail() != null && isValidEmail(author.geteMail()))
    }
}
