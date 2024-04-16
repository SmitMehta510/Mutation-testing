package com.TranquilMind.service;

import com.TranquilMind.dto.QuizDto;
import com.TranquilMind.model.Patient;
import com.TranquilMind.model.Quiz;
import com.TranquilMind.model.QuizType;

import java.util.List;

public interface QuizService {

    List<Quiz> getQuizScoresForPatient(Patient patient);

    Quiz newQuiz(QuizDto quizDto);

    List<QuizType> getQuizTypes();
}
