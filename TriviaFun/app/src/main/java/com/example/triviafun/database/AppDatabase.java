package com.example.triviafun.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.triviafun.model.TriviaHistoryStorage;
import com.example.triviafun.model.User;

@Database(entities = {User.class, TriviaHistoryStorage.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract TriviaHistoryStorageDao triviaHistoryStorageDao();

    private static AppDatabase instance;
    public static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, AppDatabase.class,
                    "UserDb").build();
        }
        return instance;
    }
}
