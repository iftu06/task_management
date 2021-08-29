package com.example.demo.service;

import com.example.demo.model.Project;

import java.util.List;

public interface ProjectService {

     void save(Project project);

     Project getProject(Integer id) ;

     void remove(Integer id);

     List<Project> getProjects();
}
