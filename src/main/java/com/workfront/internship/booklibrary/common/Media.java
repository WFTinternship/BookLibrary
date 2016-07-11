package com.workfront.internship.booklibrary.common;

public class Media {
    private int mediaId;
    private String media;
    private String mediaType;
    //private Book book;
    private int bookId;

    public int getMediaId() {
        return mediaId;
    }

    public Media setMediaId(int mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public String getMedia() {
        return media;
    }

    public Media setMedia(String media) {
        this.media = media;
        return this;
    }

    public String getMediaType() {
        return mediaType;
    }

    public Media setMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public int getBookId() {
        return bookId;
    }

    public Media setBookId(int bookId) {
        this.bookId = bookId;
        return this;
    }

    @Override
    public String toString() {
        return "Media{" +
                "media_id=" + mediaId +
                ", media='" + media + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", book_id='" + bookId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Media media = (Media) obj;

        if (getMediaId() != media.getMediaId()) return false;
        if (getMedia() != null ? !getMedia().equals(media.getMedia()) : media.getMedia() != null) return false;
        if (getMediaType() != null ? !getMediaType().equals(media.getMediaType()) : media.getMediaType() != null) return false;

        return (getBookId() != media.getBookId());

    }

    @Override
    public int hashCode() {
        int result = getMediaId();
        result = 31 * result + (getMedia() != null ? getMedia().hashCode() : 0);
        result = 31 * result + (getMediaType() != null ? getMediaType().hashCode() : 0);
        result = 31 * result + getBookId();

        return result;
    }
}
