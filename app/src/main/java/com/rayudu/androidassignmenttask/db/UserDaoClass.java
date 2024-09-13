package com.rayudu.androidassignmenttask.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.rayudu.androidassignmenttask.model.UserModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDaoClass {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserList(ArrayList<UserModel> userList);

    @Query("SELECT * FROM UserModel")
    List<UserModel> getAllUsers();

    // Update a specific UserModel
    @Update
    void updateUser(UserModel userModel);


}
