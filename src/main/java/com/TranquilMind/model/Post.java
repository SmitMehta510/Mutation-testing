package com.TranquilMind.model;

import com.TranquilMind.dto.PostDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    List<Comment> comments;
}