package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Cronograma;
import com.example.tecno_proyect.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    // Listar todos los cronogramas
    public List<Cronograma> listarTodosLosCronogramas() {
        return scheduleRepository.findAll();
    }

    // Buscar cronograma por ID
    public Optional<Cronograma> buscarCronogramaPorId(Long id) {
        return scheduleRepository.findById(id);
    }

    // Insertar nuevo cronograma
    public Cronograma insertarCronograma(Cronograma cronograma) {
        return scheduleRepository.save(cronograma);
    }

    // Actualizar cronograma existente
    public Cronograma actualizarCronograma(Long id, Cronograma datos) {
        Optional<Cronograma> existente = scheduleRepository.findById(id);
        if (existente.isEmpty()) {
            throw new RuntimeException("No se encontró cronograma con id: " + id);
        }
        Cronograma c = existente.get();
        c.setFechaInicio(datos.getFechaInicio());
        c.setFechaFinal(datos.getFechaFinal());
        c.setDiasEstimados(datos.getDiasEstimados());
        c.setEstado(datos.getEstado());
        c.setIdProyecto(datos.getIdProyecto());
        c.setUsuarioId(datos.getUsuarioId());
        return scheduleRepository.save(c);
    }

    // Eliminar cronograma por ID
    public boolean eliminarCronograma(Long id) {
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar por proyecto
    public List<Cronograma> buscarPorProyecto(Long idProyecto) {
        return scheduleRepository.findByIdProyecto(idProyecto);
    }

    // Buscar por usuario
    public List<Cronograma> buscarPorUsuario(Long usuarioId) {
        return scheduleRepository.findByUsuarioId(usuarioId);
    }

    // Buscar por estado
    public List<Cronograma> buscarPorEstado(String estado) {
        return scheduleRepository.findByEstado(estado);
    }

    // Buscar por fecha de inicio
    public List<Cronograma> buscarPorFechaInicio(LocalDate fechaInicio) {
        return scheduleRepository.findByFechaInicio(fechaInicio);
    }

    // Buscar por fecha de finalización
    public List<Cronograma> buscarPorFechaFinal(LocalDate fechaFinal) {
        return scheduleRepository.findByFechaFinal(fechaFinal);
    }

    // Buscar por días estimados
    public List<Cronograma> buscarPorDiasEstimados(Integer diasEstimados) {
        return scheduleRepository.findByDiasEstimados(diasEstimados);
    }

    // Buscar por rango de fechas de inicio
    public List<Cronograma> buscarPorRangoFechaInicio(LocalDate fechaInicio, LocalDate fechaFinal) {
        return scheduleRepository.findByFechaInicioBetween(fechaInicio, fechaFinal);
    }

    // Buscar por rango de fechas de finalización
    public List<Cronograma> buscarPorRangoFechaFinal(LocalDate fechaInicio, LocalDate fechaFinal) {
        return scheduleRepository.findByFechaFinalBetween(fechaInicio, fechaFinal);
    }

    // Buscar cronogramas activos
    public List<Cronograma> buscarCronogramasActivos() {
        return scheduleRepository.findActiveSchedules();
    }

    // Buscar cronogramas completados
    public List<Cronograma> buscarCronogramasCompletados() {
        return scheduleRepository.findCompletedSchedules();
    }

    // Buscar cronogramas pendientes
    public List<Cronograma> buscarCronogramasPendientes() {
        return scheduleRepository.findPendingSchedules();
    }

    // Buscar cronogramas que terminan hoy
    public List<Cronograma> buscarCronogramasQueTerminanHoy() {
        return scheduleRepository.findSchedulesEndingToday();
    }

    // Buscar cronogramas que inician hoy
    public List<Cronograma> buscarCronogramasQueInicianHoy() {
        return scheduleRepository.findSchedulesStartingToday();
    }

    // Buscar cronogramas vencidos
    public List<Cronograma> buscarCronogramasVencidos() {
        return scheduleRepository.findOverdueSchedules();
    }

    // Buscar por proyecto y estado
    public List<Cronograma> buscarPorProyectoYEstado(Long idProyecto, String estado) {
        return scheduleRepository.findByIdProyectoAndEstado(idProyecto, estado);
    }

    // Buscar por usuario y estado
    public List<Cronograma> buscarPorUsuarioYEstado(Long usuarioId, String estado) {
        return scheduleRepository.findByUsuarioIdAndEstado(usuarioId, estado);
    }

    // Contar por estado
    public long contarPorEstado(String estado) {
        return scheduleRepository.countByEstado(estado);
    }

    // Contar por usuario
    public long contarPorUsuario(Long usuarioId) {
        return scheduleRepository.countByUsuarioId(usuarioId);
    }

    // Buscar cronogramas largos
    public List<Cronograma> buscarCronogramasLargos(Integer dias) {
        return scheduleRepository.findLongSchedules(dias);
    }

    // Buscar ordenados por fecha de inicio
    public List<Cronograma> buscarOrdenadosPorFechaInicio() {
        return scheduleRepository.findSchedulesOrderedByStartDate();
    }

    // Obtener promedio de días estimados
    public Double obtenerPromedioDiasEstimados() {
        return scheduleRepository.getAverageEstimatedDays();
    }

    // Buscar cronogramas del mes actual
    public List<Cronograma> buscarCronogramasMesActual() {
        return scheduleRepository.findCurrentMonthSchedules();
    }
}

