package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.model.Moderator;
import com.TranquilMind.repository.ModeratorRepository;
import com.TranquilMind.service.ModeratorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeratorServiceImpl implements ModeratorService {

    private ModeratorRepository moderatorRepository;

    @Override
    public List<Moderator> getAllModerators() {
        return moderatorRepository.findAll();
    }
}
