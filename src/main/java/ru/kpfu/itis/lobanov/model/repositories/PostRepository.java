package ru.kpfu.itis.lobanov.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.model.entity.User;

import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findByNameAndAuthor(String name, User author);

    @Modifying
    @Query(value = "select * from posts where author_id = :userId", nativeQuery = true)
    List<Post> findAllByAuthor(@Param("userId") Integer userId);

    @Query(value = "SELECT post_id from users_posts where user_id = :userId", nativeQuery = true)
    List<Integer> findAllFavouritesByUser(@Param("userId") Integer userId);

    @Modifying
    @Query(value = "insert into users_posts (user_id, post_id) values (:userId, :postId)", nativeQuery = true)
    void saveToFavourites(@Param("userId") int userId, @Param("postId") int postId);

    @Modifying
    @Query(value = "delete from users_posts where user_id = :userId and post_id = :postId", nativeQuery = true)
    void removeFromFavourites(@Param("userId") int userId, @Param("postId") int postId);

    @Modifying
    @Query("update Post set likes = :likes where name = :name")
    void updateLikes(@Param("name") String name, @Param("likes") int likes);
}
