package com.TranquilMind.service;


import com.TranquilMind.dto.ModeratorDto;
import com.TranquilMind.dto.PostDto;
import com.TranquilMind.model.Moderator;
import com.TranquilMind.model.Patient;

import java.util.List;

public interface ModeratorService {

    List<ModeratorDto> getAllModerators();

    Boolean unflagPost(Long id);

    List<PostDto> getFlaggedPosts();
}
