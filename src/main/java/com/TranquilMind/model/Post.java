package com.TranquilMind.model;

import com.TranquilMind.dto.PostDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    //TODO check for image save
//    Byte[] image;

    @Enumerated(EnumType.STRING)
    PostType postType;

    Timestamp uploadedAt;

    Integer flagged;

    Boolean isDisabled;

    Boolean isApproved;

    String answer;

    @ManyToOne
    @JoinColumn(name = "answer_by_id")
    User answeredBy;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Comment> comments;

    public static PostDto toDto(Post post){
        return new PostDto(post.title,post.description,post.postedBy,post.postType,post.uploadedAt,
                post.flagged,post.comments,post.isDisabled,post.isApproved);
    }
}