package com.TranquilMind.Service.ServiceImpl;

import com.TranquilMind.dto.*;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.*;
import com.TranquilMind.repository.PatientRepository;
import com.TranquilMind.service.PostService;
import com.TranquilMind.service.UserService;
import com.TranquilMind.service.serviceImpl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserService userService;

    @Mock
    private PostService postService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatients_shouldReturnListOfPatients() {
        
        List<Patient> patients = List.of(new Patient(), new Patient());
        User user1 = new User();
        User user2 = new User();
        user1.setEmail("test1@gmail.com");
        user2.setEmail("test2@gmail.com");
        patients.get(0).setFirstName("John");
        patients.get(0).setUser(user1);
        patients.get(1).setFirstName("Jane");
        patients.get(1).setUser(user2);

        when(patientRepository.findAll()).thenReturn(patients);
        
        List<PatientDto> result = patientService.getAllPatients();
        
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void deletePatient_shouldDeletePatientWhenExists() {
        
        Long patientId = 1L;
        Patient patient = new Patient();
        when(patientRepository.findByUserId(patientId)).thenReturn(Optional.of(patient));

        boolean result = patientService.deletePatient(patientId);

        assertTrue(result);
        verify(patientRepository, times(1)).delete(patient);
    }

    @Test
    void deletePatient_shouldThrowExceptionWhenPatientDoesNotExist() {
        
        Long patientId = 1L;
        when(patientRepository.findByUserId(patientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.deletePatient(patientId));
        verify(patientRepository, never()).delete(any());
    }

    @Test
    void updatePatient_shouldUpdateExistingPatient() {

        Patient patient = new Patient();
        User user = new User();
        user.setUserId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        patient.setUser(user);
        PatientRegisterDto patientDetails = new PatientRegisterDto();
        patientDetails.setFirstName("John");
        patientDetails.setLastName("Jane");
        patientDetails.setMiddleName("Smith");
        patientDetails.setAge(20);
        when(patientRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenAnswer(i -> i.getArgument(0));

        Patient updatedPatient = patientService.updatePatient(user.getUserId(), patientDetails);

        assertEquals("John", updatedPatient.getFirstName());
        assertEquals("Jane", updatedPatient.getLastName());
        assertEquals("Smith", updatedPatient.getMiddleName());
        assertEquals(20, updatedPatient.getAge());
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void updatePatient_shouldThrowExceptionIfPatientDoesNotExist() {
        
        Long patientId = 1L;
        PatientRegisterDto patientDetails = new PatientRegisterDto();
        when(patientRepository.findByUserId(patientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.updatePatient(patientId, patientDetails));
        verify(patientRepository, never()).save(any());
    }

    @Test
    void getPatientByUserId_shouldReturnPatientWhenExists() {
        
        Long patientId = 1L;
        Patient patient = new Patient();
        when(patientRepository.findByUserId(patientId)).thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientByUserId(patientId);

        assertNotNull(result);
        verify(patientRepository, times(1)).findByUserId(patientId);
    }

    @Test
    void getPatientByUserId_shouldThrowExceptionWhenPatientDoesNotExist() {
        
        Long patientId = 1L;
        when(patientRepository.findByUserId(patientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.getPatientByUserId(patientId));
    }

    @Test
    void getPatientDtoByUserId_shouldReturnPatientDtoWhenExists() {
        
        Long patientId = 1L;
        Patient patient = new Patient();
        User user = new User();
        user.setUserId(11L);
        user.setEmail("test@mail.com");
        patient.setUser(user);
        when(patientRepository.findByUserId(patientId)).thenReturn(Optional.of(patient));

        PatientDto result = patientService.getPatientDtoByUserId(patientId);
        
        assertNotNull(result);
        verify(patientRepository, times(1)).findByUserId(patientId);
    }

    @Test
    void getPatientDtoByUserId_shouldThrowExceptionWhenPatientDoesNotExist() {
        
        Long patientId = 1L;
        when(patientRepository.findByUserId(patientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.getPatientDtoByUserId(patientId));
    }

    @Test
    void createPatient_shouldCreatePatientSuccessfully() {
        
        PatientRegisterDto patientRegisterDto = new PatientRegisterDto();
        patientRegisterDto.setEmail("test@example.com");
        patientRegisterDto.setPassword("password");
        patientRegisterDto.setFirstName("John");
        patientRegisterDto.setLastName("Jane");
        patientRegisterDto.setMiddleName("Smith");
        patientRegisterDto.setAge(20);
        patientRegisterDto.setGender(Gender.MALE);
        patientRegisterDto.setMobileNo("123456789");
        patientRegisterDto.setImage("Image");

        AuthDto authDto = new AuthDto("test@example.com", "password");
        RegisterDto registerDto = new RegisterDto(new User(), new ResponseEntity<>(HttpStatus.OK));
        when(userService.register(authDto, RoleName.PATIENT)).thenReturn(registerDto);

        Patient newPatient = patientService.createPatient(patientRegisterDto);

        assertEquals("John", newPatient.getFirstName());
        assertEquals("Jane", newPatient.getLastName());
        assertEquals("Smith", newPatient.getMiddleName());
        assertEquals(20, newPatient.getAge());
        assertEquals(Gender.MALE, newPatient.getGender());
        assertEquals("123456789", newPatient.getMobileNo());
        assertEquals("Image", newPatient.getImage());

        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void createPatient_shouldThrowExceptionIfRegistrationFails() {
        
        PatientRegisterDto patientRegisterDto = new PatientRegisterDto();
        patientRegisterDto.setEmail("test@example.com");
        patientRegisterDto.setPassword("password");

        AuthDto authDto = new AuthDto("test@example.com", "password");
        RegisterDto registerDto = new RegisterDto(new User(), new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        when(userService.register(authDto, RoleName.PATIENT)).thenReturn(registerDto);

        assertThrows(ResourceNotFoundException.class, () -> patientService.createPatient(patientRegisterDto));
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void getPosts_shouldReturnPostsForUser() {
        
        Long userId = 1L;
        List<PostDto> posts = List.of(new PostDto());
        when(postService.getPostsByUserId(userId)).thenReturn(posts);

        List<PostDto> result = patientService.getPosts(userId);
        
        assertEquals(1, result.size());
        verify(postService, times(1)).getPostsByUserId(userId);
    }


    @Test
    void updatePassword_shouldUpdatePasswordSuccessfully() {
        
        PasswordDto passwordDto = new PasswordDto();
        when(userService.updatePassword(passwordDto)).thenReturn(true);

        boolean result = patientService.updatePassword(passwordDto);

        assertTrue(result);
        verify(userService, times(1)).updatePassword(passwordDto);
    }

    @Test
    public void testGetAllPatients() {
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        User user = new User();
        user.setEmail("john.doe@gmail.com");
        user.setPassword("password");
        patient.setUser(user);

        when(patientRepository.findAll()).thenReturn(List.of(patient));

        List<PatientDto> patients = patientService.getAllPatients();

        assertNotNull(patients);
        assertEquals(1, patients.size());
        assertEquals("John", patients.get(0).getFirstName());
    }

    // Example test for deletePatient
    @Test
    public void testDeletePatient() {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setPatientId(patientId);

        when(patientRepository.findByUserId(patientId)).thenReturn(Optional.of(patient));

        boolean result = patientService.deletePatient(patientId);

        assertTrue(result);
        verify(patientRepository, times(1)).delete(patient);
    }

    // Example test for getPatientByUserId
    @Test
    public void testGetPatientByUserId() {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setPatientId(patientId);

        when(patientRepository.findByUserId(patientId)).thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientByUserId(patientId);

        assertNotNull(result);
        assertEquals(patientId, result.getPatientId());
    }

    // Example test for handling ResourceNotFoundException
    @Test
    public void testGetPatientByUserIdNotFound() {
        Long patientId = 1L;

        when(patientRepository.findByUserId(patientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            patientService.getPatientByUserId(patientId);
        });
    }
}
