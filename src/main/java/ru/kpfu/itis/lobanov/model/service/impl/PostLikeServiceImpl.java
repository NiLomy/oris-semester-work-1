package ru.kpfu.itis.lobanov.model.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.model.entity.PostLike;
import ru.kpfu.itis.lobanov.model.repositories.PostLikeRepository;
import ru.kpfu.itis.lobanov.model.service.PostLikeService;
import ru.kpfu.itis.lobanov.util.dto.PostLikeDto;
import ru.kpfu.itis.lobanov.util.exception.PostLikeNotFoundException;

@Configuration
@Transactional
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {
    private final PostLikeRepository postLikeRepository;

    @Override
    public PostLikeDto get(int id) {
        PostLike postLike = postLikeRepository.findById(id).orElseThrow(PostLikeNotFoundException::new);
        if (postLike == null) return null;
        return new PostLikeDto(postLike.getNickname(), postLike.getPost());
    }

    @Override
    public boolean isSet(String nickname, String post) {
        PostLike postLike = postLikeRepository.findByNicknameAndPost(nickname, post);
        return postLike != null;
    }

    @Override
    public int getAmountFromPost(String post) {
        return postLikeRepository.findAllByPost(post).size();
    }

    @Override
    public int getAmount() {
        return postLikeRepository.findAll().size();
    }

    @Override
    public void save(PostLike postLike) {
        postLikeRepository.save(postLike);
    }

    @Override
    public void remove(PostLike postLike) {
        PostLike like = postLikeRepository.findByNicknameAndPost(postLike.getNickname(), postLike.getPost());
        postLikeRepository.delete(like);
    }
}
