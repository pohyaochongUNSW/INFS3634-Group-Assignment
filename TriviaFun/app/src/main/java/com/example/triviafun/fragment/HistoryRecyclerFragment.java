package com.example.triviafun.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.triviafun.R;
import com.example.triviafun.StaticResource;
import com.example.triviafun.adapter.CategoryAdapter;
import com.example.triviafun.asynctask.AsyncTaskGetTriviaHistoryStorageDelegate;
import com.example.triviafun.asynctask.GetTriviaHistoryStorageAsyncTask;
import com.example.triviafun.database.AppDatabase;
import com.example.triviafun.model.TriviaHistory;
import com.example.triviafun.model.TriviaHistoryStorage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class HistoryRecyclerFragment extends Fragment implements AsyncTaskGetTriviaHistoryStorageDelegate {

    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter = new CategoryAdapter();
    private AppDatabase db;

    public HistoryRecyclerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_recycler, container, false);

        recyclerView = view.findViewById(R.id.rv_category);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        db = AppDatabase.getInstance(getContext());
        GetTriviaHistoryStorageAsyncTask getTriviaHistoryStorageAsyncTask = new GetTriviaHistoryStorageAsyncTask();
        getTriviaHistoryStorageAsyncTask.setDatabase(db);
        getTriviaHistoryStorageAsyncTask.setDelegate(HistoryRecyclerFragment.this);
        getTriviaHistoryStorageAsyncTask.execute(StaticResource.currentUser);

        return view;
    }

    @Override
    public void handleTaskGetTriviaHistoryStorageResult(List<TriviaHistoryStorage> triviaHistoryStorage) {

        List<TriviaHistoryStorage> triviaHistoryStorageList = triviaHistoryStorage;
        System.out.println("Record size: " + triviaHistoryStorageList.size());
        ArrayList<TriviaHistory> triviaHistories = new ArrayList<TriviaHistory>();
        ArrayList<String> categories = new ArrayList<String>();
        Gson gson = new Gson();
        for(int i = 0; i < triviaHistoryStorageList.size() ; i++){
            TriviaHistory triviaHistory = gson.fromJson(triviaHistoryStorageList.get(i).getTriviaHistoryJson(), TriviaHistory.class);
            triviaHistories.add(triviaHistory);
        }

        for(int i = 0; i < triviaHistories.size(); i++){
            String category = triviaHistories.get(i).getCategory();
            System.out.println(category);
            if(!checkDuplicateCategory(category, categories)){
                categories.add(category);
            }
        }

        categoryAdapter.setData(categories);
        recyclerView.setAdapter(categoryAdapter);
    }

    public boolean checkDuplicateCategory(String category, ArrayList<String> categories){

        for(int i = 0; i < categories.size(); i++){
            if(category.equals(categories.get(i))){
                return true;
            }
        }

        return false;
    }
}
