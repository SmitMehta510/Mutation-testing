package com.TranquilMind.service;

import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.dto.QuestionDto;
import com.TranquilMind.dto.ResponderDto;
import com.TranquilMind.model.Question;
import com.TranquilMind.model.Responder;


import java.util.List;

public interface ResponderService {

    List<ResponderDto> getAllResponders();

    ResponderDto addResponder(Responder responder);

    List<Question> getUnansweredQuestions();

    List<Question> getAnsweredQuestionsByResponder(Long id);

    Boolean addAnswer(QuestionDto questionDto, Long questionId);

    ResponderDto getResponderByUserId(Long id);

    boolean updatePassword(PasswordDto passwordDto);
}
