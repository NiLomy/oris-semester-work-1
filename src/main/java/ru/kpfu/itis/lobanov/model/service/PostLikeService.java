package ru.kpfu.itis.lobanov.model.service;

import ru.kpfu.itis.lobanov.model.entity.PostLike;
import ru.kpfu.itis.lobanov.util.dto.PostLikeDto;

public interface PostLikeService {
    PostLikeDto get(int id);
    boolean isSet(String nickname, String post);
    int getAmountFromPost(String post);
    int getAmount();
    void save(PostLike postLike);
    void remove(PostLike postLike);
}
