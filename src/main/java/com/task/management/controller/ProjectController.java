package com.task.management.controller;

import com.task.management.Utillity.ApiResponse;
import com.task.management.Utillity.ErrorMapper;
import com.task.management.dto.ProjectDto;
import com.task.management.error.ReturnStatus;
import com.task.management.model.Project;
import com.task.management.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by Divineit-Iftekher on 8/8/2017.
 */
@RestController
public class ProjectController {


    @Autowired
    ProjectService projectService;

    @CrossOrigin
    @PostMapping(value = "/projects")
    public Object save(@Valid @RequestBody Project project, BindingResult res) {

        if (res.hasErrors()) {
            Map<String, String> errorMap = ErrorMapper.mapError(res.getFieldErrors());
            return ApiResponse.builder().body(errorMap)
                    .status(ReturnStatus.ERROR)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        try {
            ProjectDto projectDto = projectService.save(project);
            return ResponseEntity.ok()
                    .body(ApiResponse.builder().body(projectDto)
                            .httpStatus(HttpStatus.CREATED)
                            .status(ReturnStatus.SUCCESS)
                            .message("Project Created Successfully")
                            .build());
        } catch (HttpClientErrorException exp) {
            if (exp.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.FORBIDDEN)
                        .message("You are not authorized to access the resource")
                        .build();
            } else {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.NOT_IMPLEMENTED)
                        .message("Reason Unknown")
                        .build();
            }
        }

    }

    @CrossOrigin
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public Object list() {
        try {
            List<ProjectDto> projects = projectService.getProjects();
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.FOUND)
                    .status(ReturnStatus.SUCCESS)
                    .body(projects)
                    .build();
        } catch (HttpClientErrorException exp) {
            return ApiResponse.builder()
                    .status(ReturnStatus.ERROR)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @CrossOrigin
    @GetMapping(value = "/projects/{id}")
    public Object list(@PathVariable Integer id) {
        try {
            ProjectDto project = projectService.getProject(id);
            return ApiResponse.builder()
                    .body(project).status(ReturnStatus.SUCCESS)
                    .httpStatus(HttpStatus.FOUND).build();

        } catch (HttpClientErrorException exp) {
            if (exp.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .message("Resource is not found")
                        .build();
            } else if (exp.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.FORBIDDEN)
                        .message("You are not authorized to access the resource")
                        .build();
            } else {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.NOT_IMPLEMENTED)
                        .message("Reason Unknown")
                        .build();
            }
        }

    }


    @CrossOrigin
    @DeleteMapping(value = "/projects/delete/{id}")
    public Object delete(@PathVariable Integer id) {
        try {
            projectService.remove(id);
        } catch (HttpClientErrorException exp) {
            if (exp.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.FORBIDDEN)
                        .message("You are not authorized to access the resource")
                        .build();
            } else {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.NOT_IMPLEMENTED)
                        .message("Reason Unknown")
                        .build();
            }
        }

        return ApiResponse.builder()
                .status(ReturnStatus.SUCCESS)
                .httpStatus(HttpStatus.NO_CONTENT)
                .message("Successfully deleted")
                .build();

    }


    @CrossOrigin
    @PutMapping(value = "/projects/{id}")
    public Object update(@Valid @RequestBody Project project, @PathVariable Integer id,
                         BindingResult res) {

        if (res.hasErrors()) {
            Map<String, String> errorMap = ErrorMapper.mapError(res.getFieldErrors());
            return ApiResponse.builder().body(errorMap)
                    .status(ReturnStatus.ERROR)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        try {
            ProjectDto existingProject = projectService.getProject(id);
            project.setId(id);
            ProjectDto projectDto = projectService.save(project);
            return ResponseEntity.ok()
                    .body(ApiResponse.builder().body(projectDto)
                            .httpStatus(HttpStatus.CREATED)
                            .status(ReturnStatus.SUCCESS)
                            .message("Project Updated Successfully")
                            .build());
        } catch (HttpClientErrorException exp) {
            if (exp.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.FORBIDDEN)
                        .message("You are not authorized to access the resource")
                        .build();
            } else if (exp.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .message("There is no project with this id")
                        .build();
            } else {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.NOT_IMPLEMENTED)
                        .message("Reason Unknown")
                        .build();
            }
        }

    }


}