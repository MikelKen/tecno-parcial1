package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Cronograma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Cronograma, Long> {
    
    // Buscar cronogramas por proyecto
    List<Cronograma> findByIdProyecto(Long idProyecto);
    
    // Buscar cronogramas por usuario
    List<Cronograma> findByUsuarioId(Long usuarioId);
    
    // Buscar cronogramas por estado
    List<Cronograma> findByEstado(String estado);
    
    // Buscar cronogramas por fecha de inicio
    List<Cronograma> findByFechaInicio(LocalDate fechaInicio);
    
    // Buscar cronogramas por fecha de finalización
    List<Cronograma> findByFechaFinal(LocalDate fechaFinal);
    
    // Buscar cronogramas por días estimados
    List<Cronograma> findByDiasEstimados(Integer diasEstimados);
    
    // Buscar cronogramas por rango de fechas de inicio
    @Query("SELECT s FROM Cronograma s WHERE s.fechaInicio BETWEEN :startDate AND :endDate")
    List<Cronograma> findByFechaInicioBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Buscar cronogramas por rango de fechas de finalización
    @Query("SELECT s FROM Cronograma s WHERE s.fechaFinal BETWEEN :startDate AND :endDate")
    List<Cronograma> findByFechaFinalBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Buscar cronogramas activos (en progreso)
    @Query("SELECT s FROM Cronograma s WHERE s.estado = 'En Progreso' OR s.estado = 'Activo'")
    List<Cronograma> findActiveSchedules();
    
    // Buscar cronogramas completados
    @Query("SELECT s FROM Cronograma s WHERE s.estado = 'Completado' OR s.estado = 'Terminado'")
    List<Cronograma> findCompletedSchedules();
    
    // Buscar cronogramas pendientes
    @Query("SELECT s FROM Cronograma s WHERE s.estado = 'Pendiente' OR s.estado = 'No Iniciado'")
    List<Cronograma> findPendingSchedules();
    
    // Buscar cronogramas que terminan hoy
    @Query("SELECT s FROM Cronograma s WHERE s.fechaFinal = CURRENT_DATE")
    List<Cronograma> findSchedulesEndingToday();
    
    // Buscar cronogramas que inician hoy
    @Query("SELECT s FROM Cronograma s WHERE s.fechaInicio = CURRENT_DATE")
    List<Cronograma> findSchedulesStartingToday();
    
    // Buscar cronogramas vencidos (fecha final pasada y no completados)
    @Query("SELECT s FROM Cronograma s WHERE s.fechaFinal < CURRENT_DATE AND s.estado != 'Completado' AND s.estado != 'Terminado'")
    List<Cronograma> findOverdueSchedules();
    
    // Buscar cronogramas por proyecto y estado
    List<Cronograma> findByIdProyectoAndEstado(Long idProyecto, String estado);
    
    // Buscar cronogramas por usuario y estado
    List<Cronograma> findByUsuarioIdAndEstado(Long usuarioId, String estado);
    
    // Contar cronogramas por estado
    @Query("SELECT COUNT(s) FROM Cronograma s WHERE s.estado = :estado")
    long countByEstado(@Param("estado") String estado);
    
    // Contar cronogramas por usuario
    @Query("SELECT COUNT(s) FROM Cronograma s WHERE s.usuarioId = :usuarioId")
    long countByUsuarioId(@Param("usuarioId") Long usuarioId);
    
    // Buscar cronogramas con duración mayor a días específicos
    @Query("SELECT s FROM Cronograma s WHERE s.diasEstimados > :dias")
    List<Cronograma> findLongSchedules(@Param("dias") Integer dias);
    
    // Buscar cronogramas ordenados por fecha de inicio
    @Query("SELECT s FROM Cronograma s ORDER BY s.fechaInicio ASC")
    List<Cronograma> findSchedulesOrderedByStartDate();
    
    // Obtener promedio de días estimados
    @Query("SELECT AVG(s.diasEstimados) FROM Cronograma s")
    Double getAverageEstimatedDays();
    
    // Buscar cronogramas del mes actual
    @Query("SELECT s FROM Cronograma s WHERE MONTH(s.fechaInicio) = MONTH(CURRENT_DATE) AND YEAR(s.fechaInicio) = YEAR(CURRENT_DATE)")
    List<Cronograma> findCurrentMonthSchedules();
}