package com.example.crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.crud.model.Project;

@Repository 
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Spring Data JPA va automatiquement implémenter cette méthode pour vous !
    // Elle trouvera un projet par son nom.
    Optional<Project> findByName(String name);

    // Trouve tous les projets dont le nom contient une certaine chaîne de caractères, sans tenir compte de la casse.
    List<Project> findByNameContainingIgnoreCase(String keyword);

}
