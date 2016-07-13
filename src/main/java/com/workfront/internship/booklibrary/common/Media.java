package com.workfront.internship.booklibrary.common;

public class Media {
    private int id;
    private String mediaLink;
    private String mediaType;
    //private Book book;
    private int bookId;

    public int getId() {
        return id;
    }

    public Media setId(int id) {
        this.id = id;
        return this;
    }

    public String getLink() {
        return mediaLink;
    }

    public Media setLink(String mediaLink) {
        this.mediaLink = mediaLink;
        return this;
    }

    public String getType() {
        return mediaType;
    }

    public Media setType(String mediaType) {
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
                "media_id=" + id +
                ", mediaLink='" + mediaLink + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", book_id='" + bookId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Media media = (Media) obj;

        if (getId() != media.getId()) return false;
        if (getLink() != null ? !getLink().equals(media.getLink()) : media.getLink() != null) return false;
        if (getType() != null ? !getType().equals(media.getType()) : media.getType() != null) return false;

        return (getBookId() != media.getBookId());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getLink() != null ? getLink().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + getBookId();

        return result;
    }
}
