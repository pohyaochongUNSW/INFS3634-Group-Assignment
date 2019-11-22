package com.example.triviafun.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TriviaHistoryStorage {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String username;
    public String triviaHistoryJson;
    public String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTriviaHistoryJson() {
        return triviaHistoryJson;
    }

    public void setTriviaHistoryJson(String triviaHistoryJson) {
        this.triviaHistoryJson = triviaHistoryJson;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
