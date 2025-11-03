package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.ServiceEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceEvaluationRepository extends JpaRepository<ServiceEvaluation, Long> {
    
    // Buscar evaluaciones por proyecto
    List<ServiceEvaluation> findByIdProject(Long idProject);
    
    // Buscar evaluaciones por calificación de diseño
    List<ServiceEvaluation> findByDesignQualification(Integer designQualification);
    
    // Buscar evaluaciones por calificación de fabricación
    List<ServiceEvaluation> findByFabricQualification(Integer fabricQualification);
    
    // Buscar evaluaciones por calificación de instalación
    List<ServiceEvaluation> findByInstallationQualification(Integer installationQualification);
    
    // Buscar evaluaciones con calificación de diseño mayor a un valor
    @Query("SELECT se FROM ServiceEvaluation se WHERE se.designQualification > :minRating")
    List<ServiceEvaluation> findByDesignQualificationGreaterThan(@Param("minRating") Integer minRating);
    
    // Buscar evaluaciones con calificación de fabricación mayor a un valor
    @Query("SELECT se FROM ServiceEvaluation se WHERE se.fabricQualification > :minRating")
    List<ServiceEvaluation> findByFabricQualificationGreaterThan(@Param("minRating") Integer minRating);
    
    // Buscar evaluaciones con calificación de instalación mayor a un valor
    @Query("SELECT se FROM ServiceEvaluation se WHERE se.installationQualification > :minRating")
    List<ServiceEvaluation> findByInstallationQualificationGreaterThan(@Param("minRating") Integer minRating);
    
    // Buscar evaluaciones excelentes (todas las calificaciones >= 4)
    @Query("SELECT se FROM ServiceEvaluation se WHERE se.designQualification >= 4 AND se.fabricQualification >= 4 AND se.installationQualification >= 4")
    List<ServiceEvaluation> findExcellentEvaluations();
    
    // Buscar evaluaciones por comentarios (parcial, ignorando mayúsculas)
    @Query("SELECT se FROM ServiceEvaluation se WHERE LOWER(se.comments) LIKE LOWER(CONCAT('%', :comment, '%'))")
    List<ServiceEvaluation> findByCommentsContainingIgnoreCase(@Param("comment") String comment);
    
    // Obtener promedio de calificación de diseño
    @Query("SELECT AVG(se.designQualification) FROM ServiceEvaluation se")
    Double getAverageDesignQualification();
    
    // Obtener promedio de calificación de fabricación
    @Query("SELECT AVG(se.fabricQualification) FROM ServiceEvaluation se")
    Double getAverageFabricQualification();
    
    // Obtener promedio de calificación de instalación
    @Query("SELECT AVG(se.installationQualification) FROM ServiceEvaluation se")
    Double getAverageInstallationQualification();
    
    // Obtener promedio general de todas las calificaciones
    @Query("SELECT AVG((se.designQualification + se.fabricQualification + se.installationQualification) / 3.0) FROM ServiceEvaluation se")
    Double getOverallAverageRating();
    
    // Contar evaluaciones por calificación de diseño específica
    @Query("SELECT COUNT(se) FROM ServiceEvaluation se WHERE se.designQualification = :rating")
    long countByDesignQualification(@Param("rating") Integer rating);
    
    // Buscar evaluaciones ordenadas por promedio de calificaciones (descendente)
    @Query("SELECT se FROM ServiceEvaluation se ORDER BY (se.designQualification + se.fabricQualification + se.installationQualification) DESC")
    List<ServiceEvaluation> findEvaluationsOrderedByRatingDesc();
    
    // Buscar evaluaciones con calificaciones bajas (cualquier calificación <= 2)
    @Query("SELECT se FROM ServiceEvaluation se WHERE se.designQualification <= 2 OR se.fabricQualification <= 2 OR se.installationQualification <= 2")
    List<ServiceEvaluation> findPoorEvaluations();
}