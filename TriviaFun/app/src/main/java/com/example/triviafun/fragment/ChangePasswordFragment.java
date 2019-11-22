package com.example.triviafun.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triviafun.R;
import com.example.triviafun.StaticResource;
import com.example.triviafun.asynctask.AsyncTaskChangePasswordDelegate;
import com.example.triviafun.asynctask.AsyncTaskGetUserDelegate;
import com.example.triviafun.asynctask.ChangePasswordAsyncTask;
import com.example.triviafun.asynctask.GetUserAsyncTask;
import com.example.triviafun.database.AppDatabase;
import com.example.triviafun.model.User;

public class ChangePasswordFragment extends Fragment implements AsyncTaskGetUserDelegate, AsyncTaskChangePasswordDelegate {

    public TextView notice;
    public Button submit;
    public Button back;
    public EditText currentPassword;
    public EditText newPassword;
    public EditText confirmNewPassword;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        notice = view.findViewById(R.id.notice);
        submit = view.findViewById(R.id.button_submit);
        back = view.findViewById(R.id.button_back);
        currentPassword = view.findViewById(R.id.currentPassword);
        newPassword = view.findViewById(R.id.newPassword);
        confirmNewPassword = view.findViewById(R.id.confirmNewPassword);
        notice.setVisibility(View.GONE);

        currentPassword.addTextChangedListener(new TextWatcher() {
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

        confirmNewPassword.addTextChangedListener(new TextWatcher() {
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

        newPassword.addTextChangedListener(new TextWatcher() {
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetUserAsyncTask getUserAsyncTask = new GetUserAsyncTask();
                getUserAsyncTask.setDelegate(ChangePasswordFragment.this);
                getUserAsyncTask.setDatabase(AppDatabase.getInstance(getContext()));
                getUserAsyncTask.execute(StaticResource.currentUser);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentLayout, new SettingFragment());
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    @Override
    public void handleTaskGetUserResult(User user) {
        User currentUser = user;
        String currentUserPassword = currentUser.getUserPassword();
        if(currentPassword.getText() != null) {
            String inputCurrentPassword = currentPassword.getText().toString();
            if (!inputCurrentPassword.equals(currentUserPassword)) {
                notice.setVisibility(View.VISIBLE);
                notice.setTextColor(getResources().getColor(R.color.colorRed));
                notice.setText("Incorrect Password.");
            } else {
                if(newPassword.getText() != null){
                    String newPasswordInput = newPassword.getText().toString();
                    if(newPasswordInput.length() >= 6){
                        if(confirmNewPassword.getText() != null){
                            String confirmNewPasswordInput = confirmNewPassword.getText().toString();
                            if(confirmNewPasswordInput.equals(newPasswordInput)){
                                ChangePasswordAsyncTask changePasswordAsyncTask = new ChangePasswordAsyncTask();
                                String[] input = {currentUser.getUsername(), newPasswordInput};
                                changePasswordAsyncTask.setDatabase(AppDatabase.getInstance(getContext()));
                                changePasswordAsyncTask.setDelegate(ChangePasswordFragment.this);
                                changePasswordAsyncTask.execute(input);
                            } else {
                                notice.setVisibility(View.VISIBLE);
                                notice.setTextColor(getResources().getColor(R.color.colorRed));
                                notice.setText("New password confirm didn't match.");
                            }
                        } else {
                            notice.setVisibility(View.VISIBLE);
                            notice.setTextColor(getResources().getColor(R.color.colorRed));
                            notice.setText("New password confirm didn't match.");
                        }
                    } else {
                        notice.setVisibility(View.VISIBLE);
                        notice.setTextColor(getResources().getColor(R.color.colorRed));
                        notice.setText("New Password should have at least 6 characters.");
                    }
                } else {
                    notice.setVisibility(View.VISIBLE);
                    notice.setTextColor(getResources().getColor(R.color.colorRed));
                    notice.setText("New Password cannot be empty.");
                }
            }
        } else {
            notice.setVisibility(View.VISIBLE);
            notice.setTextColor(getResources().getColor(R.color.colorRed));
            notice.setText("Incorrect Password.");
        }
    }

    @Override
    public void handleTaskChangePasswordResult(String result) {
        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
        notice.setVisibility(View.VISIBLE);
        notice.setTextColor(getResources().getColor(R.color.colorGreen));
        notice.setText(result);
    }
}
