package com.example.demo.service;

import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Divineit-Iftekher on 8/12/2017.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Transactional
    public void save(Project project) {
        projectRepository.save(project);
    }

    public Project getProject(Integer id) {
        return projectRepository.findProject(id);
    }

    public void remove(Integer id) {
        projectRepository.deleteById(id);
    }

    public List<Project> getProjects(){
        return projectRepository.findAll();
    }

}
