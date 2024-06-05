package br.com.pbattistella.project.controller;

import br.com.pbattistella.project.dto.ActivityDTO;
import br.com.pbattistella.project.model.Activity;
import br.com.pbattistella.project.model.Project;
import br.com.pbattistella.project.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
@Tag(name = "Activity", description = "Endpoints for managing activity")
public class ActivityController {

    @Autowired
    private ActivityService service;

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Find activities of project", description = "Find activities of project", tags = {"Activity"})
    public List<Activity> findByProject(@PathVariable(value = "projectId") Long projectId) {
        return service.findByProject(projectId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find one activity", description = "Find one activity", tags = {"Activity"})
    public Activity findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @PostMapping("/")
    @Operation(summary = "Create one activity", description = "Create one activity", tags = {"Activity"})
    public Activity create(@RequestBody ActivityDTO activityDTO) throws Exception {
        return service.create(activityDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updating one activity", description = "Updating one activity", tags = {"Activity"})
    public Activity update(@PathVariable(value = "id") Long id, @RequestBody ActivityDTO activityDTO) throws Exception {
        return service.update(id, activityDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleting one activity", description = "Deleting one activity", tags = {"Activity"})
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
