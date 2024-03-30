package com.TranquilMind.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long taskId;

    Integer weekNo;

    Integer taskNo;

    String description;

    String link;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;
}
