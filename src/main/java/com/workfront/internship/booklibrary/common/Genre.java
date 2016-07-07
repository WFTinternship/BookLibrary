package com.workfront.internship.booklibrary.common;

import java.util.List;

public class Genre {
    private int genreId;
    private String genre;
    private List<Book> books;


    public int getGenreId() {
        return genreId;
    }

    public Genre setGenreId(int genreId) {
        this.genreId = genreId;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public Genre setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreId=" + genreId +
                ", genre='" + genre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Genre genre = (Genre) obj;

        if (getGenreId() != genre.getGenreId()) return false;

        return getGenre() != null ? getGenre().equals(genre.getGenre()) : genre.getGenre() == null;
    }

    @Override
    public int hashCode() {
        int result = getGenreId();
        result = 31 * result + (getGenre() != null ? getGenre().hashCode() : 0);
        return result;
    }
}
