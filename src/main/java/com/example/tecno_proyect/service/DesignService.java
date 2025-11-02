package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Design;
import com.example.tecno_proyect.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DesignService {
    
    @Autowired
    private DesignRepository designRepository;
    
    /**
     * Listar todos los diseños
     */
    public List<Design> listarTodosLosDisenos() {
        return designRepository.findAll();
    }
    
    /**
     * Buscar diseño por ID
     */
    public Optional<Design> buscarDisenoPorId(Long id) {
        return designRepository.findById(id);
    }
    
    /**
     * Buscar diseño por ID de cotización
     */
    public Optional<Design> buscarDisenoPorCotizacion(Long idQuote) {
        return designRepository.findByIdQuote(idQuote);
    }
    
    /**
     * Insertar nuevo diseño
     */
    public Design insertarDiseno(Long idQuote, String urlRender, String laminatedPlane, Boolean approved, 
                                LocalDate approvedDate, String comments, String userId) {
        Design design = new Design(idQuote, urlRender, laminatedPlane, approved, approvedDate, comments, userId);
        return designRepository.save(design);
    }
    
    /**
     * Actualizar diseño existente
     */
    public Design actualizarDiseno(Long idDesign, Long idQuote, String urlRender, String laminatedPlane, Boolean approved, 
                                  LocalDate approvedDate, String comments, String userId) {
        Optional<Design> disenoExistente = designRepository.findById(idDesign);
        
        if (disenoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró diseño con ID: " + idDesign);
        }
        
        Design design = disenoExistente.get();
        design.setIdQuote(idQuote);
        design.setUrlRender(urlRender);
        design.setLaminatedPlane(laminatedPlane);
        design.setApproved(approved);
        design.setApprovedDate(approvedDate);
        design.setComments(comments);
        design.setUserId(userId);
        
        return designRepository.save(design);
    }
    
    /**
     * Eliminar diseño por ID
     */
    public boolean eliminarDiseno(Long id) {
        if (designRepository.existsById(id)) {
            designRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Buscar diseños por usuario
     */
    public List<Design> buscarDisenosPorUsuario(String userId) {
        return designRepository.findByUserId(userId);
    }
    
    /**
     * Buscar diseños aprobados
     */
    public List<Design> buscarDisenosAprobados() {
        return designRepository.findByApproved(true);
    }
    
    /**
     * Buscar diseños pendientes (no aprobados)
     */
    public List<Design> buscarDisenosPendientes() {
        return designRepository.findByApproved(false);
    }
    
    /**
     * Buscar diseños por fecha de aprobación
     */
    public List<Design> buscarDisenosPorFechaAprobacion(LocalDate approvedDate) {
        return designRepository.findByApprovedDate(approvedDate);
    }
    
    /**
     * Buscar diseños por rango de fechas de aprobación
     */
    public List<Design> buscarDisenosPorRangoFechasAprobacion(LocalDate startDate, LocalDate endDate) {
        return designRepository.findByApprovedDateBetween(startDate, endDate);
    }
    
    /**
     * Buscar diseños por comentarios (contiene texto)
     */
    public List<Design> buscarDisenosPorComentarios(String comentario) {
        return designRepository.findByCommentsContainingIgnoreCase(comentario);
    }
    
    /**
     * Buscar diseños con render
     */
    public List<Design> buscarDisenosConRender() {
        return designRepository.findDesignsWithRender();
    }
    
    /**
     * Buscar diseños con plano laminado
     */
    public List<Design> buscarDisenosConPlanoLaminado() {
        return designRepository.findDesignsWithLaminatedPlane();
    }
    
    /**
     * Contar diseños aprobados
     */
    public long contarDisenosAprobados() {
        return designRepository.countApprovedDesigns();
    }
    
    /**
     * Contar diseños pendientes
     */
    public long contarDisenosPendientes() {
        return designRepository.countPendingDesigns();
    }
    
    /**
     * Verificar si existe un diseño
     */
    public boolean existeDiseno(Long id) {
        return designRepository.existsById(id);
    }
    
    /**
     * Contar total de diseños
     */
    public long contarDisenos() {
        return designRepository.count();
    }
    
    /**
     * Aprobar diseño
     */
    public Design aprobarDiseno(Long id) {
        Optional<Design> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Design design = disenoOpt.get();
            design.setApproved(true);
            design.setApprovedDate(LocalDate.now());
            return designRepository.save(design);
        }
        throw new RuntimeException("No se encontró diseño con ID: " + id);
    }
    
    /**
     * Rechazar diseño
     */
    public Design rechazarDiseno(Long id) {
        Optional<Design> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Design design = disenoOpt.get();
            design.setApproved(false);
            design.setApprovedDate(LocalDate.now());
            return designRepository.save(design);
        }
        throw new RuntimeException("No se encontró diseño con ID: " + id);
    }
    
    /**
     * Actualizar URL de render
     */
    public Design actualizarUrlRender(Long id, String nuevaUrl) {
        Optional<Design> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Design design = disenoOpt.get();
            design.setUrlRender(nuevaUrl);
            return designRepository.save(design);
        }
        throw new RuntimeException("No se encontró diseño con ID: " + id);
    }
    
    /**
     * Actualizar plano laminado
     */
    public Design actualizarPlanoLaminado(Long id, String nuevoPlano) {
        Optional<Design> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Design design = disenoOpt.get();
            design.setLaminatedPlane(nuevoPlano);
            return designRepository.save(design);
        }
        throw new RuntimeException("No se encontró diseño con ID: " + id);
    }
    
    /**
     * Actualizar comentarios del diseño
     */
    public Design actualizarComentarios(Long id, String nuevosComentarios) {
        Optional<Design> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Design design = disenoOpt.get();
            design.setComments(nuevosComentarios);
            return designRepository.save(design);
        }
        throw new RuntimeException("No se encontró diseño con ID: " + id);
    }
    
    /**
     * Validar datos de diseño
     */
    public boolean validarDatosDiseno(String urlRender, String laminatedPlane, String comments, String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return false;
        }
        // URL render y plano laminado pueden ser opcionales
        // Comentarios pueden ser opcionales
        return true;
    }
    
    /**
     * Verificar si un diseño está aprobado
     */
    public boolean estaAprobado(Long id) {
        Optional<Design> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Design design = disenoOpt.get();
            return design.getApproved() != null && design.getApproved();
        }
        return false;
    }
    
    /**
     * Verificar si un diseño tiene render
     */
    public boolean tieneRender(Long id) {
        Optional<Design> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Design design = disenoOpt.get();
            return design.getUrlRender() != null && !design.getUrlRender().trim().isEmpty();
        }
        return false;
    }
    
    /**
     * Verificar si un diseño tiene plano laminado
     */
    public boolean tienePlanoLaminado(Long id) {
        Optional<Design> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Design design = disenoOpt.get();
            return design.getLaminatedPlane() != null && !design.getLaminatedPlane().trim().isEmpty();
        }
        return false;
    }
    
    /**
     * Obtener diseños aprobados por usuario
     */
    public List<Design> obtenerDisenosAprobadosPorUsuario(String userId) {
        return buscarDisenosPorUsuario(userId).stream()
                .filter(design -> design.getApproved() != null && design.getApproved())
                .toList();
    }
    
    /**
     * Obtener diseños pendientes por usuario
     */
    public List<Design> obtenerDisenosPendientesPorUsuario(String userId) {
        return buscarDisenosPorUsuario(userId).stream()
                .filter(design -> design.getApproved() == null || !design.getApproved())
                .toList();
    }
    
    /**
     * Contar diseños por usuario
     */
    public long contarDisenosPorUsuario(String userId) {
        return buscarDisenosPorUsuario(userId).size();
    }
    
    /**
     * Verificar si un usuario tiene diseños
     */
    public boolean usuarioTieneDisenos(String userId) {
        return contarDisenosPorUsuario(userId) > 0;
    }
}