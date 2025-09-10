package com.example.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crud.model.Project;
import com.example.crud.model.Task;
import com.example.crud.repository.TaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectService projectService; 

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
    }

    /**
     * Crée une nouvelle tâche et l'associe à un projet.
     * @param projectId L'ID du projet auquel la tâche appartient.
     * @param task L'objet Task à créer.
     * @return La tâche sauvegardée.
     */
    @Transactional
    public Task createTaskForProject(Long projectId, Task task) {
        
        Project project = projectService.getProjectById(projectId);
        
        
        project.addTask(task); 

        
        return taskRepository.save(task);
    }

    /**
     * Récupère toutes les tâches d'un projet spécifique.
     * @param projectId L'ID du projet.
     * @return Une liste de tâches.
     */
    @Transactional(readOnly = true)
    public Page<Task> getTasksByProjectId(Long projectId, Pageable pageable) {
        // On vérifie d'abord que le projet existe
        projectService.getProjectById(projectId);
        return taskRepository.findByProjectId(projectId, pageable);
    }

    /**
     * Récupère une tâche par son ID.
     * @param id L'ID de la tâche.
     * @return La tâche trouvée.
     */
    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    /**
     * Met à jour une tâche.
     * @param id L'ID de la tâche à mettre à jour.
     * @param taskDetails Les nouvelles informations.
     * @return La tâche mise à jour.
     */
    @Transactional
    public Task updateTask(Long id, Task taskDetails) {
        Task existingTask = getTaskById(id);
        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setStatus(taskDetails.getStatus());
        existingTask.setDueDate(taskDetails.getDueDate());
        return existingTask;
    }

    @Transactional(readOnly = true)
    public List<Task> searchTasks(String status, String title) {
        if (status != null && title != null) {
            return taskRepository.findByStatusAndTitleContainingIgnoreCase(status, title);
        } else if (status != null) {
            return taskRepository.findByStatus(status);
        } else if (title != null) {
            return taskRepository.findByTitleContainingIgnoreCase(title);
        } else {
            // Si aucun critère n'est fourni, on retourne une liste vide ou toutes les tâches
            // selon la logique métier souhaitée. Retourner une liste vide est plus sûr.
            return List.of(); 
        }
    }
    
    /**
     * Supprime une tâche.
     * @param id L'ID de la tâche à supprimer.
     */
    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
