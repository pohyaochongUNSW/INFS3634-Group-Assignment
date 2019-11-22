package com.example.triviafun.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviafun.R;
import com.example.triviafun.StaticResource;
import com.example.triviafun.model.Trivia;
import com.example.triviafun.model.TriviaRecord;
import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Collections;

public class TriviaQuestionActivity extends AppCompatActivity {


    private Button buttonNext;
    private TextView questionNumber;
    private TextView question;
    private TextView type;
    private TextView questionCategory;
    private TextView difficulty;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_question);

        questionNumber = findViewById(R.id.currentQuestion);
        type = findViewById(R.id.questionType);
        question = findViewById(R.id.question);
        buttonNext = findViewById(R.id.button_next);
        questionCategory = findViewById(R.id.category);
        difficulty = findViewById(R.id.difficulty);
        radioGroup = findViewById(R.id.radioGroup);

        final Trivia.Question currentQuestion = StaticResource.triviaQuestion.get(StaticResource.currentQuestion);

        String currentNumber = (StaticResource.currentQuestion+1) + "/" + StaticResource.currentTriviaTotalQuestion;
        questionNumber.setText(currentNumber);
        String questionText = currentQuestion.getQuestion();
        questionText = filterString(questionText);
        StaticResource.triviaQuestion.get(StaticResource.currentQuestion).setQuestion(questionText);
        question.setText(questionText);
        questionCategory.setText(currentQuestion.getCategory());
        String difficultyText = currentQuestion.getDifficulty();
        difficultyText = difficultyText.substring(0, 1).toUpperCase() + difficultyText.substring(1);
        difficulty.setText(difficultyText);
        ArrayList<String> answerOption = new ArrayList<>();
        if(currentQuestion.getType().equals("boolean")){
            type.setText("True / False");
            answerOption.add("True");
            answerOption.add("False");
        } else {
            type.setText("Multiple Choice");
            for(int i = 0; i < currentQuestion.getOption().size(); i++){
                String option = currentQuestion.getOption().get(i);
                option = option.replaceAll("&quot;", "\"");
                option = filterString(option);
                StaticResource.triviaQuestion.get(StaticResource.currentQuestion).getOption().set(i, option);
                currentQuestion.getOption().set(i, option);
            }
            String correctAnswer = currentQuestion.getAnswer();
            correctAnswer = filterString(correctAnswer);
            currentQuestion.setAnswer(correctAnswer);
            StaticResource.triviaQuestion.get(StaticResource.currentQuestion).setAnswer(correctAnswer);
            answerOption.add(currentQuestion.getAnswer());
            answerOption.addAll(currentQuestion.getOption());
            Collections.shuffle(answerOption);
        }

        for(int i = 0; i < answerOption.size(); i++){
            RadioButton rb = new RadioButton(this);
            rb.setText(answerOption.get(i));
            radioGroup.addView(rb);
        }

        Gson gson = new Gson();
        String json = gson.toJson(currentQuestion);
        System.out.println(json);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if(selectedId == -1) {
                    Toast.makeText(getApplicationContext(),"Please select an answer.", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton radioButton = findViewById(selectedId);
                    String answerPicked = radioButton.getText().toString();
                    boolean correctness = false;
                    if(answerPicked.equals(currentQuestion.getAnswer())){
                        correctness = true;
                    }
                    TriviaRecord triviaRecord = new TriviaRecord(
                            currentQuestion.getCategory(),
                            currentQuestion.getType(),
                            currentQuestion.getDifficulty(),
                            currentQuestion.getQuestion(),
                            currentQuestion.getAnswer(),
                            currentQuestion.getOption(),
                            answerPicked,
                            correctness
                    );
                    StaticResource.triviaRecords.add(triviaRecord);

                    System.out.println(StaticResource.triviaRecords);

                    Context context = v.getContext();
                    Intent intent = new Intent(context, QuestionResultActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra("triviaRecord", gson.toJson(triviaRecord));
                    context.startActivity(intent);
                }

            }
        });
    }

    public String filterString(String s){
        s = Jsoup.parse(s).text();

        return s;
    }

    @Override
    public void onBackPressed() {

        if(StaticResource.triviaRecords.size() > 0) {
            StaticResource.currentQuestion -= 1;
        } else {
            StaticResource.currentQuestion = 0;
            StaticResource.resetStaticResource();
        }
        super.onBackPressed();
    }
}
