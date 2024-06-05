package br.com.pbattistella.project.service;

import br.com.pbattistella.project.dto.ActivityDTO;
import br.com.pbattistella.project.model.Activity;
import br.com.pbattistella.project.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityService {

    public Page<Activity> findByProject(Long projectID, Pageable pageable);
    public Activity findById(Long id);
    public Activity create(ActivityDTO activityDTO);
    public Activity update(Long id, ActivityDTO activityDTO);
    public void delete(Long id);
}
