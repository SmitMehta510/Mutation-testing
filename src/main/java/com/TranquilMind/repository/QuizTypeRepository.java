package com.TranquilMind.repository;

import com.TranquilMind.model.Quiz;
import com.TranquilMind.model.QuizType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizTypeRepository extends JpaRepository<QuizType,Long> {

    QuizType findByQuizName(String name);
}
