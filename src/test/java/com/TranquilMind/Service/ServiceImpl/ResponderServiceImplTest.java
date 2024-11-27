package com.TranquilMind.Service.ServiceImpl;

import com.TranquilMind.dto.*;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.*;
import com.TranquilMind.repository.ResponderRepository;
import com.TranquilMind.service.QuestionService;
import com.TranquilMind.service.UserService;
import com.TranquilMind.service.serviceImpl.ResponderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResponderServiceImplTest {

    @InjectMocks
    private ResponderServiceImpl responderService;

    @Mock
    private ResponderRepository responderRepository;

    @Mock
    private QuestionService questionService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllResponders_shouldReturnResponderList() {
        
        Responder responder = new Responder();
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setUserId(1L);
        responder.setUser(user);
        when(responderRepository.findAll()).thenReturn(List.of(responder));

        List<ResponderDto> result = responderService.getAllResponders();
        
        assertEquals(1, result.size());
        verify(responderRepository, times(1)).findAll();
    }

    @Test
    void addResponder_shouldAddResponderSuccessfully() {
        
        Responder responder = new Responder();
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        responder.setUser(user);

        AuthDto authDto = new AuthDto(user.getEmail(), user.getPassword());
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUser(user);

        when(userService.register(authDto, RoleName.RESPONDER)).thenReturn(registerDto);
        when(responderRepository.save(any(Responder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponderDto result = responderService.addResponder(responder);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(responderRepository, times(1)).save(any(Responder.class));
    }

    @Test
    void addResponder_shouldThrowExceptionWhenUserNotFound() {
        
        Responder responder = new Responder();
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        responder.setUser(user);

        AuthDto authDto = new AuthDto(user.getEmail(), user.getPassword());
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUser(null);

        when(userService.register(authDto, RoleName.RESPONDER)).thenReturn(registerDto);

        assertThrows(ResourceNotFoundException.class, () -> responderService.addResponder(responder));
        verify(responderRepository, never()).save(any());
    }

    @Test
    void getUnansweredQuestions_shouldReturnQuestionList() {
        
        Question question = new Question();
        when(questionService.getUnansweredQuestions()).thenReturn(List.of(question));
        
        List<Question> result = responderService.getUnansweredQuestions();

        assertEquals(1, result.size());
        verify(questionService, times(1)).getUnansweredQuestions();
    }

    @Test
    void getAnsweredQuestionsByResponder_shouldReturnQuestions() {
        
        Long responderId = 1L;
        User user = new User();
        when(userService.getUserById(responderId)).thenReturn(user);

        Question question = new Question();
        when(questionService.getAnsweredQuestionsByResponder(user)).thenReturn(List.of(question));
        
        List<Question> result = responderService.getAnsweredQuestionsByResponder(responderId);

        assertEquals(1, result.size());
        verify(questionService, times(1)).getAnsweredQuestionsByResponder(user);
    }

//    @Test
//    void addAnswer_shouldAddAnswerSuccessfully() {
//        
//        Long questionId = 1L;
//        QuestionDto questionDto = new QuestionDto();
//        when(questionService.addAnswer(questionDto, questionId)).thenReturn(true);
//
//        
//        Boolean result = responderService.addAnswer(questionDto, questionId);
//
//        
//        assertTrue(result);
//        verify(questionService, times(1)).addAnswer(questionDto, questionId);
//    }

    @Test
    void getResponderByUserId_shouldReturnResponder() {
        
        Long userId = 1L;
        Responder responder = new Responder();
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setUserId(1L);
        responder.setUser(user);
        when(responderRepository.findByUserId(userId)).thenReturn(Optional.of(responder));

        ResponderDto result = responderService.getResponderByUserId(userId);

        assertNotNull(result);
        verify(responderRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getResponderByUserId_shouldThrowExceptionWhenResponderNotFound() {
        
        Long userId = 1L;
        when(responderRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> responderService.getResponderByUserId(userId));
    }

    @Test
    void updatePassword_shouldUpdatePasswordAndReturnTrue() {
        
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setUserId(1L);

        Responder responder = new Responder();
        when(responderRepository.findByUserId(passwordDto.getUserId())).thenReturn(Optional.of(responder));
        when(userService.updatePassword(passwordDto)).thenReturn(true);

        boolean result = responderService.updatePassword(passwordDto);

        assertTrue(result);
        assertFalse(responder.isFirstLogin());
        verify(responderRepository, times(1)).save(responder);
        verify(userService, times(1)).updatePassword(passwordDto);
    }

    @Test
    void updatePassword_shouldThrowExceptionWhenResponderNotFound() {
        
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setUserId(1L);
        when(responderRepository.findByUserId(passwordDto.getUserId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> responderService.updatePassword(passwordDto));
        verify(responderRepository, never()).save(any());
        verify(userService, never()).updatePassword(any());
    }
}
