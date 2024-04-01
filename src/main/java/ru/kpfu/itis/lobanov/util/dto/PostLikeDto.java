package ru.kpfu.itis.lobanov.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostLikeDto {
    private String nickname;
    private String post;
}
