package com.example.triviafun.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviafun.R;
import com.example.triviafun.StaticResource;
import com.example.triviafun.asynctask.AsyncTaskInsertTriviaHistoryStorageDelegate;
import com.example.triviafun.asynctask.InsertTriviaHistoryStorageAsyncTask;
import com.example.triviafun.database.AppDatabase;
import com.example.triviafun.model.Trivia;
import com.example.triviafun.model.TriviaHistory;
import com.example.triviafun.model.TriviaHistoryStorage;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TriviaRatingActivity extends AppCompatActivity implements AsyncTaskInsertTriviaHistoryStorageDelegate{

    private ImageView button_like;
    private ImageView button_unlike;
    private Button button_skip;
    private Button button_submit;
    private TextView comment;
    private String rating = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_rating);

        button_like = findViewById(R.id.button_like);
        button_unlike = findViewById(R.id.button_unlike);
        button_skip = findViewById(R.id.button_skip);
        button_submit = findViewById(R.id.button_submit);
        comment = findViewById(R.id.comment);

        button_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rating.equals("like")){
                    rating = "";
                    button_like.setImageResource(R.drawable.like);
                } else {
                    if (rating.equals("")) {
                        button_like.setImageResource(R.drawable.like_clicked);
                        rating = "like";
                    } else if (rating.equals("unlike")) {
                        button_unlike.setImageResource(R.drawable.unlike);
                        button_like.setImageResource(R.drawable.like_clicked);
                        rating = "like";
                    }
                }
            }
        });

        button_unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rating.equals("unlike")){
                    rating = "";
                    button_unlike.setImageResource(R.drawable.unlike);
                } else {
                    if (rating.equals("")) {
                        button_unlike.setImageResource(R.drawable.unlike_clicked);
                        rating = "unlike";
                    } else if (rating.equals("like")) {
                        button_unlike.setImageResource(R.drawable.unlike_clicked);
                        button_like.setImageResource(R.drawable.like);
                        rating = "unlike";
                    }
                }
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rating.equals("")){
                    Toast.makeText(getApplicationContext(),"Please pick a rate.",Toast.LENGTH_SHORT).show();
                } else {
                    if(comment.getText() == null){
                        setUpTriviaHistory(rating, null);
                    } else {
                        setUpTriviaHistory(rating, comment.getText().toString());
                    }

                    Context context = v.getContext();
                    saveRecord(context);
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpTriviaHistory(null, null);

                Context context = v.getContext();
                saveRecord(context);
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });

    }

    private void saveRecord(Context context){
        AppDatabase db= AppDatabase.getInstance(context);

        TriviaHistoryStorage triviaHistoryStorage = new TriviaHistoryStorage();
        triviaHistoryStorage.setUsername(StaticResource.currentUser);
        triviaHistoryStorage.setCategory(StaticResource.currentTriviaCategory);
        Gson gson = new Gson();
        String recordJson = gson.toJson(StaticResource.triviaHistory, TriviaHistory.class);
        triviaHistoryStorage.setTriviaHistoryJson(recordJson);

        InsertTriviaHistoryStorageAsyncTask insertTriviaHistoryStorageAsyncTask = new InsertTriviaHistoryStorageAsyncTask();
        insertTriviaHistoryStorageAsyncTask.setDatabase(db);
        insertTriviaHistoryStorageAsyncTask.setDelegate(TriviaRatingActivity.this);
        insertTriviaHistoryStorageAsyncTask.execute(triviaHistoryStorage);

        StaticResource.resetStaticResource();
    }

    @Override
    public void handleTaskInsertTriviaHistoryStorageResult(String result) {
        Toast.makeText(getApplicationContext(), "Record Saved!", Toast.LENGTH_SHORT).show();
    }

    private void setUpTriviaHistory(String rating, String comment){
        StaticResource.triviaHistory.setRating(rating);
        StaticResource.triviaHistory.setComment(comment);
    }
}
