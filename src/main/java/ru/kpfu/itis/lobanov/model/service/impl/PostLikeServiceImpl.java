package ru.kpfu.itis.lobanov.model.service.impl;

import ru.kpfu.itis.lobanov.model.dao.PostLikeDao;
import ru.kpfu.itis.lobanov.model.dao.impl.PostLikeDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.PostLike;
import ru.kpfu.itis.lobanov.model.service.PostLikeService;
import ru.kpfu.itis.lobanov.util.dto.PostLikeDto;

public class PostLikeServiceImpl implements PostLikeService {
    private final PostLikeDao postLikeDao = new PostLikeDaoImpl();
    @Override
    public PostLikeDto get(int id) {
        PostLike postLike = postLikeDao.get(id);
        if (postLike == null) return null;
        return new PostLikeDto(postLike.getNickname(), postLike.getPost());
    }

    @Override
    public boolean isSet(String nickname, String post) {
        PostLike postLike = postLikeDao.get(nickname, post);
        return postLike != null;
    }

    @Override
    public int getAmountFromPost(String post) {
        return postLikeDao.getAllFromPost(post).size();
    }

    @Override
    public int getAmount() {
        return postLikeDao.getAll().size();
    }

    @Override
    public void save(PostLike postLike) {
        postLikeDao.save(postLike);
    }

    @Override
    public void remove(PostLike postLike) {
        PostLike like = postLikeDao.get(postLike.getNickname(), postLike.getPost());
        postLikeDao.remove(like);
    }
}
