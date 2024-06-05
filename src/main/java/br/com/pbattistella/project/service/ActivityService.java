package br.com.pbattistella.project.service;

import br.com.pbattistella.project.model.Activity;
import br.com.pbattistella.project.model.Project;

import java.util.List;

public interface ActivityService {

    public List<Activity> findByProject(Long projectID);
    public Activity findById(Long id);
    public Activity create(Activity activity);
    public Activity update(Long id, Activity activity);
    public void delete(Long id);
}
