package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Task;
import com.TranquilMind.repository.TaskRepository;
import com.TranquilMind.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: "+ id));
    }

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task editTask(Task task) {
        return null;
    }

    @Override
    public Boolean deleteTask(Long id) {
        Task task = getTaskById(id);

        taskRepository.delete(task);

        return true;
    }
}
