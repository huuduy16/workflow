package com.wfl.services;

import com.wfl.domains.entities.Task;
import com.wfl.domains.forms.AssignTaskForm;
import com.wfl.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void createTask(Task task) {
        taskRepository.saveAndFlush(task);
    }

    @Transactional
    public void assignTaskToUser(AssignTaskForm assignTaskForm) {
    }
}
