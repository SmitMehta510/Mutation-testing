package com.TranquilMind.model;

import com.TranquilMind.dto.TaskDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String description;

    String link;

    @ManyToOne
    @JoinColumn(name = "course_id",nullable = false)
    @JsonBackReference
    Course course;

    public TaskDto toDto(){
        return new TaskDto(taskId, weekNo, taskNo,title, description, link);
    }


}
