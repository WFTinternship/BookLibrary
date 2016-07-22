package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.MediaType;

import java.util.List;

/**
 * Created by ${Sona} on 7/22/2016.
 */
public interface MediaTypeDAO {
    int add(MediaType mediaType);

    MediaType getMediaTypeByID(int id);

    MediaType getMediaByType(String mediaType);

    List<MediaType> getAllMediaTypes();

    void updateMediaType(MediaType mediaType);

    void deleteMediaType(int id);

    void deleteAll();
}
