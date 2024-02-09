package ru.kpfu.itis.lobanov.model.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class MessageLike {
    private int id;
    private String author;
    private int messageId;

    public MessageLike(@NonNull String author, int messageId) {
        this.author = author;
        this.messageId = messageId;
    }

    public MessageLike(int id, @NonNull String author, int messageId) {
        this.id = id;
        this.author = author;
        this.messageId = messageId;
    }
}
