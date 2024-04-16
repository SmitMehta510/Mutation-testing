package com.TranquilMind.service;

import com.TranquilMind.dto.PostDto;
import com.TranquilMind.model.Post;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPostsByTime();

    Post getPostById(Long id);

    PostDto addPost(PostDto postDto);

    boolean deletePost(Long postId, Long UserId);

    boolean disablePost(Long id, Boolean disable);

    PostDto editPost(PostDto postDto, Long userId, Long postId);

    Boolean flagPost(Long id, Boolean flag);

    List<PostDto> getFlaggedPosts();

    List<Integer> postData();

    List<PostDto> getPostsByUserId(Long userId);
}
