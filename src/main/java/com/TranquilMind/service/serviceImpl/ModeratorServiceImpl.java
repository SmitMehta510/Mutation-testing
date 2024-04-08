package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.ModeratorDto;
import com.TranquilMind.dto.PostDto;
import com.TranquilMind.model.Doctor;
import com.TranquilMind.model.Moderator;
import com.TranquilMind.model.Question;
import com.TranquilMind.repository.ModeratorRepository;
import com.TranquilMind.service.ModeratorService;
import com.TranquilMind.service.PostService;
import com.TranquilMind.service.QuestionService;
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

    @Autowired
    private QuestionService questionService;

    @Override
    public List<ModeratorDto> getAllModerators() {
        return moderatorRepository.findAll()
                .stream()
                .map(Moderator::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean updatePost(Long id,Boolean unflag) {
        return postService.updatePost(id,unflag);
    }

    @Override
    public List<PostDto> getFlaggedPosts() {
        return postService.getFlaggedPosts();
    }

    @Override
    public List<Question> getAnsweredQuestions() {
        return questionService.getAnsweredQuestions();
    }

    @Override
    public Boolean approveAnswer(Long questionId) {
        return questionService.approveAnswer(questionId);
    }
}
