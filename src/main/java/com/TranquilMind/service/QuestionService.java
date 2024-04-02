package com.TranquilMind.service;

import com.TranquilMind.model.Question;
import com.TranquilMind.model.QuizType;
import com.TranquilMind.repository.QuestionRepository;

import java.util.List;

public interface QuestionService {

    List<Question> getAllQuestions(String quizType);

    Question addNewQuestion(Question question);

    Question editQuestion(Question question);
}
