package com.TranquilMind.repository;

import com.TranquilMind.model.Post;
import com.TranquilMind.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
//
//    @Query
//    List<Post> findpostsByTime();

    List<Post> findAllByOrderByUploadedAtDesc();

    List<Post> findByPostType(PostType postType);

}
