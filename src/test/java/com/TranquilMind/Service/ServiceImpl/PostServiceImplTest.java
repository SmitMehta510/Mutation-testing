package com.TranquilMind.Service.ServiceImpl;

import com.TranquilMind.dto.PostDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.*;
import com.TranquilMind.repository.PostRepository;
import com.TranquilMind.service.PatientService;
import com.TranquilMind.service.UserService;
import com.TranquilMind.service.serviceImpl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PatientService patientService;


    @Mock
    private UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPostsByTime_shouldReturnAllPosts() {
        
        Post post = new Post();
        User user = new User();
        user.setUserId(2L);
        post.setPostedBy(user);
        when(postRepository.getPosts()).thenReturn(List.of(post));

        List<PostDto> result = postService.getAllPostsByTime();

        assertEquals(1, result.size());
        verify(postRepository, times(1)).getPosts();
    }

    @Test
    void getPostById_shouldReturnPostWhenExists() {
        
        Long postId = 1L;
        Post post = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Post result = postService.getPostById(postId);

        assertNotNull(result);
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void getPostById_shouldThrowExceptionWhenPostDoesNotExist() {
        
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.getPostById(postId));
    }

    @Test
    void addPost_shouldAddPostSuccessfully() {
        
        PostDto postDto = new PostDto();
        postDto.setPostedBy(1L);
        postDto.setTitle("New Post");
        postDto.setUploadedAt(Timestamp.valueOf(LocalDateTime.now()));

        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setMiddleName("Smith");
        User user = new User();
        Role role = new Role();
        role.setRoleName(RoleName.PATIENT);
        user.setRoles(List.of(role));
        when(userService.getUserById(1L)).thenReturn(user);
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(patientService.getPatientByUserId(1L)).thenReturn(patient);
        
        PostDto result = postService.addPost(postDto);
        
        assertEquals("New Post", result.getTitle());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void deletePost_shouldDeletePostWhenExists() {
        
        Long postId = 1L;
        Long userId = 1L;

        Post post = new Post();
        User user = new User();
        user.setUserId(userId);
        post.setPostedBy(user);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        
        boolean result = postService.deletePost(postId, userId);
        
        assertTrue(result);
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void deletePost_shouldReturnFalseIfUserIsNotAuthorized() {
     
        Long postId = 1L;
        Long userId = 2L;

        Post post = new Post();
        User user = new User();
        user.setUserId(1L);
        post.setPostedBy(user);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        
        boolean result = postService.deletePost(postId, userId);
        
        assertFalse(result);
        verify(postRepository, never()).delete(post);
    }

    @Test
    void disablePost_shouldDisablePostWhenExists() {
        
        Long postId = 1L;
        Post post = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        boolean result = postService.disablePost(postId, true);
        
        assertTrue(result);
        assertTrue(post.getIsDisabled());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void disablePost_shouldThrowExceptionWhenPostDoesNotExist() {
        
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> postService.disablePost(postId,true));
    }

    @Test
    void editPost_shouldEditPostWhenUserAuthorized() {
        
        Long userId = 1L;
        Long postId = 1L;

        Post post = new Post();
        User user = new User();
        user.setUserId(userId);
        post.setPostedBy(user);

        PostDto postDto = new PostDto();
        postDto.setTitle("Updated Title");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        PostDto result = postService.editPost(postDto, userId, postId);
        
        assertEquals("Updated Title", result.getTitle());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void editPost_shouldReturnNullWhenUserUnauthorized() {
        
        Long userId = 2L;
        Long postId = 1L;

        Post post = new Post();
        User user = new User();
        user.setUserId(1L);
        post.setPostedBy(user);

        PostDto postDto = new PostDto();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        
        PostDto result = postService.editPost(postDto, userId, postId);

        assertNull(result);
        verify(postRepository, never()).save(any());
    }

    @Test
    void flagPost_shouldIncrementFlagCount() {
        
        Long postId = 1L;
        Post post = new Post();
        post.setFlagged(0);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Boolean result = postService.flagPost(postId, true);

        assertTrue(result);
        assertEquals(1, post.getFlagged());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void getFlaggedPosts_shouldReturnFlaggedPosts() {
        
        Post post = new Post();
        post.setFlagged(1);
        post.setIsDisabled(false);

        User user = new User();
        user.setUserId(2L);
        post.setPostedBy(user);

        when(postRepository.findAll()).thenReturn(List.of(post));

        List<PostDto> result = postService.getFlaggedPosts();
        
        assertEquals(1, result.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void postData_shouldReturnPostStatistics() {
        
        when(postRepository.totalPostCount()).thenReturn(10);
        when(postRepository.flaggedPostCount()).thenReturn(2);
        
        List<Integer> result = postService.postData();
        
        assertEquals(2, result.size());
        assertEquals(10, result.get(0));
        assertEquals(2, result.get(1));
    }

    @Test
    void getPostsByUserId_shouldReturnPostsForSpecificUser() {
        
        Long userId = 1L;
        Post post = new Post();
        User user = new User();
        user.setUserId(userId);
        post.setPostedBy(user);
        when(postRepository.getPostsByUserId(userId)).thenReturn(List.of(post));
        
        List<PostDto> result = postService.getPostsByUserId(userId);

        assertEquals(1, result.size());
        verify(postRepository, times(1)).getPostsByUserId(userId);
    }
}
