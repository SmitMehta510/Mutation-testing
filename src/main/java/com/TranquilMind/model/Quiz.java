package com.TranquilMind.model;

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
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long quizId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    Patient patient;

    @ManyToOne
    @JoinColumn(name = "quiz_type_id")
    QuizType quizType;

    Integer totalScore;

    String jsonQuizScores;

}
