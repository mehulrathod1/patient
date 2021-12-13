package com.in.patient.model;

public class QuestionModel {

    String Question, Date, QuestionDescription;

    public QuestionModel(String question, String date, String questionDescription) {
        Question = question;
        Date = date;
        QuestionDescription = questionDescription;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getQuestionDescription() {
        return QuestionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        QuestionDescription = questionDescription;
    }
}
