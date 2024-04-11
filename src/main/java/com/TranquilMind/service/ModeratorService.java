package com.TranquilMind.service;


import com.TranquilMind.dto.ModeratorDto;
import com.TranquilMind.dto.PostDto;
import com.TranquilMind.model.Moderator;
import com.TranquilMind.model.Patient;
import com.TranquilMind.model.Question;

import java.util.List;

public interface ModeratorService {

    List<ModeratorDto> getAllModerators();

    Boolean unflagPost(Long id,Boolean unflag);

    Boolean disablePost(Long postId, Boolean disableFlag);

    List<PostDto> getFlaggedPosts();

    List<Question> getAnsweredQuestions();

    Boolean approveAnswer(Long questionId);


}
