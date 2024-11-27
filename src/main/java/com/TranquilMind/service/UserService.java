package com.TranquilMind.service;

import com.TranquilMind.dto.AuthDto;

import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.dto.RegisterDto;
import com.TranquilMind.dto.UserAuthDto;
import com.TranquilMind.model.RoleName;
import com.TranquilMind.model.User;

public interface UserService {


    UserAuthDto authenticate(AuthDto authDto);
    RegisterDto register (AuthDto authDto, RoleName roleName);
    boolean updatePassword(PasswordDto passwordDto);

    User getUserById(Long id);
}
