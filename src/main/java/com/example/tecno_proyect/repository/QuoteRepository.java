package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Cuota, Long> {
    
    // Buscar cotizaciones por proyecto
    List<Cuota> findByIdProyecto(Long idProyecto);
    
    // Buscar cotizaciones por usuario
    List<Cuota> findByUsuarioId(Long usuarioId);
    
    // Buscar cotizaciones por estado
    List<Cuota> findByEstado(String estado);
    
    // Buscar cotizaciones por tipo de metro
    List<Cuota> findByTipoMetro(String tipoMetro);
    
    // Buscar cotizaciones por rango de total
    @Query("SELECT q FROM Cuota q WHERE q.total BETWEEN :minTotal AND :maxTotal")
    List<Cuota> findByTotalBetween(@Param("minTotal") BigDecimal minTotal, @Param("maxTotal") BigDecimal maxTotal);
    
    // Contar cotizaciones por estado
    @Query("SELECT COUNT(q) FROM Cuota q WHERE q.estado = :estado")
    long countByEstado(@Param("estado") String estado);
    
    // Buscar cotizaciones por proyecto y estado
    List<Cuota> findByIdProyectoAndEstado(Long idProyecto, String estado);
    
    // Obtener el total de cotizaciones por proyecto
    @Query("SELECT SUM(q.total) FROM Cuota q WHERE q.idProyecto = :idProyecto AND q.estado = 'Aprobada'")
    BigDecimal getTotalApprovedQuotesByProject(@Param("idProyecto") Long idProyecto);
    
    // Buscar cotizaciones con número de muebles específico
    List<Cuota> findByNumeroMuebles(Integer numeroMuebles);
}