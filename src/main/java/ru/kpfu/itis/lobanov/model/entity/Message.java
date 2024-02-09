package ru.kpfu.itis.lobanov.model.entity;

import lombok.Data;
import lombok.NonNull;

import java.sql.Timestamp;

@Data
public class Message {
    private int id;
    private int authorId;
    private String content;
    private String post;
    private Timestamp date;
    private int likes;

    public Message(int authorId, @NonNull String content, @NonNull String post, @NonNull Timestamp date, int likes) {
        this.authorId = authorId;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
    }

    public Message(int id, int authorId, @NonNull String content, @NonNull String post, @NonNull Timestamp date, int likes) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
    }
}
