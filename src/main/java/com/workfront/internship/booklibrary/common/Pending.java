package com.workfront.internship.booklibrary.common;

import java.util.Date;

public class Pending {
    private int id;
    private User user;
    private Book book;
    private Date pendingDate;

    public int getId() {
        return id;
    }

    public Pending setId(int pendingId) {
        this.id = pendingId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Pending setUser(User user) {
        this.user = user;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public Pending setBook(Book book) {
        this.book = book;
        return this;
    }

    public Date getPendingDate() {
        return pendingDate;
    }

    public Pending setPendingDate(Date pendingDate) {
        this.pendingDate = pendingDate;
        return this;
    }

    @Override
    public String toString() {
        return "Pending{" +
                "pending_id=" + id +
                ", user_id='" + getUser().getId() + '\'' +
                ", book_id='" + getBook().getId() + '\'' +
                ", pendingTime='" + pendingDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Pending pending = (Pending) obj;

        if (getId() != pending.getId()) return false;
        if (getUser() != null ? !getUser().equals(pending.getUser()) : pending.getUser() != null) return false;
        if (getBook() != null ? !getBook().equals(pending.getBook()) : pending.getBook() != null) return false;
        return (getPendingDate() != null ? !getPendingDate().equals(pending.getPendingDate()) : pending.getPendingDate() != null);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getBook() != null ? getBook().hashCode() : 0);
        result = 31 * result + (getPendingDate() != null ? getPendingDate().hashCode() : 0);

        return result;
    }
}
