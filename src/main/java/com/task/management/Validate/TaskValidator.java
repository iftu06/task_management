package com.task.management.Validate;

import com.task.management.model.Task;
import com.task.management.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class TaskValidator implements Validator {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Task.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Task task = (Task) o;

        ValidationUtils.rejectIfEmpty(errors, "description", "error.description", "Description can not be  empty");
        ValidationUtils.rejectIfEmpty(errors, "status", "error.status", "Status can not be empty");
        ValidationUtils.rejectIfEmpty(errors, "project", "error.project", "Project is required.");
        rejectIfStatusIsClosed(task, errors);

    }

    public void rejectIfStatusIsClosed(Task task, Errors errors) {
        if (task.getId() != null) {
            Optional<Task> existingTask = taskRepository.findById(task.getId());
            if (existingTask.isPresent() && existingTask.get().getStatus().equalsIgnoreCase("close")) {
                errors.rejectValue("status", "close", new Object[]{"'status'"}, "Closed Task can not be edited");
            }
        }
    }
}


