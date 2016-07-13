package com.workfront.internship.booklibrary.common;

import java.util.Date;

public class Pending {
    private int id;
    private int userId;
    private int bookId;
    private Date pendingDate;

    public int getId() {
        return id;
    }

    public Pending setId(int pendingId) {
        this.id = pendingId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Pending setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getBookId() {
        return bookId;
    }

    public Pending setBookId(int bookId) {
        this.bookId = bookId;
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
                ", user_id='" + userId + '\'' +
                ", book_id='" + bookId + '\'' +
                ", pendingTime='" + pendingDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Pending pending = (Pending) obj;

        if (getId() != pending.getId()) return false;
        if (getUserId() != pending.getUserId()) return false;
        if (getBookId() != pending.getBookId()) return false;
        return (getPendingDate() != null ? !getPendingDate().equals(pending.getPendingDate()) : pending.getPendingDate() != null);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getUserId();
        result = 31 * result + getBookId();
        result = 31 * result + (getPendingDate() != null ? getPendingDate().hashCode() : 0);

        return result;
    }
}
