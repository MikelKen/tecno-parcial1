package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.MaterialProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialProjectRepository extends JpaRepository<MaterialProject, Long> {
    
    // Buscar materiales de un proyecto
    List<MaterialProject> findByIdProject(String idProject);
    
    // Buscar proyectos que usan un material específico
    List<MaterialProject> findByIdMaterial(Long idMaterial);
    
    // Buscar por proyecto y material específico
    List<MaterialProject> findByIdProjectAndIdMaterial(String idProject, Long idMaterial);
    
    // Buscar materiales con sobrante en proyectos
    @Query("SELECT mp FROM MaterialProject mp WHERE mp.leftOver > 0")
    List<MaterialProject> findMaterialsWithLeftOver();
    
    // Buscar materiales usados completamente (sin sobrante)
    @Query("SELECT mp FROM MaterialProject mp WHERE mp.leftOver = 0 OR mp.leftOver IS NULL")
    List<MaterialProject> findFullyUsedMaterials();
    
    // Buscar por cantidad específica
    List<MaterialProject> findByQuantity(Integer quantity);
    
    // Buscar materiales con cantidad mayor a un valor
    @Query("SELECT mp FROM MaterialProject mp WHERE mp.quantity > :minQuantity")
    List<MaterialProject> findByQuantityGreaterThan(@Param("minQuantity") Integer minQuantity);
    
    // Obtener total de materiales usados en un proyecto
    @Query("SELECT SUM(mp.quantity) FROM MaterialProject mp WHERE mp.idProject = :idProject")
    Integer getTotalMaterialsUsedInProject(@Param("idProject") String idProject);
    
    // Obtener total de sobrantes en un proyecto
    @Query("SELECT SUM(mp.leftOver) FROM MaterialProject mp WHERE mp.idProject = :idProject")
    Integer getTotalLeftOverInProject(@Param("idProject") String idProject);
    
    // Contar proyectos que usan un material específico
    @Query("SELECT COUNT(DISTINCT mp.idProject) FROM MaterialProject mp WHERE mp.idMaterial = :idMaterial")
    long countProjectsUsingMaterial(@Param("idMaterial") Long idMaterial);
    
    // Buscar materiales con sobrante específico
    List<MaterialProject> findByLeftOver(Integer leftOver);
    
    // Buscar materiales más utilizados (ordenados por cantidad descendente)
    @Query("SELECT mp FROM MaterialProject mp ORDER BY mp.quantity DESC")
    List<MaterialProject> findMostUsedMaterials();
    
    // Obtener eficiencia de uso de material (porcentaje usado vs sobrante)
    @Query("SELECT mp FROM MaterialProject mp WHERE (mp.quantity - mp.leftOver) * 100.0 / mp.quantity >= :efficiency")
    List<MaterialProject> findMaterialsWithEfficiencyAbove(@Param("efficiency") Double efficiency);
}