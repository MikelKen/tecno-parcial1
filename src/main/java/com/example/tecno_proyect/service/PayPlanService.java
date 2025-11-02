package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.PayPlan;
import com.example.tecno_proyect.repository.PayPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PayPlanService {
    
    @Autowired
    private PayPlanRepository payPlanRepository;
    
    /**
     * Listar todos los planes de pago
     */
    public List<PayPlan> listarTodosLosPlanesPago() {
        return payPlanRepository.findAll();
    }
    
    /**
     * Buscar plan de pago por ID (proyecto)
     */
    public Optional<PayPlan> buscarPlanPagoPorId(Long idPayPlan) {
        return payPlanRepository.findById(idPayPlan);
    }
    
    /**
     * Buscar plan de pago por proyecto
     */
    public Optional<PayPlan> buscarPlanPagoPorProyecto(String idProject) {
        return payPlanRepository.findByIdProject(idProject);
    }
    
    /**
     * Insertar nuevo plan de pago
     */
    public PayPlan insertarPlanPago(String idProject, BigDecimal totalDebt, BigDecimal totalPayed, Integer numberDebt, 
                                   Integer numberPays, String state) {
        PayPlan payPlan = new PayPlan(idProject, totalDebt, totalPayed, numberDebt, numberPays, state);
        return payPlanRepository.save(payPlan);
    }
    
    /**
     * Actualizar plan de pago existente
     */
    public PayPlan actualizarPlanPago(Long idPayPlan, String idProject, BigDecimal totalDebt, BigDecimal totalPayed, 
                                     Integer numberDebt, Integer numberPays, String state) {
        Optional<PayPlan> planExistente = payPlanRepository.findById(idPayPlan);
        
        if (planExistente.isEmpty()) {
            throw new RuntimeException("No se encontró plan de pago con ID: " + idPayPlan);
        }
        
        PayPlan payPlan = planExistente.get();
        payPlan.setIdProject(idProject);
        payPlan.setTotalDebt(totalDebt);
        payPlan.setTotalPayed(totalPayed);
        payPlan.setNumberDebt(numberDebt);
        payPlan.setNumberPays(numberPays);
        payPlan.setState(state);
        
        return payPlanRepository.save(payPlan);
    }
    
    /**
     * Eliminar plan de pago por ID
     */
    public boolean eliminarPlanPago(Long idPayPlan) {
        if (payPlanRepository.existsById(idPayPlan)) {
            payPlanRepository.deleteById(idPayPlan);
            return true;
        }
        return false;
    }
    
    /**
     * Buscar planes de pago por estado
     */
    public List<PayPlan> buscarPlanesPagoPorEstado(String state) {
        return payPlanRepository.findByState(state);
    }
    
    /**
     * Buscar planes con deuda mayor a un monto
     */
    public List<PayPlan> buscarPlanesConDeudaMayor(BigDecimal amount) {
        return payPlanRepository.findByTotalDebtGreaterThan(amount);
    }
    
    /**
     * Buscar planes completamente pagados
     */
    public List<PayPlan> buscarPlanesCompletamentePagados() {
        return payPlanRepository.findFullyPaidPlans();
    }
    
    /**
     * Buscar planes con deuda pendiente
     */
    public List<PayPlan> buscarPlanesConDeudaPendiente() {
        return payPlanRepository.findPlansWithPendingDebt();
    }
    
    /**
     * Buscar planes por número de pagos realizados
     */
    public List<PayPlan> buscarPlanesPorNumeroPagos(Integer numberPays) {
        return payPlanRepository.findByNumberPays(numberPays);
    }
    
    /**
     * Buscar planes por número de deudas
     */
    public List<PayPlan> buscarPlanesPorNumeroDeudas(Integer numberDebt) {
        return payPlanRepository.findByNumberDebt(numberDebt);
    }
    
    /**
     * Buscar planes por balance específico
     */
    public List<PayPlan> buscarPlanesPorBalance(BigDecimal balance) {
        return payPlanRepository.findByBalance(balance);
    }
    
    /**
     * Obtener total de deuda pendiente
     */
    public BigDecimal obtenerTotalDeudaPendiente() {
        BigDecimal total = payPlanRepository.getTotalPendingDebt();
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Obtener total pagado por todos los proyectos
     */
    public BigDecimal obtenerTotalPagado() {
        BigDecimal total = payPlanRepository.getTotalPaidAmount();
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Contar planes por estado
     */
    public long contarPlanesPorEstado(String state) {
        return payPlanRepository.countByState(state);
    }
    
    /**
     * Verificar si existe un plan de pago
     */
    public boolean existePlanPago(Long idPayPlan) {
        return payPlanRepository.existsById(idPayPlan);
    }
    
    /**
     * Contar total de planes de pago
     */
    public long contarPlanesPago() {
        return payPlanRepository.count();
    }
    
    /**
     * Actualizar deuda total del plan de pago
     */
    public PayPlan actualizarDeudaTotal(Long idPayPlan, BigDecimal nuevaDeuda) {
        Optional<PayPlan> planOpt = payPlanRepository.findById(idPayPlan);
        if (planOpt.isPresent()) {
            PayPlan payPlan = planOpt.get();
            payPlan.setTotalDebt(nuevaDeuda);
            return payPlanRepository.save(payPlan);
        }
        throw new RuntimeException("No se encontró plan de pago con ID: " + idPayPlan);
    }
    
    /**
     * Actualizar total pagado
     */
    public PayPlan actualizarTotalPagado(Long idPayPlan, BigDecimal totalPagado) {
        Optional<PayPlan> planOpt = payPlanRepository.findById(idPayPlan);
        if (planOpt.isPresent()) {
            PayPlan payPlan = planOpt.get();
            payPlan.setTotalPayed(totalPagado);
            return payPlanRepository.save(payPlan);
        }
        throw new RuntimeException("No se encontró plan de pago con ID: " + idPayPlan);
    }
    
    /**
     * Registrar pago
     */
    public PayPlan registrarPago(Long idPayPlan, BigDecimal montoPago) {
        Optional<PayPlan> planOpt = payPlanRepository.findById(idPayPlan);
        if (planOpt.isPresent()) {
            PayPlan payPlan = planOpt.get();
            BigDecimal totalPagadoActual = payPlan.getTotalPayed() != null ? payPlan.getTotalPayed() : BigDecimal.ZERO;
            BigDecimal nuevoTotalPagado = totalPagadoActual.add(montoPago);
            
            payPlan.setTotalPayed(nuevoTotalPagado);
            
            // Incrementar número de pagos
            Integer numeroPagosActual = payPlan.getNumberPays() != null ? payPlan.getNumberPays() : 0;
            payPlan.setNumberPays(numeroPagosActual + 1);
            
            // Actualizar estado si está completamente pagado
            if (nuevoTotalPagado.compareTo(payPlan.getTotalDebt()) >= 0) {
                payPlan.setState("Pagado");
            }
            
            return payPlanRepository.save(payPlan);
        }
        throw new RuntimeException("No se encontró plan de pago con ID: " + idPayPlan);
    }
    
    /**
     * Calcular balance pendiente
     */
    public BigDecimal calcularBalancePendiente(Long idPayPlan) {
        Optional<PayPlan> planOpt = payPlanRepository.findById(idPayPlan);
        if (planOpt.isPresent()) {
            PayPlan payPlan = planOpt.get();
            BigDecimal totalDeuda = payPlan.getTotalDebt() != null ? payPlan.getTotalDebt() : BigDecimal.ZERO;
            BigDecimal totalPagado = payPlan.getTotalPayed() != null ? payPlan.getTotalPayed() : BigDecimal.ZERO;
            return totalDeuda.subtract(totalPagado);
        }
        return BigDecimal.ZERO;
    }
    
    /**
     * Verificar si plan está completamente pagado
     */
    public boolean estaPagadoCompletamente(Long idPayPlan) {
        BigDecimal balance = calcularBalancePendiente(idPayPlan);
        return balance.compareTo(BigDecimal.ZERO) <= 0;
    }
    
    /**
     * Calcular porcentaje de pago
     */
    public BigDecimal calcularPorcentajePago(Long idPayPlan) {
        Optional<PayPlan> planOpt = payPlanRepository.findById(idPayPlan);
        if (planOpt.isPresent()) {
            PayPlan payPlan = planOpt.get();
            BigDecimal totalDeuda = payPlan.getTotalDebt();
            BigDecimal totalPagado = payPlan.getTotalPayed() != null ? payPlan.getTotalPayed() : BigDecimal.ZERO;
            
            if (totalDeuda == null || totalDeuda.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            }
            
            return totalPagado.divide(totalDeuda, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }
    
    /**
     * Validar datos de plan de pago
     */
    public boolean validarDatosPlanPago(String idProject, BigDecimal totalDebt, BigDecimal totalPayed, Integer numberDebt, 
                                       Integer numberPays, String state) {
        if (idProject == null || idProject.trim().isEmpty()) {
            return false;
        }
        if (totalDebt == null || totalDebt.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        if (totalPayed == null || totalPayed.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        if (numberDebt == null || numberDebt < 0) {
            return false;
        }
        if (numberPays == null || numberPays < 0) {
            return false;
        }
        if (state == null || state.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    
    /**
     * Cambiar estado del plan de pago
     */
    public PayPlan cambiarEstado(Long idPayPlan, String nuevoEstado) {
        Optional<PayPlan> planOpt = payPlanRepository.findById(idPayPlan);
        if (planOpt.isPresent()) {
            PayPlan payPlan = planOpt.get();
            payPlan.setState(nuevoEstado);
            return payPlanRepository.save(payPlan);
        }
        throw new RuntimeException("No se encontró plan de pago con ID: " + idPayPlan);
    }
}