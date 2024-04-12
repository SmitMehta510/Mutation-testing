package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.ModeratorDto;
import com.TranquilMind.dto.QuestionDto;
import com.TranquilMind.dto.ResponderDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.*;
import com.TranquilMind.repository.QuestionRepository;
import com.TranquilMind.repository.ResponderRepository;
import com.TranquilMind.service.PostService;
import com.TranquilMind.service.QuestionService;
import com.TranquilMind.service.ResponderService;
import com.TranquilMind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResponderServiceImpl implements ResponderService {

    @Autowired
    private ResponderRepository responderRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Override
    public List<ResponderDto> getAllResponders() {
        return responderRepository.findAll()
                .stream()
                .map(Responder::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> getUnansweredQuestions() {
        return questionService.getUnansweredQuestions();
    }

    @Override
    public List<Question> getAnsweredQuestionsByResponder(Long id) {
        User user = userService.getUserById(id);
        return questionService.getAnsweredQuestionsByResponder(user);
    }

    @Override
    public Boolean addAnswer(QuestionDto questionDto, Long answeredById, Long questionId) {

        User user = userService.getUserById(answeredById);

        return questionService.addAnswer(questionDto,user,questionId);
    }

    @Override
    public ResponderDto getResponderByUserId(Long id) {
        return responderRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responder not exist with id :" + id)).toDto();
    }

}
