package br.com.pbattistella.project.repository;

import br.com.pbattistella.project.model.Project;
import br.com.pbattistella.project.util.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    public List<Project> findByStatus(StatusEnum status);
}
