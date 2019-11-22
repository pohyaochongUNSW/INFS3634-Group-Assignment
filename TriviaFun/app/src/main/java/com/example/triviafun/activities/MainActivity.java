package com.example.triviafun.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.triviafun.R;
import com.example.triviafun.StaticResource;
import com.example.triviafun.fragment.HistoryRecyclerFragment;
import com.example.triviafun.fragment.SettingFragment;
import com.example.triviafun.fragment.TriviaSelectionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static String currentFragment = "trivia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(StaticResource.currentUser.equals("")){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            getApplicationContext().startActivity(intent);
        }

        Fragment fragment = new TriviaSelectionFragment();
        swapFragment(fragment);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.trivia) {
                    currentFragment = "trivia";
                    Fragment fragment = new TriviaSelectionFragment();
                    swapFragment(fragment);
                    return true;
                } else if (menuItem.getItemId() == R.id.historyPage) {
                    currentFragment = "history";
                    Fragment fragment = new HistoryRecyclerFragment();
                    swapFragment(fragment);
                    return true;
                } else if (menuItem.getItemId() == R.id.settingPage) {
                    currentFragment = "setting";
                    Fragment fragment = new SettingFragment();
                    swapFragment(fragment);
                    return true;
                }
                return false;
            }
        });
    }

    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(!StaticResource.currentUser.equals("")){
            if(currentFragment.equals("trivia")) {
                System.out.println("App close");
                moveTaskToBack(true);
            } else if(currentFragment.equals("history")){
                currentFragment = "trivia";
                bottomNavigationView.setSelectedItemId(R.id.trivia);
                swapFragment(new TriviaSelectionFragment());
            } else if(currentFragment.equals("setting")){
                currentFragment = "trivia";
                bottomNavigationView.setSelectedItemId(R.id.trivia);
                swapFragment(new TriviaSelectionFragment());
            } else if(currentFragment.equals("changePassword")){
                currentFragment = "setting";
                bottomNavigationView.setSelectedItemId(R.id.settingPage);
                swapFragment(new SettingFragment());
            } else if(currentFragment.equals("historyReviewRecycler")){
                currentFragment = "history";
                bottomNavigationView.setSelectedItemId(R.id.historyPage);
                swapFragment(new HistoryRecyclerFragment());
            }
        }
    }
}
