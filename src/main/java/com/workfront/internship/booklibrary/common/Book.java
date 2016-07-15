package com.workfront.internship.booklibrary.common;

import java.util.List;

public class Book {
    private int bookId;
    private String ISBN;
    private String title;
    private Genre genre;
    //private int genreId;
    private int volume;
    private String bookAbstract;
    private String language;
    private int count;
    private String editionYear;
    private int pages;
    private String countryOfEdition;

    private List<Media> medias;
    private List<Author> authors;
    private List<Pending> pendingList;
    private List<PickBook> pickBookList;

    public int getId() {
        return bookId;
    }

    public Book setId(int bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getISBN() {
        return ISBN;
    }

    public Book setISBN(String ISBN) {
        this.ISBN = ISBN;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
    public Genre getGenre() {
        return genre;
    }

    public Book setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }
     */

    public Genre getGenre() {
        return genre;
    }

    public Book setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public int getVolume() {
        return volume;
    }

    public Book setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    public String getBookAbstract() {
        return bookAbstract;
    }

    public Book setBookAbstract(String bookAbstract) {
        this.bookAbstract = bookAbstract;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public Book setLanguage(String language) {
        this.language = language;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Book setCount(int count) {
        this.count = count;
        return this;
    }

    public String getEditionYear() {
        return editionYear;
    }

    public Book setEditionYear(String editionYear) {
        this.editionYear = editionYear;
        return this;
    }

    public int getPages() {
        return pages;
    }

    public Book setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public String getCountryOfEdition() {
        return countryOfEdition;
    }

    public Book setCountryOfEdition(String countryOfEdition) {
        this.countryOfEdition = countryOfEdition;
        return this;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + bookId +
                ", ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", volume='" + volume + '\'' +
                ", abstract='" + bookAbstract + '\'' +
                ", language='" + language + '\'' +
                ", count='" + count + '\'' +
                ", editionYear='" + editionYear + '\'' +
                ", pages='" + pages + '\'' +
                ", countryOfEdition='" + countryOfEdition + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Book book = (Book) obj;

        if (getId() != book.getId()) return false;
        if (getISBN() != null ? !getISBN().equals(book.getISBN()) : book.getISBN() != null) return false;
        if (getTitle() != null ? !getTitle().equals(book.getTitle()) : book.getTitle() != null) return false;
        if (getGenre() != null ? !getGenre().equals(book.getGenre()) : book.getGenre() != null) return false;
        if (getVolume() != book.getVolume()) return false;

        if (getBookAbstract() != null ? !getBookAbstract().equals(book.getBookAbstract()) : book.getBookAbstract() != null)
            return false;
        if (getLanguage() != null ? !getLanguage().equals(book.getLanguage()) : book.getLanguage() != null)
            return false;
        if (getCount() != book.getCount()) return false;
        if (getEditionYear() != null ? !getEditionYear().equals(book.getEditionYear()) : book.getEditionYear() != null) return false;
        if (getPages() != book.getPages()) return false;
        return getCountryOfEdition() != null ? getCountryOfEdition().equals(book.getCountryOfEdition()): book.getCountryOfEdition() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getISBN() != null ? getISBN().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getGenre() != null ? getGenre().hashCode() : 0);
        result = 31 * result + getVolume();
        result = 31 * result + (getBookAbstract() != null ? getBookAbstract().hashCode() : 0);
        result = 31 * result + (getLanguage() != null ? getLanguage().hashCode() : 0);
        result = 31 * result + getCount();
        result = 31 * result + (getEditionYear() != null ? getEditionYear().hashCode() : 0);
        result = 31 * result + getPages();
        result = 31 * result + (getCountryOfEdition() != null ? getCountryOfEdition().hashCode() : 0);
        return result;
    }

/**
    public List<Author> getAuthors() {
        return authors;
    }

    public Book setAuthors(List<Author> authors) {
        this.authors = authors;
        return this;
    }

    public List<Pending> getPendingList() {
        return pendingList;
    }

    public Book setPendingList(List<Pending> pendingList) {
        this.pendingList = pendingList;
        return this;
    }

    public List<PickBook> getPickBookList() {
        return pickBookList;
    }

    public Book setPickBookList(List<PickBook> pickBookList) {
        this.pickBookList = pickBookList;
        return this;
    }
*/

}
