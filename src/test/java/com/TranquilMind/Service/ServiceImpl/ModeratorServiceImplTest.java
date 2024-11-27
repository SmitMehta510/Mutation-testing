package com.TranquilMind.Service.ServiceImpl;

import com.TranquilMind.dto.*;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.*;
import com.TranquilMind.repository.ModeratorRepository;
import com.TranquilMind.service.PostService;
import com.TranquilMind.service.QuestionService;
import com.TranquilMind.service.UserService;
import com.TranquilMind.service.serviceImpl.ModeratorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModeratorServiceImplTest {

    @InjectMocks
    private ModeratorServiceImpl moderatorService;

    @Mock
    private ModeratorRepository moderatorRepository;

    @Mock
    private PostService postService;

    @Mock
    private QuestionService questionService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllModerators_shouldReturnAllModerators() {
        
        Moderator moderator1 = new Moderator();
        User user1 = new User();
        user1.setEmail("test1@gmail.com");
        user1.setUserId(2L);
        moderator1.setUser(user1);
        Moderator moderator2 = new Moderator();
        User user2 = new User();
        user2.setEmail("test2@gmail.com");
        user2.setUserId(3L);
        moderator2.setUser(user2);
        when(moderatorRepository.findAll()).thenReturn(List.of(moderator1, moderator2));

        List<ModeratorDto> result = moderatorService.getAllModerators();

        assertEquals(2, result.size());
        verify(moderatorRepository, times(1)).findAll();
    }

//    @Test
//    void addModerator_shouldAddAndReturnModerator() {
//        
//        Moderator moderator = new Moderator();
//        User user = new User();
//        user.setEmail("test@gmail.com");
//        user.setUserId(2L);
//        Role role = new Role();
//        role.setRoleName(RoleName.MODERATOR);
//        user.setRoles(List.of(role));
//        moderator.setUser(user);
//
//        AuthDto authDto = new AuthDto("test@test.com", "password");
////        User user2 = new User();
////        user2.setUserId(1L);
//
//        RegisterDto registerDto = new RegisterDto();
//        registerDto.setUser(user);
//
//        when(userService.register(authDto, RoleName.MODERATOR)).thenReturn(registerDto);
//        when(moderatorRepository.save(any(Moderator.class))).thenAnswer(invocation -> {
//            Moderator savedModerator = invocation.getArgument(0);
//            savedModerator.setModeratorId(1L);
//            return savedModerator;
//        });
//
//        
//        ModeratorDto result = moderatorService.addModerator(moderator);
//
//        
//        assertNotNull(result);
//        assertEquals("test@gmail.com", result.getEmail());
//        verify(moderatorRepository, times(1)).save(any(Moderator.class));
//    }

//    @Test
//    void addModerator_shouldThrowExceptionWhenUserRegistrationFails() {
//        
//        Moderator moderator = new Moderator();
//        User user = new User();
//        user.setEmail("test@gmail.com");
//        user.setUserId(2L);
//        moderator.setUser(user);
//
//        AuthDto authDto = new AuthDto("test@test.com", "password");
//        when(userService.register(authDto, RoleName.MODERATOR)).thenReturn(null);
//
//         & Assert
//        assertThrows(ResourceNotFoundException.class, () -> moderatorService.addModerator(moderator));
//        verify(moderatorRepository, never()).save(any(Moderator.class));
//    }

    @Test
    void unflagPost_shouldCallPostServiceAndReturnResult() {
        
        Long postId = 1L;
        when(postService.flagPost(postId, false)).thenReturn(true);

        Boolean result = moderatorService.unflagPost(postId, false);

        assertTrue(result);
        verify(postService, times(1)).flagPost(postId, false);
    }

    @Test
    void disablePost_shouldCallPostServiceAndReturnResult() {
        
        Long postId = 1L;
        when(postService.disablePost(postId, true)).thenReturn(true);

        Boolean result = moderatorService.disablePost(postId, true);

        assertTrue(result);
        verify(postService, times(1)).disablePost(postId, true);
    }

    @Test
    void getFlaggedPosts_shouldReturnListOfFlaggedPosts() {
        
        when(postService.getFlaggedPosts()).thenReturn(List.of(new PostDto(), new PostDto()));

        List<PostDto> result = moderatorService.getFlaggedPosts();

        assertEquals(2, result.size());
        verify(postService, times(1)).getFlaggedPosts();
    }

    @Test
    void getAnsweredQuestions_shouldReturnUnapprovedAnswers() {
        
        when(questionService.getUnapprovedAnswers()).thenReturn(List.of(new Question(), new Question()));

        List<Question> result = moderatorService.getAnsweredQuestions();

        assertEquals(2, result.size());
        verify(questionService, times(1)).getUnapprovedAnswers();
    }

    @Test
    void approveAnswer_shouldCallQuestionServiceAndReturnResult() {
        
        Long questionId = 1L;
        when(questionService.approveAnswer(questionId)).thenReturn(true);

        Boolean result = moderatorService.approveAnswer(questionId);

        assertTrue(result);
        verify(questionService, times(1)).approveAnswer(questionId);
    }

    @Test
    void getModeratorByUserId_shouldReturnModeratorWhenFound() {
        
        Long userId = 1L;
        Moderator moderator = new Moderator();
        User user = new User();
        user.setEmail("test@test.com");
        moderator.setUser(user);
        moderator.setModeratorId(1L);

        when(moderatorRepository.findByUserId(userId)).thenReturn(Optional.of(moderator));

        ModeratorDto result = moderatorService.getModeratorByUserId(userId);

        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
        verify(moderatorRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getModeratorByUserId_shouldThrowExceptionWhenNotFound() {
        
        Long userId = 1L;
        when(moderatorRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> moderatorService.getModeratorByUserId(userId));
        verify(moderatorRepository, times(1)).findByUserId(userId);
    }

    @Test
    void updatePassword_shouldUpdatePasswordAndSetFirstLoginToFalse() {
        
        Long userId = 1L;
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setUserId(userId);
        passwordDto.setNewPassword("newPassword");

        Moderator moderator = new Moderator();
        moderator.setModeratorId(1L);
        moderator.setFirstLogin(true);

        when(moderatorRepository.findByUserId(userId)).thenReturn(Optional.of(moderator));
        when(userService.updatePassword(passwordDto)).thenReturn(true);

        boolean result = moderatorService.updatePassword(passwordDto);
        
        assertTrue(result);
        assertFalse(moderator.isFirstLogin());
        verify(moderatorRepository, times(1)).save(moderator);
        verify(userService, times(1)).updatePassword(passwordDto);
    }

    @Test
    void updatePassword_shouldThrowExceptionWhenModeratorNotFound() {
        
        Long userId = 1L;
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setUserId(userId);

        when(moderatorRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> moderatorService.updatePassword(passwordDto));
        verify(moderatorRepository, never()).save(any(Moderator.class));
    }
}
