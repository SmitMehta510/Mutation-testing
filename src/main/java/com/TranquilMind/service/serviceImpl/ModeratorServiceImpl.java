package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.ModeratorDto;
import com.TranquilMind.dto.PostDto;
import com.TranquilMind.model.Doctor;
import com.TranquilMind.model.Moderator;
import com.TranquilMind.repository.ModeratorRepository;
import com.TranquilMind.service.ModeratorService;
import com.TranquilMind.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModeratorServiceImpl implements ModeratorService {

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private PostService postService;

    @Override
    public List<ModeratorDto> getAllModerators() {
        return moderatorRepository.findAll()
                .stream()
                .map(Moderator::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean unflagPost(Long id) {
        return postService.unflagPost(id);
    }

    @Override
    public List<PostDto> getFlaggedPosts() {
        return postService.getFlaggedPosts();
    }
}
