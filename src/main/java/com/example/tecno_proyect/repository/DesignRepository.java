package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DesignRepository extends JpaRepository<Design, Long> {
    
    // Buscar diseño por cotización (relación uno a uno)
    Optional<Design> findByIdQuote(Long idQuote);
    
    // Buscar diseños por usuario
    List<Design> findByUserId(Long userId);
    
    // Buscar diseños aprobados
    List<Design> findByApproved(Boolean approved);
    
    // Buscar diseños por fecha de aprobación
    List<Design> findByApprovedDate(LocalDate approvedDate);
    
    // Buscar diseños por rango de fechas de aprobación
    @Query("SELECT d FROM Design d WHERE d.approvedDate BETWEEN :startDate AND :endDate")
    List<Design> findByApprovedDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Buscar diseños que contengan comentarios específicos
    @Query("SELECT d FROM Design d WHERE LOWER(d.comments) LIKE LOWER(CONCAT('%', :comment, '%'))")
    List<Design> findByCommentsContainingIgnoreCase(@Param("comment") String comment);
    
    // Contar diseños aprobados
    @Query("SELECT COUNT(d) FROM Design d WHERE d.approved = true")
    long countApprovedDesigns();
    
    // Contar diseños pendientes de aprobación
    @Query("SELECT COUNT(d) FROM Design d WHERE d.approved = false OR d.approved IS NULL")
    long countPendingDesigns();
    
    // Buscar diseños con URL de render
    @Query("SELECT d FROM Design d WHERE d.urlRender IS NOT NULL AND d.urlRender != ''")
    List<Design> findDesignsWithRender();
    
    // Buscar diseños con plano laminado
    @Query("SELECT d FROM Design d WHERE d.laminatedPlane IS NOT NULL AND d.laminatedPlane != ''")
    List<Design> findDesignsWithLaminatedPlane();
}