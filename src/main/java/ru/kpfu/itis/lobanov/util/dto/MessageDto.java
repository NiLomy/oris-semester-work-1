package ru.kpfu.itis.lobanov.util.dto;

import lombok.Data;
import lombok.NonNull;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class MessageDto {
    private int id;
    private String author;
    private String content;
    private String post;
    private Timestamp date;
    private int likes;
    private String authorImgUrl;

    public MessageDto(@NonNull String author, @NonNull String content, @NonNull String post, @NonNull Timestamp date, int likes, String authorImgUrl) {
        this.author = author;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
        this.authorImgUrl = authorImgUrl;
    }

    public MessageDto(int id, @NonNull String author, @NonNull String content, @NonNull String post, @NonNull Timestamp date, int likes, String authorImgUrl) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
        this.authorImgUrl = authorImgUrl;
    }
}
