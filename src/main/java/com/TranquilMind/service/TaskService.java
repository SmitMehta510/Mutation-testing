package com.TranquilMind.service;

import com.TranquilMind.model.Task;

import java.util.List;

public interface TaskService {

    Task getTaskById(Long id);

    Task addTask(Task task);

    Task editTask(Task task);

    Boolean deleteTask(Long id);

}
