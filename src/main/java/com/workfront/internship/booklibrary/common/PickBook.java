package com.workfront.internship.booklibrary.common;

import java.util.Date;


public class PickBook {
    private int pickId;
    private int bookId;
    private int userId;
    private Date pickingDate;
    private Date returnDate;

    public int getPickId() {
        return pickId;
    }

    public PickBook setPickId(int pickId) {
        this.pickId = pickId;
        return this;
    }

    public int getBookId() {
        return bookId;
    }

    public PickBook setBookId(int bookId) {
        this.bookId = bookId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public PickBook setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Date getPickingDate() {
        return pickingDate;
    }

    public PickBook setPickingDate(Date pickingDate) {
        this.pickingDate = pickingDate;
        return this;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public PickBook setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    @Override
    public String toString() {
        return "PickBook{" +
                "pickBook_id=" + pickId +
                ", book_id='" + bookId + '\'' +
                ", user_id='" + userId + '\'' +
                ", pickingDate='" + pickingDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        PickBook pickBook = (PickBook) obj;

        if (getPickId() != pickBook.getPickId()) return false;
        if (getBookId() != pickBook.getBookId()) return false;
        if (getUserId() != pickBook.getUserId()) return false;
        if (getPickingDate() != null ? !getPickingDate().equals(pickBook.getPickingDate()) : pickBook.getPickingDate() != null) return false;
        return (getPickingDate() != null ? !getPickingDate().equals(pickBook.getPickingDate()) : pickBook.getPickingDate() != null);
    }

    @Override
    public int hashCode() {
        int result = getPickId();
        result = 31 * result + getBookId();
        result = 31 * result + getUserId();
        result = 31 * result + (getPickingDate() != null ? getPickingDate().hashCode() : 0);
        result = 31 * result + (getPickingDate() != null ? getPickingDate().hashCode() : 0);
        result = 31 * result + (getReturnDate() != null ? getReturnDate().hashCode() : 0);

        return result;
    }
}
