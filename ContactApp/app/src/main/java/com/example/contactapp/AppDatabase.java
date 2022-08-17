package com.example.contactapp;

import android.content.Context;

import androidx.room.*;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "contacts")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
