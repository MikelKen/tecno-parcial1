package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Project;
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
    public List<Project> listarTodosLosProyectos() {
        return projectRepository.findAll();
    }
    
    /**
     * Buscar proyecto por ID
     */
    public Optional<Project> buscarProyectoPorId(Long id) {
        return projectRepository.findById(id);
    }
    
    /**
     * Buscar proyecto por nombre
     */
    public Optional<Project> buscarProyectoPorNombre(String name) {
        return projectRepository.findByName(name);
    }
    
    /**
     * Insertar nuevo proyecto
     */
    public Project insertarProyecto(String name, String description, String location, 
                                  String state, Long idClient, Long userId) {
        // Verificar si ya existe un proyecto con ese nombre
        if (projectRepository.existsByName(name)) {
            throw new RuntimeException("Ya existe un proyecto con nombre: " + name);
        }
        
        Project project = new Project(name, description, location, state, idClient, userId);
        return projectRepository.save(project);
    }
    
    /**
     * Actualizar proyecto existente por ID
     */
    public Project actualizarProyecto(Long id, String name, String description, String location, 
                                     String state, Long idClient, Long userId) {
        Optional<Project> proyectoExistente = projectRepository.findById(id);
        
        if (proyectoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró proyecto con ID: " + id);
        }
        
        Project project = proyectoExistente.get();
        project.setName(name);
        project.setDescription(description);
        project.setLocation(location);
        project.setState(state);
        project.setIdClient(idClient);
        project.setUserId(userId);
        
        return projectRepository.save(project);
    }
    
    /**
     * Actualizar proyecto existente por nombre
     */
    public Project actualizarProyectoPorNombre(String name, String description, String location, 
                                             String state, Long idClient, Long userId) {
        Optional<Project> proyectoExistente = projectRepository.findByName(name);
        
        if (proyectoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró proyecto con nombre: " + name);
        }
        
        Project project = proyectoExistente.get();
        project.setDescription(description);
        project.setLocation(location);
        project.setState(state);
        project.setIdClient(idClient);
        project.setUserId(userId);
        
        return projectRepository.save(project);
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
    public boolean eliminarProyectoPorNombre(String name) {
        Optional<Project> proyectoOpt = projectRepository.findByName(name);
        if (proyectoOpt.isPresent()) {
            projectRepository.delete(proyectoOpt.get());
            return true;
        }
        return false;
    }
    
    /**
     * Buscar proyectos por cliente
     */
    public List<Project> buscarProyectosPorCliente(Long idClient) {
        return projectRepository.findByIdClient(idClient);
    }
    
    /**
     * Buscar proyectos por usuario
     */
    public List<Project> buscarProyectosPorUsuario(Long userId) {
        return projectRepository.findByUserId(userId);
    }
    
    /**
     * Buscar proyectos por estado
     */
    public List<Project> buscarProyectosPorEstado(String state) {
        return projectRepository.findByState(state);
    }
    
    /**
     * Buscar proyectos activos
     */
    public List<Project> buscarProyectosActivos() {
        return projectRepository.findActiveProjects();
    }
    
    /**
     * Buscar proyectos por ubicación
     */
    public List<Project> buscarProyectosPorUbicacion(String location) {
        return projectRepository.findByLocationContainingIgnoreCase(location);
    }
    
    /**
     * Buscar proyectos por descripción
     */
    public List<Project> buscarProyectosPorDescripcion(String description) {
        return projectRepository.findByDescriptionContainingIgnoreCase(description);
    }
    
    /**
     * Contar proyectos por estado
     */
    public long contarProyectosPorEstado(String state) {
        return projectRepository.countByState(state);
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
    public boolean existeProyectoPorNombre(String name) {
        return projectRepository.existsByName(name);
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
    public Project cambiarEstadoProyecto(Long id, String nuevoEstado) {
        Optional<Project> proyectoOpt = projectRepository.findById(id);
        if (proyectoOpt.isPresent()) {
            Project project = proyectoOpt.get();
            project.setState(nuevoEstado);
            return projectRepository.save(project);
        }
        throw new RuntimeException("No se encontró proyecto con ID: " + id);
    }
    
    /**
     * Cambiar estado de proyecto por nombre
     */
    public Project cambiarEstadoProyectoPorNombre(String name, String nuevoEstado) {
        Optional<Project> proyectoOpt = projectRepository.findByName(name);
        if (proyectoOpt.isPresent()) {
            Project project = proyectoOpt.get();
            project.setState(nuevoEstado);
            return projectRepository.save(project);
        }
        throw new RuntimeException("No se encontró proyecto con nombre: " + name);
    }
    
    /**
     * Asignar proyecto a usuario por ID
     */
    public Project asignarProyectoAUsuario(Long id, Long userId) {
        Optional<Project> proyectoOpt = projectRepository.findById(id);
        if (proyectoOpt.isPresent()) {
            Project project = proyectoOpt.get();
            project.setUserId(userId);
            return projectRepository.save(project);
        }
        throw new RuntimeException("No se encontró proyecto con ID: " + id);
    }
    
    /**
     * Asignar proyecto a usuario por nombre
     */
    public Project asignarProyectoAUsuarioPorNombre(String name, Long userId) {
        Optional<Project> proyectoOpt = projectRepository.findByName(name);
        if (proyectoOpt.isPresent()) {
            Project project = proyectoOpt.get();
            project.setUserId(userId);
            return projectRepository.save(project);
        }
        throw new RuntimeException("No se encontró proyecto con nombre: " + name);
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