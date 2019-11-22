package com.example.triviafun.asynctask;

import android.os.AsyncTask;

import com.example.triviafun.database.AppDatabase;
import com.example.triviafun.model.User;

public class InsertUserAsyncTask extends AsyncTask<User, Integer, String> {

    private AsyncTaskInsertUserDelegate delegate;
    private AppDatabase database;

    public void setDelegate(AsyncTaskInsertUserDelegate delegate) {
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
    protected String doInBackground(User... users) {
        database.userDao().insertUser(users);
        return "Register successful.";
    }

    @Override
    protected void onPostExecute(String result){
        delegate.handleTaskInsertUserResult(result);
    }
}
