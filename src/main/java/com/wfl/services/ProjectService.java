package com.wfl.services;

import com.wfl.domains.entities.Project;
import com.wfl.domains.forms.ProjectCreatedResponse;
import com.wfl.exceptions.ResourceAlreadyExistException;
import com.wfl.repositories.ProjectRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public ProjectCreatedResponse createProject(Project project) {
        //must use repository due to @transactional
        if (projectRepository.existsByCode(project.getCode())) {
            throw new ResourceAlreadyExistException(
                "Already exist project with code:  " + project.getCode());
        }
        Project savedProject = projectRepository.saveAndFlush(project);
        return ProjectCreatedResponse.builder().id(savedProject.getId())
            .name(savedProject.getName()).code(savedProject.getCode()).build();
    }

    @Transactional
    public void addMemberToProject(Long projectId, List<Long> userIds) {
        List<Long> userNotInProject = userIds.stream()
            .filter(userId -> !projectRepository.existUserInProject(projectId, userId))
            .collect(Collectors.toList());
        userNotInProject.forEach(userId -> projectRepository.addUserProject(projectId, userId));
    }

    @Transactional
    public void removeMemberFromProject(Long projectId, List<Long> userIds) {
        List<Long> userInProject = userIds.stream()
            .filter(userId -> projectRepository.existUserInProject(projectId, userId))
            .collect(Collectors.toList());
        userInProject.forEach(userId -> projectRepository.deleteUserProject(projectId, userId));
    }
}
