package com.spring.elasticsearch.model.entities;


public class Tags {
    private String name;

    public Tags() {
    }

    public Tags(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "name='" + name + '\'' +
                '}';
    }
}
