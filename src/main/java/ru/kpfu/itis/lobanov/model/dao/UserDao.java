package ru.kpfu.itis.lobanov.model.dao;

import ru.kpfu.itis.lobanov.model.entity.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    User get(String nickname);
    User get(String login, String password);
    String getEmail(String login, String password);
    void updateImageUrl(String nickName, String imageUrl);
    void updatePassword(String nickName, String password);
}
