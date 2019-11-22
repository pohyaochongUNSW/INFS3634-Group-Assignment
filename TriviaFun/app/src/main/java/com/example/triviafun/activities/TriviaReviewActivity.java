package com.example.triviafun.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triviafun.R;
import com.example.triviafun.StaticResource;
import com.example.triviafun.adapter.TriviaReviewAdapter;
import com.example.triviafun.model.TriviaHistory;
import com.example.triviafun.model.TriviaHistoryStorage;
import com.example.triviafun.model.TriviaRecord;
import com.google.gson.Gson;

public class TriviaReviewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TriviaReviewAdapter triviaReviewAdapter = new TriviaReviewAdapter();
    private Button buttonEnd;

    private TextView category;
    private TextView difficulty;
    private TextView score;
    private TextView type;
    private ImageView rating;
    private TextView ratingTitle;
    private TextView comment;
    private TextView exp;
    private TextView titleComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_review);

        category = findViewById(R.id.category);
        difficulty = findViewById(R.id.difficulty);
        score = findViewById(R.id.score);
        type = findViewById(R.id.type);
        buttonEnd = findViewById(R.id.buttonEndTrivia);
        rating = findViewById(R.id.rating);
        ratingTitle = findViewById(R.id.ratingTitle);
        comment = findViewById(R.id.comment);
        exp = findViewById(R.id.exp);
        titleComment = findViewById(R.id.commentTitle);

        Intent intent = getIntent();

        TriviaHistory triviaHistory = new TriviaHistory();
        String comeFrom = intent.getStringExtra("comeFrom");
        if(comeFrom.equals("endOfTrivia")) {
            triviaHistory = StaticResource.triviaHistory;
            rating.setVisibility(View.GONE);
            ratingTitle.setVisibility(View.GONE);
            comment.setVisibility(View.GONE);
            titleComment.setVisibility(View.GONE);
        } else if(comeFrom.equals("categoryReviewAdapter")) {
            String json = intent.getStringExtra("json");
            Gson gson = new Gson();
            TriviaHistoryStorage triviaHistoryStorage = gson.fromJson(json, TriviaHistoryStorage.class);
            triviaHistory = gson.fromJson(triviaHistoryStorage.getTriviaHistoryJson(), TriviaHistory.class);
            if(triviaHistory.getRating() == null) {
                rating.setVisibility(View.GONE);
                ratingTitle.setVisibility(View.GONE);
            } else if(triviaHistory.getRating().equals("like")){
                rating.setImageResource(R.drawable.like_clicked);
            } else if(triviaHistory.getRating().equals("unlike")){
                rating.setImageResource(R.drawable.unlike_clicked);
            }

            if(triviaHistory.getComment() == null){
                comment.setVisibility(View.GONE);
                titleComment.setVisibility(View.GONE);
            } else {
                if(triviaHistory.getComment().equals("")){
                    comment.setVisibility(View.GONE);
                    titleComment.setVisibility(View.GONE);
                } else {
                    comment.setText(triviaHistory.getComment());
                }
            }
            buttonEnd.setVisibility(View.GONE);
        }

        category.setText(triviaHistory.getCategory());
        difficulty.setText(triviaHistory.getDifficulty());
        type.setText(triviaHistory.getType());
        String scoreText = triviaHistory.getScore() + "/" + triviaHistory.getNumberOfQuestion();
        score.setText(scoreText);
        exp.setText("+" + triviaHistory.getExp());

        recyclerView = findViewById(R.id.rv_trivia_review);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        triviaReviewAdapter.setData(triviaHistory.getTriviaRecords());
        recyclerView.setAdapter(triviaReviewAdapter);

        buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TriviaRatingActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(StaticResource.currentQuestion != 0) {
            StaticResource.currentQuestion -= 1;
        }
        super.onBackPressed();
    }
}
