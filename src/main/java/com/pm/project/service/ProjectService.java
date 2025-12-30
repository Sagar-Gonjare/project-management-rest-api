package com.pm.project.service;


import com.pm.common.exception.ResourceNotFoundException;
import com.pm.common.util.SecurityUtil;
import com.pm.project.dto.ProjectCreateRequest;
import com.pm.project.dto.ProjectResponse;
import com.pm.project.dto.ProjectUpdateRequest;
import com.pm.project.entity.Projects;
import com.pm.project.repository.ProjectRepository;
import com.pm.user.entity.Users;
import com.pm.user.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepo;
    private final UserRepository userRepo;

    public ProjectService(ProjectRepository projectRepo, UserRepository userRepo) {
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public ProjectResponse create(ProjectCreateRequest req) {
        Users owner = currentUsersOrThrow();

        Projects project = Projects.builder()
                .name(req.name().trim())
                .description(req.description())
                .owner(owner)
                .build();

        Projects saved = projectRepo.save(project);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> listMine() {
        Users owner = currentUsersOrThrow();

        return projectRepo.findAllByOwnerIdOrderByCreatedAtDesc(owner.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponse getMine(Long projectId) {
        Users owner = currentUsersOrThrow();

        Projects project = projectRepo.findByIdAndOwnerId(projectId, owner.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        return toResponse(project);
    }

    @Transactional
    public ProjectResponse updateMine(Long projectId, ProjectUpdateRequest req) {
        Users owner = currentUsersOrThrow();

        Projects project = projectRepo.findByIdAndOwnerId(projectId, owner.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        project.setName(req.name().trim());
        project.setDescription(req.description());

        // no need to call save() explicitly; JPA dirty checking will persist changes
        return toResponse(project);
    }

    @Transactional
    public void deleteMine(Long projectId) {
        Users owner = currentUsersOrThrow();

        Projects project = projectRepo.findByIdAndOwnerId(projectId, owner.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        projectRepo.delete(project);
    }

    private Users currentUsersOrThrow() {
        String email = SecurityUtil.currentUserEmail();
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Users not found"));
    }

    private ProjectResponse toResponse(Projects p) {
        return new ProjectResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
