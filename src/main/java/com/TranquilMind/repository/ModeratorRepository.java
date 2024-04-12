package com.TranquilMind.repository;

import com.TranquilMind.model.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator,Long> {

    @Query("SELECT m FROM Moderator m WHERE m.user.userId = :userId")
    Optional<Moderator> findByUserId(@Param("userId") Long userId);
}
