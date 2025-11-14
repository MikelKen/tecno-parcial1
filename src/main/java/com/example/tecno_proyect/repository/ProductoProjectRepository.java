package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.ProductoProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoProjectRepository extends JpaRepository<ProductoProject, Long> {
    
    // Buscar materiales de un proyecto
    List<ProductoProject> findByIdProject(Long idProject);
    
    // Buscar proyectos que usan un material específico
    List<ProductoProject> findByIdMaterial(Long idMaterial);
    
    // Buscar por proyecto y material específico
    List<ProductoProject> findByIdProjectAndIdMaterial(Long idProject, Long idMaterial);
    
    // Buscar materiales con sobrante en proyectos
    @Query("SELECT mp FROM ProductoProject mp WHERE mp.leftOver > 0")
    List<ProductoProject> findMaterialsWithLeftOver();
    
    // Buscar materiales usados completamente (sin sobrante)
    @Query("SELECT mp FROM ProductoProject mp WHERE mp.leftOver = 0 OR mp.leftOver IS NULL")
    List<ProductoProject> findFullyUsedMaterials();
    
    // Buscar por cantidad específica
    List<ProductoProject> findByQuantity(Integer quantity);
    
    // Buscar materiales con cantidad mayor a un valor
    @Query("SELECT mp FROM ProductoProject mp WHERE mp.quantity > :minQuantity")
    List<ProductoProject> findByQuantityGreaterThan(@Param("minQuantity") Integer minQuantity);
    
    // Obtener total de materiales usados en un proyecto
    @Query("SELECT SUM(mp.quantity) FROM ProductoProject mp WHERE mp.idProject = :idProject")
    Integer getTotalMaterialsUsedInProject(@Param("idProject") Long idProject);
    
    // Obtener total de sobrantes en un proyecto
    @Query("SELECT SUM(mp.leftOver) FROM ProductoProject mp WHERE mp.idProject = :idProject")
    Integer getTotalLeftOverInProject(@Param("idProject") Long idProject);
    
    // Contar proyectos que usan un material específico
    @Query("SELECT COUNT(DISTINCT mp.idProject) FROM ProductoProject mp WHERE mp.idMaterial = :idMaterial")
    long countProjectsUsingMaterial(@Param("idMaterial") Long idMaterial);
    
    // Buscar materiales con sobrante específico
    List<ProductoProject> findByLeftOver(Integer leftOver);
    
    // Buscar materiales más utilizados (ordenados por cantidad descendente)
    @Query("SELECT mp FROM ProductoProject mp ORDER BY mp.quantity DESC")
    List<ProductoProject> findMostUsedMaterials();
    
    // Obtener eficiencia de uso de material (porcentaje usado vs sobrante)
    @Query("SELECT mp FROM ProductoProject mp WHERE (mp.quantity - mp.leftOver) * 100.0 / mp.quantity >= :efficiency")
    List<ProductoProject> findMaterialsWithEfficiencyAbove(@Param("efficiency") Double efficiency);
}