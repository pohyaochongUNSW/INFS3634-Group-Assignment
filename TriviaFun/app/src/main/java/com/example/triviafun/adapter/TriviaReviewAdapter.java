package com.example.triviafun.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triviafun.R;
import com.example.triviafun.model.TriviaRecord;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class TriviaReviewAdapter extends RecyclerView.Adapter<TriviaReviewAdapter.TriviaReviewViewHolder> {

    ArrayList<TriviaRecord> triviaRecords;

    public void setData(ArrayList<TriviaRecord> triviaRecords){
        this.triviaRecords = triviaRecords;
    }

    public class TriviaReviewViewHolder extends RecyclerView.ViewHolder{

        public View v;
        public TextView questionNumber;
        public TextView question;
        public TextView answerPicked;
        public TextView correctAnswer;
        public ImageView googleSearch;

        public TriviaReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            questionNumber = v.findViewById(R.id.questionNumber);
            question = v.findViewById(R.id.question);
            answerPicked = v.findViewById(R.id.answerPicked);
            correctAnswer = v.findViewById(R.id.correctAnswer);
            googleSearch = v.findViewById(R.id.googleSearch);
        }
    }

    @NonNull
    @Override
    public TriviaReviewAdapter.TriviaReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.trivia_review, parent, false);

        // Then create an instance of your custom ViewHolder with the View you got from inflating
        // the layout.
        TriviaReviewAdapter.TriviaReviewViewHolder triviaReviewViewHolder = new TriviaReviewAdapter.TriviaReviewViewHolder(view);
        return triviaReviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TriviaReviewAdapter.TriviaReviewViewHolder holder, int position) {
        final TriviaRecord triviaRecord = triviaRecords.get(position);
        holder.questionNumber.setText("Question " + String.valueOf(position+1) + ".");
        holder.question.setText(triviaRecord.getQuestion());
        holder.answerPicked.setText(triviaRecord.getAnswerChosen());
        holder.correctAnswer.setText(triviaRecord.getAnswer());
        if(triviaRecord.getAnswer().equals(triviaRecord.getAnswerChosen())){
            holder.answerPicked.setTextColor(holder.v.getResources().getColor(R.color.colorGreen));
        } else {
            holder.answerPicked.setTextColor(holder.v.getResources().getColor(R.color.colorRed));
        }

        holder.googleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String escapedQuery = null;
                try {
                    escapedQuery = URLEncoder.encode(triviaRecord.getQuestion(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return triviaRecords.size();
    }
}
