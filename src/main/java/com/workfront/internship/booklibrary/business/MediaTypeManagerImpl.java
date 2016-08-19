package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.MediaType;
import com.workfront.internship.booklibrary.common.PickBook;
import com.workfront.internship.booklibrary.dao.DataSource;
import com.workfront.internship.booklibrary.dao.MediaTypeDAO;
import com.workfront.internship.booklibrary.dao.MediaTypeDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ${Sona} on 7/28/2016.
 */

@Component
public class MediaTypeManagerImpl implements MediaTypeManager{

    @Autowired
    private MediaTypeDAO mediaTypeDAO;

    @Autowired
    private DataSource dataSource;

    public MediaTypeManagerImpl(DataSource dataSource)throws Exception {
        this.dataSource = dataSource;
        mediaTypeDAO = new MediaTypeDAOImpl(dataSource);
    }

    @Override
    public int add(MediaType mediaType) {
        if(mediaTypeValidator(mediaType)){
            mediaTypeDAO.add(mediaType);
            if(mediaType.getId() > 0){
                return mediaType.getId();
            }
        }
        return 0;
    }

    @Override
    public MediaType getMediaTypeByID(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        MediaType mediaType = mediaTypeDAO.getMediaTypeByID(id);
        if(mediaTypeValidator(mediaType)){
            return mediaType;
        }
        return null;
    }

    @Override
    public List<MediaType> viewAllMediaTypes() {
        List<MediaType> mediaTypeList = mediaTypeDAO.getAllMediaTypes();
        if(mediaTypeList != null){
            return mediaTypeList;
        }
        return null;
    }

    @Override
    public MediaType update(MediaType mediaType) {
        if(mediaTypeValidator(mediaType)){
            mediaTypeDAO.updateMediaType(mediaType);
            return mediaType;
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if(id < 1){
            throw new IllegalArgumentException("Invalid id is entered");
        }
        if(id > 0){
            mediaTypeDAO.deleteMediaType(id);
            if(mediaTypeDAO.getMediaTypeByID(id) == null){
                return true;
            }
        }
        return false;
    }

    private boolean mediaTypeValidator(MediaType mediaType){
        return mediaType != null && mediaType.getType() != null;
    }
}
