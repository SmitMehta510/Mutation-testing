package com.TranquilMind.service;

import com.TranquilMind.dto.QuestionDto;
import com.TranquilMind.model.Question;
import com.TranquilMind.model.User;

import java.util.List;

public interface QuestionService {

    List<Question> getUnansweredQuestions();

    List<Question> getAnsweredQuestions();

    List<Question> getAnsweredQuestionsByResponder(User user);

    Boolean addAnswer(QuestionDto questionDto, User answeredBy, Long questionId);
}
