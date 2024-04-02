package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.model.Question;
import com.TranquilMind.model.QuizType;
import com.TranquilMind.repository.QuestionRepository;
import com.TranquilMind.repository.QuizTypeRepository;
import com.TranquilMind.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizTypeRepository quizTypeRepository;


    @Override
    public List<Question> getAllQuestions(String quizName) {
        QuizType quizType = quizTypeRepository.findByQuizName(quizName);
        return questionRepository.findByQuizType(quizType);
    }

    @Override
    public Question addNewQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question editQuestion(Question question) {
        return null;
    }
}
