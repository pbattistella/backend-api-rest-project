package br.com.pbattistella.project.repository;

import br.com.pbattistella.project.model.Activity;
import br.com.pbattistella.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    public List<Activity> findByProject(Project project);
}
