package com.TranquilMind.model;

import com.TranquilMind.dto.ModeratorDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Moderator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long moderatorId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    User user;

    String firstName;
    String middleName;
    String lastName;

    boolean isFirstLogin;

    public ModeratorDto toDto() {
        return new ModeratorDto(user.getEmail(), firstName, middleName, lastName, isFirstLogin);
    }
}
