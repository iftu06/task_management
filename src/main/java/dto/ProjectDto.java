package dto;

import com.example.demo.model.Project;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ProjectDto {
    private String name;

    public static ProjectDto convertToDto(Project project) {
        return ProjectDto.builder()
                .name(project.getName())
                .build();
    }

    public static List<ProjectDto> convertToDto(List<Project> projects) {
        return projects.stream()
                .map(project -> convertToDto(project))
                .collect(Collectors.toList());

    }
}
