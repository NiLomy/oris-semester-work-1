package ru.kpfu.itis.lobanov.model.entity;

public class PostLike {
    private int id;
    private String nickname;
    private String post;

    public PostLike(String nickname, String post) {
        this.nickname = nickname;
        this.post = post;
    }

    public PostLike(int id, String nickname, String post) {
        this.id = id;
        this.nickname = nickname;
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostLike that = (PostLike) o;

        if (id != that.id) return false;
        if (!nickname.equals(that.nickname)) return false;
        return post.equals(that.post);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nickname.hashCode();
        result = 31 * result + post.hashCode();
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
