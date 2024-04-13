package com.TranquilMind.repository;

import com.TranquilMind.model.Doctor;
import com.TranquilMind.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d FROM Doctor d WHERE d.user.userId = :userId")
    Optional<Doctor> findByUserId(@Param("userId") Long userId);

    @Query("select d from Doctor d where d.isDisabled = :isDisabled")
    List<Doctor> getDoctors(@Param("isDisabled") Boolean isDisabled);
}
