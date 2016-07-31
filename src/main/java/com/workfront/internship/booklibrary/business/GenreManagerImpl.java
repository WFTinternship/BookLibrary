package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.dao.DataSource;
import com.workfront.internship.booklibrary.dao.GenreDAO;
import com.workfront.internship.booklibrary.dao.GenreDAOImpl;

import java.util.List;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public class GenreManagerImpl implements GenreManager{
    private GenreDAO genreDAO;
    private DataSource dataSource;

    public GenreManagerImpl(DataSource dataSource){
        this.dataSource = dataSource;
        genreDAO = new GenreDAOImpl(dataSource);
    }

    @Override
    public int add(Genre genre) {
        if(genreValidator(genre)){
            genreDAO.add(genre);
            return genre.getId();
        }
        return 0;
    }

    @Override
    public Genre findGenreByID(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        Genre genre = genreDAO.getGenreByID(id);
        if(genreValidator(genre)){
            return genre;
        }
        return null;
    }

    @Override
    public Genre findGenreByGenreName(String genreName) {
        if(genreName == null){
            throw new IllegalArgumentException("Invalid Genre name is entered");
        }
        Genre genre = genreDAO.getGenreByGenreName(genreName);
        if(genreValidator(genre)){
            return genre;
        }
        return null;
    }

    @Override
    public List<Genre> viewAll() {
        List<Genre> genreList = genreDAO.getAllGenres();
        if(genreList != null){
            return genreList;
        }
        return null;
    }

    @Override
    public Genre update(Genre genre) {
        if(genreValidator(genre)){
            genreDAO.updateGenre(genre);
            return genre;
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        if(id > 0){
            genreDAO.deleteGenre(id);
            if(genreDAO.getGenreByID(id) == null){
                return true;
            }
        }
        return false;
    }

    private boolean genreValidator(Genre genre){
        return genre != null && genre.getGenre() != null;
    }
}
