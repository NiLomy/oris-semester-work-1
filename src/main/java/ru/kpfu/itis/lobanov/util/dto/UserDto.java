package ru.kpfu.itis.lobanov.util.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String password;
    private String imageUrl;
    private String aboutMe;

    public UserDto(String firstName, String lastName, @NonNull String login, @NonNull String email, String aboutMe) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.aboutMe = aboutMe;
    }

    public UserDto(String firstName, String lastName, @NonNull String login, @NonNull String email, String imageUrl, String aboutMe) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.imageUrl = imageUrl;
        this.aboutMe = aboutMe;
    }

    public UserDto(String firstName, String lastName, @NonNull String login, @NonNull String email, @NonNull String password, String imageUrl, String aboutMe) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.aboutMe = aboutMe;
    }
}
