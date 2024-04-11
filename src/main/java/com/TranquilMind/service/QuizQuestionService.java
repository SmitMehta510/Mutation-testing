package com.TranquilMind.service;

import com.TranquilMind.dto.QuizQuestionDto;
import com.TranquilMind.model.QuizQuestion;

import java.util.List;

public interface QuizQuestionService {

    List<QuizQuestion> getAllQuizQuestions(String quizType);

    QuizQuestion addNewQuizQuestion(QuizQuestionDto questionDto);

    QuizQuestion editQuizQuestion(QuizQuestion question);
}
