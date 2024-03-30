package com.TranquilMind.dto;

import com.TranquilMind.model.Comment;
import com.TranquilMind.model.PostType;
import com.TranquilMind.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {

    String title;
    String description;
    User postedBy;
    PostType postType;
    Timestamp uploadedAt;
    Integer flagged;
    List<Comment> comments;
    Boolean isDisabled;
    Boolean isApproved;
}
