package com.TranquilMind.service;

import com.TranquilMind.dto.PostDto;
import com.TranquilMind.model.Post;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPostsByTime();

    Post getPostById(Long id);

    Post addPost(PostDto postDto);

    boolean deletePost(Long postId, Long UserId);

    boolean disablePost(Long id);

    Post editPost(Post post, Long userId);

    Boolean unflagPost(Long id);

    List<PostDto> getFlaggedPosts();

}
