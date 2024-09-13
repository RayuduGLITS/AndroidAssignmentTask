package com.rayudu.androidassignmenttask.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.rayudu.androidassignmenttask.model.UserModel;

@Database(entities = {UserModel.class}, version = 1)
@TypeConverters(AvatarConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UserDaoClass userDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "item_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
