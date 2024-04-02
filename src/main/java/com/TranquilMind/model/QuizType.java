package com.TranquilMind.model;

//public enum QuizType {
//
//    GAD7,
//    PHQ9
//}

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizType{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long quizTypeId;

    String quizName;
}