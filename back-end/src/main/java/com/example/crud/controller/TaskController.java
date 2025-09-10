package com.example.crud.controller;

import com.example.crud.model.Task;
import com.example.crud.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api" ) // Préfixe de base pour les endpoints de tâches
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * GET /api/projects/{projectId}/tasks : Récupère toutes les tâches d'un projet avec pagination.
     * La pagination est gérée par Spring. Exemples d'URL :
     * /api/projects/1/tasks?page=0&size=10
     * /api/projects/1/tasks?page=1&size=5&sort=dueDate,desc
     */
    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<Page<Task>> getTasksForProject(@PathVariable Long projectId, Pageable pageable) {
        Page<Task> tasks = taskService.getTasksByProjectId(projectId, pageable);
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/search : Recherche des tâches par statut ou par titre.
     * Exemples d'URL :
     * /api/tasks/search?status=in_progress
     * /api/tasks/search?title=dev
     * /api/tasks/search?status=done&title=fix
     */
    @GetMapping("/tasks/search")
    public ResponseEntity<List<Task>> searchTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String title) {
        
        List<Task> tasks = taskService.searchTasks(status, title);
        return ResponseEntity.ok(tasks);
    }
}

