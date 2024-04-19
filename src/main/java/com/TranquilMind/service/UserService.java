package com.TranquilMind.service;

import com.TranquilMind.dto.AuthDto;

import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.dto.RegisterDto;
import com.TranquilMind.dto.UserAuthDto;
import com.TranquilMind.model.Role;
import com.TranquilMind.model.RoleName;
import com.TranquilMind.model.User;;

public interface UserService {


    UserAuthDto authenticate(AuthDto authDto);
    RegisterDto register (AuthDto authDto, RoleName roleName);
    Role saveRole(Role role);
    boolean updatePassword(PasswordDto passwordDto);



//    List<User> getAllUsers();
//
//    boolean deleteUser(Long id);
//
//    User updateUserById(Long id, User user);
//
    User getUserById(Long id);
//
//    AuthResponse createUser(User user) throws Exception;
}
