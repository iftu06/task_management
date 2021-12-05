package com.task.management.controller;

import com.task.management.Utillity.ApiResponse;
import com.task.management.Utillity.ErrorMapper;
import com.task.management.Utillity.ToDoSearchField;
import com.task.management.dto.ToDoDto;
import com.task.management.error.ReturnStatus;
import com.task.management.exception.ToDoException;
import com.task.management.model.ToDo;
import com.task.management.service.ToDoService;
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
public class ToDoController {


    @Autowired
    ToDoService toDoService;

    @CrossOrigin
    @PostMapping(value = "/todos")
    public Object save(@Valid @RequestBody ToDo toDo, BindingResult res) {

        if (res.hasErrors()) {
            Map<String, String> errorMap = ErrorMapper.mapError(res.getFieldErrors());
            return ApiResponse.builder().body(errorMap)
                    .status(ReturnStatus.ERROR)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        try {
            ToDoDto toDoDto = toDoService.create(toDo);
            return ResponseEntity.ok()
                    .body(ApiResponse.builder().body(toDoDto)
                            .httpStatus(HttpStatus.CREATED)
                            .status(ReturnStatus.SUCCESS)
                            .message("ToDO Created Successfully")
                            .build());
        } catch (ToDoException exp) {
            return ApiResponse.builder()
                    .status(ReturnStatus.ERROR)
                    .httpStatus(exp.getError().getHttpStatusCode())
                    .message(exp.getError().getErrorMessage())
                    .code(exp.getError().getErrorCode())
                    .build();

        }

    }

    @CrossOrigin
    @RequestMapping(value = "/todos", method = RequestMethod.GET)
    public Object list() {
        try {
            List<ToDoDto> projects = toDoService.getToDos();
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
    @GetMapping(value = "/todos/{id}")
    public Object list(@PathVariable Integer id) {
        try {
            ToDoDto toDoDto = toDoService.getToDo(id);
            return ApiResponse.builder()
                    .body(toDoDto).status(ReturnStatus.SUCCESS)
                    .httpStatus(HttpStatus.FOUND).build();

        } catch (ToDoException exp) {
            return ApiResponse.builder()
                    .status(ReturnStatus.ERROR)
                    .httpStatus(exp.getError().getHttpStatusCode())
                    .message(exp.getError().getErrorMessage())
                    .code(exp.getError().getErrorCode())
                    .build();

        }

    }


    @CrossOrigin
    @DeleteMapping(value = "/todos/delete/{id}")
    public Object delete(@PathVariable Integer id) {

        try {
            toDoService.remove(id);

            return ApiResponse.builder()
                    .status(ReturnStatus.SUCCESS)
                    .httpStatus(HttpStatus.NO_CONTENT)
                    .message("Successfully deleted")
                    .build();

        } catch (ToDoException exp) {
            return ApiResponse.builder()
                    .status(ReturnStatus.ERROR)
                    .httpStatus(exp.getError().getHttpStatusCode())
                    .message(exp.getError().getErrorMessage())
                    .code(exp.getError().getErrorCode())
                    .build();

        }

    }


    @CrossOrigin
    @PutMapping(value = "/todos/{id}")
    public Object update(@RequestBody ToDo toDo, @PathVariable Integer id,
                         BindingResult res) {

        toDo.setId(id);
        if (res.hasErrors()) {
            Map<String, String> errorMap = ErrorMapper.mapError(res.getFieldErrors());
            return ApiResponse.builder().body(errorMap)
                    .status(ReturnStatus.ERROR)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        try {
            ToDoDto toDoDto = toDoService.update(toDo);
            return ResponseEntity.ok()
                    .body(ApiResponse.builder().body(toDoDto)
                            .httpStatus(HttpStatus.CREATED)
                            .status(ReturnStatus.SUCCESS)
                            .message("Todo Updated Successfully")
                            .build());

        } catch (ToDoException exp) {
            return ApiResponse.builder()
                    .status(ReturnStatus.ERROR)
                    .httpStatus(exp.getError().getHttpStatusCode())
                    .message(exp.getError().getErrorMessage())
                    .code(exp.getError().getErrorCode())
                    .build();

        }

    }

    @CrossOrigin
    @RequestMapping(value = "/todos/search", method = RequestMethod.GET)
    public ApiResponse searchToDo(ToDoSearchField seachField) {
        try {
            List<ToDoDto> todos = toDoService.searchToDo(seachField);
            return ApiResponse.builder().httpStatus(HttpStatus.FOUND)
                    .status(ReturnStatus.SUCCESS)
                    .body(todos)
                    .build();

        } catch (ToDoException exp) {
            return ApiResponse.builder()
                    .status(ReturnStatus.ERROR)
                    .httpStatus(exp.getError().getHttpStatusCode())
                    .message(exp.getError().getErrorMessage())
                    .code(exp.getError().getErrorCode())
                    .build();

        }

    }


    @CrossOrigin
    @PutMapping(value = "/todos/markAsDone/{id}")
    public Object markasDone(@PathVariable Integer id) {

        try {
            ToDoDto todoDto = toDoService.markAsDone(id);
            return ResponseEntity.ok()
                    .body(ApiResponse.builder().body(todoDto)
                            .httpStatus(HttpStatus.OK)
                            .status(ReturnStatus.SUCCESS)
                            .message("TODO is completed")
                            .build());

        } catch (ToDoException exp) {
            return ApiResponse.builder()
                    .status(ReturnStatus.ERROR)
                    .httpStatus(exp.getError().getHttpStatusCode())
                    .message(exp.getError().getErrorMessage())
                    .code(exp.getError().getErrorCode())
                    .build();

        }

    }


}
