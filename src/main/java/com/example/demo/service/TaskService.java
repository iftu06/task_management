package com.example.demo.service;

import com.example.demo.Utillity.TaskSeachField;
import com.example.demo.dto.TaskDto;
import com.example.demo.model.Task;

import java.util.List;

public interface TaskService {

    public TaskDto save(Task task);

    public TaskDto getTask(Integer id);

    public List<TaskDto> getAllTasks();

    public void remove(Integer id);

    public List<TaskDto> getTasksByProject(Integer id);

    public List<TaskDto> searchTask(TaskSeachField seachField);
}
