package com.TranquilMind.Service.ServiceImpl;

import com.TranquilMind.dto.AuthDto;
import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.dto.RegisterDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Role;
import com.TranquilMind.model.RoleName;
import com.TranquilMind.model.User;
import com.TranquilMind.repository.RoleRepository;
import com.TranquilMind.repository.UserRepository;
import com.TranquilMind.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updatePassword_shouldReturnTrueWhenUserExists() {
        // Arrange
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setUserId(1L);
        User user = new User();
        when(userRepository.findById(passwordDto.getUserId())).thenReturn(Optional.of(user));

        // Act
        boolean result = userService.updatePassword(passwordDto);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).findById(passwordDto.getUserId());
    }

    @Test
    void updatePassword_shouldReturnFalseWhenUserDoesNotExist() {
        // Arrange
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setUserId(1L);
        when(userRepository.findById(passwordDto.getUserId())).thenReturn(Optional.empty());

        // Act
        boolean result = userService.updatePassword(passwordDto);

        // Assert
        assertFalse(result);
        verify(userRepository, times(1)).findById(passwordDto.getUserId());
    }

    @Test
    void register_shouldReturnConflictWhenEmailExists() {
        // Arrange
        AuthDto authDto = new AuthDto("test@test.com", "password");
        when(userRepository.existsByEmail(authDto.getEmail())).thenReturn(true);

        // Act
        RegisterDto result = userService.register(authDto, RoleName.PATIENT);

        // Assert
        assertNull(result.getUser());
        assertEquals(ResponseEntity.status(409).body("Try using different email. This email is already taken!"),
                result.getResponse());
        verify(userRepository, times(1)).existsByEmail(authDto.getEmail());
    }

    @Test
    void register_shouldCreateUserWhenEmailDoesNotExist() {
        // Arrange
        AuthDto authDto = new AuthDto("test@test.com", "password");
        Role role = new Role();
        role.setRoleName(RoleName.PATIENT);
        User savedUser = new User();
        savedUser.setUserId(1L);

        when(userRepository.existsByEmail(authDto.getEmail())).thenReturn(false);
        when(roleRepository.findByRoleName(RoleName.PATIENT)).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        RegisterDto result = userService.register(authDto, RoleName.PATIENT);

        // Assert
        assertNotNull(result.getUser());
        assertEquals(1L, result.getUser().getUserId());
        verify(userRepository, times(1)).existsByEmail(authDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserById_shouldReturnUserWhenExists() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserById_shouldThrowExceptionWhenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }
}
