package com.TranquilMind.model;

import com.TranquilMind.dto.EnrollCourseDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrolledCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    Course course;

    Integer completed;

    public EnrollCourseDto toDto(){
        return new EnrollCourseDto(user.getUserId(),course.getCourseId(),completed);
    }
}
