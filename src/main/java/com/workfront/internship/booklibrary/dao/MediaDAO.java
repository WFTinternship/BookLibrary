package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Media;
import java.util.List;

public interface MediaDAO {
    int add(Media media);

    Media getMediaByID(int id);

    List<Media> getAllMedia();

    void updateMedia(Media media);

    void deleteMedia(int id);
}
