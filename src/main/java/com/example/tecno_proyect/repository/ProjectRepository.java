package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Proyecto, Long> {
    
    // Buscar proyecto por nombre (único)
    Optional<Proyecto> findByNombre(String nombre);
    
    // Verificar si existe por nombre
    boolean existsByNombre(String nombre);
    
    // Buscar proyectos por cliente
    List<Proyecto> findByIdCliente(Long idCliente);
    
    // Buscar proyectos por usuario
    List<Proyecto> findByUsuarioId(Long usuarioId);
    
    // Buscar proyectos por estado
    List<Proyecto> findByEstado(String estado);
    
    // Buscar proyectos por ubicación
    @Query("SELECT p FROM Proyecto p WHERE LOWER(p.ubicacion) LIKE LOWER(CONCAT('%', :ubicacion, '%'))")
    List<Proyecto> findByUbicacionContainingIgnoreCase(@Param("ubicacion") String ubicacion);
    
    // Buscar proyectos por descripción
    @Query("SELECT p FROM Proyecto p WHERE LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))")
    List<Proyecto> findByDescripcionContainingIgnoreCase(@Param("descripcion") String descripcion);
    
    // Contar proyectos por estado
    @Query("SELECT COUNT(p) FROM Proyecto p WHERE p.estado = :estado")
    long countByEstado(@Param("estado") String estado);
    
    // Buscar proyectos activos (no terminados)
    @Query("SELECT p FROM Proyecto p WHERE p.estado NOT IN ('Terminado', 'Cancelado')")
    List<Proyecto> findActiveProjects();
    
    // Buscar proyectos por cliente y estado
    List<Proyecto> findByIdClienteAndEstado(Long idCliente, String estado);
    
    // Buscar proyectos por usuario y estado
    List<Proyecto> findByUsuarioIdAndEstado(Long usuarioId, String estado);
}