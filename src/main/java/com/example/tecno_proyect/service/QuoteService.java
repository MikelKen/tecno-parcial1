package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Quote;
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
    public List<Quote> listarTodasLasCotizaciones() {
        return quoteRepository.findAll();
    }
    
    /**
     * Buscar cotización por ID
     */
    public Optional<Quote> buscarCotizacionPorId(Long id) {
        return quoteRepository.findById(id);
    }
    
    /**
     * Insertar nueva cotización
     */
    public Quote insertarCotizacion(String typeMetro, BigDecimal costMetro, BigDecimal quantityMetro,
                                   BigDecimal costFurniture, BigDecimal total, String state,
                                   Integer furnitureNumber, String comments, String idProject, Long userId) {
        Quote quote = new Quote(typeMetro, costMetro, quantityMetro, costFurniture, total, 
                               state, furnitureNumber, comments, idProject, userId);
        return quoteRepository.save(quote);
    }
    
    /**
     * Actualizar cotización existente
     */
    public Quote actualizarCotizacion(Long id, String typeMetro, BigDecimal costMetro, BigDecimal quantityMetro,
                                     BigDecimal costFurniture, BigDecimal total, String state,
                                     Integer furnitureNumber, String comments, String idProject, Long userId) {
        Optional<Quote> cotizacionExistente = quoteRepository.findById(id);
        
        if (cotizacionExistente.isEmpty()) {
            throw new RuntimeException("No se encontró cotización con ID: " + id);
        }
        
        Quote quote = cotizacionExistente.get();
        quote.setTypeMetro(typeMetro);
        quote.setCostMetro(costMetro);
        quote.setQuantityMetro(quantityMetro);
        quote.setCostFurniture(costFurniture);
        quote.setTotal(total);
        quote.setState(state);
        quote.setFurnitureNumber(furnitureNumber);
        quote.setComments(comments);
        quote.setIdProject(idProject);
        quote.setUserId(userId);
        
        return quoteRepository.save(quote);
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
    public List<Quote> buscarCotizacionesPorProyecto(String idProject) {
        return quoteRepository.findByIdProject(idProject);
    }
    
    /**
     * Buscar cotizaciones por usuario
     */
    public List<Quote> buscarCotizacionesPorUsuario(Long userId) {
        return quoteRepository.findByUserId(userId);
    }
    
    /**
     * Buscar cotizaciones por estado
     */
    public List<Quote> buscarCotizacionesPorEstado(String state) {
        return quoteRepository.findByState(state);
    }
    
    /**
     * Buscar cotizaciones por tipo de metro
     */
    public List<Quote> buscarCotizacionesPorTipoMetro(String typeMetro) {
        return quoteRepository.findByTypeMetro(typeMetro);
    }
    
    /**
     * Buscar cotizaciones por rango de total
     */
    public List<Quote> buscarCotizacionesPorRangoTotal(BigDecimal minTotal, BigDecimal maxTotal) {
        return quoteRepository.findByTotalBetween(minTotal, maxTotal);
    }
    
    /**
     * Obtener total de cotizaciones aprobadas por proyecto
     */
    public BigDecimal obtenerTotalCotizacionesAprobadasPorProyecto(String idProject) {
        BigDecimal total = quoteRepository.getTotalApprovedQuotesByProject(idProject);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Cambiar estado de cotización
     */
    public Quote cambiarEstadoCotizacion(Long id, String nuevoEstado) {
        Optional<Quote> cotizacionOpt = quoteRepository.findById(id);
        if (cotizacionOpt.isPresent()) {
            Quote quote = cotizacionOpt.get();
            quote.setState(nuevoEstado);
            return quoteRepository.save(quote);
        }
        throw new RuntimeException("No se encontró cotización con ID: " + id);
    }
    
    /**
     * Calcular total de cotización
     */
    public BigDecimal calcularTotalCotizacion(BigDecimal costMetro, BigDecimal quantityMetro, BigDecimal costFurniture) {
        BigDecimal totalMetros = costMetro.multiply(quantityMetro);
        return totalMetros.add(costFurniture);
    }
    
    /**
     * Contar cotizaciones por estado
     */
    public long contarCotizacionesPorEstado(String state) {
        return quoteRepository.countByState(state);
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
    public Quote aprobarCotizacion(Long id) {
        return cambiarEstadoCotizacion(id, "Aprobada");
    }
    
    /**
     * Rechazar cotización
     */
    public Quote rechazarCotizacion(Long id) {
        return cambiarEstadoCotizacion(id, "Rechazada");
    }
}