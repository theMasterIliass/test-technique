package com.example.crud.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.crud.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Changement : Utilise Pageable pour la pagination
    Page<Task> findByProjectId(Long projectId, Pageable pageable);

    List<Task> findByStatus(String status);
    
    // Recherche des tâches dont le titre contient la chaîne fournie, insensible à la casse
    List<Task> findByTitleContainingIgnoreCase(String title);

    // Recherche combinée
    List<Task> findByStatusAndTitleContainingIgnoreCase(String status, String title);
}