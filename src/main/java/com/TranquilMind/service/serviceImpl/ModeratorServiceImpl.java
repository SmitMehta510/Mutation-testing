package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.AuthDto;
import com.TranquilMind.dto.ModeratorDto;
import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.dto.PostDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Moderator;
import com.TranquilMind.model.Question;
import com.TranquilMind.model.RoleName;
import com.TranquilMind.model.User;
import com.TranquilMind.repository.ModeratorRepository;
import com.TranquilMind.service.ModeratorService;
import com.TranquilMind.service.PostService;
import com.TranquilMind.service.QuestionService;
import com.TranquilMind.service.UserService;
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

    @Autowired
    private UserService userService;

    @Override
    public List<ModeratorDto> getAllModerators() {
        return moderatorRepository.findAll()
                .stream()
                .map(Moderator::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ModeratorDto addModerator(Moderator moderator) {
        AuthDto authDto = new AuthDto(moderator.getUser().getEmail(), moderator.getUser().getPassword());
        User user = userService.register(authDto, RoleName.MODERATOR).getUser();
        if(user!=null){
            moderator.setUser(user);
            moderator.setFirstLogin(true);
            return moderatorRepository.save(moderator).toDto();
        }else{
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public Boolean unflagPost(Long id,Boolean unflag) {
        return postService.flagPost(id,unflag);
    }

    @Override
    public Boolean disablePost(Long postId, Boolean disableFlag) {
        return postService.disablePost(postId,disableFlag);
    }

    @Override
    public List<PostDto> getFlaggedPosts() {
        return postService.getFlaggedPosts();
    }

    @Override
    public List<Question> getAnsweredQuestions() {
        return questionService.getUnapprovedAnswers();
    }

    @Override
    public Boolean approveAnswer(Long questionId) {
        return questionService.approveAnswer(questionId);
    }

    @Override
    public ModeratorDto getModeratorByUserId(Long id) {
        return moderatorRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moderator not exist with id :" + id)).toDto();
    }

    @Override
    public boolean updatePassword(PasswordDto passwordDto) {
        Moderator moderator = moderatorRepository.findByUserId(passwordDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Moderator not exist with id :" + passwordDto.getUserId()));
        moderator.setFirstLogin(false);
        moderatorRepository.save(moderator);
        return userService.updatePassword(passwordDto);
    }

}
