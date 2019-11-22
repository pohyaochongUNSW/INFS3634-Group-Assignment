package com.example.triviafun.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.triviafun.model.User;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User... Users);

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    User getUser(String username);

    @Query("UPDATE user SET user_password = :password WHERE username = :username")
    void updatePassword(String username, String password);
}
