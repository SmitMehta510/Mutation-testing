package com.TranquilMind.service;

import com.TranquilMind.dto.PostDto;
import com.TranquilMind.model.Post;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPostsByTime();

    Post getPostById(Long id);

    PostDto addPost(PostDto postDto);

    boolean deletePost(Long postId, Long UserId);

    boolean disablePost(Long id, Long userId);

    PostDto editPost(PostDto postDto, Long userId, Long postId);

    Boolean updatePost(Long id, Boolean unflag);

    List<PostDto> getFlaggedPosts();

}
