package com.spring.elasticsearch.model.domain;


public class Question {

    String dificulty;
    String dificultyto;
    String tags;
    String number;

    public String getDificulty() {
        return dificulty;
    }

    public void setDificulty(String dificulty) {
        this.dificulty = dificulty;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDificultyto() {
        return dificultyto;
    }

    public void setDificultyto(String dificultyto) {
        this.dificultyto = dificultyto;
    }

    public Question() {
        this.dificulty = "0.00";
        this.dificultyto = "9.99";
        this.tags = "";
        this.number = "0";
    }

}
