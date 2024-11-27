package com.TranquilMind.model;

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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postId;

    String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String description;

    @ManyToOne
    @JoinColumn(name = "posted_by_id")
    User postedBy;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String image;

    Timestamp uploadedAt;

    Integer flagged;

    Boolean isDisabled;

    Boolean isApproved;
}