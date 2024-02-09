package ru.kpfu.itis.lobanov.util.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class MessageLikeDto {
    private String author;
    private int messageId;

    public MessageLikeDto(@NonNull String author, int messageId) {
        this.author = author;
        this.messageId = messageId;
    }
}
