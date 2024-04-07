package com.TranquilMind.repository;

import com.TranquilMind.model.Question;
import com.TranquilMind.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Query("select q from Question q where q.answered = false ")
    List<Question> findUnansweredQuestions();

    @Query("select q from Question q where q.answered = true ")
    List<Question> findAnsweredQuestions();

    List<Question> findByAnsweredBy(User user);

}
