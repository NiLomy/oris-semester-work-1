package ru.kpfu.itis.lobanov.util.dto;

public class PostLikeDto {
    private String nickname;
    private String post;

    public PostLikeDto(String nickname, String post) {
        this.nickname = nickname;
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostLikeDto that = (PostLikeDto) o;

        if (!nickname.equals(that.nickname)) return false;
        return post.equals(that.post);
    }

    @Override
    public int hashCode() {
        int result = nickname.hashCode();
        result = 31 * result + post.hashCode();
        return result;
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
