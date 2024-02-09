package ru.kpfu.itis.lobanov.util.dto;

import lombok.Data;
import lombok.NonNull;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class PostDto {
    private String name;
    private String category;
    private String content;
    private String author;
    private Timestamp date;
    private int likes;
    private String authorImageUrl;

    public PostDto(@NonNull String name, @NonNull String category, @NonNull String content, @NonNull String author, @NonNull Timestamp date, int likes, String authorImageUrl) {
        this.name = name;
        this.category = category;
        this.content = content;
        this.author = author;
        this.date = date;
        this.likes = likes;
        this.authorImageUrl = authorImageUrl;
    }
}
