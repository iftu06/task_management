package com.example.demo.service;

import com.example.demo.dto.ProjectDto;
import com.example.demo.model.Project;

import java.util.List;

public interface ProjectService {

     ProjectDto save(Project project);

     ProjectDto getProject(Integer id) ;

     void remove(Integer id);

     List<ProjectDto> getProjects();
}
