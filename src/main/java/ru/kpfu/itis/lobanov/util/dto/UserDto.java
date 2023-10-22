package ru.kpfu.itis.lobanov.util.dto;

public class UserDto {
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String password;
    private String imageUrl;
    private String aboutMe;

    public UserDto(String firstName, String lastName, String login, String email, String aboutMe) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.aboutMe = aboutMe;
    }

    public UserDto(String firstName, String lastName, String login, String email, String imageUrl, String aboutMe) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.imageUrl = imageUrl;
        this.aboutMe = aboutMe;
    }

    public UserDto(String firstName, String lastName, String login, String email, String password, String imageUrl, String aboutMe) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.aboutMe = aboutMe;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
