package com.TranquilMind.service.serviceImpl;


import com.TranquilMind.dto.AuthDto;
import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.dto.QuestionDto;
import com.TranquilMind.dto.ResponderDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.*;
import com.TranquilMind.repository.ResponderRepository;
import com.TranquilMind.service.QuestionService;
import com.TranquilMind.service.ResponderService;
import com.TranquilMind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public ResponderDto addResponder(Responder responder) {
        AuthDto authDto = new AuthDto(responder.getUser().getEmail(), responder.getUser().getPassword());
        User user = userService.register(authDto, RoleName.RESPONDER).getUser();
        if(user!=null){
            responder.setUser(user);
            responder.setFirstLogin(true);
            return responderRepository.save(responder).toDto();
        }else{
            throw new ResourceNotFoundException("User not found");
        }
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
    public Boolean addAnswer(QuestionDto questionDto, Long questionId) {
        return questionService.addAnswer(questionDto,questionId);
    }

    @Override
    public ResponderDto getResponderByUserId(Long id) {
        return responderRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responder not exist with id :" + id)).toDto();
    }

    @Override
    public boolean updatePassword(PasswordDto passwordDto) {
        Responder responder = responderRepository.findByUserId(passwordDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Responder not exist with id :" + passwordDto.getUserId()));
        responder.setFirstLogin(false);
        responderRepository.save(responder);
        return userService.updatePassword(passwordDto);
    }

}
