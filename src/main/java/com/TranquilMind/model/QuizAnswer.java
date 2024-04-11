package com.TranquilMind.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long quizAnswerId;

    @ManyToOne
    @JoinColumn(name = "quiz_question_id")
    @JsonBackReference
    QuizQuestion quizQuestion;

    String answerOption;

    String score;

}
