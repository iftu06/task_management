package com.task.management.service;

import com.task.management.Utillity.UserUtil;
import com.task.management.dto.ProjectDto;
import com.task.management.model.Project;
import com.task.management.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    @Value("${com.task.management.base.url}")
    private String baseUrl;

    @Transactional
    public ProjectDto save(Project project) {
        String userName = UserUtil.getUserName();

        if (project.getId() == null) {
            project.setCreatedBy(userName);
            project.setUpdatedBy(userName);
        } else {
            project.setCreatedBy(userName);
            project.setUpdatedBy(userName);
            Optional<Project> projectOpt = projectRepository.findById(project.getId());
            if (projectOpt.isPresent()) {
                Project projectDb = projectOpt.get();
                if (!projectDb.getCreatedBy().equals(userName) && !UserUtil.isAdmin()) {
                    throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
                }
            }
        }

        Project projectDb = projectRepository.save(project);
        return ProjectDto.convertToDto(projectDb,baseUrl);
    }

    public ProjectDto getProject(Integer projectId) {

        String userName = UserUtil.getUserName();
        Optional<Project> projectOpt = projectRepository.findById(projectId);

        if (!projectOpt.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        if (UserUtil.isAdmin()) {
            return ProjectDto.convertToDto(projectOpt.get(),baseUrl);
        } else {
            String projectCreatedBy = projectOpt.get().getCreatedBy();
            if (!projectCreatedBy.equals(userName)) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
            }
        }

        return ProjectDto.convertToDto(projectOpt.get(),baseUrl);

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

        return !projects.isEmpty() ? ProjectDto.convertToDto(projects,baseUrl) : new ArrayList<>();
    }

}
