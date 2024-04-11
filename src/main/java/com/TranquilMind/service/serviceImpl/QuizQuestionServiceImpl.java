package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.QuizQuestionDto;
import com.TranquilMind.model.QuizAnswer;
import com.TranquilMind.model.QuizQuestion;
import com.TranquilMind.model.QuizType;
import com.TranquilMind.repository.QuizAnswerRepository;
import com.TranquilMind.repository.QuizQuestionRepository;
import com.TranquilMind.repository.QuizTypeRepository;
import com.TranquilMind.service.QuizQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizQuestionServiceImpl implements QuizQuestionService {

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Autowired
    private QuizTypeRepository quizTypeRepository;

    @Autowired
    private QuizAnswerRepository quizAnswerRepository;


    @Override
    public List<QuizQuestion> getAllQuizQuestions(String quizName) {
        QuizType quizType = quizTypeRepository.findByQuizName(quizName);
        return quizQuestionRepository.findByQuizType(quizType);
    }

    @Override
    public QuizQuestion addNewQuizQuestion(QuizQuestionDto quizQuestionDto) {
        QuizQuestion quizQuestion = new QuizQuestion();

        QuizType quizType = quizTypeRepository.findByQuizName(quizQuestionDto.getQuizType());

        quizQuestion.setQuizType(quizType);
        quizQuestion.setDescription(quizQuestionDto.getDescription());
        quizQuestion.setOptions(null);
        QuizQuestion savedQuizQuestion = quizQuestionRepository.save(quizQuestion);

        List<QuizAnswer> quizAnswers = new ArrayList<>();
        quizQuestionDto.getOptions().forEach(option -> {
            QuizAnswer quizAnswer = new QuizAnswer();
            quizAnswer.setQuizQuestion(savedQuizQuestion);
            quizAnswer.setAnswerOption(option.getAnswerOption());
            quizAnswer.setScore(option.getScore());
            quizAnswers.add(quizAnswer);
            quizAnswerRepository.save(quizAnswer);
        });
        savedQuizQuestion.setOptions(quizAnswers);
        return quizQuestionRepository.save(savedQuizQuestion);
    }

    @Override
    public QuizQuestion editQuizQuestion(QuizQuestion question) {
        return null;
    }
}
