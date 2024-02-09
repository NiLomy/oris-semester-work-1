package ru.kpfu.itis.lobanov.model.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class PostLike {
    private int id;
    private String nickname;
    private String post;

    public PostLike(@NonNull String nickname, @NonNull String post) {
        this.nickname = nickname;
        this.post = post;
    }

    public PostLike(int id, @NonNull String nickname, @NonNull String post) {
        this.id = id;
        this.nickname = nickname;
        this.post = post;
    }
}
