package ru.kpfu.itis.lobanov.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.model.entity.Message;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Integer> {
    Message findMessagesByAuthorIdAndContentAndPostAndDateAndLikes(int authorId, String content, String post, Timestamp date, int likes);

    List<Message> findAllByPost(String post);

    @Modifying
    @Query("update Message set likes = :likes where id = :id")
    void updateLikes(@Param("id") Integer id, @Param("likes") int likes);

    @Modifying
    @Query("select m.authorId from Message m group by m.authorId order by count(m) desc")
    List<Integer> getMostFrequentUserId();
}
