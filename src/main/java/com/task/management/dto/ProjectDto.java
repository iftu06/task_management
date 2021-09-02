package com.task.management.dto;

import com.task.management.Utillity.Link;
import com.task.management.model.Project;
import com.task.management.model.Task;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ProjectDto {
    private String name;
    private List<Link> links = new ArrayList<>();

    public static ProjectDto convertToDto(Project project,String baseUrl) {
        return ProjectDto.builder()
                .name(project.getName())
                .links(setLink(project,baseUrl))
                .build();
    }

    public static List<ProjectDto> convertToDto(List<Project> projects,String baseUrl) {
        return projects.stream()
                .map(project -> convertToDto(project,baseUrl))
                .collect(Collectors.toList());

    }

    private static List<Link> setLink(Project project, String baseUrl) {
        List<Link> links = new ArrayList<>();
        String detail = baseUrl+"/projects/"+project.getId();
        String update = baseUrl+"/projects/"+project.getId();
        String delete = baseUrl+"/projects/delete/"+project.getId();

        Link detailLink = new Link(detail,"detail");
        Link updateLink = new Link(update,"update");
        Link deleteLink = new Link(delete,"delete");
        links.add(detailLink);
        links.add(updateLink);
        links.add(deleteLink);
        return links;
    }
}
