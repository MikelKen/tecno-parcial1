package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.PlanPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PayPlanRepository extends JpaRepository<PlanPago, Long> {
    
    // Buscar plan de pago por proyecto (relación uno a muchos)
    List<PlanPago> findByIdProyecto(Long idProyecto);
    
    // Buscar planes de pago por estado
    List<PlanPago> findByEstado(String estado);
    
    // Buscar planes con deuda mayor a un monto
    @Query("SELECT pp FROM PlanPago pp WHERE pp.totalDeuda > :amount")
    List<PlanPago> findByTotalDeudaGreaterThan(@Param("amount") BigDecimal amount);
    
    // Buscar planes completamente pagados
    @Query("SELECT pp FROM PlanPago pp WHERE pp.totalPagado >= pp.totalDeuda")
    List<PlanPago> findFullyPaidPlans();
    
    // Buscar planes con deuda pendiente
    @Query("SELECT pp FROM PlanPago pp WHERE pp.totalPagado < pp.totalDeuda")
    List<PlanPago> findPlansWithPendingDebt();
    
    // Buscar planes por número de pagos realizados
    List<PlanPago> findByNumeroPagos(Integer numeroPagos);
    
    // Buscar planes por número de deudas
    List<PlanPago> findByNumeroDeuda(Integer numeroDeuda);
    
    // Obtener total de deuda pendiente
    @Query("SELECT SUM(pp.totalDeuda - pp.totalPagado) FROM PlanPago pp WHERE pp.totalPagado < pp.totalDeuda")
    BigDecimal getTotalPendingDebt();
    
    // Obtener total pagado por todos los proyectos
    @Query("SELECT SUM(pp.totalPagado) FROM PlanPago pp")
    BigDecimal getTotalPaidAmount();
    
    // Contar planes por estado
    @Query("SELECT COUNT(pp) FROM PlanPago pp WHERE pp.estado = :estado")
    long countByEstado(@Param("estado") String estado);
    
    // Buscar planes con balance específico
    @Query("SELECT pp FROM PlanPago pp WHERE (pp.totalDeuda - pp.totalPagado) = :balance")
    List<PlanPago> findByBalance(@Param("balance") BigDecimal balance);
}