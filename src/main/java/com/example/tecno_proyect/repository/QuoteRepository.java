package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    
    // Buscar cotizaciones por proyecto
    List<Quote> findByIdProject(String idProject);
    
    // Buscar cotizaciones por usuario
    List<Quote> findByUserId(Long userId);
    
    // Buscar cotizaciones por estado
    List<Quote> findByState(String state);
    
    // Buscar cotizaciones por tipo de metro
    List<Quote> findByTypeMetro(String typeMetro);
    
    // Buscar cotizaciones por rango de total
    @Query("SELECT q FROM Quote q WHERE q.total BETWEEN :minTotal AND :maxTotal")
    List<Quote> findByTotalBetween(@Param("minTotal") BigDecimal minTotal, @Param("maxTotal") BigDecimal maxTotal);
    
    // Contar cotizaciones por estado
    @Query("SELECT COUNT(q) FROM Quote q WHERE q.state = :state")
    long countByState(@Param("state") String state);
    
    // Buscar cotizaciones por proyecto y estado
    List<Quote> findByIdProjectAndState(String idProject, String state);
    
    // Obtener el total de cotizaciones por proyecto
    @Query("SELECT SUM(q.total) FROM Quote q WHERE q.idProject = :idProject AND q.state = 'Aprobada'")
    BigDecimal getTotalApprovedQuotesByProject(@Param("idProject") String idProject);
    
    // Buscar cotizaciones con número de muebles específico
    List<Quote> findByFurnitureNumber(Integer furnitureNumber);
}