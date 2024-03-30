package com.TranquilMind.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long courseId;

    String courseName;

    String description;

    Double price;

    CourseCategory category;

    Integer totalTask;

    @OneToMany(mappedBy = "course")
    List<Task> tasks;

    @OneToMany(mappedBy = "course")
    List<EnrolledCourse> enrollments;
}
