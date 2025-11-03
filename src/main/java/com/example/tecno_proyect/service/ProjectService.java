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
     * Buscar proyecto por nombre (ID)
     */
    public Optional<Project> buscarProyectoPorNombre(String name) {
        return projectRepository.findById(name);
    }
    
    /**
     * Insertar nuevo proyecto
     */
    public Project insertarProyecto(String name, String description, String location, 
                                  String state, Long idClient, String userId) {
        // Verificar si ya existe un proyecto con ese nombre
        if (projectRepository.existsById(name)) {
            throw new RuntimeException("Ya existe un proyecto con nombre: " + name);
        }
        
        Project project = new Project(name, description, location, state, idClient, userId);
        return projectRepository.save(project);
    }
    
    /**
     * Actualizar proyecto existente
     */
    public Project actualizarProyecto(String name, String description, String location, 
                                     String state, Long idClient, String userId) {
        Optional<Project> proyectoExistente = projectRepository.findById(name);
        
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
     * Eliminar proyecto por nombre
     */
    public boolean eliminarProyecto(String name) {
        if (projectRepository.existsById(name)) {
            projectRepository.deleteById(name);
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
    public List<Project> buscarProyectosPorUsuario(String userId) {
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
     * Verificar si existe un proyecto
     */
    public boolean existeProyecto(String name) {
        return projectRepository.existsById(name);
    }
    
    /**
     * Contar total de proyectos
     */
    public long contarProyectos() {
        return projectRepository.count();
    }
    
    /**
     * Cambiar estado de proyecto
     */
    public Project cambiarEstadoProyecto(String name, String nuevoEstado) {
        Optional<Project> proyectoOpt = projectRepository.findById(name);
        if (proyectoOpt.isPresent()) {
            Project project = proyectoOpt.get();
            project.setState(nuevoEstado);
            return projectRepository.save(project);
        }
        throw new RuntimeException("No se encontró proyecto con nombre: " + name);
    }
    
    /**
     * Asignar proyecto a usuario
     */
    public Project asignarProyectoAUsuario(String name, String userId) {
        Optional<Project> proyectoOpt = projectRepository.findById(name);
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