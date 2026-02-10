package com.example.demo.model;

import java.util.Date;

public class Post {
    private String text;
    private Integer likes = 0;
    private Date creationDate;

    public Post(String text, Date creationDate) {
        this.text = text;
        this.creationDate = creationDate;
    }

    public String getText() {
        return text;
    }

    public Integer getLikes() {
        return likes;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
