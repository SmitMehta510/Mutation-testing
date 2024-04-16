package com.TranquilMind.repository;

import com.TranquilMind.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select p from Post p where p.isDisabled = false order by p.uploadedAt desc ")
    List<Post> getPosts();

    @Query("select  count(*) from Post")
    Integer totalPostCount();

    @Query("select count(*) from Post p where p.flagged !=0")
    Integer flaggedPostCount();

    @Query("select p from Post p where p.postedBy.userId= :userId")
    List<Post> getPostsByUserId(@Param("userId") Long userId);
}
