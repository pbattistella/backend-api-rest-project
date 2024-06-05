package br.com.pbattistella.project.service;

import br.com.pbattistella.project.exception.ResourceNotFoundException;
import br.com.pbattistella.project.model.Project;
import br.com.pbattistella.project.repository.ProjectRepository;
import br.com.pbattistella.project.util.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final Logger logger = Logger.getLogger(ProjectServiceImpl.class.getName());

    @Autowired
    private ProjectRepository repository;

    @Override
    public List<Project> findAll() {
        logger.info("Finding all projects!");
        return repository.findAll();
    }

    @Override
    public List<Project> findByStatus(StatusEnum status) {
        logger.info("Finding all projects with status defined!");
        return repository.findByStatus(status);
    }

    @Override
    public Project findById(Long id) {
        logger.info("Finding one project!");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
    }

    @Override
    public Project create(Project project) {
        logger.info("Creating one project!");
        return repository.save(project);
    }

    @Override
    public Project update(Long id, Project project) {
        logger.info("Updating one project!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
        entity.setName(project.getName());
        entity.setDescription(project.getDescription());
        entity.setStatus(project.getStatus());
        entity.setStartDate(project.getStartDate());
        entity.setEndDate(project.getEndDate());
        entity.setManager(project.getManager());

        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting one project!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
        repository.delete(entity);
    }
}
