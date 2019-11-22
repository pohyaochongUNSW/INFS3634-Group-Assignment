package com.example.triviafun.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.triviafun.model.TriviaHistoryStorage;

import java.util.List;


@Dao
public interface TriviaHistoryStorageDao {

    @Query("SELECT * FROM triviahistorystorage WHERE username = :username and category = :category ORDER BY id DESC")
    List<TriviaHistoryStorage> getTriviaHistoryStorageByUserAndCategory(String username, String category);

    @Query("SELECT * FROM triviahistorystorage WHERE username = :username")
    List<TriviaHistoryStorage> getTriviaHistoryStorageByUser(String username);

    @Insert
    void insertTriviaHistoryStorage(TriviaHistoryStorage... TriviaHistoryStorage);
}
