package com.TranquilMind.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDto {

    String question;

    String answer;

    Long questionBy;

    String questionByName;

    Timestamp uploadedAt;

    Timestamp answeredAt;

    Boolean answered;

    Boolean isApprovedByModerator;

    Long answeredBy;
}
