package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Tarea;
import com.example.tecno_proyect.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Listar todas las tareas
    public List<Tarea> listarTodasLasTareas() {
        return taskRepository.findAll();
    }

    // Buscar tarea por ID
    public Optional<Tarea> buscarTareaPorId(Long id) {
        return taskRepository.findById(id);
    }

    // Insertar nueva tarea
    public Tarea insertarTarea(Tarea tarea) {
        return taskRepository.save(tarea);
    }

    // Actualizar tarea existente
    public Tarea actualizarTarea(Long id, Tarea datos) {
        Optional<Tarea> existente = taskRepository.findById(id);
        if (existente.isEmpty()) {
            throw new RuntimeException("No se encontró tarea con id: " + id);
        }
        Tarea t = existente.get();
        t.setHoraInicio(datos.getHoraInicio());
        t.setHoraFinal(datos.getHoraFinal());
        t.setDescripcion(datos.getDescripcion());
        t.setEstado(datos.getEstado());
        t.setIdCronograma(datos.getIdCronograma());
        t.setUsuarioId(datos.getUsuarioId());
        return taskRepository.save(t);
    }

    // Eliminar tarea por ID
    public boolean eliminarTarea(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar por cronograma
    public List<Tarea> buscarPorCronograma(Long idCronograma) {
        return taskRepository.findByIdCronograma(idCronograma);
    }

    // Buscar por usuario
    public List<Tarea> buscarPorUsuario(Long usuarioId) {
        return taskRepository.findByUsuarioId(usuarioId);
    }

    // Buscar por estado
    public List<Tarea> buscarPorEstado(String estado) {
        return taskRepository.findByEstado(estado);
    }

    // Buscar por descripción (parcial, ignorando mayúsculas)
    public List<Tarea> buscarPorDescripcionParcial(String descripcion) {
        return taskRepository.findByDescripcionContainingIgnoreCase(descripcion);
    }

    // Buscar por hora de inicio
    public List<Tarea> buscarPorHoraInicio(LocalTime horaInicio) {
        return taskRepository.findByHoraInicio(horaInicio);
    }

    // Buscar por hora de finalización
    public List<Tarea> buscarPorHoraFinal(LocalTime horaFinal) {
        return taskRepository.findByHoraFinal(horaFinal);
    }

    // Buscar por rango de horas
    public List<Tarea> buscarPorRangoHoras(LocalTime startTime, LocalTime endTime) {
        return taskRepository.findByTimeRange(startTime, endTime);
    }

    // Buscar tareas activas
    public List<Tarea> buscarTareasActivas() {
        return taskRepository.findActiveTasks();
    }

    // Buscar tareas completadas
    public List<Tarea> buscarTareasCompletadas() {
        return taskRepository.findCompletedTasks();
    }

    // Buscar tareas pendientes
    public List<Tarea> buscarTareasPendientes() {
        return taskRepository.findPendingTasks();
    }

    // Buscar por usuario y estado
    public List<Tarea> buscarPorUsuarioYEstado(Long usuarioId, String estado) {
        return taskRepository.findByUsuarioIdAndEstado(usuarioId, estado);
    }

    // Buscar por cronograma y estado
    public List<Tarea> buscarPorCronogramaYEstado(Long idCronograma, String estado) {
        return taskRepository.findByIdCronogramaAndEstado(idCronograma, estado);
    }

    // Contar por estado
    public long contarPorEstado(String estado) {
        return taskRepository.countByEstado(estado);
    }

    // Contar por usuario
    public long contarPorUsuario(Long usuarioId) {
        return taskRepository.countByUsuarioId(usuarioId);
    }

    // Buscar ordenadas por hora de inicio
    public List<Tarea> buscarOrdenadasPorHoraInicio() {
        return taskRepository.findTasksOrderedByStartTime();
    }

    // Buscar tareas largas
    public List<Tarea> buscarTareasLargas(Integer hours) {
        return taskRepository.findTasksLongerThan(hours);
    }

    // Buscar por usuario ordenadas por hora de inicio
    public List<Tarea> buscarPorUsuarioOrdenadasPorHoraInicio(Long usuarioId) {
        return taskRepository.findByUsuarioIdOrderedByStartTime(usuarioId);
    }

    // Buscar tareas que se superponen en tiempo
    public List<Tarea> buscarTareasSuperpuestas(Long usuarioId, LocalTime startTime, LocalTime endTime) {
        return taskRepository.findOverlappingTasks(usuarioId, startTime, endTime);
    }
}

