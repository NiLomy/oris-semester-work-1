package ru.kpfu.itis.lobanov.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "likes_for_messages")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String author;
    @Column(name = "message_id")
    private int messageId;

    public MessageLike(@NonNull String author, int messageId) {
        this.author = author;
        this.messageId = messageId;
    }
}
