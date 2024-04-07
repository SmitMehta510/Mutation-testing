package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.PostDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Post;
import com.TranquilMind.model.User;
import com.TranquilMind.repository.PostRepository;
import com.TranquilMind.service.DoctorService;
import com.TranquilMind.service.PatientService;
import com.TranquilMind.service.PostService;
import com.TranquilMind.service.UserService;
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

    @Override
    public List<PostDto> getAllPostsByTime() {
        return postRepository.findAllByOrderByUploadedAtDesc()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id :" + id));
    }

    @Override
    public Post addPost(PostDto postDto) {
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
        return postRepository.save(post);
    }

    @Override
    public boolean deletePost(Long postId, Long userId) {

        Post post = getPostById(postId);

        if (post.getPostedBy().getUserId().equals(userId)) {
            postRepository.delete(post);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean disablePost(Long id) {
        return false;
    }

    @Override
    public Post editPost(Post post, Long userId) {

        Post savedPost = getPostById(post.getPostId());

        if (savedPost.getPostedBy().getUserId().equals(userId)) {
            savedPost.setDescription(post.getDescription());
            savedPost.setTitle(post.getTitle());
            return postRepository.save(savedPost);
        } else {
            return null;
        }
    }

    @Override
    public Boolean unflagPost(Long id) {

        Post post  = getPostById(id);

        if (post != null) {
            post.setIsApproved(true);
            post.setFlagged(0);

            postRepository.save(post);
            return true;
        }else
            return false;
    }

    @Override
    public List<PostDto> getFlaggedPosts() {
        return postRepository.findAll()
                .stream()
                .filter(p -> p.getFlagged()>0)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PostDto toDto(Post post){
        User user = post.getPostedBy();
        String name= "";
        if(user.getRoles().get(0).getRoleName().equals("PATIENT")){
            name = patientService.getPatientByUserId(user.getUserId()).getFirstName()
                    .concat(patientService.getPatientByUserId(user.getUserId()).getMiddleName())
                    .concat(patientService.getPatientByUserId(user.getUserId()).getLastName());
        }else if(user.getRoles().get(0).getRoleName().equals("DOCTOR")){
            name = doctorService.getDoctorByUserId(user.getUserId()).getFirstName()
                    .concat(doctorService.getDoctorByUserId(user.getUserId()).getMiddleName())
                    .concat(doctorService.getDoctorByUserId(user.getUserId()).getLastName());
        }
        return new PostDto(post.getTitle(), post.getDescription(),post.getPostedBy().getUserId(),name,post.getUploadedAt(),
                post.getImage(), post.getFlagged(), post.getComments(),post.getIsDisabled(),post.getIsApproved());
    }
}
