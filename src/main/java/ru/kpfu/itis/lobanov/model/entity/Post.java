package ru.kpfu.itis.lobanov.model.entity;

import java.sql.Date;
import java.util.Objects;

public class Post {
    private int id;
    private String name;
    private String category;
    private String content;
    private int authorId;
    private Date date;
    private int likes;

    public Post(String name, String category, String content, int authorId, Date date, int likes) {
        this.name = name;
        this.category = category;
        this.content = content;
        this.authorId = authorId;
        this.date = date;
        this.likes = likes;
    }

    public Post(int id, String name, String category, String content, int authorId, Date date, int likes) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.content = content;
        this.authorId = authorId;
        this.date = date;
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (id != post.id) return false;
        if (authorId != post.authorId) return false;
        if (likes != post.likes) return false;
        if (!name.equals(post.name)) return false;
        if (!category.equals(post.category)) return false;
        if (!content.equals(post.content)) return false;
        return Objects.equals(date, post.date);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + authorId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + likes;
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
