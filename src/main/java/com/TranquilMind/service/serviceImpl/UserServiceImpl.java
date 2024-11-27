package com.TranquilMind.service.serviceImpl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public boolean updatePassword(PasswordDto passwordDto) {
        Optional<User> user = userRepository.findById(passwordDto.getUserId());
        if (user.isPresent()) {
            User user1 = user.get();
            return true;
        } else {
            return false;
        }
    }


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
            user.setPassword(authDto.getPassword());
            Role role = roleRepository.findByRoleName(roleName);
            user.setRoles(Collections.singletonList(role));
            registerDto.setUser(userRepository.save(user));
//            String token = jwtUtilities.generateToken(authDto.getEmail(), Collections.singletonList(role.getRoleName()));
//            registerDto.setResponse(
//                    new ResponseEntity<>(new UserAuthDto(token, "Bearer ", user.getUserId(),
//                                    user.getRoles().get(0).getRoleName()), HttpStatus.OK)
//            );
        }
        return registerDto;
    }

    @Override
    public UserAuthDto authenticate(AuthDto authDto) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authDto.getEmail(),
//                        authDto.getPassword()
//                )
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        List<String> rolesNames = new ArrayList<>();
//        user.getRoles().forEach(r -> rolesNames.add(r.getRoleName()));
////        String token = jwtUtilities.generateToken(user.getUsername(), rolesNames);
//        return new UserAuthDto(token, "Bearer ", user.getUserId(), user.getRoles().get(0).getRoleName());
        return null;
    }

    @Override
    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
    }


}
