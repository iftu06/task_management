package com.example.demo.service;

import com.example.demo.model.Project;
import dto.ProjectDto;

import java.util.List;

public interface ProjectService {

     ProjectDto save(Project project);

     ProjectDto getProject(Integer id) ;

     void remove(Integer id);

     List<ProjectDto> getProjects();
}
