package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Cuota;
import com.example.tecno_proyect.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuoteService {
    
    @Autowired
    private QuoteRepository quoteRepository;
    
    /**
     * Listar todas las cotizaciones
     */
    public List<Cuota> listarTodasLasCotizaciones() {
        return quoteRepository.findAll();
    }
    
    /**
     * Buscar cotización por ID
     */
    public Optional<Cuota> buscarCotizacionPorId(Long id) {
        return quoteRepository.findById(id);
    }
    
    /**
     * Insertar nueva cotización
     */
    public Cuota insertarCotizacion(String tipoMetro, BigDecimal costoMetro, BigDecimal cantidadMetro,
                                   BigDecimal costoMuebles, BigDecimal total, String estado,
                                   Integer numeroMuebles, String comentarios, Long idProyecto, Long usuarioId) {
        Cuota cuota = new Cuota(tipoMetro, costoMetro, cantidadMetro, costoMuebles, total, 
                               estado, numeroMuebles, comentarios, idProyecto, usuarioId);
        return quoteRepository.save(cuota);
    }
    
    /**
     * Actualizar cotización existente
     */
    public Cuota actualizarCotizacion(Long id, String tipoMetro, BigDecimal costoMetro, BigDecimal cantidadMetro,
                                     BigDecimal costoMuebles, BigDecimal total, String estado,
                                     Integer numeroMuebles, String comentarios, Long idProyecto, Long usuarioId) {
        Optional<Cuota> cotizacionExistente = quoteRepository.findById(id);
        
        if (cotizacionExistente.isEmpty()) {
            throw new RuntimeException("No se encontró cotización con ID: " + id);
        }
        
        Cuota cuota = cotizacionExistente.get();
        cuota.setTipoMetro(tipoMetro);
        cuota.setCostoMetro(costoMetro);
        cuota.setCantidadMetro(cantidadMetro);
        cuota.setCostoMuebles(costoMuebles);
        cuota.setTotal(total);
        cuota.setEstado(estado);
        cuota.setNumeroMuebles(numeroMuebles);
        cuota.setComentarios(comentarios);
        cuota.setIdProyecto(idProyecto);
        cuota.setUsuarioId(usuarioId);
        
        return quoteRepository.save(cuota);
    }
    
    /**
     * Eliminar cotización por ID
     */
    public boolean eliminarCotizacion(Long id) {
        if (quoteRepository.existsById(id)) {
            quoteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Buscar cotizaciones por proyecto
     */
    public List<Cuota> buscarCotizacionesPorProyecto(Long idProyecto) {
        return quoteRepository.findByIdProyecto(idProyecto);
    }
    
    /**
     * Buscar cotizaciones por usuario
     */
    public List<Cuota> buscarCotizacionesPorUsuario(Long usuarioId) {
        return quoteRepository.findByUsuarioId(usuarioId);
    }
    
    /**
     * Buscar cotizaciones por estado
     */
    public List<Cuota> buscarCotizacionesPorEstado(String estado) {
        return quoteRepository.findByEstado(estado);
    }
    
    /**
     * Buscar cotizaciones por tipo de metro
     */
    public List<Cuota> buscarCotizacionesPorTipoMetro(String tipoMetro) {
        return quoteRepository.findByTipoMetro(tipoMetro);
    }
    
    /**
     * Buscar cotizaciones por rango de total
     */
    public List<Cuota> buscarCotizacionesPorRangoTotal(BigDecimal minTotal, BigDecimal maxTotal) {
        return quoteRepository.findByTotalBetween(minTotal, maxTotal);
    }
    
    /**
     * Obtener total de cotizaciones aprobadas por proyecto
     */
    public BigDecimal obtenerTotalCotizacionesAprobadasPorProyecto(String idProyecto) {
        BigDecimal total = quoteRepository.getTotalApprovedQuotesByProject(Long.parseLong(idProyecto));
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Cambiar estado de cotización
     */
    public Cuota cambiarEstadoCotizacion(Long id, String nuevoEstado) {
        Optional<Cuota> cotizacionOpt = quoteRepository.findById(id);
        if (cotizacionOpt.isPresent()) {
            Cuota cuota = cotizacionOpt.get();
            cuota.setEstado(nuevoEstado);
            return quoteRepository.save(cuota);
        }
        throw new RuntimeException("No se encontró cotización con ID: " + id);
    }
    
    /**
     * Calcular total de cotización
     */
    public BigDecimal calcularTotalCotizacion(BigDecimal costoMetro, BigDecimal cantidadMetro, BigDecimal costoMuebles) {
        BigDecimal totalMetros = costoMetro.multiply(cantidadMetro);
        return totalMetros.add(costoMuebles);
    }
    
    /**
     * Contar cotizaciones por estado
     */
    public long contarCotizacionesPorEstado(String estado) {
        return quoteRepository.countByEstado(estado);
    }
    
    /**
     * Verificar si existe una cotización
     */
    public boolean existeCotizacion(Long id) {
        return quoteRepository.existsById(id);
    }
    
    /**
     * Contar total de cotizaciones
     */
    public long contarCotizaciones() {
        return quoteRepository.count();
    }
    
    /**
     * Aprobar cotización
     */
    public Cuota aprobarCotizacion(Long id) {
        return cambiarEstadoCotizacion(id, "Aprobada");
    }
    
    /**
     * Rechazar cotización
     */
    public Cuota rechazarCotizacion(Long id) {
        return cambiarEstadoCotizacion(id, "Rechazada");
    }
}
