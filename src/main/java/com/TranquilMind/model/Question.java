package com.TranquilMind.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long questionId;

    String question;

    String answer;

    @ManyToOne
    @JoinColumn(name = "questioned_by_id", nullable = false)
    User questionBy;

    Timestamp uploadedAt;

    Timestamp answeredAt;

    Boolean answered;

    Boolean isApprovedByModerator;

    @ManyToOne
    @JoinColumn(name = "answered_by_id")
    User answeredBy;

}
