package ru.kpfu.itis.lobanov.model.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class User {
    private int id;
    private String name;
    private String lastname;
    private String email;
    private String login;
    private String password;
    private String imageUrl;
    private String aboutMe;

    public User(String name, String lastname, @NonNull String email, @NonNull String login) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.password = null;
    }

    public User(String name, String lastname, @NonNull String email, @NonNull String login, String aboutMe) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.aboutMe = aboutMe;
    }

    public User(String name, String lastname, @NonNull String email, @NonNull String login, @NonNull String password, String aboutMe) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.aboutMe = aboutMe;
    }

    public User(String name, String lastname, @NonNull String email, @NonNull String login, @NonNull String password, String imageUrl, String aboutMe) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.imageUrl = imageUrl;
        this.aboutMe = aboutMe;
    }

    public User(int id, String name, String lastname, @NonNull String email, @NonNull String login, @NonNull String password, String imageUrl, String aboutMe) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.imageUrl = imageUrl;
        this.aboutMe = aboutMe;
    }
}
