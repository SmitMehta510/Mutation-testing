package com.TranquilMind.repository;

import com.TranquilMind.model.Responder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponderRepository extends JpaRepository<Responder,Long> {

    @Query("SELECT r FROM Responder r WHERE r.user.userId = :userId")
    Optional<Responder> findByUserId(@Param("userId") Long userId);
}
