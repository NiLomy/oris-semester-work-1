package ru.kpfu.itis.lobanov.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.model.entity.MessageLike;

import java.util.List;

@Repository
@Transactional
public interface MessageLikeRepository extends JpaRepository<MessageLike, Integer> {
    MessageLike findByAuthorAndMessageId(String author, int messageId);

    List<MessageLike> findAllByMessageId(int messageId);
}
