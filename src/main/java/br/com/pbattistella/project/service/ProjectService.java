package br.com.pbattistella.project.service;

import br.com.pbattistella.project.model.Project;
import br.com.pbattistella.project.util.StatusEnum;

import java.util.List;

public interface ProjectService {

    public List<Project> findAll();
    public List<Project> findByStatus(StatusEnum status);
    public Project findById(Long id);

    public Project create(Project project);
    public Project update(Long id, Project customer);
    public void delete(Long id);
}
