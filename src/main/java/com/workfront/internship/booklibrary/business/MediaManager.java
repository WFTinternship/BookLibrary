package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Media;
import java.util.List;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public interface MediaManager {
    int add(Media media);

    int add(String link, int mediaTypeId, int bookId);

    Media getMediaByID(int id);

    List<Media> viewAllMedia();

    List<Media> viewAllMediaByBookId(int bookId);

//    List<Media> viewAllMediaByMediaType(int id);

    Media update(Media media);

    boolean delete(int id);
}
