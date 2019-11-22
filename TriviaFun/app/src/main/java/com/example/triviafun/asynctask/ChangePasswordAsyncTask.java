package com.example.triviafun.asynctask;

import android.os.AsyncTask;

import com.example.triviafun.database.AppDatabase;

public class ChangePasswordAsyncTask extends AsyncTask<String, Integer, String> {

    private AsyncTaskChangePasswordDelegate delegate;
    private AppDatabase database;

    public void setDelegate(AsyncTaskChangePasswordDelegate delegate) {
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
    protected String doInBackground(String... strings) {

        database.userDao().updatePassword(strings[0], strings[1]);

        return "Password Updated.";
    }

    @Override
    protected void onPostExecute(String result){
        delegate.handleTaskChangePasswordResult(result);
    }
}
