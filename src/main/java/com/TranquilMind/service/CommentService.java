package com.TranquilMind.service;

import com.TranquilMind.dto.CommentDto;
import com.TranquilMind.model.Comment;
import com.TranquilMind.model.Post;
import com.TranquilMind.model.User;

import java.util.List;

public interface CommentService {

    List<CommentDto> getCommentByPost(Post post);

    List<Comment> getCommentByUser(User user);
    CommentDto addComment(CommentDto commentDto);
    Comment updateComment(Comment comment);
    Boolean deleteComment(Comment comment);

    Comment getCommentById(Long id);

}
