package ru.kpfu.itis.lobanov.model.entity;

import java.sql.Date;
import java.util.Objects;

public class Message {
    private int id;
    private String author;
    private String content;
    private String post;
    private Date date;
    private int likes;

    public Message(String author, String content, String post, Date date, int likes) {
        this.author = author;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
    }

    public Message(int id, String author, String content, String post, Date date, int likes) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (likes != message.likes) return false;
        if (!Objects.equals(author, message.author)) return false;
        if (!Objects.equals(content, message.content)) return false;
        if (!Objects.equals(post, message.post)) return false;
        return Objects.equals(date, message.date);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (post != null ? post.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + likes;
        return result;
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
}
