package com.TranquilMind.repository;

import com.TranquilMind.model.Patient;
import com.TranquilMind.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {

    List<Quiz> findByPatient(Patient patient);
}
