package ru.kpfu.itis.lobanov.model.entity;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String lastname;
    private String email;
    private String login;
    private String password;
    private String imageUrl;
    private String aboutMe;

    public User(String name, String lastname, String email, String login) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.password = null;
    }

    public User(String name, String lastname, String email, String login, String aboutMe) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.aboutMe = aboutMe;
    }

    public User(String name, String lastname, String email, String login, String password, String aboutMe) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.aboutMe = aboutMe;
    }

    public User(String name, String lastname, String email, String login, String password, String imageUrl, String aboutMe) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.imageUrl = imageUrl;
        this.aboutMe = aboutMe;
    }

    public User(int id, String name, String lastname, String email, String login, String password, String imageUrl, String aboutMe) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.imageUrl = imageUrl;
        this.aboutMe = aboutMe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!Objects.equals(name, user.name)) return false;
        if (!Objects.equals(lastname, user.lastname)) return false;
        if (!email.equals(user.email)) return false;
        if (!login.equals(user.login)) return false;
        return Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
}
