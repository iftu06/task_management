package dto;

import com.example.demo.Utillity.DateTimeUtil;
import com.example.demo.model.Task;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class TaskDtoAfterSave {

    private Integer id;

    private String description;

    private ProjectDto project;

    private String dueDate;

    private String status;

    public static TaskDtoAfterSave convertToDto(Task task) {
        String dueDate = null;
        if (task.getDueDate() != null) {
            dueDate = DateTimeUtil.formateDate(DateTimeFormatter.ofPattern("yyyy-MM-dd"), task.getDueDate());
        }

        return TaskDtoAfterSave.builder()
                .id(task.getId())
                .description(task.getDescription())
                .dueDate(dueDate)
                .project(ProjectDto.convertToDto(task.getProject()))
                .status(task.getStatus())
                .build();

    }

    public static List<TaskDtoAfterSave> convertToDto(List<Task> tasks) {
        return tasks.stream()
                .map(task -> convertToDto(task))
                .collect(Collectors.toList());

    }

    public static TaskDtoAfterSave convertToDtoAfterSave(Task task) {
        String dueDate = null;
        if (task.getDueDate() != null) {
            dueDate = DateTimeUtil.formateDate(DateTimeFormatter.ofPattern("yyyy-MM-dd"), task.getDueDate());
        }
        return TaskDtoAfterSave.builder()
                .id(task.getId())
                .description(task.getDescription())
                .dueDate(dueDate)
                .status(task.getStatus())
                .build();


    }

}
