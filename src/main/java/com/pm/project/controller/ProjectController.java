package com.pm.project.controller;


import com.pm.project.dto.ProjectCreateRequest;
import com.pm.project.dto.ProjectResponse;
import com.pm.project.dto.ProjectUpdateRequest;
import com.pm.project.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectCreateRequest req) {
        ProjectResponse created = service.create(req);
        URI location = URI.create("/api/projects/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> listMine() {
        return ResponseEntity.ok(service.listMine());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getMine(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(service.getMine(projectId));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateMine(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody ProjectUpdateRequest req
    ) {
        return ResponseEntity.ok(service.updateMine(projectId, req));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteMine(@PathVariable("projectId") Long projectId) {
        service.deleteMine(projectId);
        return ResponseEntity.noContent().build();
    }
}
