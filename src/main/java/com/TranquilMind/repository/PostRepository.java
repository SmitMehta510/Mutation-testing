package com.TranquilMind.repository;

import com.TranquilMind.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
//
//    @Query
//    List<Post> findpostsByTime();

    List<Post> findAllByOrderByUploadedAtDesc();

}
