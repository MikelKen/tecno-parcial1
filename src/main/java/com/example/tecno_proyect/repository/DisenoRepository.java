package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Diseno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DisenoRepository extends JpaRepository<Diseno, Long> {
    
    // Buscar diseño por cotización (relación uno a uno)
    Optional<Diseno> findByIdQuote(Long idQuote);
    
    // Buscar diseños por usuario
    List<Diseno> findByUserId(Long userId);
    
    // Buscar diseños aprobados
    List<Diseno> findByApproved(Boolean approved);
    
    // Buscar diseños por fecha de aprobación
    List<Diseno> findByApprovedDate(LocalDate approvedDate);
    
    // Buscar diseños por rango de fechas de aprobación
    @Query("SELECT d FROM Diseno d WHERE d.approvedDate BETWEEN :startDate AND :endDate")
    List<Diseno> findByApprovedDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Buscar diseños que contengan comentarios específicos
    @Query("SELECT d FROM Diseno d WHERE LOWER(d.comments) LIKE LOWER(CONCAT('%', :comment, '%'))")
    List<Diseno> findByCommentsContainingIgnoreCase(@Param("comment") String comment);
    
    // Contar diseños aprobados
    @Query("SELECT COUNT(d) FROM Diseno d WHERE d.approved = true")
    long countApprovedDisenos();
    
    // Contar diseños pendientes de aprobación
    @Query("SELECT COUNT(d) FROM Diseno d WHERE d.approved = false OR d.approved IS NULL")
    long countPendingDisenos();
    
    // Buscar diseños con URL de render
    @Query("SELECT d FROM Diseno d WHERE d.urlRender IS NOT NULL AND d.urlRender != ''")
    List<Diseno> findDisenosWithRender();
    
    // Buscar diseños con plano laminado
    @Query("SELECT d FROM Diseno d WHERE d.laminatedPlane IS NOT NULL AND d.laminatedPlane != ''")
    List<Diseno> findDisenosWithLaminatedPlane();
}