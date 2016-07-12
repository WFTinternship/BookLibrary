package com.workfront.internship.booklibrary.common;

import java.util.List;

public class Author {
    private int authorId;
    private String name;
    private String surname;
    private String eMail;
    private String webPage;
    private String biography;
    private List<Book> books;


    public int getId() {
        return authorId;
    }

    public Author setId(int id) {
        this.authorId = id;
        return this;
    }


    public String getName() {
        return name;
    }

    public Author setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Author setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String geteMail() {
        return eMail;
    }

    public Author seteMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public String getWebPage() {
        return webPage;
    }

    public Author setWebPage(String webPage) {
        this.webPage = webPage;
        return this;
    }

    public String getBiography() {
        return biography;
    }

    public Author setBiography(String biography) {
        this.biography = biography;
        return this;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", eMail='" + eMail + '\'' +
                ", webPage='" + webPage + '\'' +
                ", biography='" + biography + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Author author = (Author) obj;

        if (getId() != author.getId()) return false;
        if (getName() != null ? !getName().equals(author.getName()) : author.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(author.getSurname()) : author.getSurname() != null) return false;
        if (geteMail() != null ? !geteMail().equals(author.geteMail()) : author.geteMail() != null) return false;
        if (getWebPage() != null ? !getWebPage().equals(author.getWebPage()) : author.getWebPage() != null) return false;
        return getBiography() != null ? getBiography().equals(author.getBiography()) : author.getBiography() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (geteMail() != null ? geteMail().hashCode() : 0);
        result = 31 * result + (getWebPage() != null ? getWebPage().hashCode() : 0);
        result = 31 * result + (getBiography() != null ? getBiography().hashCode() : 0);
        return result;
    }
}
