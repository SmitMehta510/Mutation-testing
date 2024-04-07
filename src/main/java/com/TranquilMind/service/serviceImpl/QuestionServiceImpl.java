package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.QuestionDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Question;
import com.TranquilMind.model.User;
import com.TranquilMind.repository.QuestionRepository;
import com.TranquilMind.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getUnansweredQuestions() {
        return questionRepository.findUnansweredQuestions();
    }

    @Override
    public List<Question> getAnsweredQuestions() {
        return questionRepository.findAnsweredQuestions();
    }

    @Override
    public List<Question> getAnsweredQuestionsByResponder(User user) {
        return questionRepository.findByAnsweredBy(user);
    }

    public Question findById(Long questionid) {
        return questionRepository.findById(questionid).orElseThrow(
                () -> new ResourceNotFoundException("Question not found with id "+ questionid));
    }

    @Override
    public Boolean addAnswer(QuestionDto questionDto, User answeredBy, Long questionId) {

        Question question = findById(questionId);

        if (question!= null) {
            question.setAnswer(questionDto.getAnswer());
            question.setAnsweredBy(answeredBy);
            question.setAnsweredAt(questionDto.getAnsweredAt());
            question.setIsApprovedByModerator(false);
            question.setAnswered(true);
            questionRepository.save(question);
            return true;
        }else{
            return false;
        }

    }
}
