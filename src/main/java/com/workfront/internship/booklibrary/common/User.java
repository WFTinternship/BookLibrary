package com.workfront.internship.booklibrary.common;

/**
 * Created by Sona on 7/1/2016.
 */
public class User {
    //void builder(){};

    private int userId;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String address;
    private String eMail;
    private String phone;
    private String accessPrivilege;


    public User(){
    }

    public User(User user){
        setUserId(user.getUserId());
        setName(user.getName());
        setSurname(user.getSurname());
        setUsername(user.getUsername());
        setPassword(user.getPassword());
        setAddress(user.getAddress());
        seteMail(user.geteMail());
        setPhone(user.getPhone());
        setAccessPrivilege(user.getAccessPrivilege());
    }

    public User(int userId, String name, String surname, String username, String password, String address,
                String eMail, String phone, String accessPrivilege) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.address = address;
        this.eMail = eMail;
        this.phone = phone;
        this.accessPrivilege = accessPrivilege;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccessPrivilege() {
        return accessPrivilege;
    }

    public void setAccessPrivilege(String access_privilege) {
        this.accessPrivilege = access_privilege;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", eMail='" + eMail + '\'' +
                ", phone='" + phone + '\'' +
                ", accessPrivilege='" + accessPrivilege + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getUserId() != user.getUserId()) return false;
        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(user.getSurname()) : user.getSurname() != null) return false;
        if (getUsername() != null ? !getUsername().equals(user.getUsername()) : user.getUsername() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(user.getAddress()) : user.getAddress() != null) return false;
        if (geteMail() != null ? !geteMail().equals(user.geteMail()) : user.geteMail() != null) return false;
        if (getPhone() != null ? !getPhone().equals(user.getPhone()) : user.getPhone() != null) return false;
        return getAccessPrivilege() != null ? getAccessPrivilege().equals(user.getAccessPrivilege()) : user.getAccessPrivilege() == null;

    }

    @Override
    public int hashCode() {
        int result = getUserId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (geteMail() != null ? geteMail().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getAccessPrivilege() != null ? getAccessPrivilege().hashCode() : 0);
        return result;
    }
}
