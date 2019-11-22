package com.example.triviafun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.triviafun.R;
import com.example.triviafun.StaticResource;
import com.example.triviafun.activities.TriviaQuestionActivity;
import com.example.triviafun.model.CategoryList;
import com.example.triviafun.model.Trivia;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TriviaSelectionFragment extends Fragment {

    private Spinner numberOfQuestion;
    private Spinner category;
    private Spinner difficulty;
    private Spinner type;
    private Button button;
    private TextView notice;

    public TriviaSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_trivia_selection, container, false);

        numberOfQuestion = view.findViewById(R.id.spinnerNumberOfQuestion);
        category = view.findViewById(R.id.spinnerCategory);
        difficulty = view.findViewById(R.id.spinnerDifficulty);
        type = view.findViewById(R.id.spinnerType);
        button = view.findViewById(R.id.buttonSubmit);
        notice = view.findViewById(R.id.notice);
        notice.setVisibility(View.GONE);

        String[] questionNumberSelection = new String[]{"5", "10", "15", "20", "25", "30"};
        String[] difficultySelection = new String[]{"Any Difficulty", "Easy", "Medium", "Hard"};
        final String[] typeSelection = new String[]{"Any Type", "Multiple Choice", "True / False"};

        final RequestQueue requestQueue =  Volley.newRequestQueue(getContext());
        String categoryUrl = "https://opentdb.com/api_category.php";

        if(StaticResource.categories.size() < 1) {
            Response.Listener responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    CategoryList categoryList = gson.fromJson(response, CategoryList.class);
                    ArrayList<CategoryList.Category> categories= categoryList.getCategories();
                    CategoryList.Category category = new CategoryList().new Category(0, "Any Category");
                    categories.add(0, category);
                    StaticResource.categories = categories;
                    setUpCategory();
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "The request failed: " + error, Toast.LENGTH_SHORT).show();
                    requestQueue.stop();
                }
            };

            StringRequest stringRequest = new StringRequest(Request.Method.GET, categoryUrl, responseListener,
                    errorListener);
            requestQueue.add(stringRequest);
        } else {
            setUpCategory();
        }

        ArrayAdapter<String> numberAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, questionNumberSelection);
        final ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, difficultySelection);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, typeSelection);

        numberOfQuestion.setAdapter(numberAdapter);
        difficulty.setAdapter(difficultyAdapter);
        type.setAdapter(typeAdapter);
        setUpCategory();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberSelected = Integer.parseInt(numberOfQuestion.getSelectedItem().toString());
                String categorySelected = category.getSelectedItem().toString();
                String difficultySelected = difficulty.getSelectedItem().toString();
                String typeSelected = type.getSelectedItem().toString();

                StaticResource.currentTriviaDifficulty = difficultySelected;
                StaticResource.currentTriviaTotalQuestion = numberSelected;
                StaticResource.currentTriviaCategory = categorySelected;
                StaticResource.currentTriviaType = typeSelected;

                System.out.println(StaticResource.currentTriviaCategory);
                String apiUrl = "https://opentdb.com/api.php?amount=" + numberSelected;
                if (!categorySelected.equals("Any Category")) {
                    for (int i = 0; i < StaticResource.categories.size(); i++) {
                        CategoryList.Category category = StaticResource.categories.get(i);
                        if (categorySelected.equals(category.getName())) {
                            apiUrl = apiUrl + "&category=" + category.getId();
                            break;
                        }
                    }
                }

                if (!difficultySelected.equals("Any Difficulty")) {
                    apiUrl = apiUrl + "&difficulty=" + difficultySelected.toLowerCase();
                }

                if (typeSelected.equals(("Multiple Choice"))) {
                    apiUrl = apiUrl + "&type=multiple";
                } else if (typeSelected.equals("True / False")) {
                    apiUrl = apiUrl + "&type=boolean";
                }

                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int response_code = -1;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            response_code = jsonObject.getInt("response_code");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(response_code == 0) {
                            Gson gson = new Gson();
                            Trivia trivia = gson.fromJson(response, Trivia.class);
                            StaticResource.triviaQuestion = trivia.getQuestions();
                            Intent intent = new Intent(getContext(), TriviaQuestionActivity.class);
                            getContext().startActivity(intent);
                        } else {
                            switch(response_code){
                                case 1:
                                    notice.setText("Error 1: Not enough question in database, change Number of Question, Category, Difficulty or Type and retry.");
                                    break;
                                case 2:
                                    notice.setText("Error 2: Please connect system admin with error code.");
                                    break;
                                case 3:
                                    notice.setText("Error 3: Please connect system admin with error code.");
                                    break;
                                case 4:
                                    notice.setText("Error 4: Please connect system admin with error code.");
                                    break;
                                default:
                                    notice.setText("Error -1: Please connect system admin with error code.");

                            }
                            notice.setVisibility(View.VISIBLE);
                            notice.setTextColor(getResources().getColor(R.color.colorRed));
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        requestQueue.stop();
                    }
                };

                StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, responseListener,
                        errorListener);
                requestQueue.add(stringRequest);
            }
        });

        return view;
    }

    private void setUpCategory(){
        ArrayList<String> categorySelection = new ArrayList<String>();
        if(StaticResource.categories.size() < 1){
            categorySelection.add("Any Category");
        } else {
            for (int i = 0; i < StaticResource.categories.size(); i++) {
                categorySelection.add(StaticResource.categories.get(i).getName());
            }
        }
        if(getContext() != null) {
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, categorySelection);
            category.setAdapter(categoryAdapter);
        }
    }
}
