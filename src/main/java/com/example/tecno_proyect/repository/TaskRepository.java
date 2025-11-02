package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Buscar tareas por cronograma
    List<Task> findByIdSchedule(Long idSchedule);
    
    // Buscar tareas por usuario
    List<Task> findByUserId(String userId);
    
    // Buscar tareas por estado
    List<Task> findByState(String state);
    
    // Buscar tareas por descripción (parcial, ignorando mayúsculas)
    @Query("SELECT t FROM Task t WHERE LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    List<Task> findByDescriptionContainingIgnoreCase(@Param("description") String description);
    
    // Buscar tareas por hora de inicio
    List<Task> findByInitHour(LocalTime initHour);
    
    // Buscar tareas por hora de finalización
    List<Task> findByFinalHour(LocalTime finalHour);
    
    // Buscar tareas por rango de horas
    @Query("SELECT t FROM Task t WHERE t.initHour >= :startTime AND t.finalHour <= :endTime")
    List<Task> findByTimeRange(@Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
    
    // Buscar tareas activas (en progreso)
    @Query("SELECT t FROM Task t WHERE t.state = 'En Progreso' OR t.state = 'Iniciada'")
    List<Task> findActiveTasks();
    
    // Buscar tareas completadas
    @Query("SELECT t FROM Task t WHERE t.state = 'Completada' OR t.state = 'Terminada'")
    List<Task> findCompletedTasks();
    
    // Buscar tareas pendientes
    @Query("SELECT t FROM Task t WHERE t.state = 'Pendiente' OR t.state = 'No Iniciada'")
    List<Task> findPendingTasks();
    
    // Buscar tareas por usuario y estado
    List<Task> findByUserIdAndState(String userId, String state);
    
    // Buscar tareas por cronograma y estado
    List<Task> findByIdScheduleAndState(Long idSchedule, String state);
    
    // Contar tareas por estado
    @Query("SELECT COUNT(t) FROM Task t WHERE t.state = :state")
    long countByState(@Param("state") String state);
    
    // Contar tareas por usuario
    @Query("SELECT COUNT(t) FROM Task t WHERE t.userId = :userId")
    long countByUserId(@Param("userId") String userId);
    
    // Buscar tareas ordenadas por hora de inicio
    @Query("SELECT t FROM Task t ORDER BY t.initHour ASC")
    List<Task> findTasksOrderedByStartTime();
    
    // Buscar tareas con duración mayor a un tiempo específico
    @Query("SELECT t FROM Task t WHERE FUNCTION('TIMESTAMPDIFF', HOUR, t.initHour, t.finalHour) > :hours")
    List<Task> findTasksLongerThan(@Param("hours") Integer hours);
    
    // Buscar tareas por usuario ordenadas por hora de inicio
    @Query("SELECT t FROM Task t WHERE t.userId = :userId ORDER BY t.initHour ASC")
    List<Task> findByUserIdOrderedByStartTime(@Param("userId") String userId);
    
    // Buscar tareas que se superponen en tiempo
    @Query("SELECT t FROM Task t WHERE t.userId = :userId AND " +
           "((t.initHour <= :endTime AND t.finalHour >= :startTime))")
    List<Task> findOverlappingTasks(@Param("userId") String userId, 
                                   @Param("startTime") LocalTime startTime, 
                                   @Param("endTime") LocalTime endTime);
}