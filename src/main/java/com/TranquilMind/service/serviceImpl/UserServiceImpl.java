package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.config.JwtUtilities;
import com.TranquilMind.config.SpringSecurityConfig;
import com.TranquilMind.dto.AuthDto;
import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.dto.RegisterDto;
import com.TranquilMind.dto.UserAuthDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Role;
import com.TranquilMind.model.RoleName;
import com.TranquilMind.model.User;
import com.TranquilMind.repository.RoleRepository;
import com.TranquilMind.repository.UserRepository;
import com.TranquilMind.service.UserService;
import com.TranquilMind.utilities.AppLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder = new SpringSecurityConfig().passwordEncoder();

    @Autowired
    private JwtUtilities jwtUtilities;

    Logger logger = AppLogger.getLogger();

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public boolean updatePassword(PasswordDto passwordDto) {
        Optional<User> user = userRepository.findById(passwordDto.getUserId());
        if (user.isPresent()) {
            User user1 = user.get();
            if (passwordEncoder.matches(passwordDto.getOldPassword(), user1.getPassword())) {
                user1.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
                userRepository.save(user1);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

//    @Override
//    public ResponseEntity<?> register(RegisterDto registerDto) {
//        if(userRepository.existsByEmail(registerDto.getEmail()))
//        { return  new ResponseEntity<>("email is already taken !", HttpStatus.SEE_OTHER); }
//        else
//        { User user = new User();
//            user.setEmail(registerDto.getEmail());
////            user.setFirstName(registerDto.getFirstName());
////            user.setLastName(registerDto.getLastName());
//            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
//            //By Default , he/she is a simple user
//            Role role = roleRepository.findByRoleName(RoleName.PATIENT);
//            user.setRoles(Collections.singletonList(role));
//            userRepository.save(user);
//            String token = jwtUtilities.generateToken(registerDto.getEmail(),Collections.singletonList(role.getRoleName()));
//            return new ResponseEntity<>(new BearerToken(token , "Bearer "),HttpStatus.OK);
//
//        }
//    }


//    @Override
//    public RegisterDto register(AuthDto authDto, RoleName roleName) {
//        RegisterDto registerDto = new RegisterDto();
//        if (userRepository.existsByEmail(authDto.getEmail())) {
//            registerDto.setUser(null);
////            registerDto.setResponse( new ResponseEntity<>("email is already taken !", HttpStatus.SEE_OTHER));
//            registerDto.setResponse(
//                    new ResponseEntity<>("Try using different email. This email is already taken!", HttpStatus.CONFLICT));
////            logger.warn("This email is already taken!",authDto.getEmail());
//        } else {
//            User user = new User();
//            user.setEmail(authDto.getEmail());
//            user.setPassword(passwordEncoder.encode(authDto.getPassword()));
//            Role role = roleRepository.findByRoleName(roleName);
//            user.setRoles(Collections.singletonList(role));
//            registerDto.setUser(userRepository.save(user));
//            String token = jwtUtilities.generateToken(authDto.getEmail(), Collections.singletonList(role.getRoleName()));
//            registerDto.setResponse(new ResponseEntity<>(new BearerToken(token, "Bearer "), HttpStatus.OK));
//        }
//        return registerDto;
//    }

    @Override
    public RegisterDto register(AuthDto authDto, RoleName roleName) {
        RegisterDto registerDto = new RegisterDto();
        if (userRepository.existsByEmail(authDto.getEmail())) {
            registerDto.setUser(null);
//            registerDto.setResponse( new ResponseEntity<>("email is already taken !", HttpStatus.SEE_OTHER));
            registerDto.setResponse(
                    new ResponseEntity<>("Try using different email. This email is already taken!", HttpStatus.CONFLICT));
//            logger.warn("This email is already taken!",authDto.getEmail());
        } else {
            User user = new User();
            user.setEmail(authDto.getEmail());
            user.setPassword(passwordEncoder.encode(authDto.getPassword()));
            Role role = roleRepository.findByRoleName(roleName);
            user.setRoles(Collections.singletonList(role));
            registerDto.setUser(userRepository.save(user));
            String token = jwtUtilities.generateToken(authDto.getEmail(), Collections.singletonList(role.getRoleName()));
            registerDto.setResponse(
                    new ResponseEntity<>(new UserAuthDto(token, "Bearer ", user.getUserId(),
                                    user.getRoles().get(0).getRoleName()), HttpStatus.OK)
            );
        }
        return registerDto;
    }

    @Override
    public UserAuthDto authenticate(AuthDto authDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDto.getEmail(),
                        authDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<String> rolesNames = new ArrayList<>();
        user.getRoles().forEach(r -> rolesNames.add(r.getRoleName()));
        String token = jwtUtilities.generateToken(user.getUsername(), rolesNames);
        return new UserAuthDto(token, "Bearer ", user.getUserId(), user.getRoles().get(0).getRoleName());
    }


    //    @Override
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    @Override
//    public AuthResponse createUser(User user) throws Exception {
//        User isExist = userRepository.findByEmail(user.getEmail());
//
//        if(isExist != null){
//            throw new Exception("Duplicate email");
//        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        User savedUser = userRepository.save(user);
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
//        String token = JwtProvider.generateToken(authentication);
//
//        AuthResponse authResponse = new AuthResponse(token,"Register success");
//        return authResponse;
//    }
//
//    @Override
//    public boolean deleteUser(Long id) {
//        boolean isDeleted= true;
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
//
//        if(user == null){
//            return false;
//        }else {
//            userRepository.delete(user);
//        }
//
//        return false;
//    }
//
//    @Override
//    public User updateUserById(Long id, User userDetails) throws ResourceNotFoundException{
//
//        User User = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
//
//        User.setFirstName(userDetails.getFirstName());
//        User.setLastName(userDetails.getLastName());
//        User.setEmail(userDetails.getEmail());
//        User.setAge(userDetails.getAge());
//
//        return userRepository.save(User);
//    }
//
    @Override
    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
    }


}
