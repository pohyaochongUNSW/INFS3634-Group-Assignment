package com.example.triviafun.asynctask;

import android.os.AsyncTask;

import com.example.triviafun.database.AppDatabase;
import com.example.triviafun.model.User;

public class GetUserAsyncTask extends AsyncTask<String, Integer, User> {

    private AsyncTaskGetUserDelegate delegate;

    private AppDatabase database;

    public void setDelegate(AsyncTaskGetUserDelegate delegate) {
        this.delegate = delegate;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected User doInBackground(String... strings) {
        return database.userDao().getUser(strings[0]);
    }

    @Override
    protected void onPostExecute(User result){
        delegate.handleTaskGetUserResult(result);
    }
}
