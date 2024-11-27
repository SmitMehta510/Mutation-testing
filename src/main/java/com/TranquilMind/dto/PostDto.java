package com.TranquilMind.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
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
    Boolean isDisabled;
    Boolean isApproved;
}
