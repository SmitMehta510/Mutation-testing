package com.TranquilMind.service;

import com.TranquilMind.dto.QuizDto;
import com.TranquilMind.model.Quiz;
import com.TranquilMind.model.QuizType;

import java.util.List;

public interface QuizService {

    List<Quiz> getQuizScoresForPatient(Long id);

    Quiz newQuiz(QuizDto quizDto);

    List<QuizType> getQuizTypes();
}
