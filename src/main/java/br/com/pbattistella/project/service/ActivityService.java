package br.com.pbattistella.project.service;

import br.com.pbattistella.project.dto.ActivityDTO;
import br.com.pbattistella.project.model.Activity;
import br.com.pbattistella.project.model.Project;

import java.util.List;

public interface ActivityService {

    public List<Activity> findByProject(Long projectID);
    public Activity findById(Long id);
    public Activity create(ActivityDTO activityDTO);
    public Activity update(Long id, ActivityDTO activityDTO);
    public void delete(Long id);
}
