package com.TranquilMind.model;

import com.TranquilMind.dto.ResponderDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Responder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long responderId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    User user;

    String firstName;
    String middleName;
    String lastName;

    boolean isFirstLogin;

    public ResponderDto toDto(){
        return new ResponderDto(user.getEmail(), firstName, middleName, lastName, isFirstLogin);
    }
}
