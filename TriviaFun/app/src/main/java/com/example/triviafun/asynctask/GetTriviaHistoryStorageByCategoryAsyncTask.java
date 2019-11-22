package com.example.triviafun.asynctask;

import android.os.AsyncTask;

import com.example.triviafun.database.AppDatabase;
import com.example.triviafun.model.TriviaHistoryStorage;

import java.util.List;

public class GetTriviaHistoryStorageByCategoryAsyncTask extends AsyncTask<String, Integer, List<TriviaHistoryStorage>> {

    private AsyncTaskGetTriviaHistoryStorageByCategoryDelegate delegate;

    private AppDatabase database;

    public void setDelegate(AsyncTaskGetTriviaHistoryStorageByCategoryDelegate delegate) {
        this.delegate = delegate;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected List<TriviaHistoryStorage> doInBackground(String... strings) {
        return database.triviaHistoryStorageDao().getTriviaHistoryStorageByUserAndCategory(strings[0], strings[1]);
    }

    @Override
    protected void onPostExecute(List<TriviaHistoryStorage> result){
        delegate.handleTaskGetTriviaHistoryStorageByCategoryResult(result);
    }
}
