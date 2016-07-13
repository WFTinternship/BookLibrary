package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Genre;
import java.util.List;


public interface GenreDAO {
    void add(Genre genre);

    Genre getGenreByID(int id);

    Genre getGenreByGenreName(String genreName);

    List<Genre> getAllGenres();

    void updateGenre(Genre genre);

    void deleteGenre(int id);
}
