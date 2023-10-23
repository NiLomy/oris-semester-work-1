package ru.kpfu.itis.lobanov.util.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class PostDto {
    private String name;
    private String category;
    private String content;
    private String author;
    private Timestamp date;
    private int likes;
    private String authorImageUrl;

    public PostDto(String name, String category, String content, String author, Timestamp date, int likes, String authorImageUrl) {
        this.name = name;
        this.category = category;
        this.content = content;
        this.author = author;
        this.date = date;
        this.likes = likes;
        this.authorImageUrl = authorImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostDto postDto = (PostDto) o;

        if (likes != postDto.likes) return false;
        if (!name.equals(postDto.name)) return false;
        if (!category.equals(postDto.category)) return false;
        if (!content.equals(postDto.content)) return false;
        if (!author.equals(postDto.author)) return false;
        if (!date.equals(postDto.date)) return false;
        return Objects.equals(authorImageUrl, postDto.authorImageUrl);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + likes;
        result = 31 * result + (authorImageUrl != null ? authorImageUrl.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }
}
