package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Proyecto;
import com.example.tecno_proyect.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    /**
     * Listar todos los proyectos
     */
    public List<Proyecto> listarTodosLosProyectos() {
        return projectRepository.findAll();
    }
    
    /**
     * Buscar proyecto por ID
     */
    public Optional<Proyecto> buscarProyectoPorId(Long id) {
        return projectRepository.findById(id);
    }
    
    /**
     * Buscar proyecto por nombre
     */
    public Optional<Proyecto> buscarProyectoPorNombre(String nombre) {
        return projectRepository.findByNombre(nombre);
    }
    
    /**
     * Insertar nuevo proyecto
     */
    public Proyecto insertarProyecto(String nombre, String descripcion, String ubicacion, 
                                  String estado, Long idCliente, Long usuarioId) {
        // Verificar si ya existe un proyecto con ese nombre
        if (projectRepository.existsByNombre(nombre)) {
            throw new RuntimeException("Ya existe un proyecto con nombre: " + nombre);
        }
        
        Proyecto proyecto = new Proyecto(nombre, descripcion, ubicacion, estado, idCliente, usuarioId);
        return projectRepository.save(proyecto);
    }
    
    /**
     * Actualizar proyecto existente por ID
     */
    public Proyecto actualizarProyecto(Long id, String nombre, String descripcion, String ubicacion, 
                                     String estado, Long idCliente, Long usuarioId) {
        Optional<Proyecto> proyectoExistente = projectRepository.findById(id);
        
        if (proyectoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró proyecto con ID: " + id);
        }
        
        Proyecto proyecto = proyectoExistente.get();
        proyecto.setNombre(nombre);
        proyecto.setDescripcion(descripcion);
        proyecto.setUbicacion(ubicacion);
        proyecto.setEstado(estado);
        proyecto.setIdCliente(idCliente);
        proyecto.setUsuarioId(usuarioId);
        
        return projectRepository.save(proyecto);
    }
    
    /**
     * Actualizar proyecto existente por nombre
     */
    public Proyecto actualizarProyectoPorNombre(String nombre, String descripcion, String ubicacion, 
                                             String estado, Long idCliente, Long usuarioId) {
        Optional<Proyecto> proyectoExistente = projectRepository.findByNombre(nombre);
        
        if (proyectoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró proyecto con nombre: " + nombre);
        }
        
        Proyecto proyecto = proyectoExistente.get();
        proyecto.setDescripcion(descripcion);
        proyecto.setUbicacion(ubicacion);
        proyecto.setEstado(estado);
        proyecto.setIdCliente(idCliente);
        proyecto.setUsuarioId(usuarioId);
        
        return projectRepository.save(proyecto);
    }
    
    /**
     * Eliminar proyecto por ID
     */
    public boolean eliminarProyecto(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Eliminar proyecto por nombre
     */
    public boolean eliminarProyectoPorNombre(String nombre) {
        Optional<Proyecto> proyectoOpt = projectRepository.findByNombre(nombre);
        if (proyectoOpt.isPresent()) {
            projectRepository.delete(proyectoOpt.get());
            return true;
        }
        return false;
    }
    
    /**
     * Buscar proyectos por cliente
     */
    public List<Proyecto> buscarProyectosPorCliente(Long idCliente) {
        return projectRepository.findByIdCliente(idCliente);
    }
    
    /**
     * Buscar proyectos por usuario
     */
    public List<Proyecto> buscarProyectosPorUsuario(Long usuarioId) {
        return projectRepository.findByUsuarioId(usuarioId);
    }
    
    /**
     * Buscar proyectos por estado
     */
    public List<Proyecto> buscarProyectosPorEstado(String estado) {
        return projectRepository.findByEstado(estado);
    }
    
    /**
     * Buscar proyectos activos
     */
    public List<Proyecto> buscarProyectosActivos() {
        return projectRepository.findActiveProjects();
    }
    
    /**
     * Buscar proyectos por ubicación
     */
    public List<Proyecto> buscarProyectosPorUbicacion(String ubicacion) {
        return projectRepository.findByUbicacionContainingIgnoreCase(ubicacion);
    }
    
    /**
     * Buscar proyectos por descripción
     */
    public List<Proyecto> buscarProyectosPorDescripcion(String descripcion) {
        return projectRepository.findByDescripcionContainingIgnoreCase(descripcion);
    }
    
    /**
     * Contar proyectos por estado
     */
    public long contarProyectosPorEstado(String estado) {
        return projectRepository.countByEstado(estado);
    }
    
    /**
     * Verificar si existe un proyecto por ID
     */
    public boolean existeProyecto(Long id) {
        return projectRepository.existsById(id);
    }
    
    /**
     * Verificar si existe un proyecto por nombre
     */
    public boolean existeProyectoPorNombre(String nombre) {
        return projectRepository.existsByNombre(nombre);
    }
    
    /**
     * Contar total de proyectos
     */
    public long contarProyectos() {
        return projectRepository.count();
    }
    
    /**
     * Cambiar estado de proyecto por ID
     */
    public Proyecto cambiarEstadoProyecto(Long id, String nuevoEstado) {
        Optional<Proyecto> proyectoOpt = projectRepository.findById(id);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            proyecto.setEstado(nuevoEstado);
            return projectRepository.save(proyecto);
        }
        throw new RuntimeException("No se encontró proyecto con ID: " + id);
    }
    
    /**
     * Cambiar estado de proyecto por nombre
     */
    public Proyecto cambiarEstadoProyectoPorNombre(String nombre, String nuevoEstado) {
        Optional<Proyecto> proyectoOpt = projectRepository.findByNombre(nombre);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            proyecto.setEstado(nuevoEstado);
            return projectRepository.save(proyecto);
        }
        throw new RuntimeException("No se encontró proyecto con nombre: " + nombre);
    }
    
    /**
     * Asignar proyecto a usuario por ID
     */
    public Proyecto asignarProyectoAUsuario(Long id, Long usuarioId) {
        Optional<Proyecto> proyectoOpt = projectRepository.findById(id);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            proyecto.setUsuarioId(usuarioId);
            return projectRepository.save(proyecto);
        }
        throw new RuntimeException("No se encontró proyecto con ID: " + id);
    }
    
    /**
     * Asignar proyecto a usuario por nombre
     */
    public Proyecto asignarProyectoAUsuarioPorNombre(String nombre, Long usuarioId) {
        Optional<Proyecto> proyectoOpt = projectRepository.findByNombre(nombre);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            proyecto.setUsuarioId(usuarioId);
            return projectRepository.save(proyecto);
        }
        throw new RuntimeException("No se encontró proyecto con nombre: " + nombre);
    }
    
    /**
     * Obtener estadísticas de proyectos
     */
    public String obtenerEstadisticasProyectos() {
        long totalProyectos = contarProyectos();
        long proyectosActivos = buscarProyectosActivos().size();
        long proyectosCompletados = contarProyectosPorEstado("Terminado");
        
        return String.format("Total: %d, Activos: %d, Completados: %d", 
                           totalProyectos, proyectosActivos, proyectosCompletados);
    }
}