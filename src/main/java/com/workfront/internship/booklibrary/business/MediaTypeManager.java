package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.MediaType;
import java.util.List;

/**
 * Created by ${Sona} on 7/28/2016.
 */
public interface MediaTypeManager {
    int add(MediaType mediaType);

    MediaType getMediaTypeByID(int id);

    List<MediaType> viewAllMediaTypes();

    MediaType update(MediaType mediaType);

    boolean delete(int id);
}
