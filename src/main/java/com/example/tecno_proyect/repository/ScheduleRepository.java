package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    
    // Buscar cronogramas por proyecto
    List<Schedule> findByIdProject(Long idProject);
    
    // Buscar cronogramas por usuario
    List<Schedule> findByUserId(Long userId);
    
    // Buscar cronogramas por estado
    List<Schedule> findByState(String state);
    
    // Buscar cronogramas por fecha de inicio
    List<Schedule> findByInitDate(LocalDate initDate);
    
    // Buscar cronogramas por fecha de finalización
    List<Schedule> findByFinalDate(LocalDate finalDate);
    
    // Buscar cronogramas por días estimados
    List<Schedule> findByEstimateDays(Integer estimateDays);
    
    // Buscar cronogramas por rango de fechas de inicio
    @Query("SELECT s FROM Schedule s WHERE s.initDate BETWEEN :startDate AND :endDate")
    List<Schedule> findByInitDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Buscar cronogramas por rango de fechas de finalización
    @Query("SELECT s FROM Schedule s WHERE s.finalDate BETWEEN :startDate AND :endDate")
    List<Schedule> findByFinalDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Buscar cronogramas activos (en progreso)
    @Query("SELECT s FROM Schedule s WHERE s.state = 'En Progreso' OR s.state = 'Activo'")
    List<Schedule> findActiveSchedules();
    
    // Buscar cronogramas completados
    @Query("SELECT s FROM Schedule s WHERE s.state = 'Completado' OR s.state = 'Terminado'")
    List<Schedule> findCompletedSchedules();
    
    // Buscar cronogramas pendientes
    @Query("SELECT s FROM Schedule s WHERE s.state = 'Pendiente' OR s.state = 'No Iniciado'")
    List<Schedule> findPendingSchedules();
    
    // Buscar cronogramas que terminan hoy
    @Query("SELECT s FROM Schedule s WHERE s.finalDate = CURRENT_DATE")
    List<Schedule> findSchedulesEndingToday();
    
    // Buscar cronogramas que inician hoy
    @Query("SELECT s FROM Schedule s WHERE s.initDate = CURRENT_DATE")
    List<Schedule> findSchedulesStartingToday();
    
    // Buscar cronogramas vencidos (fecha final pasada y no completados)
    @Query("SELECT s FROM Schedule s WHERE s.finalDate < CURRENT_DATE AND s.state != 'Completado' AND s.state != 'Terminado'")
    List<Schedule> findOverdueSchedules();
    
    // Buscar cronogramas por proyecto y estado
    List<Schedule> findByIdProjectAndState(Long idProject, String state);
    
    // Buscar cronogramas por usuario y estado
    List<Schedule> findByUserIdAndState(Long userId, String state);
    
    // Contar cronogramas por estado
    @Query("SELECT COUNT(s) FROM Schedule s WHERE s.state = :state")
    long countByState(@Param("state") String state);
    
    // Contar cronogramas por usuario
    @Query("SELECT COUNT(s) FROM Schedule s WHERE s.userId = :userId")
    long countByUserId(@Param("userId") Long userId);
    
    // Buscar cronogramas con duración mayor a días específicos
    @Query("SELECT s FROM Schedule s WHERE s.estimateDays > :days")
    List<Schedule> findLongSchedules(@Param("days") Integer days);
    
    // Buscar cronogramas ordenados por fecha de inicio
    @Query("SELECT s FROM Schedule s ORDER BY s.initDate ASC")
    List<Schedule> findSchedulesOrderedByStartDate();
    
    // Obtener promedio de días estimados
    @Query("SELECT AVG(s.estimateDays) FROM Schedule s")
    Double getAverageEstimatedDays();
    
    // Buscar cronogramas del mes actual
    @Query("SELECT s FROM Schedule s WHERE MONTH(s.initDate) = MONTH(CURRENT_DATE) AND YEAR(s.initDate) = YEAR(CURRENT_DATE)")
    List<Schedule> findCurrentMonthSchedules();
}