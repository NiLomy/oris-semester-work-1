package ru.kpfu.itis.lobanov.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.model.entity.PostLike;

import java.util.List;

@Repository
@Transactional
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    PostLike findByNicknameAndPost(String nickname, String pos);

    List<PostLike> findAllByPost(String post);
}
