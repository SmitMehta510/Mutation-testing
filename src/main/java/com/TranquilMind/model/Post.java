package com.TranquilMind.model;

import com.TranquilMind.dto.PostDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.parameters.P;

import java.sql.Timestamp;
import java.util.List;

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

    String description;

    @ManyToOne
    @JoinColumn(name = "posted_by_id")
    User postedBy;

    String image;

    Timestamp uploadedAt;

    Integer flagged;

    Boolean isDisabled;

    Boolean isApproved;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Comment> comments;
}