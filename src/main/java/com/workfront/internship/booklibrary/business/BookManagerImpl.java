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
    private AuthorDAO authorDAO;
    private AuthorManager authorManager;
    private DataSource dataSource;

    public BookManagerImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        bookDAO = new BookDAOImpl(dataSource);
        authorDAO = new AuthorDAOImpl(dataSource);
        authorManager = new AuthorManagerImpl(dataSource);
    }

    @Override
    public int add(Book book, List<Author> authorList) throws Exception {
//        if(authorList.isEmpty()) {
//            throw new Exception("Trying to add a book without mentioning its author/s.");
//        }
//        if(!bookValidator(book) | book.getId()>0) {
//            throw new Exception("Trying to add a book with invalid parameter/s.");
//        }
//        if(!authorList.isEmpty()){
//            for(Author author : authorList){
//                if(!bookDAO.contains(author.getId())) { //todo in BookDAO add a method contains(int authorId) which gets info from book_author table
//                    bookDAO.addAuthorToBook(book.getId(), author.getId());
//                }else if(!book.getAuthors().get(author.getId()).equals(author)) {
//                    authorDAO.checkAndUpdateAuthor(dataSource.getConnection(), author); //todo correct the method in authorDAO
//                }
//            }
//        }
//        book.setAuthors(authorList);
//
//        if(bookValidator(book)){
//            bookDAO.add(book);
//            return book.getId();
//        }
//        else return 0;
        return 0; //delete this row
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
