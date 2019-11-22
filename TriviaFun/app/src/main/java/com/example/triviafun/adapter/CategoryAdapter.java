package com.example.triviafun.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triviafun.R;
import com.example.triviafun.StaticResource;
import com.example.triviafun.activities.MainActivity;
import com.example.triviafun.fragment.HistoryReviewRecyclerFragment;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<String> categories;

    public void setData(ArrayList<String> categories){
        this.categories = categories;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        public View v;
        public TextView categoryName;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            categoryName = v.findViewById(R.id.categoryName);
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category, parent, false);

        // Then create an instance of your custom ViewHolder with the View you got from inflating
        // the layout.
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, final int position) {
        holder.categoryName.setText(categories.get(position));

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticResource.categoryToReview = categories.get(position);
                MainActivity.currentFragment = "historyReviewRecycler";
                Fragment fragment = new HistoryReviewRecyclerFragment();
                swapFragment(fragment, v.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private void swapFragment(Fragment newFragment, Context context){

        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayout, newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
