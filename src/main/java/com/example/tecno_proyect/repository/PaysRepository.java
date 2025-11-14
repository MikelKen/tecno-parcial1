package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaysRepository extends JpaRepository<Pago, Long> {
    
    // Buscar pagos por cliente
    List<Pago> findByIdCliente(Long idCliente);
    
    // Buscar pagos por plan de pago
    List<Pago> findByIdPlanPago(Long idPlanPago);
    
    // Buscar pagos por estado
    List<Pago> findByEstado(String estado);
    
    // Buscar pagos por fecha
    List<Pago> findByFecha(LocalDate fecha);
    
    // Buscar pagos por rango de fechas
    @Query("SELECT p FROM Pago p WHERE p.fecha BETWEEN :startDate AND :endDate")
    List<Pago> findByFechaBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Buscar pagos por rango de montos
    @Query("SELECT p FROM Pago p WHERE p.total BETWEEN :minAmount AND :maxAmount")
    List<Pago> findByTotalBetween(@Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount);
    
    // Buscar pagos mayores a un monto
    @Query("SELECT p FROM Pago p WHERE p.total > :amount")
    List<Pago> findByTotalGreaterThan(@Param("amount") BigDecimal amount);
    
    // Obtener total pagado por cliente
    @Query("SELECT SUM(p.total) FROM Pago p WHERE p.idCliente = :idCliente")
    BigDecimal getTotalPaidByClient(@Param("idCliente") Long idCliente);
    
    // Obtener total pagado por plan de pago
    @Query("SELECT SUM(p.total) FROM Pago p WHERE p.idPlanPago = :idPlanPago AND p.estado = 'Completado'")
    BigDecimal getTotalPaidByPayPlan(@Param("idPlanPago") Long idPlanPago);
    
    // Buscar pagos del mes actual
    @Query("SELECT p FROM Pago p WHERE MONTH(p.fecha) = MONTH(CURRENT_DATE) AND YEAR(p.fecha) = YEAR(CURRENT_DATE)")
    List<Pago> findCurrentMonthPays();
    
    // Contar pagos por estado
    @Query("SELECT COUNT(p) FROM Pago p WHERE p.estado = :estado")
    long countByEstado(@Param("estado") String estado);
    
    // Buscar pagos por cliente y estado
    List<Pago> findByIdClienteAndEstado(Long idCliente, String estado);
    
    // Buscar últimos pagos (ordenados por fecha descendente)
    @Query("SELECT p FROM Pago p ORDER BY p.fecha DESC")
    List<Pago> findLatestPays();
}