package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.CommentDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.*;
import com.TranquilMind.repository.CommentRepository;
import com.TranquilMind.service.*;
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

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Override
    public List<CommentDto> getCommentByPost(Post post) {
        return commentRepository.findAllByPost(post)
                .stream()
                .map(this::toDto)
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
        return toDto(commentRepository.save(comment));
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


    public CommentDto toDto(Comment comment){
        return new CommentDto(comment.getCommentId(),comment.getDescription(),comment.getUploadedAt(), comment.getPost().getPostId(),
                comment.getCommentBy().getUserId(),getUserFullName(comment.getCommentBy()));
    }


    public String getUserFullName(User user) {
        String fullName = "";
        String roleName = user.getRoles().get(0).getRoleName();

        if (roleName.equals("PATIENT")) {
            Patient patient = patientService.getPatientByUserId(user.getUserId());
            fullName = concatFullName(patient.getFirstName(), patient.getMiddleName(), patient.getLastName());
        } else if (roleName.equals("DOCTOR")) {
            Doctor doctor = doctorService.getDoctorByUserId(user.getUserId());
            fullName = concatFullName(doctor.getFirstName(), doctor.getMiddleName(), doctor.getLastName());
        }
        return fullName;
    }

    private String concatFullName(String firstName, String middleName, String lastName) {
        return firstName + " " + middleName + " " + lastName;
    }
}
