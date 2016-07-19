package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ${Sona} on 7/13/2016.
 */
public class TestUtil {
    public static void closeResource(AutoCloseable closeable){
        try{
            if (closeable != null ) closeable.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String uuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static PickBook getRandomPickBook(Book book, User user){
        PickBook pickBook = new PickBook();
        pickBook.setBook(book);
        pickBook.setUser(user);

        DateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        Date todate1 = cal.getTime();
        String fromdate = dateFormat.format(todate1);
        pickBook.setPickingDate(Timestamp.valueOf(fromdate));
        pickBook.setReturnDate(Timestamp.valueOf(fromdate));

        return pickBook;
    }


    public static Pending getRandomPending(User user, Book book){
        Pending pending = new Pending();
        pending.setUser(user);
        pending.setBook(book);

        DateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        Date todate1 = cal.getTime();
        String fromdate = dateFormat.format(todate1);
        pending.setPendingDate(Timestamp.valueOf(fromdate));

        return pending;
    }

    public static Genre getRandomGenre(){
        Genre genre = new Genre();
        genre.setGenre("education" + uuid());
        return genre;
    }

    public static Book getRandomBook(Genre genre) {
        Book book = new Book();

        book.setISBN("0123456789");
        book.setTitle("New Book" + uuid());
        book.setGenre(genre);
        book.setVolume(1);
        book.setBookAbstract("BookAbstract");
        book.setLanguage("English");
        book.setCount(0);
        book.setEditionYear("2015");
        book.setPages(100);
        book.setCountryOfEdition("Armenia");

        return book;
    }

    public static Media getRandomMedia(Book book){
        Media media = new Media();

        media.setLink("GO_TO_MEDIA" + uuid());
        media.setType("photo");
        media.setBook(book);

        return media;
    }

    public static Author getRandomAuthor() {
        Author author = new Author();

        author.setName("Hermann");
        author.setSurname("Hesse" + uuid());
        author.seteMail("hehe@yahoo.com");
        author.setBiography("HermannHesseBioLink");

        return author;
    }

    public static User getRandomUser(){
        User user = new User();
        user.setName("name");
        user.setSurname("surname");
        user.setUsername("username" + uuid());
        user.setPassword("password");
        user.setAddress("address");
        user.seteMail("sona" + uuid() + "@yahoo.com");
        user.setPhone("phone number");
        user.setAccessPrivilege("user");

        return user;
    }
}
