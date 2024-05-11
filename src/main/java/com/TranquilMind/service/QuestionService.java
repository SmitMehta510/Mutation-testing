package com.TranquilMind.service;

import com.TranquilMind.dto.QuestionDto;
import com.TranquilMind.model.Question;
import com.TranquilMind.model.User;

import java.util.List;

public interface QuestionService {

    List<Question> getUnansweredQuestions();

    List<QuestionDto> getApprovedQuestions();

    List<Question> getUnapprovedAnswers();

    List<Question> getAnsweredQuestionsByResponder(User user);

    Boolean addAnswer(QuestionDto questionDto, Long questionId);

    Boolean approveAnswer(Long questionId);

    List<Integer> questionData();

    QuestionDto addQuestion(QuestionDto questionDto);

    List<QuestionDto> getQuestionByUserId(Long userId);
}
