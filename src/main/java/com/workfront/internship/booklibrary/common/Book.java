package com.workfront.internship.booklibrary.common;

/**
 * Created by Workfront on 7/1/2016.
 */
public class Book {
    private int book_id;
    private String ISBN;
    private String title;
    private int genre_id;
    private int volume;
    private String bookAbstract;
    private String language;
    private int count;
    private String editionYear;
    private int pages;
    private String countryOfEdition;

    public Book(){}

    public Book(Book book){
        setBook_id(book.getBook_id());
        setISBN(book.getISBN());
        setTitle(book.getTitle());
        setGenre_id(book.getGenre_id());
        setVolume(book.getVolume());
        setBookAbstract(book.getBookAbstract());
        setLanguage(book.getLanguage());
        setCount(book.getCount());
        setEditionYear(book.getEditionYear());
        setPages(book.getPages());
        setCountryOfEdition(book.getCountryOfEdition());
    }

    public Book(int bookId, String ISBN, String title, int genreId, int volume, String bookAbstract,
                String language, int count, String editionYear, int pages, String countryOfEdition){
        this.book_id = bookId;
        this.ISBN = ISBN;
        this.title = title;
        this.genre_id = genreId;
        this.volume = volume;
        this.bookAbstract = bookAbstract;
        this.language = language;
        this.count = count;
        this.editionYear = editionYear;
        this.pages = pages;
        this.countryOfEdition = countryOfEdition;
    }


    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getBookAbstract() {
        return bookAbstract;
    }

    public void setBookAbstract(String bookAbstract) {
        this.bookAbstract = bookAbstract;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getEditionYear() {
        return editionYear;
    }

    public void setEditionYear(String editionYear) {
        this.editionYear = editionYear;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getCountryOfEdition() {
        return countryOfEdition;
    }

    public void setCountryOfEdition(String countryOfEdition) {
        this.countryOfEdition = countryOfEdition;
    }


    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", genre_id='" + genre_id + '\'' +
                ", volume='" + volume + '\'' +
                ", abstract='" + bookAbstract + '\'' +
                ", language='" + language + '\'' +
                ", count='" + count + '\'' +
                ", edition_year='" + editionYear + '\'' +
                ", pages='" + pages + '\'' +
                ", country_of_edition='" + countryOfEdition + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Book book = (Book) obj;

        if (getBook_id() != book.getBook_id()) return false;
        if (getISBN() != null ? !getISBN().equals(book.getISBN()) : book.getISBN() != null) return false;
        if (getTitle() != null ? !getTitle().equals(book.getTitle()) : book.getTitle() != null) return false;
        if (getGenre_id() != book.getGenre_id()) return false;
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
        int result = getBook_id();
        result = 31 * result + (getISBN() != null ? getISBN().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + getGenre_id();
        result = 31 * result + getVolume();
        result = 31 * result + (getBookAbstract() != null ? getBookAbstract().hashCode() : 0);
        result = 31 * result + (getLanguage() != null ? getLanguage().hashCode() : 0);
        result = 31 * result + getCount();
        result = 31 * result + (getEditionYear() != null ? getEditionYear().hashCode() : 0);
        result = 31 * result + getPages();
        result = 31 * result + (getCountryOfEdition() != null ? getCountryOfEdition().hashCode() : 0);
        return result;
    }
}
