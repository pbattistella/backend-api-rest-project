package br.com.pbattistella.project.repository;

import br.com.pbattistella.project.model.Activity;
import br.com.pbattistella.project.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    public Page<Activity> findByProject(Project project, Pageable pageable);
}
