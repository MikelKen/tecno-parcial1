package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Schedule;
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
    public List<Schedule> listarTodosLosCronogramas() {
        return scheduleRepository.findAll();
    }

    // Buscar cronograma por ID
    public Optional<Schedule> buscarCronogramaPorId(Long id) {
        return scheduleRepository.findById(id);
    }

    // Insertar nuevo cronograma
    public Schedule insertarCronograma(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    // Actualizar cronograma existente
    public Schedule actualizarCronograma(Long id, Schedule datos) {
        Optional<Schedule> existente = scheduleRepository.findById(id);
        if (existente.isEmpty()) {
            throw new RuntimeException("No se encontró cronograma con id: " + id);
        }
        Schedule s = existente.get();
        s.setInitDate(datos.getInitDate());
        s.setFinalDate(datos.getFinalDate());
        s.setEstimateDays(datos.getEstimateDays());
        s.setState(datos.getState());
        s.setIdProject(datos.getIdProject());
        s.setUserId(datos.getUserId());
        return scheduleRepository.save(s);
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
    public List<Schedule> buscarPorProyecto(String idProject) {
        return scheduleRepository.findByIdProject(idProject);
    }

    // Buscar por usuario
    public List<Schedule> buscarPorUsuario(Long userId) {
        return scheduleRepository.findByUserId(userId);
    }

    // Buscar por estado
    public List<Schedule> buscarPorEstado(String state) {
        return scheduleRepository.findByState(state);
    }

    // Buscar por fecha de inicio
    public List<Schedule> buscarPorFechaInicio(LocalDate initDate) {
        return scheduleRepository.findByInitDate(initDate);
    }

    // Buscar por fecha de finalización
    public List<Schedule> buscarPorFechaFinal(LocalDate finalDate) {
        return scheduleRepository.findByFinalDate(finalDate);
    }

    // Buscar por días estimados
    public List<Schedule> buscarPorDiasEstimados(Integer estimateDays) {
        return scheduleRepository.findByEstimateDays(estimateDays);
    }

    // Buscar por rango de fechas de inicio
    public List<Schedule> buscarPorRangoFechaInicio(LocalDate startDate, LocalDate endDate) {
        return scheduleRepository.findByInitDateBetween(startDate, endDate);
    }

    // Buscar por rango de fechas de finalización
    public List<Schedule> buscarPorRangoFechaFinal(LocalDate startDate, LocalDate endDate) {
        return scheduleRepository.findByFinalDateBetween(startDate, endDate);
    }

    // Buscar cronogramas activos
    public List<Schedule> buscarCronogramasActivos() {
        return scheduleRepository.findActiveSchedules();
    }

    // Buscar cronogramas completados
    public List<Schedule> buscarCronogramasCompletados() {
        return scheduleRepository.findCompletedSchedules();
    }

    // Buscar cronogramas pendientes
    public List<Schedule> buscarCronogramasPendientes() {
        return scheduleRepository.findPendingSchedules();
    }

    // Buscar cronogramas que terminan hoy
    public List<Schedule> buscarCronogramasQueTerminanHoy() {
        return scheduleRepository.findSchedulesEndingToday();
    }

    // Buscar cronogramas que inician hoy
    public List<Schedule> buscarCronogramasQueInicianHoy() {
        return scheduleRepository.findSchedulesStartingToday();
    }

    // Buscar cronogramas vencidos
    public List<Schedule> buscarCronogramasVencidos() {
        return scheduleRepository.findOverdueSchedules();
    }

    // Buscar por proyecto y estado
    public List<Schedule> buscarPorProyectoYEstado(String idProject, String state) {
        return scheduleRepository.findByIdProjectAndState(idProject, state);
    }

    // Buscar por usuario y estado
    public List<Schedule> buscarPorUsuarioYEstado(Long userId, String state) {
        return scheduleRepository.findByUserIdAndState(userId, state);
    }

    // Contar por estado
    public long contarPorEstado(String state) {
        return scheduleRepository.countByState(state);
    }

    // Contar por usuario
    public long contarPorUsuario(Long userId) {
        return scheduleRepository.countByUserId(userId);
    }

    // Buscar cronogramas largos
    public List<Schedule> buscarCronogramasLargos(Integer days) {
        return scheduleRepository.findLongSchedules(days);
    }

    // Buscar ordenados por fecha de inicio
    public List<Schedule> buscarOrdenadosPorFechaInicio() {
        return scheduleRepository.findSchedulesOrderedByStartDate();
    }

    // Obtener promedio de días estimados
    public Double obtenerPromedioDiasEstimados() {
        return scheduleRepository.getAverageEstimatedDays();
    }

    // Buscar cronogramas del mes actual
    public List<Schedule> buscarCronogramasMesActual() {
        return scheduleRepository.findCurrentMonthSchedules();
    }
}
