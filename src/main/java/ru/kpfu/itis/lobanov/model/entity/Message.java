package ru.kpfu.itis.lobanov.model.entity;

import java.sql.Date;
import java.util.Objects;

public class Message {
    private int id;
    private int authorId;
    private String content;
    private String post;
    private Date date;
    private int likes;

    public Message(int authorId, String content, String post, Date date, int likes) {
        this.authorId = authorId;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
    }

    public Message(int id, int authorId, String content, String post, Date date, int likes) {
        this.id = id;
        this.authorId = authorId;
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
        if (authorId != message.authorId) return false;
        if (likes != message.likes) return false;
        if (!content.equals(message.content)) return false;
        if (!post.equals(message.post)) return false;
        return date.equals(message.date);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + authorId;
        result = 31 * result + content.hashCode();
        result = 31 * result + post.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + likes;
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
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
