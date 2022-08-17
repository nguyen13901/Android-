package com.example.contactapp;

import androidx.room.*;

import java.io.Serializable;

@Entity
public class Contact implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private byte[] avatar;

    @ColumnInfo
    private String firstName;

    @ColumnInfo
    private String lastName;

    @ColumnInfo
    private String mobile;

    @ColumnInfo
    private String email;

    @ColumnInfo
    private boolean mark;

    public Contact(byte[] avatar, String firstName, String lastName, String mobile, String email) {
        this.avatar = avatar;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.mark = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public boolean isMark() {
        return mark;
    }
}
