package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Task;
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
    public List<Task> listarTodasLasTareas() {
        return taskRepository.findAll();
    }

    // Buscar tarea por ID
    public Optional<Task> buscarTareaPorId(Long id) {
        return taskRepository.findById(id);
    }

    // Insertar nueva tarea
    public Task insertarTarea(Task task) {
        return taskRepository.save(task);
    }

    // Actualizar tarea existente
    public Task actualizarTarea(Long id, Task datos) {
        Optional<Task> existente = taskRepository.findById(id);
        if (existente.isEmpty()) {
            throw new RuntimeException("No se encontró tarea con id: " + id);
        }
        Task t = existente.get();
        t.setInitHour(datos.getInitHour());
        t.setFinalHour(datos.getFinalHour());
        t.setDescription(datos.getDescription());
        t.setState(datos.getState());
        t.setIdSchedule(datos.getIdSchedule());
        t.setUserId(datos.getUserId());
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
    public List<Task> buscarPorCronograma(Long idSchedule) {
        return taskRepository.findByIdSchedule(idSchedule);
    }

    // Buscar por usuario
    public List<Task> buscarPorUsuario(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    // Buscar por estado
    public List<Task> buscarPorEstado(String state) {
        return taskRepository.findByState(state);
    }

    // Buscar por descripción (parcial, ignorando mayúsculas)
    public List<Task> buscarPorDescripcionParcial(String description) {
        return taskRepository.findByDescriptionContainingIgnoreCase(description);
    }

    // Buscar por hora de inicio
    public List<Task> buscarPorHoraInicio(LocalTime initHour) {
        return taskRepository.findByInitHour(initHour);
    }

    // Buscar por hora de finalización
    public List<Task> buscarPorHoraFinal(LocalTime finalHour) {
        return taskRepository.findByFinalHour(finalHour);
    }

    // Buscar por rango de horas
    public List<Task> buscarPorRangoHoras(LocalTime startTime, LocalTime endTime) {
        return taskRepository.findByTimeRange(startTime, endTime);
    }

    // Buscar tareas activas
    public List<Task> buscarTareasActivas() {
        return taskRepository.findActiveTasks();
    }

    // Buscar tareas completadas
    public List<Task> buscarTareasCompletadas() {
        return taskRepository.findCompletedTasks();
    }

    // Buscar tareas pendientes
    public List<Task> buscarTareasPendientes() {
        return taskRepository.findPendingTasks();
    }

    // Buscar por usuario y estado
    public List<Task> buscarPorUsuarioYEstado(Long userId, String state) {
        return taskRepository.findByUserIdAndState(userId, state);
    }

    // Buscar por cronograma y estado
    public List<Task> buscarPorCronogramaYEstado(Long idSchedule, String state) {
        return taskRepository.findByIdScheduleAndState(idSchedule, state);
    }

    // Contar por estado
    public long contarPorEstado(String state) {
        return taskRepository.countByState(state);
    }

    // Contar por usuario
    public long contarPorUsuario(Long userId) {
        return taskRepository.countByUserId(userId);
    }

    // Buscar ordenadas por hora de inicio
    public List<Task> buscarOrdenadasPorHoraInicio() {
        return taskRepository.findTasksOrderedByStartTime();
    }

    // Buscar tareas largas
    public List<Task> buscarTareasLargas(Integer hours) {
        return taskRepository.findTasksLongerThan(hours);
    }

    // Buscar por usuario ordenadas por hora de inicio
    public List<Task> buscarPorUsuarioOrdenadasPorHoraInicio(Long userId) {
        return taskRepository.findByUserIdOrderedByStartTime(userId);
    }

    // Buscar tareas que se superponen en tiempo
    public List<Task> buscarTareasSuperpuestas(Long userId, LocalTime startTime, LocalTime endTime) {
        return taskRepository.findOverlappingTasks(userId, startTime, endTime);
    }
}
