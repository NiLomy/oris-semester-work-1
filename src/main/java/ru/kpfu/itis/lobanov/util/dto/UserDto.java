package ru.kpfu.itis.lobanov.util.dto;

public class UserDto {
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String password;

    public UserDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public UserDto(String firstName, String lastName, String login, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
    }

    public UserDto(String name, String lastname, String login, String email, String password) {
        this.firstName = name;
        this.lastName = lastname;
        this.login = login;
        this.email = email;
        this.password = password;
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
}
