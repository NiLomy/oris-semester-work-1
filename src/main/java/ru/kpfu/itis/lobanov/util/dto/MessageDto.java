package ru.kpfu.itis.lobanov.util.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class MessageDto {
    private int id;
    private String author;
    private String content;
    private String post;
    private Timestamp date;
    private int likes;
    private String authorImgUrl;

    public MessageDto(String author, String content, String post, Timestamp date, int likes, String authorImgUrl) {
        this.author = author;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
        this.authorImgUrl = authorImgUrl;
    }

    public MessageDto(int id, String author, String content, String post, Timestamp date, int likes, String authorImgUrl) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
        this.authorImgUrl = authorImgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageDto that = (MessageDto) o;

        if (id != that.id) return false;
        if (likes != that.likes) return false;
        if (!author.equals(that.author)) return false;
        if (!content.equals(that.content)) return false;
        if (!post.equals(that.post)) return false;
        if (!date.equals(that.date)) return false;
        return Objects.equals(authorImgUrl, that.authorImgUrl);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + author.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + post.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + likes;
        result = 31 * result + (authorImgUrl != null ? authorImgUrl.hashCode() : 0);
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getAuthorImgUrl() {
        return authorImgUrl;
    }

    public void setAuthorImgUrl(String authorImgUrl) {
        this.authorImgUrl = authorImgUrl;
    }
}
