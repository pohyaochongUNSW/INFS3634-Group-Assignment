package com.example.triviafun;

import com.example.triviafun.model.CategoryList;
import com.example.triviafun.model.Trivia;
import com.example.triviafun.model.TriviaHistory;
import com.example.triviafun.model.TriviaRecord;

import java.util.ArrayList;

public class StaticResource {
    // Current login user
    public static String currentUser = "";

    // Categories get from api
    public static ArrayList<CategoryList.Category> categories = new ArrayList<CategoryList.Category>();

    // Question get from api
    public static ArrayList<Trivia.Question> triviaQuestion = new ArrayList<Trivia.Question>();

    // Current trivia category
    public static String currentTriviaCategory = "";

    // Current trivia total question number
    public static int currentTriviaTotalQuestion = 0;

    // Current trivia type
    public static String currentTriviaType = "";

    // Current trivia difficulty
    public static String currentTriviaDifficulty = "";

    // Current exp earn
    public static int currentExpEarn = 0;

    // Current trivia Question
    public static int currentQuestion = 0;

    // Current trivia score
    public static int currentTriviaScore = 0;

    // Trivia record with username, rate, comment, score
    public static TriviaHistory triviaHistory = new TriviaHistory();

    // Trivia record
    public static ArrayList<TriviaRecord> triviaRecords = new ArrayList<TriviaRecord>();

    public static void resetStaticResource(){
        currentTriviaScore = 0;
        triviaHistory = new TriviaHistory();
        triviaQuestion.clear();
        currentQuestion = 0;
        currentTriviaCategory = "";
        currentTriviaTotalQuestion = 0;
        currentTriviaType = "";
        triviaHistory = new TriviaHistory();
        triviaRecords.clear();
        currentTriviaDifficulty = "";
        currentExpEarn = 0;
    }

    public static String categoryToReview = "";
}
