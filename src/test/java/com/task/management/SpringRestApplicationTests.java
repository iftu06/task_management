package com.task.management;

import com.task.management.dto.ToDoDto;
import com.task.management.model.Priority;
import com.task.management.model.ToDo;
import com.task.management.model.ToDoStatus;
import com.task.management.service.ToDoService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRestApplicationTests {

    @MockBean
    ToDoService toDoService;

    @Test
    public void save() {
        ToDo toDo = ToDo.builder()
                .description("Something")
                .priority(Priority.HIGH)
                .title("Assign task")
                .build();
        ToDoDto toDoDto = ToDoDto.builder()
                .id(1)
                .description(toDo.getDescription())
                .title(toDo.getTitle())
                .status(ToDoStatus.STARTED)
                .priority(Priority.HIGH.name())
                .build();
        Mockito.when(toDoService.create(toDo)).thenReturn(toDoDto);
        Assertions.assertNotNull(toDoService.create(toDo).getId());
        Assertions.assertEquals(1, toDoDto.getId());
        Assertions.assertEquals("HIGH", toDoService.create(toDo).getPriority());
        Assertions.assertEquals("STARTED", toDoService.create(toDo).getStatus().name());
        Assertions.assertNotNull(toDoService.create(toDo).getTitle());

    }

    @Test
    public void markAsDone() {
        ToDoDto toDoDto = ToDoDto.builder()
                .status(ToDoStatus.DONE)
                .build();
        Mockito.when(toDoService.markAsDone(1)).thenReturn(toDoDto);

        Assertions.assertEquals("DONE",
                toDoService.markAsDone(1).getStatus().name());

    }

    @Test
    public void update() {
        ToDo toDo = ToDo.builder()
                .description("Something")
                .id(1)
                .priority(Priority.MEDIUM)
                .title("Assign task")
                .build();
        ToDoDto toDoDto = ToDoDto.builder()
                .id(1)
                .description(toDo.getDescription())
                .title(toDo.getTitle())
                .status(ToDoStatus.STARTED)
                .priority(Priority.MEDIUM.name())
                .build();
        Mockito.when(toDoService.update(toDo)).thenReturn(toDoDto);
        Assertions.assertEquals("MEDIUM", toDoService.update(toDo).getPriority());
        Assertions.assertEquals("STARTED", toDoService.update(toDo).getStatus().name());
        Assertions.assertNotNull(toDoService.update(toDo).getTitle());
    }


}
