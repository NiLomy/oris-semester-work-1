package ru.kpfu.itis.lobanov.model.dao;

import ru.kpfu.itis.lobanov.model.entity.PostLike;

import java.util.List;

public interface PostLikeDao extends Dao<PostLike> {
    PostLike get(String nickname, String post);

    List<PostLike> getAllFromPost(String post);
}
