package com.example.triviafun.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviafun.R;
import com.example.triviafun.asynctask.AsyncTaskGetUserDelegate;
import com.example.triviafun.asynctask.AsyncTaskInsertUserDelegate;
import com.example.triviafun.asynctask.GetUserAsyncTask;
import com.example.triviafun.asynctask.InsertUserAsyncTask;
import com.example.triviafun.database.AppDatabase;
import com.example.triviafun.model.User;

public class RegisterActivity extends AppCompatActivity implements AsyncTaskGetUserDelegate, AsyncTaskInsertUserDelegate {

    private EditText username;
    private EditText password;
    private TextView buttonCancel;
    private Button buttonRegister;
    private TextView noticeRegister;
    private String inputUsername = "";
    private String inputPassword = "";
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        buttonCancel = findViewById(R.id.button_back);
        buttonRegister = findViewById(R.id.buttonRegister);
        noticeRegister = findViewById(R.id.noticeRegister);
        noticeRegister.setVisibility(View.GONE);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noticeRegister.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noticeRegister.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                db = AppDatabase.getInstance(context);

                inputUsername = username.getText().toString().toLowerCase();
                inputPassword = password.getText().toString();

                GetUserAsyncTask getUserAsyncTask = new GetUserAsyncTask();
                getUserAsyncTask.setDatabase(db);
                getUserAsyncTask.setDelegate(RegisterActivity.this);
                getUserAsyncTask.execute(inputUsername);
            }
        });
    }

    @Override
    public void handleTaskGetUserResult(User user) {
        if(inputUsername.length() < 5){
            noticeRegister.setText("Username minimum need to have 5 characters.");
            noticeRegister.setVisibility(View.VISIBLE);
        } else if(inputPassword.length() < 6){
            noticeRegister.setText("Password minimum need to have 6 characters.");
            noticeRegister.setVisibility(View.VISIBLE);
        } else {
            if(user != null){
                noticeRegister.setText("Username already been used.");
                noticeRegister.setVisibility(View.VISIBLE);
            } else {
                User newUser = new User(inputUsername, inputPassword);
                InsertUserAsyncTask insertUserAsyncTask = new InsertUserAsyncTask();
                insertUserAsyncTask.setDatabase(db);
                insertUserAsyncTask.setDelegate(RegisterActivity.this);
                insertUserAsyncTask.execute(newUser);
            }
        }
    }

    @Override
    public void handleTaskInsertUserResult(String result) {
        Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }
}
