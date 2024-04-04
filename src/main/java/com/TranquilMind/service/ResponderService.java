package com.TranquilMind.service;

import com.TranquilMind.model.Post;
import com.TranquilMind.model.Responder;

import java.util.List;

public interface ResponderService {

    List<Responder> getAllResponders();

    List<Post> getPatientQuestions();

    List<Post> getAnsweredPostsByResponder(Long id);
}
