package com.task.management.repository;

import com.task.management.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Divineit-Iftekher on 8/9/2017.
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project,Integer> {

    @Override
    List<Project> findAll();

    @Query(value = "select t from Project t where t.id = ?1  ")
    public Project findProject (Integer id);

    List<Project> findByCreatedBy(String userName);

}
