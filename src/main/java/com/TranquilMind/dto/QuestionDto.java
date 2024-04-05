package com.TranquilMind.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDto {

    String question;

    String answer;

    Long questionBy;

    Timestamp uploadedAt;

    Timestamp answeredAt;

    Boolean answered;

    Boolean isApprovedByModerator;

    Long answeredBy;
}
