package com.example.demo.service;

import com.example.demo.Utillity.UserUtil;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.repository.ProjectRepository;
import dto.ProjectDto;
import dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Divineit-Iftekher on 8/12/2017.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Transactional
    public ProjectDto save(Project project) {
        UserDetails user = UserUtil.getPrincipal();

        if (project.getId() == null) {
            project.setCreatedBy(user.getUsername());
            project.setUpdatedBy(user.getUsername());
        } else {
            project.setCreatedBy(user.getUsername());
            String userName = UserUtil.getUserName();
            Optional<Project> projectOpt = projectRepository.findById(project.getId());
            if (projectOpt.isPresent()) {
                Project projectDb = projectOpt.get();
                if (!projectDb.getCreatedBy().equals(userName) && !UserUtil.isAdmin()) {
                    throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
                }
            }
        }

        Project projectDb = projectRepository.save(project);
        return ProjectDto.convertToDto(projectDb);
    }

    public ProjectDto getProject(Integer projectId) {

        String userName = UserUtil.getUserName();
        Optional<Project> projectOpt = projectRepository.findById(projectId);

        if (!projectOpt.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        if (UserUtil.isAdmin()) {
            return ProjectDto.convertToDto(projectOpt.get());
        } else {
            String projectCreatedBy = projectOpt.get().getCreatedBy();
            if (!projectCreatedBy.equals(userName)) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
            }
        }

        return ProjectDto.convertToDto(projectOpt.get());

    }

    public void remove(Integer projectId) {

        Optional<Project> project = projectRepository.findById(projectId);

        if (!project.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        if (!UserUtil.isAdmin()) {
            String projectCreatedBy = project.get().getCreatedBy();
            String userName = UserUtil.getUserName();
            if (!projectCreatedBy.equals(userName)) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
            }
        }

        projectRepository.deleteById(projectId);

    }

    public List<ProjectDto> getProjects() {
        String userName = UserUtil.getUserName();
        List<Project> projects = new ArrayList<>();
        if (UserUtil.isAdmin()) {
            projects = projectRepository.findAll();
        } else {
            projects = projectRepository.findByCreatedBy(userName);
        }

        return !projects.isEmpty() ? ProjectDto.convertToDto(projects) : new ArrayList<>();
    }

}
