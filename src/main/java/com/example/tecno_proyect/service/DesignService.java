package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Diseno;
import com.example.tecno_proyect.repository.DisenoRepository;
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
    private DisenoRepository designRepository;
    
    /**
     * Listar todos los diseños
     */
    public List<Diseno> listarTodosLosDisenos() {
        return designRepository.findAll();
    }
    
    /**
     * Buscar diseño por ID
     */
    public Optional<Diseno> buscarDisenoPorId(Long id) {
        return designRepository.findById(id);
    }
    
    /**
     * Buscar diseño por ID de cotización
     */
    public Optional<Diseno> buscarDisenoPorCotizacion(Long idCuota) {
        List<Diseno> disenos = designRepository.findByIdCuota(idCuota);
        return disenos.isEmpty() ? Optional.empty() : Optional.of(disenos.get(0));
    }
    
    /**
     * Insertar nuevo diseño
     */
    public Diseno insertarDiseno(Long idCuota, String urlRender, String planoLaminar, Boolean aprobado, 
                                LocalDate fechaAprobacion, String comentarios, Long usuarioId) {
        Diseno diseno = new Diseno(idCuota, urlRender, planoLaminar, aprobado, fechaAprobacion, comentarios, usuarioId);
        return designRepository.save(diseno);
    }
    
    /**
     * Actualizar diseño existente
     */
    public Diseno actualizarDiseno(Long idDiseno, Long idCuota, String urlRender, String planoLaminar, Boolean aprobado, 
                                  LocalDate fechaAprobacion, String comentarios, Long usuarioId) {
        Optional<Diseno> disenoExistente = designRepository.findById(idDiseno);
        
        if (disenoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró diseño con ID: " + idDiseno);
        }
        
        Diseno diseno = disenoExistente.get();
        diseno.setIdCuota(idCuota);
        diseno.setUrlRender(urlRender);
        diseno.setPlanoLaminar(planoLaminar);
        diseno.setAprobado(aprobado);
        diseno.setFechaAprobacion(fechaAprobacion);
        diseno.setComentarios(comentarios);
        diseno.setUsuarioId(usuarioId);
        
        return designRepository.save(diseno);
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
    public List<Diseno> buscarDisenosPorUsuario(Long usuarioId) {
        return designRepository.findByUsuarioId(usuarioId);
    }
    
    /**
     * Buscar diseños aprobados
     */
    public List<Diseno> buscarDisenosAprobados() {
        return designRepository.findByAprobado(true);
    }
    
    /**
     * Buscar diseños pendientes (no aprobados)
     */
    public List<Diseno> buscarDisenosPendientes() {
        return designRepository.findByAprobado(false);
    }
    
    /**
     * Buscar diseños por fecha de aprobación
     */
    public List<Diseno> buscarDisenosPorFechaAprobacion(LocalDate fechaAprobacion) {
        return designRepository.findByFechaAprobacion(fechaAprobacion);
    }
    
    /**
     * Buscar diseños por rango de fechas de aprobación
     */
    public List<Diseno> buscarDisenosPorRangoFechasAprobacion(LocalDate fechaInicio, LocalDate fechaFinal) {
        return designRepository.findByFechaAprobacionBetween(fechaInicio, fechaFinal);
    }
    
    /**
     * Buscar diseños por comentarios (contiene texto)
     */
    public List<Diseno> buscarDisenosPorComentarios(String comentario) {
        return designRepository.findByComentariosContainingIgnoreCase(comentario);
    }
    
    /**
     * Buscar diseños con render
     */
    public List<Diseno> buscarDisenosConRender() {
        return designRepository.findDisenosWithRender();
    }
    
    /**
     * Buscar diseños con plano laminado
     */
    public List<Diseno> buscarDisenosConPlanoLaminado() {
        return designRepository.findDisenosWithLaminatedPlane();
    }
    
    /**
     * Contar diseños aprobados
     */
    public long contarDisenosAprobados() {
        return designRepository.countApprovedDisenos();
    }
    
    /**
     * Contar diseños pendientes
     */
    public long contarDisenosPendientes() {
        return designRepository.countPendingDisenos();
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
    public Diseno aprobarDiseno(Long id) {
        Optional<Diseno> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Diseno diseno = disenoOpt.get();
            diseno.setAprobado(true);
            diseno.setFechaAprobacion(LocalDate.now());
            return designRepository.save(diseno);
        }
        throw new RuntimeException("No se encontró diseño con ID: " + id);
    }
    
    /**
     * Rechazar diseño
     */
    public Diseno rechazarDiseno(Long id) {
        Optional<Diseno> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Diseno diseno = disenoOpt.get();
            diseno.setAprobado(false);
            diseno.setFechaAprobacion(LocalDate.now());
            return designRepository.save(diseno);
        }
        throw new RuntimeException("No se encontró diseño con ID: " + id);
    }
    
    /**
     * Actualizar URL de render
     */
    public Diseno actualizarUrlRender(Long id, String nuevaUrl) {
        Optional<Diseno> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Diseno diseno = disenoOpt.get();
            diseno.setUrlRender(nuevaUrl);
            return designRepository.save(diseno);
        }
        throw new RuntimeException("No se encontró diseño con ID: " + id);
    }
    
    /**
     * Actualizar plano laminado
     */
    public Diseno actualizarPlanoLaminado(Long id, String nuevoPlano) {
        Optional<Diseno> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Diseno diseno = disenoOpt.get();
            diseno.setPlanoLaminar(nuevoPlano);
            return designRepository.save(diseno);
        }
        throw new RuntimeException("No se encontró diseño con ID: " + id);
    }
    
    /**
     * Actualizar comentarios del diseño
     */
    public Diseno actualizarComentarios(Long id, String nuevosComentarios) {
        Optional<Diseno> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Diseno diseno = disenoOpt.get();
            diseno.setComentarios(nuevosComentarios);
            return designRepository.save(diseno);
        }
        throw new RuntimeException("No se encontró diseño con ID: " + id);
    }
    
    /**
     * Validar datos de diseño
     */
    public boolean validarDatosDiseno(String urlRender, String planoLaminar, String comentarios, Long usuarioId) {
        if (usuarioId == null) {
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
        Optional<Diseno> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Diseno diseno = disenoOpt.get();
            return diseno.getAprobado() != null && diseno.getAprobado();
        }
        return false;
    }
    
    /**
     * Verificar si un diseño tiene render
     */
    public boolean tieneRender(Long id) {
        Optional<Diseno> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Diseno diseno = disenoOpt.get();
            return diseno.getUrlRender() != null && !diseno.getUrlRender().trim().isEmpty();
        }
        return false;
    }
    
    /**
     * Verificar si un diseño tiene plano laminado
     */
    public boolean tienePlanoLaminado(Long id) {
        Optional<Diseno> disenoOpt = designRepository.findById(id);
        if (disenoOpt.isPresent()) {
            Diseno diseno = disenoOpt.get();
            return diseno.getPlanoLaminar() != null && !diseno.getPlanoLaminar().trim().isEmpty();
        }
        return false;
    }
    
    /**
     * Obtener diseños aprobados por usuario
     */
    public List<Diseno> obtenerDisenosAprobadosPorUsuario(Long usuarioId) {
        return buscarDisenosPorUsuario(usuarioId).stream()
                .filter(diseno -> diseno.getAprobado() != null && diseno.getAprobado())
                .toList();
    }
    
    /**
     * Obtener diseños pendientes por usuario
     */
    public List<Diseno> obtenerDisenosPendientesPorUsuario(Long usuarioId) {
        return buscarDisenosPorUsuario(usuarioId).stream()
                .filter(diseno -> diseno.getAprobado() == null || !diseno.getAprobado())
                .toList();
    }
    
    /**
     * Contar diseños por usuario
     */
    public long contarDisenosPorUsuario(Long usuarioId) {
        return buscarDisenosPorUsuario(usuarioId).size();
    }
    
    /**
     * Verificar si un usuario tiene diseños
     */
    public boolean usuarioTieneDisenos(Long usuarioId) {
        return contarDisenosPorUsuario(usuarioId) > 0;
    }
}
