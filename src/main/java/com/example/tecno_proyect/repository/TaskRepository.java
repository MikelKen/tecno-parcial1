package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tarea, Long> {
    
    // Buscar tareas por cronograma
    List<Tarea> findByIdCronograma(Long idCronograma);
    
    // Buscar tareas por usuario
    List<Tarea> findByUsuarioId(Long usuarioId);
    
    // Buscar tareas por estado
    List<Tarea> findByEstado(String estado);
    
    // Buscar tareas por descripción (parcial, ignorando mayúsculas)
    @Query("SELECT t FROM Tarea t WHERE LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))")
    List<Tarea> findByDescripcionContainingIgnoreCase(@Param("descripcion") String descripcion);
    
    // Buscar tareas por hora de inicio
    List<Tarea> findByHoraInicio(LocalTime horaInicio);
    
    // Buscar tareas por hora de finalización
    List<Tarea> findByHoraFinal(LocalTime horaFinal);
    
    // Buscar tareas por rango de horas
    @Query("SELECT t FROM Tarea t WHERE t.horaInicio >= :startTime AND t.horaFinal <= :endTime")
    List<Tarea> findByTimeRange(@Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
    
    // Buscar tareas activas (en progreso)
    @Query("SELECT t FROM Tarea t WHERE t.estado = 'En Progreso' OR t.estado = 'Iniciada'")
    List<Tarea> findActiveTasks();
    
    // Buscar tareas completadas
    @Query("SELECT t FROM Tarea t WHERE t.estado = 'Completada' OR t.estado = 'Terminada'")
    List<Tarea> findCompletedTasks();
    
    // Buscar tareas pendientes
    @Query("SELECT t FROM Tarea t WHERE t.estado = 'Pendiente' OR t.estado = 'No Iniciada'")
    List<Tarea> findPendingTasks();
    
    // Buscar tareas por usuario y estado
    List<Tarea> findByUsuarioIdAndEstado(Long usuarioId, String estado);
    
    // Buscar tareas por cronograma y estado
    List<Tarea> findByIdCronogramaAndEstado(Long idCronograma, String estado);
    
    // Contar tareas por estado
    @Query("SELECT COUNT(t) FROM Tarea t WHERE t.estado = :estado")
    long countByEstado(@Param("estado") String estado);
    
    // Contar tareas por usuario
    @Query("SELECT COUNT(t) FROM Tarea t WHERE t.usuarioId = :usuarioId")
    long countByUsuarioId(@Param("usuarioId") Long usuarioId);
    
    // Buscar tareas ordenadas por hora de inicio
    @Query("SELECT t FROM Tarea t ORDER BY t.horaInicio ASC")
    List<Tarea> findTasksOrderedByStartTime();
    
    // Buscar tareas con duración mayor a un tiempo específico
    @Query("SELECT t FROM Tarea t WHERE FUNCTION('TIMESTAMPDIFF', HOUR, t.horaInicio, t.horaFinal) > :hours")
    List<Tarea> findTasksLongerThan(@Param("hours") Integer hours);
    
    // Buscar tareas por usuario ordenadas por hora de inicio
    @Query("SELECT t FROM Tarea t WHERE t.usuarioId = :usuarioId ORDER BY t.horaInicio ASC")
    List<Tarea> findByUsuarioIdOrderedByStartTime(@Param("usuarioId") Long usuarioId);
    
    // Buscar tareas que se superponen en tiempo
    @Query("SELECT t FROM Tarea t WHERE t.usuarioId = :usuarioId AND " +
           "((t.horaInicio <= :endTime AND t.horaFinal >= :startTime))")
    List<Tarea> findOverlappingTasks(@Param("usuarioId") Long usuarioId, 
                                   @Param("startTime") LocalTime startTime, 
                                   @Param("endTime") LocalTime endTime);
}