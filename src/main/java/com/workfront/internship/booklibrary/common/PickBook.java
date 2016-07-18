package com.workfront.internship.booklibrary.common;

import java.util.Date;


public class PickBook {
    private int id;

    private Book book;
    private User user;

//    private int bookId;
//    private int userId;

    private Date pickingDate;
    private Date returnDate;

    public int getId() {
        return id;
    }

    public PickBook setId(int id) {
        this.id = id;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public PickBook setBook(Book book) {
        this.book = book;
        return this;
    }

    public User getUser() {
        return user;
    }

    public PickBook setUser(User user) {
        this.user = user;
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
                ", book_id='" + getBook().getId() + '\'' +
                ", user_id='" + getUser().getId() + '\'' +
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
        if (getBook() != null ? !getBook().equals(pickBook.getBook()) : pickBook.getBook() != null) return false;
        if (getUser() != null ? !getUser().equals(pickBook.getUser()) : pickBook.getUser() != null) return false;
        if (getPickingDate() != null ? !getPickingDate().equals(pickBook.getPickingDate()) : pickBook.getPickingDate() != null) return false;
        return (getPickingDate() != null ? !getPickingDate().equals(pickBook.getPickingDate()) : pickBook.getPickingDate() != null);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getBook() != null ? getBook().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getPickingDate() != null ? getPickingDate().hashCode() : 0);
        result = 31 * result + (getPickingDate() != null ? getPickingDate().hashCode() : 0);
        result = 31 * result + (getReturnDate() != null ? getReturnDate().hashCode() : 0);

        return result;
    }
}
