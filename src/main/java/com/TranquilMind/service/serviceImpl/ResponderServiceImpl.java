package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.model.Responder;
import com.TranquilMind.repository.ResponderRepository;
import com.TranquilMind.service.ResponderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponderServiceImpl implements ResponderService {

    @Autowired
    private ResponderRepository responderRepository;

    @Override
    public List<Responder> getAllResponders() {
        return responderRepository.findAll();
    }
}
