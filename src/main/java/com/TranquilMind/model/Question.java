package com.TranquilMind.model;

import com.TranquilMind.dto.QuestionDto;
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

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String question;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String answer;

    Timestamp uploadedAt;

    Timestamp answeredAt;

    Boolean answered;

    Boolean isApprovedByModerator;

    @ManyToOne
    @JoinColumn(name = "questioned_by_id", nullable = false)
    User questionBy;

    @ManyToOne
    @JoinColumn(name = "answered_by_id")
    User answeredBy;

    public QuestionDto toDto(String name) {
        return new QuestionDto(question, answer, questionBy.getUserId(),name, uploadedAt, answeredAt, answered,
                isApprovedByModerator, answeredBy != null ? answeredBy.getUserId() : null);
    }
}
