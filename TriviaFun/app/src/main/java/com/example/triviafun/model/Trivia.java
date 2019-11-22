package com.example.triviafun.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Trivia {

    public int response_code;
    @SerializedName("results")
    public ArrayList<Question> questions;

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public class Question{
        public String category;
        public String type;
        public String difficulty;
        public String question;
        @SerializedName("correct_answer")
        public String answer;
        @SerializedName("incorrect_answers")
        public ArrayList<String> option;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public ArrayList<String> getOption() {
            return option;
        }

        public void setOption(ArrayList<String> option) {
            this.option = option;
        }
    }
}
