package com.spring.elasticsearch.model.domain;


import java.util.List;

public class ExamResponse {
    String Question;
    List<String> Answers;

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public List<String> getAnswers() {
        return Answers;
    }

    public void setAnswers(List<String> answers) {
        Answers = answers;
    }
}
