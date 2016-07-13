package com.workfront.internship.booklibrary.common;

import java.util.Date;


public class PickBook {
    private int id;
    private int bookId;
    private int userId;
    private Date pickingDate;
    private Date returnDate;

    public int getId() {
        return id;
    }

    public PickBook setId(int id) {
        this.id = id;
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
                "pickBook_id=" + id +
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

        if (getId() != pickBook.getId()) return false;
        if (getBookId() != pickBook.getBookId()) return false;
        if (getUserId() != pickBook.getUserId()) return false;
        if (getPickingDate() != null ? !getPickingDate().equals(pickBook.getPickingDate()) : pickBook.getPickingDate() != null) return false;
        return (getPickingDate() != null ? !getPickingDate().equals(pickBook.getPickingDate()) : pickBook.getPickingDate() != null);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getBookId();
        result = 31 * result + getUserId();
        result = 31 * result + (getPickingDate() != null ? getPickingDate().hashCode() : 0);
        result = 31 * result + (getPickingDate() != null ? getPickingDate().hashCode() : 0);
        result = 31 * result + (getReturnDate() != null ? getReturnDate().hashCode() : 0);

        return result;
    }
}
