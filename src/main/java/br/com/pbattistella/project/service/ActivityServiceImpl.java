package br.com.pbattistella.project.service;

import br.com.pbattistella.project.exception.ResourceNotFoundException;
import br.com.pbattistella.project.model.Activity;
import br.com.pbattistella.project.model.Project;
import br.com.pbattistella.project.repository.ActivityRepository;
import br.com.pbattistella.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final Logger logger = Logger.getLogger(ActivityServiceImpl.class.getName());

    @Autowired
    ActivityRepository repository;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public List<Activity> findByProject(Long projectId) {
        logger.info("Finding all activity of project!");

        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        return repository.findByProject(project);
    }

    @Override
    public Activity findById(Long id) {
        logger.info("Finding one activity!");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
    }

    @Override
    public Activity create(Activity activity) {
        logger.info("Creating one activity!");
        return repository.save(activity);
    }

    @Override
    public Activity update(Long id, Activity activity) {
        logger.info("Updating one activity!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
        entity.setName(activity.getName());
        entity.setDescription(activity.getDescription());
        entity.setStartDate(activity.getStartDate());
        entity.setEndDate(activity.getEndDate());
        entity.setCustomer(activity.getCustomer());
        entity.setProject(activity.getProject());

        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting one activity!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
        repository.delete(entity);
    }
}
