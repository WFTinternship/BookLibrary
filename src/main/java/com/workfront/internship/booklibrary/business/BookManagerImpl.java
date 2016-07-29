package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.dao.*;

import java.util.List;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public class BookManagerImpl implements BookManager{

    private BookDAO bookDAO;
    private AuthorManager authorManager;
    private DataSource dataSource;

    public BookManagerImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        bookDAO = new BookDAOImpl(dataSource);
        authorManager = new AuthorManagerImpl(dataSource);
    }

    @Override
    public int add(Book book, Author author) {
/**        if(author == null){
            throw new IllegalArgumentException("Duplicate entry is not allowed");
        }

        if(!authorMO.isExist(author.getId())){
            authorDAO.add()
        }
        if(author.getId() > 0){
            if(bookValidator(book)){
                bookDAO.add(book);
                return book.getId();
            }
        }
 */
        return 0;
    }

    @Override
    public Book findBookByID(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        Book book = bookDAO.getBookByID(id);
        if(bookValidator(book)){
            return book;
        }
        return null;
    }

    @Override
    public Book findBookByTitle(String title) {
        if(title == null){
            throw new IllegalArgumentException("Invalid title is entered");
        }
        Book book = bookDAO.getBookByTitle(title);
        if (bookValidator(book)){
            return book;
        }
        return null;
    }

    @Override
    public List<Book> viewAll() {
        List<Book> bookList = bookDAO.getAllBooks();
        if(bookList != null){
            return bookList;
        }
        return null;
    }

    @Override
    public List<Book> viewAllBooksByAuthor(int id) {

        return null;
    }//todo add DAO method

    @Override
    public List<Book> viewAllBooksByGenre(int id) {
        return null;
    } // todo add DAO method

    @Override
    public Book update(Book book) {
        if(bookValidator(book)){
            bookDAO.updateBook(book);
            return book;
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if(id < 1) {
            throw new IllegalArgumentException("Invalid id is entered");
        }
        if(id > 0){
            bookDAO.deleteBook(id);
            if(bookDAO.getBookByID(id) == null){
                return true;
            }
        }
        return false;
    }

    private boolean bookValidator(Book book){
        return book != null && book.getTitle() != null && book.getGenre()!= null && book.getBookAbstract() != null &&
                book.getLanguage() != null && book.getCount() > 0 && book.getEditionYear() != null && book.getPages() > 1 &&
                book.getCountryOfEdition() != null;
    }
}
