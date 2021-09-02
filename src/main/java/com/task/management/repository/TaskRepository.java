package com.task.management.repository;

import com.task.management.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Divineit-Iftekher on 8/9/2017.
 */
@Repository
public interface TaskRepository extends CrudRepository<Task,Integer> {

    @Override
    List<Task> findAll();

//    @Query(value = "select t from Task t where t.id = ?1  ")
//    public Task findTask (Integer id);

    List<Task> findByProjectId(Integer id);

    Optional<Task> findByIdAndCreatedBy(Integer id,String createdBy);

    List<Task> findByCreatedBy(String createdBy);

    List<Task> findByCreatedByAndProjectId(String userName,Integer projectId);

}
