package com.example.triviafun.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triviafun.R;
import com.example.triviafun.activities.TriviaReviewActivity;
import com.example.triviafun.model.Trivia;
import com.example.triviafun.model.TriviaHistory;
import com.example.triviafun.model.TriviaHistoryStorage;
import com.google.gson.Gson;

import java.util.List;

public class CategoryReviewAdapter extends RecyclerView.Adapter<CategoryReviewAdapter.CategoryReviewViewHolder> {

    private List<TriviaHistoryStorage> triviaHistories;

    public void setData(List<TriviaHistoryStorage> triviaHistories){
        this.triviaHistories = triviaHistories;
    }

    public class CategoryReviewViewHolder extends RecyclerView.ViewHolder {
        public View v;
        public TextView type;
        public TextView date;
        public TextView score;
        public TextView difficulty;

        public CategoryReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            type = v.findViewById(R.id.reviewType);
            date = v.findViewById(R.id.reviewDate);
            score = v.findViewById(R.id.reviewScore);
            difficulty = v.findViewById(R.id.reviewDifficulty);
        }
    }

    @NonNull
    @Override
    public CategoryReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.trivia_review_by_category, parent, false);

        // Then create an instance of your custom ViewHolder with the View you got from inflating
        // the layout.
        CategoryReviewAdapter.CategoryReviewViewHolder categoryReviewViewHolder = new CategoryReviewAdapter.CategoryReviewViewHolder(view);
        return categoryReviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryReviewViewHolder holder, int position) {
        final TriviaHistoryStorage triviaHistoryStorage = triviaHistories.get(position);
        final Gson gson = new Gson();
        TriviaHistory triviaHistory = gson.fromJson(triviaHistoryStorage.getTriviaHistoryJson(), TriviaHistory.class);

        holder.difficulty.setText(triviaHistory.getDifficulty());
        holder.date.setText(triviaHistory.getDate());
        holder.type.setText(triviaHistory.getType());
        String scoreGet = triviaHistory.getScore() + "/" + triviaHistory.getNumberOfQuestion();
        holder.score.setText(scoreGet);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, TriviaReviewActivity.class);
                String json = gson.toJson(triviaHistoryStorage, TriviaHistoryStorage.class);
                intent.putExtra("comeFrom", "categoryReviewAdapter");
                intent.putExtra("json", json);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return triviaHistories.size();
    }
}
