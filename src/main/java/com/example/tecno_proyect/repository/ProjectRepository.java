package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    
    // Buscar proyectos por cliente
    List<Project> findByIdClient(Long idClient);
    
    // Buscar proyectos por usuario
    List<Project> findByUserId(String userId);
    
    // Buscar proyectos por estado
    List<Project> findByState(String state);
    
    // Buscar proyectos por ubicación
    @Query("SELECT p FROM Project p WHERE LOWER(p.location) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Project> findByLocationContainingIgnoreCase(@Param("location") String location);
    
    // Buscar proyectos por descripción
    @Query("SELECT p FROM Project p WHERE LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    List<Project> findByDescriptionContainingIgnoreCase(@Param("description") String description);
    
    // Contar proyectos por estado
    @Query("SELECT COUNT(p) FROM Project p WHERE p.state = :state")
    long countByState(@Param("state") String state);
    
    // Buscar proyectos activos (no terminados)
    @Query("SELECT p FROM Project p WHERE p.state NOT IN ('Terminado', 'Cancelado')")
    List<Project> findActiveProjects();
    
    // Buscar proyectos por cliente y estado
    List<Project> findByIdClientAndState(Long idClient, String state);
    
    // Buscar proyectos por usuario y estado
    List<Project> findByUserIdAndState(String userId, String state);
}