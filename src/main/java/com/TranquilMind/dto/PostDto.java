package com.TranquilMind.dto;

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

    Long Id;
    String title;
    String description;
    Long postedBy;
    String name;
    Timestamp uploadedAt;
    String image;
    Integer flagged;
    List<CommentDto> comments;
    Boolean isDisabled;
    Boolean isApproved;
}
