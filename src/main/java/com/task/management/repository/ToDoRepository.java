package com.task.management.repository;

import com.task.management.model.ToDo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Divineit-Iftekher on 8/9/2017.
 */
@Repository
public interface ToDoRepository extends CrudRepository<ToDo,Integer> {

    @Override
    List<ToDo> findAll();

}
