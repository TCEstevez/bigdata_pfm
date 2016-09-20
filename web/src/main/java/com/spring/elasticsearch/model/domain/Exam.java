package com.spring.elasticsearch.model.domain;


import java.util.ArrayList;
import java.util.List;

public class Exam {

    List<Question> questions;
    String result="";

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Exam(){
        questions =new ArrayList<Question>();
    }
}
