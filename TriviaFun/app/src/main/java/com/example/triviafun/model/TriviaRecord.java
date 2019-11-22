package com.example.triviafun.model;

import java.util.ArrayList;

public class TriviaRecord {
    public String category;
    public String type;
    public String difficulty;
    public String question;
    public String answer;
    public ArrayList<String> option;
    public String answerChosen;
    public boolean correctness;

    public TriviaRecord(String category, String type, String difficulty,
                        String question, String answer, ArrayList<String> option,
                        String answerChosen, boolean correctness) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.answer = answer;
        this.option = option;
        this.answerChosen = answerChosen;
        this.correctness = correctness;
    }

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

    public String getAnswerChosen() {
        return answerChosen;
    }

    public void setAnswerChosen(String answerChosen) {
        this.answerChosen = answerChosen;
    }

    public boolean getCorrectness() {
        return correctness;
    }

    public void setCorrectness(boolean correctness) {
        this.correctness = correctness;
    }
}
