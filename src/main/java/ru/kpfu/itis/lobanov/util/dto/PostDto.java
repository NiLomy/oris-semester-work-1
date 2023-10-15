package ru.kpfu.itis.lobanov.util.dto;

import java.sql.Date;

public class PostDto {
    private String name;
    private String category;
    private String content;
    private String author;
    private Date date;
    private int likes;

    public PostDto(String name, String category, String content, String author, Date date, int likes) {
        this.name = name;
        this.category = category;
        this.content = content;
        this.author = author;
        this.date = date;
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
