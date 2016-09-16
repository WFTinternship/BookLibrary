package com.workfront.internship.booklibrary.controller;

import com.workfront.internship.booklibrary.business.*;
import com.workfront.internship.booklibrary.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import static com.workfront.internship.booklibrary.controller.ControllerUtil.getIntegerFromString;

/**
 * Created by ${Sona} on 9/9/2016.
 */
@Controller
public class AdministratorController {

    @Autowired
    private AuthorManager authorManager;
    @Autowired
    private GenreManager genreManager;
    @Autowired
    private MediaTypeManager mediaTypeManager;
    @Autowired
    private MediaManager mediaManager;
    @Autowired
    private BookManager bookManager;

    @RequestMapping("/addAuthor")
    public String addAuthor(HttpServletRequest request){
        Author author = new Author();

        String name=request.getParameter("name");
        String surname=request.getParameter("surname");
        String authorBirthYearString = request.getParameter("authorBirthYear");
        int authorBirthYear =Integer.parseInt(authorBirthYearString);
        String authorBirthCity = request.getParameter("authorBirthCity");
        String email=request.getParameter("email");
        String webPage=request.getParameter("web-page");
        String biography=request.getParameter("biography:e");

        author.setName(name);
        author.setSurname(surname);
        author.setBirthYear(authorBirthYear);
        author.setBirthCity(authorBirthCity);
        author.seteMail(email);
        author.setWebPage(webPage);
        author.setBiography(biography);

        authorManager.uploadAuthorInfo(author);

        if(author==null) {
            return "addAuthor";
        }

        request.getSession().setAttribute("author", author);
        return "redirect:/administrator";
    }

    @RequestMapping("/addBook")
    public String addBook(HttpServletRequest request){
        Book book = new Book();
        Genre genre = new Genre();
        Author author = new Author();
        List<Author> authorList = new ArrayList<>();

        String genreIdString = request.getParameter("genre");

        int genreId = Integer.parseInt(genreIdString);
        genre = genreManager.findGenreByID(genreId);

        String authorName = request.getParameter("authorName");
        String authorSurname = request.getParameter("authorSurname");
        String authorBirthYearString = request.getParameter("authorBirthYear");
        int authorBirthYear =Integer.parseInt(authorBirthYearString);
        String authorBirthCity = request.getParameter("authorBirthCity");
        String email=request.getParameter("email");
        String webPage=request.getParameter("web-page");
        String biography=request.getParameter("biography:e");
        author.setName(authorName);
        author.setSurname(authorSurname);
        author.setBirthYear(authorBirthYear);
        author.setBirthCity(authorBirthCity);
        author.seteMail(email);
        author.setWebPage(webPage);
        author.setBiography(biography);
        authorList.add(author);

        String title = request.getParameter("title");
        String volumeString = request.getParameter("volume");
        int volume = getIntegerFromString(volumeString); // volumeString == null || volumeString == ""? 0 : Integer.parseInt(volumeString);
        String bookAbstract = request.getParameter("abstract");
        String language = request.getParameter("language");
        int bookCount = Integer.parseInt(request.getParameter("count"));
        String editionYear = request.getParameter("editionYear");
        int pages = Integer.parseInt(request.getParameter("pages"));
        String countryOfEdition = request.getParameter("countryOfEdition");

        book.setTitle(title);
        book.setVolume(volume);
        book.setBookAbstract(bookAbstract);
        book.setLanguage(language);
        book.setCount(bookCount);
        book.setEditionYear(editionYear);
        book.setPages(pages);
        book.setCountryOfEdition(countryOfEdition);
        book.setGenre(genre);

        try{
            bookManager.add(book, authorList);

        }catch (Exception e) {
            e.printStackTrace();
            return "addBook";
        }

        request.setAttribute("book", book);
        return "redirect:/administrator";
    }

    @RequestMapping("/addGenre")
    public String addGenre(HttpServletRequest request){
        Genre genre = new Genre();
        String genreName = request.getParameter("genre");
        genre.setGenre(genreName);
        genreManager.add(genre);
        if(genre == null){
            return "addGenre";
        }
        request.getSession().setAttribute("genre", genre);
        return "redirect:/administrator";
    }

    @RequestMapping("/addAuthorToBook")
    public String addAuthorToBook(HttpServletRequest request){
        Book book = new Book();
        Author author = new Author();

        String bookIdString = request.getParameter("book");
        int bookId = Integer.parseInt(bookIdString);

        String authorIdString = request.getParameter("author");
        int authorId = Integer.parseInt(authorIdString);

        bookManager.addAuthorToBook(bookId, authorId);

        return "redirect:/administrator";
    }

    @RequestMapping("/viewAuthorsOfBook")
    public String viewAuthorsOfBook(HttpServletRequest request){
        Book book = new Book();
        String bookIdString = request.getParameter("bookId");
        int bookId = getIntegerFromString(bookIdString);
        book = bookManager.findBookByID(bookId);
        request.setAttribute("book", book);

        List<Author> authorList = authorManager.viewAllAuthorsByBook(bookId);
        request.setAttribute("view authors", authorList);

        return "showAuthorsOfBook";
    }

    @RequestMapping("/addMediaType")
    public String addMediaType(HttpServletRequest request){
        MediaType mediaType = new MediaType();
        String mediaTypeName = request.getParameter("mediaType");
        mediaType.setType(mediaTypeName);
        mediaTypeManager.add(mediaType);
        if(mediaType == null){
            return "addMediaType";
        }
        request.getSession().setAttribute("mediaType", mediaType);
        return "redirect:/administrator";
    }




    @RequestMapping("/addMedia")
    public String addMedia(HttpServletRequest request){
        Media media = new Media();
        String mediaName = request.getParameter("mediaName");
        media.setLink(mediaName);
        return "redirect:/administrator";
    }





    @RequestMapping("/updateBookDetails")
    public String updateBookDetails(HttpServletRequest request){
        return "";
    }


    @RequestMapping("/administrator")
    public String getAdminPage(HttpServletRequest request){
        List<Genre> genreList = genreManager.viewAll();
        List<Author> authorList = authorManager.viewAllAuthors();
        List<Book> bookList = bookManager.viewAll();

        request.setAttribute("genres", genreList);
        request.setAttribute("authors", authorList);
        request.setAttribute("books", bookList);

        return "administrator";
    }

}
