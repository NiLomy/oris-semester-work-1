package ru.kpfu.itis.lobanov.util.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class PostLikeDto {
    private String nickname;
    private String post;

    public PostLikeDto(@NonNull String nickname, @NonNull String post) {
        this.nickname = nickname;
        this.post = post;
    }
}
