package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.model.QuizQuestion;
import com.TranquilMind.model.QuizType;
import com.TranquilMind.repository.QuizQuestionRepository;
import com.TranquilMind.repository.QuizTypeRepository;
import com.TranquilMind.service.QuizQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuizQuestionService {

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Autowired
    private QuizTypeRepository quizTypeRepository;


    @Override
    public List<QuizQuestion> getAllQuizQuestions(String quizName) {
        QuizType quizType = quizTypeRepository.findByQuizName(quizName);
        return quizQuestionRepository.findByQuizType(quizType);
    }

    @Override
    public QuizQuestion addNewQuizQuestion(QuizQuestion question) {
        return quizQuestionRepository.save(question);
    }

    @Override
    public QuizQuestion editQuizQuestion(QuizQuestion question) {
        return null;
    }
}
