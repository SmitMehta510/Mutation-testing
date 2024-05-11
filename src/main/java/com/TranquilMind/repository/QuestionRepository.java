package com.TranquilMind.repository;

import com.TranquilMind.model.Question;
import com.TranquilMind.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Query("select q from Question q where q.answered = false ")
    List<Question> findUnansweredQuestions();

    @Query("select q from Question q where q.answered = true and q.isApprovedByModerator = false ")
    List<Question> findUnapprovedAnswers();

    @Query("select q from Question q where q.answered = true  and  q.isApprovedByModerator = true ")
    List<Question> approvedQuestions();

    List<Question> findByAnsweredBy(User user);

    @Query("select count(*) from Question ")
    Integer totalquestione();

    @Query("select count(*) from Question q where q.answered = false")
    Integer unansweredQuestionCount();

    @Query("select q from Question q where q.questionBy.userId= :userId")
    List<Question> getQuestionByUserId(@Param("userId") Long userId);
}
