package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.PayPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PayPlanRepository extends JpaRepository<PayPlan, Long> {
    
    // Buscar plan de pago por proyecto (relación uno a uno)
    Optional<PayPlan> findByIdProject(String idProject);
    
    // Buscar planes de pago por estado
    List<PayPlan> findByState(String state);
    
    // Buscar planes con deuda mayor a un monto
    @Query("SELECT pp FROM PayPlan pp WHERE pp.totalDebt > :amount")
    List<PayPlan> findByTotalDebtGreaterThan(@Param("amount") BigDecimal amount);
    
    // Buscar planes completamente pagados
    @Query("SELECT pp FROM PayPlan pp WHERE pp.totalPayed >= pp.totalDebt")
    List<PayPlan> findFullyPaidPlans();
    
    // Buscar planes con deuda pendiente
    @Query("SELECT pp FROM PayPlan pp WHERE pp.totalPayed < pp.totalDebt")
    List<PayPlan> findPlansWithPendingDebt();
    
    // Buscar planes por número de pagos realizados
    List<PayPlan> findByNumberPays(Integer numberPays);
    
    // Buscar planes por número de deudas
    List<PayPlan> findByNumberDebt(Integer numberDebt);
    
    // Obtener total de deuda pendiente
    @Query("SELECT SUM(pp.totalDebt - pp.totalPayed) FROM PayPlan pp WHERE pp.totalPayed < pp.totalDebt")
    BigDecimal getTotalPendingDebt();
    
    // Obtener total pagado por todos los proyectos
    @Query("SELECT SUM(pp.totalPayed) FROM PayPlan pp")
    BigDecimal getTotalPaidAmount();
    
    // Contar planes por estado
    @Query("SELECT COUNT(pp) FROM PayPlan pp WHERE pp.state = :state")
    long countByState(@Param("state") String state);
    
    // Buscar planes con balance específico
    @Query("SELECT pp FROM PayPlan pp WHERE (pp.totalDebt - pp.totalPayed) = :balance")
    List<PayPlan> findByBalance(@Param("balance") BigDecimal balance);
}