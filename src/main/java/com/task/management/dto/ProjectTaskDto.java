package com.task.management.dto;

import com.task.management.Utillity.Link;
import com.task.management.model.Project;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ProjectTaskDto {
    private String name;

    public static ProjectTaskDto convertToDto(Project project) {
        return ProjectTaskDto.builder()
                .name(project.getName())
                .build();
    }

    public static List<ProjectTaskDto> convertToDto(List<Project> projects) {
        return projects.stream()
                .map(project -> convertToDto(project))
                .collect(Collectors.toList());

    }

}
