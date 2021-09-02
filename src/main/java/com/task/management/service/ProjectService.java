package com.task.management.service;

import com.task.management.dto.ProjectDto;
import com.task.management.model.Project;

import java.util.List;

public interface ProjectService {

     ProjectDto save(Project project);

     ProjectDto getProject(Integer id) ;

     void remove(Integer id);

     List<ProjectDto> getProjects();
}
