package com.example.triviafun.asynctask;

import android.os.AsyncTask;

import com.example.triviafun.database.AppDatabase;
import com.example.triviafun.model.TriviaHistoryStorage;

public class InsertTriviaHistoryStorageAsyncTask extends AsyncTask<TriviaHistoryStorage, Integer, String> {

    private AsyncTaskInsertTriviaHistoryStorageDelegate delegate;
    private AppDatabase database;

    public void setDelegate(AsyncTaskInsertTriviaHistoryStorageDelegate delegate) {
        this.delegate = delegate;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(TriviaHistoryStorage... triviaHistoryStorage) {
        database.triviaHistoryStorageDao().insertTriviaHistoryStorage(triviaHistoryStorage);
        return "Saved Trivia Record";
    }

    @Override
    protected void onPostExecute(String result){
        delegate.handleTaskInsertTriviaHistoryStorageResult(result);
    }
}
