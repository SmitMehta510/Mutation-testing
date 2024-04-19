package com.TranquilMind.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String description;

    Double price;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String courseImage;

    CourseCategory category;

    Integer totalTask;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Task> tasks;

    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    @JsonIgnore
    List<EnrolledCourse> enrollments;
}
