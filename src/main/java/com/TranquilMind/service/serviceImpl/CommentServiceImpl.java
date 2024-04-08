package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.CommentDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Comment;
import com.TranquilMind.model.Post;
import com.TranquilMind.model.User;
import com.TranquilMind.repository.CommentRepository;
import com.TranquilMind.service.CommentService;
import com.TranquilMind.service.PostService;
import com.TranquilMind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Override
    public List<CommentDto> getCommentByPost(Post post) {
        return commentRepository.findAllByPost(post)
                .stream()
                .map(Comment::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> getCommentByUser(User user) {
        return List.of();
    }

    @Override
    public CommentDto addComment(CommentDto commentDto) {

        Comment comment = new Comment();
        User user = userService.getUserById(commentDto.getCommentById());
        Post post = postService.getPostById(commentDto.getPostId());

        comment.setCommentBy(user);
        comment.setPost(post);
        comment.setDescription(commentDto.getDescription());
        comment.setUploadedAt(commentDto.getUploadedAt());
        return commentRepository.save(comment).toDto();
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Boolean deleteComment(Comment comment) {
        Comment commentFromDb = getCommentById(comment.getCommentId());

        if (commentFromDb != null) {
            commentRepository.delete(commentFromDb);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id " + id));
    }
}
