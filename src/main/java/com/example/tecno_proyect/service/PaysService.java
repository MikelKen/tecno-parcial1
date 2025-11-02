package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Pays;
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
    public List<Pays> listarTodosLosPagos() {
        return paysRepository.findAll();
    }
    
    /**
     * Buscar pago por ID
     */
    public Optional<Pays> buscarPagoPorId(Long id) {
        return paysRepository.findById(id);
    }
    
    /**
     * Insertar nuevo pago
     */
    public Pays insertarPago(LocalDate date, BigDecimal total, String state, String idClient, Long idPayPlan) {
        Pays pays = new Pays(date, total, state, idClient, idPayPlan);
        return paysRepository.save(pays);
    }
    
    /**
     * Actualizar pago existente
     */
    public Pays actualizarPago(Long id, LocalDate date, BigDecimal total, String state, String idClient, Long idPayPlan) {
        Optional<Pays> pagoExistente = paysRepository.findById(id);
        
        if (pagoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró pago con ID: " + id);
        }
        
        Pays pays = pagoExistente.get();
        pays.setDate(date);
        pays.setTotal(total);
        pays.setState(state);
        pays.setIdClient(idClient);
        pays.setIdPayPlan(idPayPlan);
        
        return paysRepository.save(pays);
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
    public List<Pays> buscarPagosPorCliente(String idClient) {
        return paysRepository.findByIdClient(idClient);
    }
    
    /**
     * Buscar pagos por plan de pago
     */
    public List<Pays> buscarPagosPorPlanPago(Long idPayPlan) {
        return paysRepository.findByIdPayPlan(idPayPlan);
    }
    
    /**
     * Buscar pagos por estado
     */
    public List<Pays> buscarPagosPorEstado(String state) {
        return paysRepository.findByState(state);
    }
    
    /**
     * Buscar pagos por fecha específica
     */
    public List<Pays> buscarPagosPorFecha(LocalDate date) {
        return paysRepository.findByDate(date);
    }
    
    /**
     * Buscar pagos por rango de fechas
     */
    public List<Pays> buscarPagosPorRangoFechas(LocalDate startDate, LocalDate endDate) {
        return paysRepository.findByDateBetween(startDate, endDate);
    }
    
    /**
     * Buscar pagos por rango de montos
     */
    public List<Pays> buscarPagosPorRangoMontos(BigDecimal minAmount, BigDecimal maxAmount) {
        return paysRepository.findByTotalBetween(minAmount, maxAmount);
    }
    
    /**
     * Buscar pagos mayores a un monto
     */
    public List<Pays> buscarPagosMayoresA(BigDecimal amount) {
        return paysRepository.findByTotalGreaterThan(amount);
    }
    
    /**
     * Buscar pagos por cliente y estado
     */
    public List<Pays> buscarPagosPorClienteYEstado(String idClient, String state) {
        return paysRepository.findByIdClientAndState(idClient, state);
    }
    
    /**
     * Buscar pagos del mes actual
     */
    public List<Pays> buscarPagosMesActual() {
        return paysRepository.findCurrentMonthPays();
    }
    
    /**
     * Buscar últimos pagos
     */
    public List<Pays> buscarUltimosPagos() {
        return paysRepository.findLatestPays();
    }
    
    /**
     * Obtener total pagado por cliente
     */
    public BigDecimal obtenerTotalPagadoPorCliente(String idClient) {
        BigDecimal total = paysRepository.getTotalPaidByClient(idClient);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Obtener total pagado por plan de pago
     */
    public BigDecimal obtenerTotalPagadoPorPlanPago(Long idPayPlan) {
        BigDecimal total = paysRepository.getTotalPaidByPayPlan(idPayPlan);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Contar pagos por estado
     */
    public long contarPagosPorEstado(String state) {
        return paysRepository.countByState(state);
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
    public Pays actualizarTotalPago(Long id, BigDecimal nuevoTotal) {
        Optional<Pays> pagoOpt = paysRepository.findById(id);
        if (pagoOpt.isPresent()) {
            Pays pays = pagoOpt.get();
            pays.setTotal(nuevoTotal);
            return paysRepository.save(pays);
        }
        throw new RuntimeException("No se encontró pago con ID: " + id);
    }
    
    /**
     * Actualizar estado del pago
     */
    public Pays actualizarEstadoPago(Long id, String nuevoEstado) {
        Optional<Pays> pagoOpt = paysRepository.findById(id);
        if (pagoOpt.isPresent()) {
            Pays pays = pagoOpt.get();
            pays.setState(nuevoEstado);
            return paysRepository.save(pays);
        }
        throw new RuntimeException("No se encontró pago con ID: " + id);
    }
    
    /**
     * Validar datos de pago
     */
    public boolean validarDatosPago(LocalDate date, BigDecimal total, String state, String idClient, Long idPayPlan) {
        if (date == null) {
            return false;
        }
        if (total == null || total.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (state == null || state.trim().isEmpty()) {
            return false;
        }
        if (idClient == null || idClient.trim().isEmpty()) {
            return false;
        }
        if (idPayPlan == null) {
            return false;
        }
        return true;
    }
    
    /**
     * Obtener pagos por año
     */
    public List<Pays> obtenerPagosPorAno(int year) {
        LocalDate inicioAno = LocalDate.of(year, 1, 1);
        LocalDate finAno = LocalDate.of(year, 12, 31);
        return buscarPagosPorRangoFechas(inicioAno, finAno);
    }
    
    /**
     * Obtener total pagado por año
     */
    public BigDecimal obtenerTotalPagadoPorAno(int year) {
        List<Pays> pagosPorAno = obtenerPagosPorAno(year);
        return pagosPorAno.stream()
                .map(Pays::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Verificar si un cliente tiene pagos
     */
    public boolean clienteTienePagos(String idClient) {
        return !buscarPagosPorCliente(idClient).isEmpty();
    }
    
    /**
     * Verificar si un plan de pago tiene pagos
     */
    public boolean planPagoTienePagos(Long idPayPlan) {
        return !buscarPagosPorPlanPago(idPayPlan).isEmpty();
    }
    
    /**
     * Buscar último pago por cliente
     */
    public Optional<Pays> buscarUltimoPagoPorCliente(String idClient) {
        List<Pays> pagos = buscarPagosPorCliente(idClient);
        return pagos.stream()
                .max((p1, p2) -> p1.getDate().compareTo(p2.getDate()));
    }
    
    /**
     * Buscar primer pago por cliente
     */
    public Optional<Pays> buscarPrimerPagoPorCliente(String idClient) {
        List<Pays> pagos = buscarPagosPorCliente(idClient);
        return pagos.stream()
                .min((p1, p2) -> p1.getDate().compareTo(p2.getDate()));
    }
    
    /**
     * Calcular promedio de pagos por cliente
     */
    public BigDecimal calcularPromedioPagosPorCliente(String idClient) {
        List<Pays> pagos = buscarPagosPorCliente(idClient);
        if (pagos.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal total = pagos.stream()
                .map(Pays::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return total.divide(BigDecimal.valueOf(pagos.size()), 2, RoundingMode.HALF_UP);
    }
    
    /**
     * Marcar pago como completado
     */
    public Pays marcarPagoCompletado(Long id) {
        return actualizarEstadoPago(id, "Completado");
    }
    
    /**
     * Marcar pago como pendiente
     */
    public Pays marcarPagoPendiente(Long id) {
        return actualizarEstadoPago(id, "Pendiente");
    }
    
    /**
     * Marcar pago como cancelado
     */
    public Pays marcarPagoCancelado(Long id) {
        return actualizarEstadoPago(id, "Cancelado");
    }
    
    /**
     * Buscar pagos completados
     */
    public List<Pays> buscarPagosCompletados() {
        return buscarPagosPorEstado("Completado");
    }
    
    /**
     * Buscar pagos pendientes
     */
    public List<Pays> buscarPagosPendientes() {
        return buscarPagosPorEstado("Pendiente");
    }
    
    /**
     * Contar pagos por cliente
     */
    public long contarPagosPorCliente(String idClient) {
        return buscarPagosPorCliente(idClient).size();
    }
    
    /**
     * Contar pagos por plan de pago
     */
    public long contarPagosPorPlanPago(Long idPayPlan) {
        return buscarPagosPorPlanPago(idPayPlan).size();
    }
}