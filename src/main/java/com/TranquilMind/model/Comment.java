package com.TranquilMind.model;

import com.TranquilMind.dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long commentId;

    String description;

    Timestamp uploadedAt;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonBackReference
    Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User commentBy;

    public CommentDto toDto(){
        return new CommentDto(description,uploadedAt, post.getPostId(), commentBy.getUserId());
    }
}
