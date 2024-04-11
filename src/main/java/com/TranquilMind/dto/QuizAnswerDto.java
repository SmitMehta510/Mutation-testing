package com.TranquilMind.dto;

import com.TranquilMind.model.QuizQuestion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizAnswerDto {

    Long quizQuestionId;

    String answerOption;

    String score;
}
