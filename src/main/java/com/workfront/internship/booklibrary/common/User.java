package com.workfront.internship.booklibrary.common;

/**
 * Created by Workfront on 7/1/2016.
 */
public class User {
    //void builder(){};

    private int user_id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String address;
    private String e_mail;
    private String phone;
    private String access_privilage;


    public User(){} //todo

    public User(int id, String name, String surname, String username, String pass,
                String address, String mail, String phone, String access){}

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccess_privilage() {
        return access_privilage;
    }

    public void setAccess_privilage(String access_privilage) {
        this.access_privilage = access_privilage;
    }
}
