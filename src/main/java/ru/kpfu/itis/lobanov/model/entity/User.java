package ru.kpfu.itis.lobanov.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String lastname;
    private String email;
    @Column(unique = true)
    private String login;
    private String password;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "about_me")
    private String aboutMe;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

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

    public User(String name, String lastname, @NonNull String email, @NonNull String login, @NonNull String password, String imageUrl, String aboutMe, Set<Role> roles) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.imageUrl = imageUrl;
        this.aboutMe = aboutMe;
        this.roles = roles;
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
