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
import com.example.triviafun.StaticResource;
import com.example.triviafun.asynctask.AsyncTaskGetUserDelegate;
import com.example.triviafun.asynctask.GetUserAsyncTask;
import com.example.triviafun.database.AppDatabase;
import com.example.triviafun.model.User;

public class LoginActivity extends AppCompatActivity implements AsyncTaskGetUserDelegate {

    private EditText username;
    private EditText password;
    private TextView buttonRegister;
    private Button buttonLogin;
    private String inputUsername = "";
    private String inputPassword = "";
    private TextView notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        buttonRegister = findViewById(R.id.buttonRegisterPage);
        buttonLogin = findViewById(R.id.buttonLogin);
        notice = findViewById(R.id.notice);
        notice.setVisibility(View.GONE);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notice.setVisibility(View.GONE);
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
                notice.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(context, RegisterActivity.class);
                context.startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                AppDatabase db = AppDatabase.getInstance(context);
                inputUsername = username.getText().toString().toLowerCase();
                inputPassword = password.getText().toString();

                GetUserAsyncTask getUserAsyncTask = new GetUserAsyncTask();
                getUserAsyncTask.setDatabase(db);
                getUserAsyncTask.setDelegate(LoginActivity.this);
                getUserAsyncTask.execute(inputUsername);
            }
        });
    }


    @Override
    public void handleTaskGetUserResult(User user) {
        if(user == null){
            notice.setText("Invalid username or password.");
            notice.setTextColor(getResources().getColor(R.color.colorRed));
            notice.setVisibility(View.VISIBLE);
        } else {
            if(user.getUserPassword().equals(inputPassword)){
                StaticResource.currentUser = inputUsername;
                Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            } else {
                notice.setText("Invalid username or password.");
                notice.setTextColor(getResources().getColor(R.color.colorRed));
                notice.setVisibility(View.VISIBLE);
            }
        }
    }
}

