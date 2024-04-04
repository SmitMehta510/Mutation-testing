package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.model.Post;
import com.TranquilMind.model.Responder;
import com.TranquilMind.repository.ResponderRepository;
import com.TranquilMind.service.PostService;
import com.TranquilMind.service.ResponderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ResponderServiceImpl implements ResponderService {

    @Autowired
    private ResponderRepository responderRepository;

    private PostService postService;

    @Override
    public List<Responder> getAllResponders() {
        return responderRepository.findAll();
    }

    @Override
    public List<Post> getPatientQuestions() {
        return postService.getPatientQuestions();
    }

    @Override
    public List<Post> getAnsweredPostsByResponder(Long id) {
        List<Post> posts = postService.getPatientQuestions();
        return posts.stream().filter(post -> Objects.equals(post.getAnsweredBy().getUserId(), id)).toList();
    }
}
