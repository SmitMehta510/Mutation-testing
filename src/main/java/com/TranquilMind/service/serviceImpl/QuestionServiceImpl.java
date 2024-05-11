package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.QuestionDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Patient;
import com.TranquilMind.model.Question;
import com.TranquilMind.model.User;
import com.TranquilMind.repository.QuestionRepository;
import com.TranquilMind.service.PatientService;
import com.TranquilMind.service.QuestionService;
import com.TranquilMind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getUnansweredQuestions() {
        return questionRepository.findUnansweredQuestions();
    }

    @Override
    public List<QuestionDto> getApprovedQuestions() {
        return questionRepository.approvedQuestions()
                .stream().map(question -> question.toDto(getUserFullName(question.getQuestionBy()))).toList();
    }

    @Override
    public List<Question> getUnapprovedAnswers() {
        return questionRepository.findUnapprovedAnswers();
    }

    @Override
    public List<Question> getAnsweredQuestionsByResponder(User user) {
        return questionRepository.findByAnsweredBy(user);
    }

    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(
                () -> new ResourceNotFoundException("Question not found with id "+ questionId));
    }

    @Override
    public Boolean addAnswer(QuestionDto questionDto, Long questionId) {

        Question question = findById(questionId);

        User user = userService.getUserById(questionDto.getAnsweredBy());

        if (question!= null && user != null) {
            question.setAnswer(questionDto.getAnswer());
            question.setAnsweredBy(user);
            question.setAnsweredAt(questionDto.getAnsweredAt());
            question.setIsApprovedByModerator(false);
            question.setAnswered(true);
            questionRepository.save(question);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Boolean approveAnswer(Long questionId) {

        Question question = findById(questionId);
        if (question != null) {
            question.setIsApprovedByModerator(true);
            questionRepository.save(question);
            return true;
        }else{
            return false;
        }
    }

    public List<Integer> questionData(){
        List<Integer> questionData = new ArrayList<>();
        questionData.add(questionRepository.totalquestione());
        questionData.add(questionRepository.unansweredQuestionCount());
        return questionData;
    }

    @Override
    public QuestionDto addQuestion(QuestionDto questionDto) {

        Question question = new Question();

        User user = userService.getUserById(questionDto.getQuestionBy());

        question.setQuestion(questionDto.getQuestion());
        question.setQuestionBy(user);
        question.setUploadedAt(questionDto.getUploadedAt());
        question.setAnswered(false);
        question.setIsApprovedByModerator(false);
        return questionRepository.save(question).toDto(null);

    }

    @Override
    public List<QuestionDto> getQuestionByUserId(Long userId) {
        Patient patient = patientService.getPatientByUserId(userId);
        return questionRepository.getQuestionByUserId(userId)
                .stream().map(question -> question.toDto(getUserFullName(question.getQuestionBy()))).toList();
    }

    private String getUserFullName(User user) {
        String fullName = "";
        String roleName = user.getRoles().get(0).getRoleName();

        Patient patient = patientService.getPatientByUserId(user.getUserId());
        fullName = concatFullName(patient.getFirstName(), patient.getMiddleName(), patient.getLastName());
        return fullName;
    }

    private String concatFullName(String firstName, String middleName, String lastName) {
        return firstName + " " + middleName + " " + lastName;
    }
}
