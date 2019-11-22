package com.example.triviafun.asynctask;

import android.os.AsyncTask;

import com.example.triviafun.database.AppDatabase;
import com.example.triviafun.model.TriviaHistory;
import com.example.triviafun.model.TriviaHistoryStorage;
import com.example.triviafun.model.User;

import java.util.List;

public class GetTriviaHistoryStorageAsyncTask extends AsyncTask<String, Integer, List<TriviaHistoryStorage>> {

    private AsyncTaskGetTriviaHistoryStorageDelegate delegate;

    private AppDatabase database;

    public void setDelegate(AsyncTaskGetTriviaHistoryStorageDelegate delegate) {
        this.delegate = delegate;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected List<TriviaHistoryStorage> doInBackground(String... strings) {
        return database.triviaHistoryStorageDao().getTriviaHistoryStorageByUser(strings[0]);
    }

    @Override
    protected void onPostExecute(List<TriviaHistoryStorage> result){
        delegate.handleTaskGetTriviaHistoryStorageResult(result);
    }
}
