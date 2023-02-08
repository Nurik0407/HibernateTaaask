package peaksoft.service;

import peaksoft.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    String saveProject(Project project);
    String saveProject(List<Project> projects);

    List<Project> getAll();

    Optional<Project> getByID(Long id);

    String deleteByID(Long id);

    String deleteAll();

    Project update(Long id,Project newProject);

    String assignProgrammer(Long projectID,Long programmerID);

    Project maxPriceProject();

    String getQuickProject();
}
