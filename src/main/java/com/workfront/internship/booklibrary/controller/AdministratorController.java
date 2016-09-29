package com.workfront.internship.booklibrary.controller;

import com.workfront.internship.booklibrary.business.*;
import com.workfront.internship.booklibrary.common.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.logging.Logger;

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
    @Autowired
    private PickBookManager pickBookManager;
    @Autowired
    private PendingsManager pendingsManager;

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(FileUploadController.class);
    //    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private MultipartResolver multipartResolver;

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
        String biography=request.getParameter("biography");

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
        String biography=request.getParameter("biography");

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

    @RequestMapping("/viewMediasForBook")
    public String showMediasOfBook(HttpServletRequest request){
        Book book = new Book();
        String bookIdString = request.getParameter("bookIdForMedias");
        int bookId = getIntegerFromString(bookIdString);
        book = bookManager.findBookByID(bookId);
        request.setAttribute("book", book);

        List<Media> mediaList = mediaManager.viewAllMediaByBookId(bookId);
        request.setAttribute("view medias", mediaList);
        if(mediaList.isEmpty()){
            return "redirect:/administrator";
        }

        return "ShowMediasOfBook";
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

    @RequestMapping(value = "/addMediaToBook", method = RequestMethod.POST)
    public String addMediaToBook(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Media media = new Media();

        ServletContext context = request.getSession().getServletContext();
        String contextPath  = context.getRealPath("WEB-INF\\..");

        String bookIdString = request.getParameter("bookId");
        int bookId = Integer.parseInt(bookIdString);

        String mediaTypeIdString = request.getParameter("mediaTypeId");
        int mediaTypeId = Integer.parseInt(mediaTypeIdString);

//        String rootPath = System.getProperty("catalina.home");
//        String rootPath = "C:\\Users\\Workfront\\IdeaProjects\\BookLibrary\\src\\main\\webapp\\resources\\image";
        String uploadPath = contextPath + "\\resources\\upload";

        String filePath = null;

            if (!file.isEmpty()) {
                filePath = uploadImage(uploadPath, file);
            }

        media.setLink(filePath);
        media.setType(mediaTypeManager.getMediaTypeByID(mediaTypeId));
        media.setBook(bookManager.findBookByID(bookId));
        mediaManager.add(media);

        return "redirect:/administrator";
    }

    private String uploadImage(String uploadPath, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = null;

        //create file path
        File uploadFolder = new File(uploadPath);
        if(!uploadFolder.exists()){
            uploadFolder.mkdir();
        }
        filePath = uploadPath + File.separator + fileName;
        File storeFile = new File(filePath);

        // saves the file on disk
        FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
        return filePath;
    }

    @RequestMapping("/showBook")
    public String showBookDetails(HttpServletRequest request){
        List<Book> bookList = bookManager.viewAll();
        request.setAttribute("books", bookList);
        return "redirect:/administrator";
    }

    @RequestMapping(value="/editBook", method = RequestMethod.POST)
    public String editBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Book book = new Book();
        Genre genre = new Genre();
        String genreIdString = request.getParameter("genre");

        int genreId = Integer.parseInt(genreIdString);
        genre = genreManager.findGenreByID(genreId);

        int bookId = getIntegerFromString(request.getParameter("bookId"));
        String title = request.getParameter("title");
        String volumeString = request.getParameter("volume");
        int volume = getIntegerFromString(volumeString);
        String bookAbstract = request.getParameter("bookAbstract");
        String language = request.getParameter("language");
        int bookCount = Integer.parseInt(request.getParameter("count"));
        String editionYear = request.getParameter("editionYear");
        int pages = Integer.parseInt(request.getParameter("pages"));
        String countryOfEdition = request.getParameter("countryOfEdition");

        book.setId(bookId);
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
            bookManager.update(book);

        }catch (Exception e) {
            e.printStackTrace();
            return "/ErrorPage";
        }

        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        writer.write("{\"success\":true}");
        writer.close();

        return "redirect:/administrator";
    }

    private boolean bookIsPicked(int bookId){
        return !(pickBookManager.getPickBookByBookID(bookId) == null);
    }

    @RequestMapping(value="/deleteBook", method = RequestMethod.POST)
    public String deleteBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Book book = new Book();
        int id = getIntegerFromString(request.getParameter("bookId"));

        try{
            if(!bookIsPicked(id)) {
                bookManager.delete(id);
            }else{
                throw new Exception("The book is picked and cannot be deleted");
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Cannot delete the book");
//            return "/ErrorPage";
        }

        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        writer.write("{\"success\":true}");
        writer.close();

//        request.setAttribute("bookId", 0);
        return "redirect:/administrator";
    }

    @RequestMapping("/administrator")
    public String getAdminPage(HttpServletRequest request){
        List<Genre> genreList = genreManager.viewAll();
        List<Author> authorList = authorManager.viewAllAuthors();
        List<Book> bookList = bookManager.viewAll();
        List<MediaType> mediaTypeList = mediaTypeManager.viewAllMediaTypes();

        request.setAttribute("genres", genreList);
        request.setAttribute("authors", authorList);
        request.setAttribute("books", bookList);
        request.setAttribute("mediaTypes", mediaTypeList);

        return "administrator";
    }

}
