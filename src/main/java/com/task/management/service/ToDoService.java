package com.task.management.service;

import com.task.management.Utillity.ToDoSearchField;
import com.task.management.dto.ToDoDto;
import com.task.management.model.ToDo;

import java.util.List;

public interface ToDoService {

     ToDoDto create(ToDo toDo);

     ToDoDto getToDo(Integer id) ;

     void remove(Integer id);

     ToDoDto update(ToDo toDo);

     List<ToDoDto> getToDos();

     ToDoDto markAsDone(Integer id);

     List<ToDoDto> searchToDo(ToDoSearchField seachField);
}
