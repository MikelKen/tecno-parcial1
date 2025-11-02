package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaysRepository extends JpaRepository<Pays, Long> {
    
    // Buscar pagos por cliente
    List<Pays> findByIdClient(String idClient);
    
    // Buscar pagos por plan de pago
    List<Pays> findByIdPayPlan(Long idPayPlan);
    
    // Buscar pagos por estado
    List<Pays> findByState(String state);
    
    // Buscar pagos por fecha
    List<Pays> findByDate(LocalDate date);
    
    // Buscar pagos por rango de fechas
    @Query("SELECT p FROM Pays p WHERE p.date BETWEEN :startDate AND :endDate")
    List<Pays> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Buscar pagos por rango de montos
    @Query("SELECT p FROM Pays p WHERE p.total BETWEEN :minAmount AND :maxAmount")
    List<Pays> findByTotalBetween(@Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount);
    
    // Buscar pagos mayores a un monto
    @Query("SELECT p FROM Pays p WHERE p.total > :amount")
    List<Pays> findByTotalGreaterThan(@Param("amount") BigDecimal amount);
    
    // Obtener total pagado por cliente
    @Query("SELECT SUM(p.total) FROM Pays p WHERE p.idClient = :idClient AND p.state = 'Completado'")
    BigDecimal getTotalPaidByClient(@Param("idClient") String idClient);
    
    // Obtener total pagado por plan de pago
    @Query("SELECT SUM(p.total) FROM Pays p WHERE p.idPayPlan = :idPayPlan AND p.state = 'Completado'")
    BigDecimal getTotalPaidByPayPlan(@Param("idPayPlan") Long idPayPlan);
    
    // Buscar pagos del mes actual
    @Query("SELECT p FROM Pays p WHERE MONTH(p.date) = MONTH(CURRENT_DATE) AND YEAR(p.date) = YEAR(CURRENT_DATE)")
    List<Pays> findCurrentMonthPays();
    
    // Contar pagos por estado
    @Query("SELECT COUNT(p) FROM Pays p WHERE p.state = :state")
    long countByState(@Param("state") String state);
    
    // Buscar pagos por cliente y estado
    List<Pays> findByIdClientAndState(String idClient, String state);
    
    // Buscar últimos pagos (ordenados por fecha descendente)
    @Query("SELECT p FROM Pays p ORDER BY p.date DESC")
    List<Pays> findLatestPays();
}