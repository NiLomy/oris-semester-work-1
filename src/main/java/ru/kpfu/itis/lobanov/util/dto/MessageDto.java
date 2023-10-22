package ru.kpfu.itis.lobanov.util.dto;

import java.sql.Date;

public class MessageDto {
    private int id;
    private String author;
    private String content;
    private String post;
    private Date date;
    private int likes;
    private String authorImgUrl;

    public MessageDto(String author, String content, String post, Date date, int likes, String authorImgUrl) {
        this.author = author;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
        this.authorImgUrl = authorImgUrl;
    }

    public MessageDto(int id, String author, String content, String post, Date date, int likes, String authorImgUrl) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
        this.authorImgUrl = authorImgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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

    public String getAuthorImgUrl() {
        return authorImgUrl;
    }

    public void setAuthorImgUrl(String authorImgUrl) {
        this.authorImgUrl = authorImgUrl;
    }
}
