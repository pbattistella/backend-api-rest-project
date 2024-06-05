package br.com.pbattistella.project.service;

import br.com.pbattistella.project.dto.ActivityDTO;
import br.com.pbattistella.project.exception.ResourceNotFoundException;
import br.com.pbattistella.project.model.Activity;
import br.com.pbattistella.project.repository.ActivityRepository;
import br.com.pbattistella.project.repository.CustomerRepository;
import br.com.pbattistella.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final Logger logger = Logger.getLogger(ActivityServiceImpl.class.getName());

    @Autowired
    ActivityRepository repository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Page<Activity> findByProject(Long projectId, Pageable pageable) {
        logger.info("Finding all activity of project!");

        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        return repository.findByProject(project, pageable);
    }

    @Override
    public Activity findById(Long id) {
        logger.info("Finding one activity!");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
    }

    @Override
    public Activity create(ActivityDTO activityDTO) {
        logger.info("Creating one activity!");

        var project = projectRepository.findById(activityDTO.getProject())
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        var customer = customerRepository.findById(activityDTO.getCustomer())
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        var entity = new Activity();
        entity.setProject(project);
        entity.setCustomer(customer);
        entity.setName(activityDTO.getName());
        entity.setDescription(activityDTO.getDescription());
        entity.setStartDate(activityDTO.getStartDate());
        entity.setEndDate(activityDTO.getEndDate());

        return repository.save(entity);
    }

    @Override
    public Activity update(Long id, ActivityDTO activityDTO) {
        logger.info("Updating one activity!");

        var project = projectRepository.findById(activityDTO.getProject())
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        var customer = customerRepository.findById(activityDTO.getCustomer())
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        entity.setId(activityDTO.getId());
        entity.setProject(project);
        entity.setCustomer(customer);
        entity.setName(activityDTO.getName());
        entity.setDescription(activityDTO.getDescription());
        entity.setStartDate(activityDTO.getStartDate());
        entity.setEndDate(activityDTO.getEndDate());

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
