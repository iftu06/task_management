package com.task.management.dto;

import com.task.management.Utillity.Link;
import com.task.management.model.Priority;
import com.task.management.model.ToDoStatus;
import com.task.management.model.ToDo;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ToDoDto {

    private Integer id;

    private String title;

    private String description;

    private ToDoStatus status;

    private String priority;

    private List<Link> links = new ArrayList();

    public static ToDoDto convertToDto(ToDo toDo, String baseUrl) {

        return ToDoDto.builder()
                .id(toDo.getId())
                .title(toDo.getTitle())
                .description(toDo.getDescription())
                .status(toDo.getStatus())
                .links(setLink(toDo, baseUrl))
                .priority(toDo.getPriority().name())
                .build();

    }

    public static List<ToDoDto> convertToDto(List<ToDo> todos, String baseUrl) {

        return todos.stream()
                .map(task -> convertToDto(task, baseUrl))
                .collect(Collectors.toList());
    }

    private static List<Link> setLink(ToDo toDo, String baseUrl) {
        List<Link> links = new ArrayList<>();
        String detail = baseUrl + "/todos/" + toDo.getId();
        String update = baseUrl + "/todos/" + toDo.getId();
        String delete = baseUrl + "/todos/delete/" + toDo.getId();

        Link detailLink = new Link(detail, "detail");
        Link updateLink = new Link(update, "update");
        Link deleteLink = new Link(delete, "delete");
        links.add(detailLink);
        links.add(updateLink);
        links.add(deleteLink);
        return links;
    }

}
