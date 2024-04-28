package com.TranquilMind.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModeratorDto {

    String email;
    String firstName;
    String middleName;
    String lastName;
    boolean isFirstLogin;

}
