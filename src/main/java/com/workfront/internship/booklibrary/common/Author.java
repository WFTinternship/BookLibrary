package com.workfront.internship.booklibrary.common;

/**
 * Created by Workfront on 7/1/2016.
 */
public class Author {
    private int authorId;
    private String name;
    private String surname;
    private String eMail;
    private String webPage;
    private String biography;

    public Author(){}

    public Author(Author author){
        setAuthorId(author.getAuthorId());
        setName(author.getName());
        setSurname(author.getSurname());
        seteMail(author.geteMail());
        setWebPage(author.getWebPage());
        setBiography(author.getBiography());
    }

    public Author(int authorId, String name, String surname,
                String eMail, String webPage, String biography) {
        this.authorId = authorId;
        this.name = name;
        this.surname = surname;
        this.eMail = eMail;
        this.webPage = webPage;
        this.biography = biography;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
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

        if (getAuthorId() != author.getAuthorId()) return false;
        if (getName() != null ? !getName().equals(author.getName()) : author.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(author.getSurname()) : author.getSurname() != null) return false;
        if (geteMail() != null ? !geteMail().equals(author.geteMail()) : author.geteMail() != null) return false;
        if (getWebPage() != null ? !getWebPage().equals(author.getWebPage()) : author.getWebPage() != null) return false;
        return getBiography() != null ? getBiography().equals(author.getBiography()) : author.getBiography() == null;
    }

    @Override
    public int hashCode() {
        int result = getAuthorId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (geteMail() != null ? geteMail().hashCode() : 0);
        result = 31 * result + (getWebPage() != null ? getWebPage().hashCode() : 0);
        result = 31 * result + (getBiography() != null ? getBiography().hashCode() : 0);
        return result;
    }
}
