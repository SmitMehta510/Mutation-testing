package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.PostDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Post;
import com.TranquilMind.repository.PostRepository;
import com.TranquilMind.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<PostDto> getAllPostsByTime() {
        return postRepository.findAllByOrderByUploadedAtDesc()
                .stream()
                .map(Post::toDto)
                .collect(Collectors.toList());

    }

//    public List<DoctorDto> getAllDoctors() {
//        return doctorRepository.findAll()
//                .stream()
//                .map(Doctor::toDto)
//                .collect(Collectors.toList());
//    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id :"+ id));
    }

    @Override
    public Post addPost(PostDto postDto) {
        Post post = new Post();

        post.setPostedBy(postDto.getPostedBy());
        post.setDescription(postDto.getDescription());
        post.setPostType(postDto.getPostType());
        post.setTitle(postDto.getTitle());
        post.setFlagged(postDto.getFlagged());
        post.setIsApproved(false);
        post.setIsDisabled(false);
        post.setUploadedAt(postDto.getUploadedAt());
        return postRepository.save(post);
    }

    @Override
    public boolean deletePost(Long postId,Long userId) {

        Post post = getPostById(postId);

        if(post.getPostedBy().getUserId().equals(userId)){
            postRepository.delete(post);
            return true;
        }else{
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

        if(savedPost.getPostedBy().getUserId().equals(userId)){
            savedPost.setDescription(post.getDescription());
            savedPost.setTitle(post.getTitle());
            return postRepository.save(savedPost);
        }else{
            return null;
        }


    }
}
