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
public class QuizAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long answerId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    Question question;

    String answerOption;

}
