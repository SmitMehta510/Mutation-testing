package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.QuizDto;
import com.TranquilMind.model.Patient;
import com.TranquilMind.model.Quiz;
import com.TranquilMind.model.QuizType;
import com.TranquilMind.repository.QuizRepository;
import com.TranquilMind.repository.QuizTypeRepository;
import com.TranquilMind.service.PatientService;
import com.TranquilMind.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private QuizTypeRepository quizTypeRepository;

    @Override
    public List<Quiz> getQuizScoresForPatient(Patient patient) {
        return quizRepository.findByPatient(patient);
    }

    @Override
    public Quiz newQuiz(QuizDto quizDto) {
        Patient patient = patientService.getPatientByUserId(quizDto.getPatientId());
        Quiz quiz = new Quiz();
        quiz.setQuizType(quizDto.getQuizType());
        quiz.setJsonQuizScores(quizDto.getJsonQuizScores());
        quiz.setPatient(patient);
        quiz.setTotalScore(quizDto.getTotalScore());
        return quizRepository.save(quiz);
    }

    @Override
    public List<QuizType> getQuizTypes() {
        return quizTypeRepository.findAll();
    }
}
