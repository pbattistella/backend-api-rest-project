package br.com.pbattistella.project.service;

import br.com.pbattistella.project.model.Activity;

import java.util.List;

public interface ActivityService {

    public List<Activity> findAll();
    public Activity findById(Long id);
    public Activity create(Activity activity);
    public Activity update(Long id, Activity activity);
    public void delete(Long id);
}
