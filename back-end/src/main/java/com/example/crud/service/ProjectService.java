package com.example.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crud.model.Project;
import com.example.crud.repository.ProjectRepository;

import jakarta.persistence.EntityNotFoundException;

@Service 
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired 
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // --- Opérations CRUD ---

    /**
     * Crée un nouveau projet.
     * @param project L'objet Project à sauvegarder.
     * @return Le projet sauvegardé avec son ID généré.
     */
    @Transactional 
    public Project createProject(Project project) {
        
        return projectRepository.save(project);
    }

    /**
     * Récupère tous les projets.
     * @return Une liste de tous les projets.
     */
    @Transactional(readOnly = true) // Optimisation pour les opérations de lecture seule
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Récupère un projet par son ID.
     * @param id L'ID du projet.
     * @return Le projet trouvé.
     * @throws EntityNotFoundException si aucun projet n'est trouvé.
     */
    @Transactional(readOnly = true)
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));
    }

    /**
     * Met à jour un projet existant.
     * @param id L'ID du projet à mettre à jour.
     * @param projectDetails Les nouvelles informations du projet.
     * @return Le projet mis à jour.
     */
    @Transactional
    public Project updateProject(Long id, Project projectDetails) {
        Project existingProject = getProjectById(id); 

        // Met à jour les champs de l'entité existante
        existingProject.setName(projectDetails.getName());
        existingProject.setDescription(projectDetails.getDescription());
        existingProject.setStartDate(projectDetails.getStartDate());
        existingProject.setEndDate(projectDetails.getEndDate());

        
        return existingProject;
    }

    /**
     * Supprime un projet par son ID.
     * @param id L'ID du projet à supprimer.
     */
    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new EntityNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
        
    }
}

