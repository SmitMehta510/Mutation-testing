package com.TranquilMind.service;

import com.TranquilMind.dto.QuestionDto;
import com.TranquilMind.dto.ResponderDto;
import com.TranquilMind.model.Post;
import com.TranquilMind.model.Question;
import com.TranquilMind.model.Responder;

import java.util.List;

public interface ResponderService {

    List<ResponderDto> getAllResponders();

    List<Question> getUnansweredQuestions();

    List<Question> getAnsweredQuestionsByResponder(Long id);

    Boolean addAnswer(QuestionDto questionDto, Long answeredById, Long questionId);
}
