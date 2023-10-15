package ru.kpfu.itis.lobanov.model.entity;

public class MessageLike {
    private int id;
    private String author;
    private int messageId;

    public MessageLike(String author, int messageId) {
        this.author = author;
        this.messageId = messageId;
    }

    public MessageLike(int id, String author, int messageId) {
        this.id = id;
        this.author = author;
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageLike that = (MessageLike) o;

        if (id != that.id) return false;
        if (messageId != that.messageId) return false;
        return author.equals(that.author);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + author.hashCode();
        result = 31 * result + messageId;
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

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
