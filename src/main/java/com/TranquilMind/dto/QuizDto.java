package com.TranquilMind.dto;

import com.TranquilMind.model.QuizType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizDto {

    Long patientId;

    QuizType quizType;

    Integer totalScore;

    String jsonQuizScores;
}
