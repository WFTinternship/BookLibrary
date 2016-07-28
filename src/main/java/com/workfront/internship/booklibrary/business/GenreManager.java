package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Genre;
import java.util.List;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public interface GenreManager {
    int add(Genre genre);

    Genre findGenreByID(int id);

    Genre findGenreByGenreName(String genreName);

    List<Genre> viewAll();

    Genre update(Genre genre);

    boolean delete(int id);
}
