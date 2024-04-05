package com.TranquilMind.service;

import com.TranquilMind.model.QuizQuestion;

import java.util.List;

public interface QuizQuestionService {

    List<QuizQuestion> getAllQuizQuestions(String quizType);

    QuizQuestion addNewQuizQuestion(QuizQuestion question);

    QuizQuestion editQuizQuestion(QuizQuestion question);
}
