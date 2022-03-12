package com.wfl.api.controllers;

import com.wfl.domains.entities.Project;
import com.wfl.domains.responses.success.CreatedResponse;
import com.wfl.domains.responses.success.NoContentResponse;
import com.wfl.exceptions.FieldValidateException;
import com.wfl.services.ProjectService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/project")
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project) {
        if (!project.isValid()) {
            throw new FieldValidateException("Project information is not valid");
        }
        return new CreatedResponse<>(projectService.createProject(project));
    }

    @PostMapping("/project/{projectId}/add-member")
    public ResponseEntity<?> addMemberToProject(@Positive @PathVariable("projectId") Long projectId,
        @NotNull @RequestBody List<@Positive Long> userIds) {
        projectService.addMemberToProject(projectId, userIds);
        return new NoContentResponse();
    }

    @PostMapping("/project/{projectId}/remove-member")
    public ResponseEntity<?> removeMemberFromProject(
        @Positive @PathVariable("projectId") Long projectId,
        @NotNull @RequestBody List<@Positive Long> userIds) {
        projectService.removeMemberFromProject(projectId, userIds);
        return new NoContentResponse();
    }
}
