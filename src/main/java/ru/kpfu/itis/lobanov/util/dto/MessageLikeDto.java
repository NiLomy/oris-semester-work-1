package ru.kpfu.itis.lobanov.util.dto;

import java.util.Objects;

public class MessageLikeDto {
    private String author;
    private int messageId;

    public MessageLikeDto(String author, int messageId) {
        this.author = author;
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageLikeDto that = (MessageLikeDto) o;

        if (messageId != that.messageId) return false;
        return Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + messageId;
        return result;
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
