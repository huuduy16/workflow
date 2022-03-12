package com.wfl.api.controllers;

import com.wfl.domains.entities.Task;
import com.wfl.domains.forms.AssignTaskForm;
import com.wfl.domains.responses.success.CreatedResponse;
import com.wfl.domains.responses.success.NoContentResponse;
import com.wfl.services.TaskService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task")
    public ResponseEntity<?> createTask(@Valid @RequestBody Task task) {
        taskService.createTask(task);
        return new CreatedResponse<>();
    }

    @PostMapping("/task")
    public ResponseEntity<?> assignTaskToUser(@Valid @RequestBody AssignTaskForm assignTaskForm) {
        return new NoContentResponse();
    }
}
