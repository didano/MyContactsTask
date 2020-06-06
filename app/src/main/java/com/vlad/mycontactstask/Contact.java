package com.vlad.mycontactstask;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "contacts")
public class Contact implements Serializable {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "contact_id")
    private long id;

    @ColumnInfo(name = "contact_name")
    private String name;

    @ColumnInfo(name = "contact_surname")
    private String surname;

    @ColumnInfo(name = "contact_phoneNumber")
    private String phoneNumber;

    @ColumnInfo(name = "contact_email")
    private String email;

    @ColumnInfo(name = "contact_photo")
    int photo;

    public Contact(long id,String name, String surname, String phoneNumber, String email, int photo) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

}
