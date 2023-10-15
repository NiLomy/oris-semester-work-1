package ru.kpfu.itis.lobanov.util.dto;

public class MessageLikeDto {
    private String author;
    private int messageId;

    public MessageLikeDto(String author, int messageId) {
        this.author = author;
        this.messageId = messageId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
