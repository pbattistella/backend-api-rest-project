package br.com.pbattistella.project.controller;

import br.com.pbattistella.project.model.Project;
import br.com.pbattistella.project.service.ProjectService;
import br.com.pbattistella.project.util.StatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@Tag(name = "Project", description = "Endpoints for managing project")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @GetMapping("/")
    @Operation(summary = "Finds all projects", description = "Finds all projects", tags = {"Project"})
    public List<Project> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find one project", description = "Find one project", tags = {"Project"})
    public Project findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Finds all projects witch status passed by parameter", description = "Finds all projects witch status passed by parameter", tags = {"Project"})
    public List<Project> findByStatus(@PathVariable(value = "status") StatusEnum status) {
        return service.findByStatus(status);
    }

    @PostMapping("/")
    @Operation(summary = "Create one project", description = "Create one project", tags = {"Project"})
    public Project create(@RequestBody Project project) throws Exception {
        return service.create(project);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updating one project", description = "Updating one project", tags = {"Project"})
    public Project update(@PathVariable(value = "id") Long id, @RequestBody Project project) throws Exception {
        return service.update(id, project);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleting one project", description = "Deleting one project", tags = {"Project"})
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}