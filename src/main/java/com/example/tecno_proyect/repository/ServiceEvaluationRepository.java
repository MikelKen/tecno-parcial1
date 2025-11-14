package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.ServicioEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceEvaluationRepository extends JpaRepository<ServicioEvaluacion, Long> {
    
    // Buscar evaluaciones por proyecto
    List<ServicioEvaluacion> findByIdProyecto(Long idProyecto);
    
    // Buscar evaluaciones por calificación de diseño
    List<ServicioEvaluacion> findByCalificacionDiseno(Integer calificacionDiseno);
    
    // Buscar evaluaciones por calificación de fabricación
    List<ServicioEvaluacion> findByCalificacionFabricacion(Integer calificacionFabricacion);
    
    // Buscar evaluaciones por calificación de instalación
    List<ServicioEvaluacion> findByCalificacionInstalacion(Integer calificacionInstalacion);
    
    // Buscar evaluaciones con calificación de diseño mayor a un valor
    @Query("SELECT se FROM ServicioEvaluacion se WHERE se.calificacionDiseno > :minRating")
    List<ServicioEvaluacion> findByCalificacionDisenoGreaterThan(@Param("minRating") Integer minRating);
    
    // Buscar evaluaciones con calificación de fabricación mayor a un valor
    @Query("SELECT se FROM ServicioEvaluacion se WHERE se.calificacionFabricacion > :minRating")
    List<ServicioEvaluacion> findByCalificacionFabricacionGreaterThan(@Param("minRating") Integer minRating);
    
    // Buscar evaluaciones con calificación de instalación mayor a un valor
    @Query("SELECT se FROM ServicioEvaluacion se WHERE se.calificacionInstalacion > :minRating")
    List<ServicioEvaluacion> findByCalificacionInstalacionGreaterThan(@Param("minRating") Integer minRating);
    
    // Buscar evaluaciones excelentes (todas las calificaciones >= 4)
    @Query("SELECT se FROM ServicioEvaluacion se WHERE se.calificacionDiseno >= 4 AND se.calificacionFabricacion >= 4 AND se.calificacionInstalacion >= 4")
    List<ServicioEvaluacion> findExcellentEvaluations();
    
    // Buscar evaluaciones por comentarios (parcial, ignorando mayúsculas)
    @Query("SELECT se FROM ServicioEvaluacion se WHERE LOWER(se.comentarios) LIKE LOWER(CONCAT('%', :comment, '%'))")
    List<ServicioEvaluacion> findByComentariosContainingIgnoreCase(@Param("comment") String comment);
    
    // Obtener promedio de calificación de diseño
    @Query("SELECT AVG(se.calificacionDiseno) FROM ServicioEvaluacion se")
    Double getAverageDesignQualification();
    
    // Obtener promedio de calificación de fabricación
    @Query("SELECT AVG(se.calificacionFabricacion) FROM ServicioEvaluacion se")
    Double getAverageFabricQualification();
    
    // Obtener promedio de calificación de instalación
    @Query("SELECT AVG(se.calificacionInstalacion) FROM ServicioEvaluacion se")
    Double getAverageInstallationQualification();
    
    // Obtener promedio general de todas las calificaciones
    @Query("SELECT AVG((se.calificacionDiseno + se.calificacionFabricacion + se.calificacionInstalacion) / 3.0) FROM ServicioEvaluacion se")
    Double getOverallAverageRating();
    
    // Contar evaluaciones por calificación de diseño específica
    @Query("SELECT COUNT(se) FROM ServicioEvaluacion se WHERE se.calificacionDiseno = :rating")
    long countByDesignQualification(@Param("rating") Integer rating);
    
    // Buscar evaluaciones ordenadas por promedio de calificaciones (descendente)
    @Query("SELECT se FROM ServicioEvaluacion se ORDER BY (se.calificacionDiseno + se.calificacionFabricacion + se.calificacionInstalacion) DESC")
    List<ServicioEvaluacion> findEvaluationsOrderedByRatingDesc();
    
    // Buscar evaluaciones con calificaciones bajas (cualquier calificación <= 2)
    @Query("SELECT se FROM ServicioEvaluacion se WHERE se.calificacionDiseno <= 2 OR se.calificacionFabricacion <= 2 OR se.calificacionInstalacion <= 2")
    List<ServicioEvaluacion> findPoorEvaluations();
}