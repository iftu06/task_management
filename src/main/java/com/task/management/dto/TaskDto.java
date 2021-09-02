package com.task.management.dto;

import com.task.management.Utillity.DateTimeUtil;
import com.task.management.Utillity.Link;
import com.task.management.model.Task;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class TaskDto {

    private Integer id;

    private String description;

    private ProjectDto project;

    private String dueDate;

    private String status;

    private List<Link> links = new ArrayList();

    public static TaskDto convertToDto(Task task, String baseUrl) {
        String dueDate = null;
        if (task.getDueDate() != null) {
            dueDate = DateTimeUtil.formateDate(DateTimeFormatter.ofPattern("yyyy-MM-dd"), task.getDueDate());
        }

        return TaskDto.builder()
                .id(task.getId())
                .description(task.getDescription())
                .dueDate(dueDate)
                .project(ProjectDto.convertToDto(task.getProject(),baseUrl))
                .status(task.getStatus())
                .links(setLink(task,baseUrl))
                .build();

    }

    public static List<TaskDto> convertToDto(List<Task> tasks, String baseUrl) {

        return tasks.stream()
                .map(task -> convertToDto(task, baseUrl))
                .collect(Collectors.toList());
    }

    private static List<Link> setLink(Task task, String baseUrl) {
        List<Link> links = new ArrayList<>();
        String detail = baseUrl+"/tasks/"+task.getId();
        String update = baseUrl+"/tasks/"+task.getId();
        String delete = baseUrl+"/tasks/delete/"+task.getId();

        Link detailLink = new Link(detail,"detail");
        Link updateLink = new Link(update,"update");
        Link deleteLink = new Link(delete,"delete");
        links.add(detailLink);
        links.add(updateLink);
        links.add(deleteLink);
        return links;
    }

}
