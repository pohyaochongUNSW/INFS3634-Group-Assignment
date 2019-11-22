package com.example.triviafun.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviafun.R;
import com.example.triviafun.StaticResource;
import com.example.triviafun.model.TriviaRecord;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class QuestionResultActivity extends AppCompatActivity {

    private TextView questionNumber;
    private TextView type;
    private TextView questionCategory;
    private TextView difficulty;
    private TextView question;
    private TextView answerPicked;
    private TextView correctAnswer;
    private Button buttonNext;
    private TextView scoreEarn;
    private TextView expEarn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_result);

        Intent intent = getIntent();
        String json = intent.getStringExtra("triviaRecord");
        Gson gson = new Gson();
        TriviaRecord triviaRecord = gson.fromJson(json, TriviaRecord.class);

        question = findViewById(R.id.question);
        answerPicked = findViewById(R.id.answerPicked);
        correctAnswer = findViewById(R.id.correctAnswer);
        buttonNext = findViewById(R.id.button_next);
        questionNumber = findViewById(R.id.currentQuestion);
        type = findViewById(R.id.questionType);
        questionCategory = findViewById(R.id.category);
        difficulty = findViewById(R.id.difficulty);
        scoreEarn = findViewById(R.id.scoreEarn);
        expEarn = findViewById(R.id.exp);

        String currentNumber = (StaticResource.currentQuestion+1) + "/" + StaticResource.currentTriviaTotalQuestion;
        questionNumber.setText(currentNumber);
        questionCategory.setText(triviaRecord.getCategory());
        String difficultyText = triviaRecord.getDifficulty();
        difficultyText = difficultyText.substring(0, 1).toUpperCase() + difficultyText.substring(1);
        difficulty.setText(difficultyText);
        if(triviaRecord.getType().equals("boolean")){
            type.setText("True / False");
        } else {
            type.setText("Multiple Choice");
        }


        question.setText(triviaRecord.getQuestion());
        correctAnswer.setText(triviaRecord.getAnswer());
        if(triviaRecord.getCorrectness()){
            StaticResource.currentTriviaScore += 1;
            answerPicked.setTextColor(getResources().getColor(R.color.colorGreen));
            answerPicked.setText(triviaRecord.getAnswerChosen());
            scoreEarn.setText("+1");
            scoreEarn.setTextColor(getResources().getColor(R.color.colorGreen));
            if(difficultyText.equals("Easy")){
                StaticResource.currentExpEarn += 1;
                expEarn.setText(String.valueOf("+1"));
            } else if(difficultyText.equals("Medium")){
                StaticResource.currentExpEarn += 2;
                expEarn.setText(String.valueOf("+2"));
            } else if(difficultyText.equals("Hard")){
                StaticResource.currentExpEarn += 3;
                expEarn.setText(String.valueOf("+3"));
            }
            expEarn.setTextColor(getResources().getColor(R.color.colorGreen));
        } else {
            answerPicked.setTextColor(getResources().getColor(R.color.colorRed));
            answerPicked.setText(triviaRecord.getAnswerChosen());
            expEarn.setText("+0");
            scoreEarn.setText("+0");
        }

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticResource.currentQuestion = StaticResource.currentQuestion+1;
                if(StaticResource.currentQuestion == StaticResource.triviaQuestion.size()){
                    setUpTriviaHistory("", "");
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TriviaReviewActivity.class);
                    intent.putExtra("comeFrom","endOfTrivia");
                    context.startActivity(intent);
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TriviaQuestionActivity.class);
                    context.startActivity(intent);
                }
            }
        });

    }

    private void setUpTriviaHistory(String rating, String comment){
        StaticResource.triviaHistory.setTriviaRecords(StaticResource.triviaRecords);
        StaticResource.triviaHistory.setUserName(StaticResource.currentUser);
        StaticResource.triviaHistory.setRating(rating);
        StaticResource.triviaHistory.setComment(comment);
        StaticResource.triviaHistory.setScore(StaticResource.currentTriviaScore);
        StaticResource.triviaHistory.setCategory(StaticResource.currentTriviaCategory);
        StaticResource.triviaHistory.setDifficulty(StaticResource.currentTriviaDifficulty);
        StaticResource.triviaHistory.setType(StaticResource.currentTriviaType);
        StaticResource.triviaHistory.setNumberOfQuestion(StaticResource.currentTriviaTotalQuestion);
        StaticResource.triviaHistory.setExp(StaticResource.currentExpEarn);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        String datetime = dateFormat.format(c.getTime());
        StaticResource.triviaHistory.setDate(datetime);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Not allow to re-do previous question.", Toast.LENGTH_LONG).show();
    }
}
