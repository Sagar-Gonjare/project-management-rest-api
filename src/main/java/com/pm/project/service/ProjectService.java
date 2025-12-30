package com.pm.project.service;

import com.pm.project.dto.ProjectCreateREquest;
import com.pm.project.dto.ProjectResponse;
import com.pm.project.dto.ProjectUpdateRequest;
import java.util.List;

public interface ProjectService {

  ProjectResponse create(ProjectCreateREquest req);

  List<ProjectResponse> listMine();

  ProjectResponse getMine(Long projectId);

  ProjectResponse updateMine(Long projectId, ProjectUpdateRequest req);

  void deleteMine(Long projectId);
}
