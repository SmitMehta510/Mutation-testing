package com.TranquilMind.repository;

import com.TranquilMind.model.Question;
import com.TranquilMind.model.QuizType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findByQuizType(QuizType quizType);
}
