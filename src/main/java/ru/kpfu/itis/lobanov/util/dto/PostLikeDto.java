package ru.kpfu.itis.lobanov.util.dto;

public class PostLikeDto {
    private String nickname;
    private String post;

    public PostLikeDto(String nickname, String post) {
        this.nickname = nickname;
        this.post = post;
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
