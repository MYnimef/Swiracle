package com.mynimef.swiracle.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mynimef.swiracle.models.PostImage;
import com.mynimef.swiracle.models.PostInfo;
import com.mynimef.swiracle.models.UserDetails;

@Database(entities = {PostInfo.class, PostImage.class, UserDetails.class}, version = 1, exportSchema = false)
public abstract class SingletonDatabase extends RoomDatabase {
    private static SingletonDatabase instance;

    public abstract UserDao userDao();
    public abstract PostDao postDao();
    public abstract ImagesDao imagesDao();

    public static synchronized SingletonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SingletonDatabase.class, "swiracle_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}