package com.example.demo.service;

import com.example.demo.model.Task;
import dto.TaskDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    public TaskDto save(Task task);

    public TaskDto getTask(Integer id);

    public List<TaskDto> getAllTasks();

    public void remove(Integer id);

    public List<TaskDto> getTasksByProject(Integer id);
}
