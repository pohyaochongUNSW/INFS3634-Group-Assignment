package com.example.triviafun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.triviafun.R;
import com.example.triviafun.StaticResource;
import com.example.triviafun.activities.AboutUsActivity;
import com.example.triviafun.activities.LoginActivity;
import com.example.triviafun.activities.MainActivity;
import com.example.triviafun.asynctask.AsyncTaskGetTriviaHistoryStorageDelegate;
import com.example.triviafun.asynctask.GetTriviaHistoryStorageAsyncTask;
import com.example.triviafun.database.AppDatabase;
import com.example.triviafun.model.TriviaHistory;
import com.example.triviafun.model.TriviaHistoryStorage;
import com.google.gson.Gson;

import java.util.List;


public class SettingFragment extends Fragment implements AsyncTaskGetTriviaHistoryStorageDelegate {

    private TextView username;
    private ProgressBar progressBar;
    private TextView level;
    private Button changePassword;
    private Button aboutUs;
    private Button signout;
    private TextView curretXp;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        username = view.findViewById(R.id.userName);
        progressBar = view.findViewById(R.id.progressBar);
        level = view.findViewById(R.id.level);
        username.setText(StaticResource.currentUser);
        changePassword = view.findViewById(R.id.button_change_password);
        aboutUs = view.findViewById(R.id.button_about_us);
        signout = view.findViewById(R.id.button_signout);
        curretXp = view.findViewById(R.id.currentExp);


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MainActivity.currentFragment = "changePassword";
                fragmentTransaction.replace(R.id.fragmentLayout, new ChangePasswordFragment());
                fragmentTransaction.commit();
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AboutUsActivity.class);
                getContext().startActivity(intent);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticResource.currentUser = "";
                StaticResource.categoryToReview = "";
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
            }
        });

        GetTriviaHistoryStorageAsyncTask getTriviaHistoryStorageAsyncTask = new GetTriviaHistoryStorageAsyncTask();
        getTriviaHistoryStorageAsyncTask.setDatabase(AppDatabase.getInstance(getContext()));
        getTriviaHistoryStorageAsyncTask.setDelegate(SettingFragment.this);
        getTriviaHistoryStorageAsyncTask.execute(StaticResource.currentUser);

        return view;
    }

    @Override
    public void handleTaskGetTriviaHistoryStorageResult(List<TriviaHistoryStorage> triviaHistoryStorage) {
        int exp = 0;
        List<TriviaHistoryStorage> triviaHistoryStorageList = triviaHistoryStorage;
        Gson gson = new Gson();
        for(int i = 0; i < triviaHistoryStorageList.size(); i++){
            TriviaHistory triviaHistory = gson.fromJson(triviaHistoryStorageList.get(i).getTriviaHistoryJson(), TriviaHistory.class);
            exp += triviaHistory.getExp();
        }

        int levelInt = (exp / 100) + 1;
        progressBar.setProgress(exp%100);
        level.setText("Level " + String.valueOf(levelInt));
        curretXp.setText(String.valueOf(exp) + "/100");
    }
}
