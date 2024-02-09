package ru.kpfu.itis.lobanov.model.entity;

import lombok.Data;
import lombok.NonNull;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class Post {
    private int id;
    private String name;
    private String category;
    private String content;
    private int authorId;
    private Timestamp date;
    private int likes;

    public Post(@NonNull String name, @NonNull String category, @NonNull String content, int authorId, @NonNull Timestamp date, int likes) {
        this.name = name;
        this.category = category;
        this.content = content;
        this.authorId = authorId;
        this.date = date;
        this.likes = likes;
    }

    public Post(int id, @NonNull String name, @NonNull String category, @NonNull String content, int authorId, @NonNull Timestamp date, int likes) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.content = content;
        this.authorId = authorId;
        this.date = date;
        this.likes = likes;
    }
}
