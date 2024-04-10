package com.TranquilMind.dto;


import com.TranquilMind.model.CourseCategory;
import com.TranquilMind.model.Task;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDto {

    String courseName;

    String description;

    Double price;

    CourseCategory category;

    Integer totalTask;

    List<TaskDto> tasks;
}
