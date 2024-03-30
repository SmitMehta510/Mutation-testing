package com.TranquilMind.dto;

import com.TranquilMind.model.User;
import lombok.Data;

@Data
public class UserAuthDto {
    private String accessToken ;
    private String tokenType ;
    private Long userId;

    public UserAuthDto(String accessToken , String tokenType, Long userId) {
        this.tokenType = tokenType ;
        this.accessToken = accessToken;
        this.userId = userId;
    }
}
