package com.TranquilMind.repository;

import com.TranquilMind.model.QuizQuestion;
import com.TranquilMind.model.QuizType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Long> {

    List<QuizQuestion> findByQuizType(QuizType quizType);
}
