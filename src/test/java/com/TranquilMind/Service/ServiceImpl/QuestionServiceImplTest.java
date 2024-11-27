package com.TranquilMind.Service.ServiceImpl;

import com.TranquilMind.dto.QuestionDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Question;
import com.TranquilMind.model.User;
import com.TranquilMind.repository.QuestionRepository;
import com.TranquilMind.service.PatientService;
import com.TranquilMind.service.UserService;
import com.TranquilMind.service.serviceImpl.QuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserService userService;

    @Mock
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnAllQuestions() {
        
        when(questionRepository.findAll()).thenReturn(List.of(new Question(), new Question()));

        
        List<Question> result = questionService.findAll();

        
        assertEquals(2, result.size());
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    void getUnansweredQuestions_shouldReturnUnansweredQuestions() {
        
        when(questionRepository.findUnansweredQuestions()).thenReturn(List.of(new Question()));

        
        List<Question> result = questionService.getUnansweredQuestions();

        
        assertEquals(1, result.size());
        verify(questionRepository, times(1)).findUnansweredQuestions();
    }

    @Test
    void addAnswer_shouldUpdateAnswerAndReturnTrue() {
        
        Long questionId = 1L;
        Question question = new Question();
        question.setQuestionId(questionId);

        QuestionDto questionDto = new QuestionDto();
        questionDto.setAnsweredBy(2L);
        questionDto.setAnswer("Sample Answer");
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        questionDto.setAnsweredAt(time);

        User user = new User();

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(userService.getUserById(questionDto.getAnsweredBy())).thenReturn(user);

        
        Boolean result = questionService.addAnswer(questionDto, questionId);

        
        assertTrue(result);
        assertEquals("Sample Answer", question.getAnswer());
        assertEquals(user, question.getAnsweredBy());
        assertFalse(question.getIsApprovedByModerator());
        assertTrue(question.getAnswered());
        assertEquals(time, question.getAnsweredAt());
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    void addAnswer_shouldThrowExceptionWhenQuestionOrUserNotFound() {
        
        Long questionId = 1L;
        QuestionDto questionDto = new QuestionDto();
        questionDto.setAnsweredBy(2L);
        questionDto.setQuestionBy(5L);

        when(questionRepository.findById(questionId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> questionService.addAnswer(questionDto, questionId));
        verify(questionRepository, never()).save(any());
    }

    @Test
    void approveAnswer_shouldSetApprovedByModerator() {
        
        Long questionId = 1L;
        Question question = new Question();
        question.setQuestionId(questionId);
        question.setIsApprovedByModerator(false);

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        
        Boolean result = questionService.approveAnswer(questionId);

        
        assertTrue(result);
        assertTrue(question.getIsApprovedByModerator());
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    void approveAnswer_shouldThrowExceptionWhenQuestionNotFound() {
        
        Long questionId = 1L;
        when(questionRepository.findById(questionId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> questionService.approveAnswer(questionId));
        verify(questionRepository, never()).save(any());
    }

    @Test
    void addQuestion_shouldCreateAndReturnQuestionDto() {
        
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestion("New Question");
        questionDto.setQuestionBy(2L);
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        questionDto.setUploadedAt(time);

        User user = new User();
        when(userService.getUserById(questionDto.getQuestionBy())).thenReturn(user);

        Question question = new Question();
        when(questionRepository.save(any(Question.class))).thenAnswer(invocation -> {
            Question savedQuestion = invocation.getArgument(0);
            savedQuestion.setQuestionId(1L);
            return savedQuestion;
        });

        
        QuestionDto result = questionService.addQuestion(questionDto);

        
        assertNotNull(result);
        assertEquals("New Question", result.getQuestion());
        assertFalse(result.getIsApprovedByModerator());
        assertFalse(result.getAnswered());
        assertEquals(time, result.getUploadedAt());
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    void getQuestionByUserId_shouldReturnQuestionsForUser() {
        
        Long userId = 2L;

        Question question = new Question();
        User user = new User();
        user.setUserId(userId);
        question.setQuestionBy(user);
        when(questionRepository.getQuestionByUserId(userId)).thenReturn(List.of(question));

        
        List<QuestionDto> result = questionService.getQuestionByUserId(userId);

        
        assertEquals(1, result.size());
        verify(questionRepository, times(1)).getQuestionByUserId(userId);
    }

    @Test
    void questionData_shouldReturnQuestionCounts() {
        
        when(questionRepository.totalquestione()).thenReturn(10);
        when(questionRepository.unansweredQuestionCount()).thenReturn(3);

        
        List<Integer> result = questionService.questionData();

        
        assertEquals(2, result.size());
        assertEquals(10, result.get(0));
        assertEquals(3, result.get(1));
        verify(questionRepository, times(1)).totalquestione();
        verify(questionRepository, times(1)).unansweredQuestionCount();
    }

    @Test
    void findById_shouldReturnQuestionWhenFound() {
        
        Long questionId = 1L;
        Question question = new Question();
        question.setQuestionId(questionId);

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        
        Question result = questionService.findById(questionId);

        
        assertNotNull(result);
        assertEquals(questionId, result.getQuestionId());
        verify(questionRepository, times(1)).findById(questionId);
    }

    @Test
    void findById_shouldThrowExceptionWhenNotFound() {
        
        Long questionId = 1L;
        when(questionRepository.findById(questionId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> questionService.findById(questionId));
        verify(questionRepository, times(1)).findById(questionId);
    }
}
