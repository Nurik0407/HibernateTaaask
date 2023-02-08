package peaksoft.service;

import peaksoft.entity.Project;
import peaksoft.repository.ProjectRepository;
import peaksoft.repository.ProjectRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class ProjectServiceImpl implements ProjectService{
    ProjectRepository projectRepository = new ProjectRepositoryImpl();
    @Override
    public String saveProject(Project project) {
        return projectRepository.saveProject(project);
    }

    @Override
    public String saveProject(List<Project> projects) {
        return projectRepository.saveProject(projects);
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.getAll();
    }

    @Override
    public Optional<Project> getByID(Long id) {
        return projectRepository.getByID(id);
    }

    @Override
    public String deleteByID(Long id) {
        return projectRepository.deleteByID(id);
    }

    @Override
    public String deleteAll() {
        return projectRepository.deleteAll();
    }

    @Override
    public Project update(Long id, Project newProject) {
        return projectRepository.update(id, newProject);
    }

    @Override
    public String assignProgrammer(Long projectID, Long programmerID) {

        return projectRepository.assignProgrammer(projectID, programmerID);
    }

    @Override
    public Project maxPriceProject() {
        return projectRepository.maxPriceProject();
    }

    @Override
    public String getQuickProject() {
        return projectRepository.getQuickProject();
    }
}
