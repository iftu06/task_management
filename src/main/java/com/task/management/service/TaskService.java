package com.task.management.service;

import com.task.management.Utillity.TaskSeachField;
import com.task.management.dto.TaskDto;
import com.task.management.model.Task;

import java.util.List;

public interface TaskService {

    public TaskDto save(Task task);

    public TaskDto getTask(Integer id);

    public List<TaskDto> getAllTasks();

    public void remove(Integer id);

    public List<TaskDto> getTasksByProject(Integer id);

    public List<TaskDto> searchTask(TaskSeachField seachField);
}
