package com.workfront.internship.booklibrary.common;

/**
 * Created by ${Sona} on 7/22/2016.
 */
public class MediaType {
    private int id;
    private String type;

    public int getId(){
        return id;
    }

    public MediaType setId(int id){
        this.id = id;
        return this;
    }

    public String getType(){
        return type;
    }

    public MediaType setType(String type){
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "MediaType  " +
                "mediaType_id = " + getId() +
                " media type = " + getType();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        MediaType mediaType = (MediaType) obj;

        if (getId() != mediaType.getId()) return false;
        return (getType() != null ? !getType().equals(mediaType.getType()) : mediaType.getType() == null);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = result + (getType() != null ? getType().hashCode() : 0);

        return result;
    }
}
