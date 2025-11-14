package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Pago;
import com.example.tecno_proyect.repository.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaysService {
    
    @Autowired
    private PaysRepository paysRepository;
    
    /**
     * Listar todos los pagos
     */
    public List<Pago> listarTodosLosPagos() {
        return paysRepository.findAll();
    }
    
    /**
     * Buscar pago por ID
     */
    public Optional<Pago> buscarPagoPorId(Long id) {
        return paysRepository.findById(id);
    }
    
    /**
     * Insertar nuevo pago
     */
    public Pago insertarPago(LocalDate fecha, BigDecimal total, String estado, Long idCliente, Long idPlanPago) {
        Pago pago = new Pago(fecha, total, estado, idCliente, idPlanPago);
        return paysRepository.save(pago);
    }
    
    /**
     * Actualizar pago existente
     */
    public Pago actualizarPago(Long id, LocalDate fecha, BigDecimal total, String estado, Long idCliente, Long idPlanPago) {
        Optional<Pago> pagoExistente = paysRepository.findById(id);
        
        if (pagoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró pago con ID: " + id);
        }
        
        Pago pago = pagoExistente.get();
        pago.setFecha(fecha);
        pago.setTotal(total);
        pago.setEstado(estado);
        pago.setIdCliente(idCliente);
        pago.setIdPlanPago(idPlanPago);
        
        return paysRepository.save(pago);
    }
    
    /**
     * Eliminar pago por ID
     */
    public boolean eliminarPago(Long id) {
        if (paysRepository.existsById(id)) {
            paysRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Buscar pagos por cliente
     */
    public List<Pago> buscarPagosPorCliente(Long idCliente) {
        return paysRepository.findByIdCliente(idCliente);
    }
    
    /**
     * Buscar pagos por plan de pago
     */
    public List<Pago> buscarPagosPorPlanPago(Long idPlanPago) {
        return paysRepository.findByIdPlanPago(idPlanPago);
    }
    
    /**
     * Buscar pagos por estado
     */
    public List<Pago> buscarPagosPorEstado(String estado) {
        return paysRepository.findByEstado(estado);
    }
    
    /**
     * Buscar pagos por fecha específica
     */
    public List<Pago> buscarPagosPorFecha(LocalDate fecha) {
        return paysRepository.findByFecha(fecha);
    }
    
    /**
     * Buscar pagos por rango de fechas
     */
    public List<Pago> buscarPagosPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFinal) {
        return paysRepository.findByFechaBetween(fechaInicio, fechaFinal);
    }
    
    /**
     * Buscar pagos por rango de montos
     */
    public List<Pago> buscarPagosPorRangoMontos(BigDecimal totalMin, BigDecimal totalMax) {
        return paysRepository.findByTotalBetween(totalMin, totalMax);
    }
    
    /**
     * Buscar pagos mayores a un monto
     */
    public List<Pago> buscarPagosMayoresA(BigDecimal monto) {
        return paysRepository.findByTotalGreaterThan(monto);
    }
    
    /**
     * Buscar pagos por cliente y estado
     */
    public List<Pago> buscarPagosPorClienteYEstado(Long idCliente, String estado) {
        return paysRepository.findByIdClienteAndEstado(idCliente, estado);
    }
    
    /**
     * Buscar pagos del mes actual
     */
    public List<Pago> buscarPagosMesActual() {
        return paysRepository.findCurrentMonthPays();
    }
    
    /**
     * Buscar últimos pagos
     */
    public List<Pago> buscarUltimosPagos() {
        return paysRepository.findLatestPays();
    }
    
    /**
     * Obtener total pagado por cliente
     */
    public BigDecimal obtenerTotalPagadoPorCliente(Long idCliente) {
        BigDecimal total = paysRepository.getTotalPaidByClient(idCliente);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Obtener total pagado por plan de pago
     */
    public BigDecimal obtenerTotalPagadoPorPlanPago(Long idPlanPago) {
        BigDecimal total = paysRepository.getTotalPaidByPayPlan(idPlanPago);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Contar pagos por estado
     */
    public long contarPagosPorEstado(String estado) {
        return paysRepository.countByEstado(estado);
    }
    
    /**
     * Verificar si existe un pago
     */
    public boolean existePago(Long id) {
        return paysRepository.existsById(id);
    }
    
    /**
     * Contar total de pagos
     */
    public long contarPagos() {
        return paysRepository.count();
    }
    
    /**
     * Actualizar total del pago
     */
    public Pago actualizarTotalPago(Long id, BigDecimal nuevoTotal) {
        Optional<Pago> pagoOpt = paysRepository.findById(id);
        if (pagoOpt.isPresent()) {
            Pago pago = pagoOpt.get();
            pago.setTotal(nuevoTotal);
            return paysRepository.save(pago);
        }
        throw new RuntimeException("No se encontró pago con ID: " + id);
    }
    
    /**
     * Actualizar estado del pago
     */
    public Pago actualizarEstadoPago(Long id, String nuevoEstado) {
        Optional<Pago> pagoOpt = paysRepository.findById(id);
        if (pagoOpt.isPresent()) {
            Pago pago = pagoOpt.get();
            pago.setEstado(nuevoEstado);
            return paysRepository.save(pago);
        }
        throw new RuntimeException("No se encontró pago con ID: " + id);
    }
    
    /**
     * Validar datos de pago
     */
    public boolean validarDatosPago(LocalDate fecha, BigDecimal total, String estado, Long idCliente, Long idPlanPago) {
        if (fecha == null) {
            return false;
        }
        if (total == null || total.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (estado == null || estado.trim().isEmpty()) {
            return false;
        }
        if (idCliente == null) {
            return false;
        }
        if (idPlanPago == null) {
            return false;
        }
        return true;
    }
    
    /**
     * Obtener pagos por año
     */
    public List<Pago> obtenerPagosPorAno(int anio) {
        LocalDate inicioAnio = LocalDate.of(anio, 1, 1);
        LocalDate finAnio = LocalDate.of(anio, 12, 31);
        return buscarPagosPorRangoFechas(inicioAnio, finAnio);
    }
    
    /**
     * Obtener total pagado por año
     */
    public BigDecimal obtenerTotalPagadoPorAno(int anio) {
        List<Pago> pagosPorAnio = obtenerPagosPorAno(anio);
        return pagosPorAnio.stream()
                .map(Pago::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Verificar si un cliente tiene pagos
     */
    public boolean clienteTienePagos(Long idCliente) {
        return !buscarPagosPorCliente(idCliente).isEmpty();
    }
    
    /**
     * Verificar si un plan de pago tiene pagos
     */
    public boolean planPagoTienePagos(Long idPlanPago) {
        return !buscarPagosPorPlanPago(idPlanPago).isEmpty();
    }
    
    /**
     * Buscar último pago por cliente
     */
    public Optional<Pago> buscarUltimoPagoPorCliente(Long idCliente) {
        List<Pago> pagos = buscarPagosPorCliente(idCliente);
        return pagos.stream()
                .max((p1, p2) -> p1.getFecha().compareTo(p2.getFecha()));
    }
    
    /**
     * Buscar primer pago por cliente
     */
    public Optional<Pago> buscarPrimerPagoPorCliente(Long idCliente) {
        List<Pago> pagos = buscarPagosPorCliente(idCliente);
        return pagos.stream()
                .min((p1, p2) -> p1.getFecha().compareTo(p2.getFecha()));
    }
    
    /**
     * Calcular promedio de pagos por cliente
     */
    public BigDecimal calcularPromedioPagosPorCliente(Long idCliente) {
        List<Pago> pagos = buscarPagosPorCliente(idCliente);
        if (pagos.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal total = pagos.stream()
                .map(Pago::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return total.divide(BigDecimal.valueOf(pagos.size()), 2, RoundingMode.HALF_UP);
    }
    
    /**
     * Marcar pago como completado
     */
    public Pago marcarPagoCompletado(Long id) {
        return actualizarEstadoPago(id, "Completado");
    }
    
    /**
     * Marcar pago como pendiente
     */
    public Pago marcarPagoPendiente(Long id) {
        return actualizarEstadoPago(id, "Pendiente");
    }
    
    /**
     * Marcar pago como cancelado
     */
    public Pago marcarPagoCancelado(Long id) {
        return actualizarEstadoPago(id, "Cancelado");
    }
    
    /**
     * Buscar pagos completados
     */
    public List<Pago> buscarPagosCompletados() {
        return buscarPagosPorEstado("Completado");
    }
    
    /**
     * Buscar pagos pendientes
     */
    public List<Pago> buscarPagosPendientes() {
        return buscarPagosPorEstado("Pendiente");
    }
    
    /**
     * Contar pagos por cliente
     */
    public long contarPagosPorCliente(Long idCliente) {
        return buscarPagosPorCliente(idCliente).size();
    }
    
    /**
     * Contar pagos por plan de pago
     */
    public long contarPagosPorPlanPago(Long idPlanPago) {
        return buscarPagosPorPlanPago(idPlanPago).size();
    }
    
    /**
     * Obtener todos los pagos de un plan de pago
     */
    public List<Pago> obtenerPagosPorPlanPago(Long idPlanPago) {
        return buscarPagosPorPlanPago(idPlanPago);
    }
    
    /**
     * Pagar - cambiar estado a pagado y actualizar plan asociado
     */
    @Autowired
    private PayPlanService payPlanService;
    
    public Pago pagar(Long idPago, LocalDate fechaPago, Long idCliente) {
        Optional<Pago> pagoOpt = paysRepository.findById(idPago);
        if (pagoOpt.isPresent()) {
            Pago pago = pagoOpt.get();
            
            // Actualizar el pago
            pago.setEstado("Pagado");
            pago.setFecha(fechaPago);
            if (idCliente != null) {
                pago.setIdCliente(idCliente);
            }
            
            Pago pagoActualizado = paysRepository.save(pago);
            
            // Actualizar el plan de pago asociado
            if (pago.getIdPlanPago() != null) {
                payPlanService.obtenerPlanPagoCompleto(pago.getIdPlanPago());
            }
            
            return pagoActualizado;
        }
        throw new RuntimeException("No se encontró pago con ID: " + idPago);
    }
}
