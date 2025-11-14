package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Diseno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DisenoRepository extends JpaRepository<Diseno, Long> {
    
    // Buscar diseño por cuota (relación muchos a uno)
    List<Diseno> findByIdCuota(Long idCuota);
    
    // Buscar diseños por usuario
    List<Diseno> findByUsuarioId(Long usuarioId);
    
    // Buscar diseños aprobados
    List<Diseno> findByAprobado(Boolean aprobado);
    
    // Buscar diseños por fecha de aprobación
    List<Diseno> findByFechaAprobacion(LocalDate fechaAprobacion);
    
    // Buscar diseños por rango de fechas de aprobación
    @Query("SELECT d FROM Diseno d WHERE d.fechaAprobacion BETWEEN :startDate AND :endDate")
    List<Diseno> findByFechaAprobacionBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Buscar diseños que contengan comentarios específicos
    @Query("SELECT d FROM Diseno d WHERE LOWER(d.comentarios) LIKE LOWER(CONCAT('%', :comment, '%'))")
    List<Diseno> findByComentariosContainingIgnoreCase(@Param("comment") String comment);
    
    // Contar diseños aprobados
    @Query("SELECT COUNT(d) FROM Diseno d WHERE d.aprobado = true")
    long countApprovedDisenos();
    
    // Contar diseños pendientes de aprobación
    @Query("SELECT COUNT(d) FROM Diseno d WHERE d.aprobado = false OR d.aprobado IS NULL")
    long countPendingDisenos();
    
    // Buscar diseños con URL de render
    @Query("SELECT d FROM Diseno d WHERE d.urlRender IS NOT NULL AND d.urlRender != ''")
    List<Diseno> findDisenosWithRender();
    
    // Buscar diseños con plano laminado
    @Query("SELECT d FROM Diseno d WHERE d.planoLaminar IS NOT NULL AND d.planoLaminar != ''")
    List<Diseno> findDisenosWithLaminatedPlane();
}