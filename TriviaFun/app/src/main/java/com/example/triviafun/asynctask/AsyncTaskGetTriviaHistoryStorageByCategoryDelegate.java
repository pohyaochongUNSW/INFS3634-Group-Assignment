package com.example.triviafun.asynctask;

import com.example.triviafun.model.TriviaHistoryStorage;

import java.util.List;

public interface AsyncTaskGetTriviaHistoryStorageByCategoryDelegate {
    void handleTaskGetTriviaHistoryStorageByCategoryResult(List<TriviaHistoryStorage> triviaHistoryStorage);
}
