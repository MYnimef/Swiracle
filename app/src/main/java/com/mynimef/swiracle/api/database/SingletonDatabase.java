package com.mynimef.swiracle.api.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PostInfo.class, ClothesElement.class}, version = 1, exportSchema = false)
public abstract class SingletonDatabase extends RoomDatabase {
    private static SingletonDatabase instance;

    public abstract PostDao postDao();

    public static synchronized SingletonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SingletonDatabase.class, "post_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}