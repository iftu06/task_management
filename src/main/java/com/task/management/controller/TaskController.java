package com.task.management.controller;

import com.task.management.Utillity.ApiResponse;
import com.task.management.Utillity.ErrorMapper;
import com.task.management.Utillity.TaskSeachField;
import com.task.management.Validate.TaskValidator;
import com.task.management.dto.TaskDto;
import com.task.management.error.ReturnStatus;
import com.task.management.model.Task;
import com.task.management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

/**
 * Created by Divineit-Iftekher on 8/8/2017.
 */
@RestController
public class TaskController {


    @Autowired
    TaskService taskService;

    @Autowired
    TaskValidator taskValidator;

    @CrossOrigin
    @PostMapping(value = "/tasks")
    public Object save(@RequestBody Task task, BindingResult res) {

        taskValidator.validate(task, res);
        if (res.hasErrors()) {
            Map<String, String> errorMap = ErrorMapper.mapError(res.getFieldErrors());
            return ApiResponse.builder().body(errorMap)
                    .status(ReturnStatus.ERROR)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        try {
            TaskDto taskDto = taskService.save(task);
            return ResponseEntity.ok()
                    .body(ApiResponse.builder().body(taskDto)
                            .httpStatus(HttpStatus.CREATED)
                            .status(ReturnStatus.SUCCESS)
                            .message("Task Created Successfully")
                            .build());
        } catch (HttpClientErrorException exp) {

            if (exp.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.FORBIDDEN)
                        .message("You are not authorized to access the resource")
                        .build();
            } else if (exp.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Your request is not valid")
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
    @RequestMapping(value = "/tasks/all", method = RequestMethod.GET)
    public ApiResponse getAllTask() throws Exception {
        try {
            List<TaskDto> tasks = taskService.getAllTasks();
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.FOUND)
                    .status(ReturnStatus.SUCCESS)
                    .body(tasks)
                    .build();
        } catch (HttpClientErrorException exp) {
            return ApiResponse.builder()
                    .status(ReturnStatus.ERROR)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public ApiResponse getTask(@PathVariable Integer id) {
        try {

            TaskDto task = taskService.getTask(id);
            return ApiResponse.builder()
                    .body(task).status(ReturnStatus.SUCCESS)
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
    @DeleteMapping(value = "/tasks/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) throws Exception {
        try {
            taskService.remove(id);
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
    @RequestMapping(value = "/project/{projectId}/tasks", method = RequestMethod.GET)
    public ApiResponse getAllTaskByProect(@PathVariable Integer projectId) throws Exception {
        List<TaskDto> tasks = taskService.getTasksByProject(projectId);
        return ApiResponse.builder().httpStatus(HttpStatus.FOUND)
                .status(ReturnStatus.SUCCESS)
                .body(tasks)
                .build();

    }

    @CrossOrigin
    @RequestMapping(value = "/tasks/search", method = RequestMethod.GET)
    public ApiResponse searchTask(TaskSeachField seachField) {
        List<TaskDto> tasks = taskService.searchTask(seachField);
        return ApiResponse.builder().httpStatus(HttpStatus.FOUND)
                .status(ReturnStatus.SUCCESS)
                .body(tasks)
                .build();

    }


    @CrossOrigin
    @PutMapping(value = "/tasks/{id}")
    public Object update(@RequestBody Task task, @PathVariable Integer id,
                         BindingResult res) {

        task.setId(id);
        taskValidator.validate(task, res);
        if (res.hasErrors()) {
            Map<String, String> errorMap = ErrorMapper.mapError(res.getFieldErrors());
            return ApiResponse.builder().body(errorMap)
                    .status(ReturnStatus.ERROR)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        try {
            TaskDto existingTask = taskService.getTask(id);
            TaskDto taskDto = taskService.save(task);
            return ResponseEntity.ok()
                    .body(ApiResponse.builder().body(taskDto)
                            .httpStatus(HttpStatus.CREATED)
                            .status(ReturnStatus.SUCCESS)
                            .message("Task Updated Successfully")
                            .build());
        } catch (HttpClientErrorException exp) {

            if (exp.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.FORBIDDEN)
                        .message("You are not authorized to access the resource")
                        .build();
            } else if (exp.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Your request is not valid")
                        .build();
            } else if (exp.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return ApiResponse.builder()
                        .status(ReturnStatus.ERROR)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .message("There is no task with this id")
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