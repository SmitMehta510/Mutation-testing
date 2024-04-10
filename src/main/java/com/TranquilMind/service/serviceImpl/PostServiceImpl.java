package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.PostDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Doctor;
import com.TranquilMind.model.Patient;
import com.TranquilMind.model.Post;
import com.TranquilMind.model.User;
import com.TranquilMind.repository.CommentRepository;
import com.TranquilMind.repository.PostRepository;
import com.TranquilMind.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Override
    public List<PostDto> getAllPostsByTime() {
        return postRepository.findAllByOrderByUploadedAtDesc()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id :" + id));
    }

    @Override
    public PostDto addPost(PostDto postDto) {
        Post post = new Post();

        User user = userService.getUserById(postDto.getPostedBy());

        post.setPostedBy(user);
        post.setImage(post.getImage());
        post.setDescription(postDto.getDescription());
        post.setUploadedAt(postDto.getUploadedAt());
        post.setTitle(postDto.getTitle());
        post.setFlagged(postDto.getFlagged());
        post.setIsApproved(false);
        post.setIsDisabled(false);
        post.setUploadedAt(postDto.getUploadedAt());
        return toDto(postRepository.save(post));
    }

    @Override
    public boolean deletePost(Long postId, Long userId) {

        Post post = getPostById(postId);
        if (post != null) {
            if (post.getPostedBy().getUserId().equals(userId)) {
                postRepository.delete(post);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean disablePost(Long id,Long userId) {
//        Post post = getPostById(id);
//
//        if (post != null) {
//            if(post)
//        }
//
//        return ;
        return false;
    }

    @Override
    public PostDto editPost(PostDto postDto, Long userId, Long postId) {

        Post savedPost = getPostById(postId);

        if (savedPost.getPostedBy().getUserId().equals(userId)) {
            savedPost.setDescription(postDto.getDescription());
            savedPost.setTitle(postDto.getTitle());
            return toDto(postRepository.save(savedPost));
        } else {
            return null;
        }
    }

    @Override
    public Boolean updatePost(Long id, Boolean unflag) {

        Post post = getPostById(id);

        if (post != null) {
            if (unflag) {
                post.setIsApproved(true);
                post.setFlagged(0);
            } else {
                post.setIsApproved(false);
                post.setIsDisabled(true);
            }

            postRepository.save(post);
            return true;
        } else
            return false;
    }

    @Override
    public List<PostDto> getFlaggedPosts() {
        return postRepository.findAll()
                .stream()
                .filter(p -> p.getFlagged() > 0)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PostDto toDto(Post post) {
        User user = post.getPostedBy();
        String name = getUserFullName(user);

        return new PostDto(post.getTitle(), post.getDescription(), user.getUserId(), name,
                post.getUploadedAt(), post.getImage(), post.getFlagged(), commentService.getCommentByPost(post),
                post.getIsDisabled(), post.getIsApproved());
    }

    private String getUserFullName(User user) {
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
