package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Media;
import com.workfront.internship.booklibrary.common.MediaType;

import java.util.List;

public interface MediaDAO {
    int add(Media media);

    Media getMediaByID(int id);

    List<Media> getAllMedia();

    List<Media> getAllMediaByBook(int bookId);

    List<Media>  getAllMediaByMediaType(int mediaTypeId);

    void updateMedia(Media media);

    void deleteMedia(int id);

    void deleteAll();
}
