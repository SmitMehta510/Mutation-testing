package com.TranquilMind.service;

import com.TranquilMind.dto.AuthDto;

import com.TranquilMind.dto.RegisterDto;
import com.TranquilMind.dto.UserAuthDto;
import com.TranquilMind.model.Role;
import com.TranquilMind.model.RoleName;;

public interface UserService {


    UserAuthDto authenticate(AuthDto authDto);
    RegisterDto register (AuthDto authDto, RoleName roleName);
    Role saveRole(Role role);



//    List<User> getAllUsers();
//
//    boolean deleteUser(Long id);
//
//    User updateUserById(Long id, User user);
//
//    User getUserById(Long id);
//
//    AuthResponse createUser(User user) throws Exception;
}
