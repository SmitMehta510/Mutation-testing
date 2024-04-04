package com.TranquilMind.model;

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
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long questionId;

    @ManyToOne
    @JoinColumn(name = "quiz_type_id")
    QuizType quizType;

    String description;

    @OneToMany(mappedBy = "question")
    @JsonManagedReference
    List<QuizAnswers> options;

}
