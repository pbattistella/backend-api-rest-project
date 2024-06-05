package br.com.pbattistella.project.service;

import br.com.pbattistella.project.model.Project;
import br.com.pbattistella.project.util.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {

    public Page<Project> findAll(Pageable pageable);
    public List<Project> findByStatus(StatusEnum status);
    public Project findById(Long id);

    public Project create(Project project);
    public Project update(Long id, Project customer);
    public void delete(Long id);
}
