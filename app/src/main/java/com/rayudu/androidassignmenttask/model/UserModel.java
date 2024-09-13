package com.rayudu.androidassignmenttask.model;


import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class UserModel {

    @PrimaryKey
    public int idNum = 0;
    int id;
    String email,first_name,last_name,avatar;
    boolean imageUploaded = false;

    public boolean isImageUploaded() {
        return imageUploaded;
    }

    public void setImageUploaded(boolean imageUploaded) {
        this.imageUploaded = imageUploaded;
    }
/*Bitmap localAvatarPath = null;

    public Bitmap getLocalAvatarPath() {
        return localAvatarPath;
    }

    public void setLocalAvatarPath(Bitmap localAvatarPath) {
        this.localAvatarPath = localAvatarPath;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
