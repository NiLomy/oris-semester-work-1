package ru.kpfu.itis.lobanov.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.model.entity.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

    User findByLoginAndPassword(String login, String password);

    User findByEmailAndPassword(String email, String password);

    @Query("select u.email from User u where u.login = :login and u.password = :password")
    String findEmailByLoginAndPassword(@Param("login") String login, @Param("password") String password);

    @Modifying
    @Query("update User set imageUrl = :imageUrl where login = :login")
    void updateImageUrl(@Param("login") String login, @Param("imageUrl") String imageUrl);

    @Modifying
    @Query("update User set password = :password where login = :login")
    void updatePassword(@Param("login") String login, @Param("password") String password);

    @Modifying
    @Query(value = "insert into user_role (user_id, role_id) values (:userId, :roleId);", nativeQuery = true)
    void setRoles(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}
