package com.example.triviafun.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triviafun.R;
import com.example.triviafun.StaticResource;
import com.example.triviafun.adapter.CategoryAdapter;
import com.example.triviafun.adapter.CategoryReviewAdapter;
import com.example.triviafun.asynctask.AsyncTaskGetTriviaHistoryStorageByCategoryDelegate;
import com.example.triviafun.asynctask.GetTriviaHistoryStorageByCategoryAsyncTask;
import com.example.triviafun.database.AppDatabase;
import com.example.triviafun.model.TriviaHistoryStorage;

import java.util.List;

public class HistoryReviewRecyclerFragment extends Fragment implements AsyncTaskGetTriviaHistoryStorageByCategoryDelegate {

    private RecyclerView recyclerView;
    private CategoryReviewAdapter categoryReviewAdapter = new CategoryReviewAdapter();
    private TextView category;

    public HistoryReviewRecyclerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historyreview_recycler, container, false);

        category = view.findViewById(R.id.category);
        category.setText(StaticResource.categoryToReview);

        recyclerView = view.findViewById(R.id.rv_history_review);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        GetTriviaHistoryStorageByCategoryAsyncTask getTriviaHistoryStorageByCategoryAsyncTask = new GetTriviaHistoryStorageByCategoryAsyncTask();
        getTriviaHistoryStorageByCategoryAsyncTask.setDatabase(AppDatabase.getInstance(getContext()));
        getTriviaHistoryStorageByCategoryAsyncTask.setDelegate(HistoryReviewRecyclerFragment.this);
        String[] strings = new String[2];
        strings[0] = StaticResource.currentUser;
        strings[1] = StaticResource.categoryToReview;
        getTriviaHistoryStorageByCategoryAsyncTask.execute(strings);

        return view;
    }

    @Override
    public void handleTaskGetTriviaHistoryStorageByCategoryResult(List<TriviaHistoryStorage> triviaHistoryStorage) {
        categoryReviewAdapter.setData(triviaHistoryStorage);
        recyclerView.setAdapter(categoryReviewAdapter);
    }
}
