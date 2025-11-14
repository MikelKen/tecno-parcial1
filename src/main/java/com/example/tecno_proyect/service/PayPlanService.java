package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.PlanPago;
import com.example.tecno_proyect.model.Pago;
import com.example.tecno_proyect.repository.PayPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    
    @Autowired
    @Lazy
    private PaysService paysService;
    
    /**
     * Listar todos los planes de pago
     */
    public List<PlanPago> listarTodosLosPlanesPago() {
        return payPlanRepository.findAll();
    }
    
    /**
     * Buscar plan de pago por ID
     */
    public Optional<PlanPago> buscarPlanPagoPorId(Long idPlanPago) {
        return payPlanRepository.findById(idPlanPago);
    }
    
    /**
     * Buscar plan de pago por proyecto
     */
    public Optional<PlanPago> buscarPlanPagoPorProyecto(Long idProyecto) {
        List<PlanPago> planes = payPlanRepository.findByIdProyecto(idProyecto);
        return planes.isEmpty() ? Optional.empty() : Optional.of(planes.get(0));
    }
    
    /**
     * Insertar nuevo plan de pago
     */
    public PlanPago insertarPlanPago(Long idProyecto, BigDecimal totalDeuda, BigDecimal totalPagado, Integer numeroDeuda, Integer numeroPagos, String estado) {
        PlanPago planPago = new PlanPago(idProyecto, totalDeuda, totalPagado, numeroDeuda, numeroPagos, estado);
        return payPlanRepository.save(planPago);
    }
    
    /**
     * Actualizar plan de pago existente
     */
    public PlanPago actualizarPlanPago(Long idPlanPago, Long idProyecto, BigDecimal totalDeuda, BigDecimal totalPagado, Integer numeroDeuda, Integer numeroPagos, String estado) {
        Optional<PlanPago> planExistente = payPlanRepository.findById(idPlanPago);
        
        if (planExistente.isEmpty()) {
            throw new RuntimeException("No se encontró plan de pago con ID: " + idPlanPago);
        }
        
        PlanPago planPago = planExistente.get();
        planPago.setIdProyecto(idProyecto);
        planPago.setTotalDeuda(totalDeuda);
        planPago.setTotalPagado(totalPagado);
        planPago.setNumeroDeuda(numeroDeuda);
        planPago.setNumeroPagos(numeroPagos);
        planPago.setEstado(estado);
        
        return payPlanRepository.save(planPago);
    }
    
    /**
     * Eliminar plan de pago por ID
     */
    public boolean eliminarPlanPago(Long idPlanPago) {
        if (payPlanRepository.existsById(idPlanPago)) {
            payPlanRepository.deleteById(idPlanPago);
            return true;
        }
        return false;
    }
    
    /**
     * Obtener plan de pago completo con información de pagos
     */
    public PlanPago obtenerPlanPagoCompleto(Long idPlanPago) {
        Optional<PlanPago> planOpt = payPlanRepository.findById(idPlanPago);
        
        if (planOpt.isPresent()) {
            PlanPago plan = planOpt.get();
            // Obtener información de pagos relacionados
            List<Pago> pagos = paysService.buscarPagosPorPlanPago(idPlanPago);
            
            // Actualizar totales basado en los pagos
            BigDecimal totalPagado = pagos.stream()
                    .map(Pago::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            plan.setTotalPagado(totalPagado);
            
            return payPlanRepository.save(plan);
        }
        
        throw new RuntimeException("No se encontró plan de pago con ID: " + idPlanPago);
    }
    
    /**
     * Actualizar estado de plan de pago
     */
    public PlanPago actualizarEstadoPlanPago(Long idPlanPago, String nuevoEstado) {
        Optional<PlanPago> planOpt = payPlanRepository.findById(idPlanPago);
        
        if (planOpt.isPresent()) {
            PlanPago plan = planOpt.get();
            plan.setEstado(nuevoEstado);
            return payPlanRepository.save(plan);
        }
        
        throw new RuntimeException("No se encontró plan de pago con ID: " + idPlanPago);
    }
    
    /**
     * Registrar pago en plan de pago
     */
    public PlanPago registrarPago(Long idPlanPago, BigDecimal montoPago) {
        Optional<PlanPago> planOpt = payPlanRepository.findById(idPlanPago);
        
        if (planOpt.isEmpty()) {
            throw new RuntimeException("No se encontró plan de pago con ID: " + idPlanPago);
        }
        
        PlanPago plan = planOpt.get();
        
        // Verificar si el monto no excede la deuda pendiente
        BigDecimal deudaPendiente = plan.getTotalDeuda().subtract(plan.getTotalPagado());
        
        if (montoPago.compareTo(deudaPendiente) > 0) {
            throw new RuntimeException("El monto a pagar excede la deuda pendiente");
        }
        
        // Actualizar total pagado
        BigDecimal nuevoTotalPagado = plan.getTotalPagado().add(montoPago);
        plan.setTotalPagado(nuevoTotalPagado);
        
        // Si la deuda está completamente pagada, cambiar estado a "PAGADO"
        if (nuevoTotalPagado.compareTo(plan.getTotalDeuda()) >= 0) {
            plan.setEstado("PAGADO");
        }
        
        return payPlanRepository.save(plan);
    }
    
    /**
     * Calcular deuda pendiente de plan de pago
     */
    public BigDecimal calcularDeudaPendiente(Long idPlanPago) {
        Optional<PlanPago> planOpt = payPlanRepository.findById(idPlanPago);
        
        if (planOpt.isPresent()) {
            PlanPago plan = planOpt.get();
            return plan.getTotalDeuda().subtract(plan.getTotalPagado());
        }
        
        throw new RuntimeException("No se encontró plan de pago con ID: " + idPlanPago);
    }
    
    /**
     * Calcular porcentaje pagado de plan de pago
     */
    public BigDecimal calcularPorcentajePagado(Long idPlanPago) {
        Optional<PlanPago> planOpt = payPlanRepository.findById(idPlanPago);
        
        if (planOpt.isPresent()) {
            PlanPago plan = planOpt.get();
            if (plan.getTotalDeuda().compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            }
            return plan.getTotalPagado()
                    .divide(plan.getTotalDeuda(), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        
        throw new RuntimeException("No se encontró plan de pago con ID: " + idPlanPago);
    }
    
    /**
     * Buscar planes de pago por estado
     */
    public List<PlanPago> buscarPlanesPorEstado(String estado) {
        return payPlanRepository.findByEstado(estado);
    }
    
    /**
     * Buscar planes de pago completamente pagados
     */
    public List<PlanPago> buscarPlanesPagados() {
        return payPlanRepository.findFullyPaidPlans();
    }
    
    /**
     * Buscar planes de pago con deuda pendiente
     */
    public List<PlanPago> buscarPlanesPendientes() {
        return payPlanRepository.findPlansWithPendingDebt();
    }
    
    /**
     * Obtener total de deuda pendiente
     */
    public BigDecimal obtenerTotalDeudaPendiente() {
        BigDecimal total = payPlanRepository.getTotalPendingDebt();
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Obtener total de pagos realizados
     */
    public BigDecimal obtenerTotalPagosRealizados() {
        BigDecimal total = payPlanRepository.getTotalPaidAmount();
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Contar planes de pago por estado
     */
    public long contarPlanesPorEstado(String estado) {
        return payPlanRepository.countByEstado(estado);
    }
    
    /**
     * Contar total de planes de pago
     */
    public long contarTotalPlanes() {
        return payPlanRepository.count();
    }
}
