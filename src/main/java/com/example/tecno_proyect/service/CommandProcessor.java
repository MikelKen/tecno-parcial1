package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommandProcessor {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ProjectService projectService;

        @Autowired
        private TaskService taskService;
    @Autowired
    private PersonaService personaService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private DesignService designService;

    @Autowired
    private PayPlanService payPlanService;

    @Autowired
    private PaysService paysService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialProjectService materialProjectService;

    @Autowired
    private EmailResponseService emailResponseService;

    public String processCommand(String subject, String senderEmail) {
        System.out.println("DEBUG: processCommand iniciado - subject: [" + subject + "], sender: [" + senderEmail + "]");
        try {
            if (subject == null || subject.trim().isEmpty()) {
                return "Error: Comando vacío. Formato esperado: COMANDO[\"parametros\"]";
            }

            // Identificar el comando
            System.out.println("DEBUG: Extrayendo comando...");
            String command = extractCommand(subject);
            System.out.println("DEBUG: Comando extraído: [" + command + "]");
            String[] parameters = extractParameters(subject);
            System.out.println("DEBUG: Parámetros extraídos: " + parameters.length);

            System.out.println("DEBUG: Entrando al switch con comando: [" + command.toUpperCase() + "]");
            switch (command.toUpperCase()) {
                // Clientes
                case "LISCLI":
                    System.out.println("DEBUG: Ejecutando caso LISCLI...");
                    return handleListClientes(parameters);
                case "INSCLI":
                    return handleInsertCliente(parameters);
                case "BUSCLIEMAIL":
                    return handleBuscarClientePorEmail(parameters);
                case "UPDCLI":
                    return handleActualizarCliente(parameters);
                case "DELCLI":
                    return handleEliminarCliente(parameters);
                case "BUSCLIPROY":
                    return handleBuscarClientesConProyectos(parameters);
                case "ESTCLIS":
                    return handleObtenerEstadisticasClientes(parameters);
                
                // Usuarios
                case "LISUSR":
                    return handleListarTodosLosUsuarios(parameters);
                case "BUSUSRNOM":
                    return handleBuscarUsuarioPorNombre(parameters);
                case "BUSUSREMAIL":
                    return handleBuscarUsuarioPorEmail(parameters);
                case "INSUSR":
                    return handleInsertarUsuario(parameters);
                case "UPDUSR":
                    return handleActualizarUsuario(parameters);
                case "DELUSR":
                    return handleEliminarUsuario(parameters);
                case "BUSUSRROL":
                    return handleBuscarUsuariosPorRol(parameters);

                // Proyectos
                case "LISPROY":
                    return handleListarTodosLosProyectos(parameters);
                case "INSPROY":
                    return handleInsertarProyecto(parameters);
                case "BUSPROYNOM":
                    return handleBuscarProyectoPorNombre(parameters);
                case "UPDPROY":
                    return handleActualizarProyecto(parameters);
                case "BUSPROYCLI":
                    return handleBuscarProyectosPorCliente(parameters);
                case "BUSPROYUSR":
                    return handleBuscarProyectosPorUsuario(parameters);
                case "BUSPROYEST":
                    return handleBuscarProyectosPorEstado(parameters);
                case "ESTPROY":
                    return handleObtenerEstadisticasProyectos(parameters);

                // Comandos de materiales-productos
                case "LISMAT":
                    return handleListarTodosLosMateriales(parameters);
                case "BUSMATNOM":
                    return handleBuscarMaterialPorNombre(parameters);
                case "INSMAT":
                    return handleInsertarMaterial(parameters);
                case "UPDMAT":
                    return handleActualizarMaterial(parameters);
                case "BUSMATTIPO":
                    return handleBuscarMaterialesPorTipo(parameters);
                case "UPDMATPRECIO":
                    return handleActualizarPrecioMaterial(parameters);
                case "UPDMATSTOCK":
                    return handleActualizarStockMaterial(parameters);
                case "REDMATSTOCK":
                    return handleReducirStockMaterial(parameters);
                case "AUMMATSTOCK":
                    return handleAumentarStockMaterial(parameters);
                case "VERMATDISP":
                    return handleVerificarDisponibilidadMaterial(parameters);
                
                // Diseños
                case "LISDESIGN":
                    return handleListarTodosLosDisenos(parameters);
                case "BUSDESIGNID":
                    return handleBuscarDisenoPorId(parameters);
                case "BUSDESIGNQUOTE":
                    return handleBuscarDisenoPorCotizacion(parameters);
                case "INSDESIGN":
                    return handleInsertarDiseno(parameters);
                case "UPDDESIGN":
                    return handleActualizarDiseno(parameters);
                case "DELDESIGN":
                    return handleEliminarDiseno(parameters);
                case "BUSDESIGNUSR":
                    return handleBuscarDisenosPorUsuario(parameters);
                case "DESIGNAPPR":
                    return handleBuscarDisenosAprobados(parameters);
                case "APPRDESIGN":
                    return handleAprobarDiseno(parameters);
                case "REJDESIGN":
                    return handleRechazarDiseno(parameters);
                case "DESIGNAPPRUSR":
                    return handleObtenerDisenosAprobadosPorUsuario(parameters);
                case "DESIGNPENDUSR":
                    return handleObtenerDisenosPendientesPorUsuario(parameters);
                

                // Cronogramas
                case "LISSCH":
                    return handleListarTodosLosCronogramas(parameters);
                case "BUSSCHID":
                    return handleBuscarCronogramaPorId(parameters);
                case "INSSCH":
                    return handleInsertarCronograma(parameters);
                case "UPDSCH":
                    return handleActualizarCronograma(parameters);
                case "BUSSCHPROY":
                    return handleBuscarCronogramasPorProyecto(parameters);
                case "BUSSCHUSR":
                    return handleBuscarCronogramasPorUsuario(parameters);
                case "SCHACT":
                    return handleBuscarCronogramasActivos(parameters);
                case "SCHCOMP":
                    return handleBuscarCronogramasCompletados(parameters);
                // Tareas
                case "LISTASK":
                    return handleListarTodasLasTareas(parameters);
                case "BUSTASKID":
                    return handleBuscarTareaPorId(parameters);
                case "INSTASK":
                    return handleInsertarTarea(parameters);
                case "UPDTASK":
                    return handleActualizarTarea(parameters);
                case "DELTASK":
                    return handleEliminarTarea(parameters);
                case "BUSTASKSCH":
                    return handleBuscarTareasPorCronograma(parameters);
                case "BUSTASKUSR":
                    return handleBuscarTareasPorUsuario(parameters);
                case "TASKACT":
                    return handleBuscarTareasActivas(parameters);
                case "TASKCOMP":
                    return handleBuscarTareasCompletadas(parameters);
                case "TASKPEND":
                    return handleBuscarTareasPendientes(parameters);

                // Cotizaciones
                case "LISQUOTE":
                    return handleListarTodasLasCotizaciones(parameters);
                case "BUSQUOTEID":
                    return handleBuscarCotizacionPorId(parameters);
                case "INSQUOTE":
                    return handleInsertarCotizacion(parameters);
                case "UPDQUOTE":
                    return handleActualizarCotizacion(parameters);
                case "DELQUOTE":
                    return handleEliminarCotizacion(parameters);
                case "BUSQUOTEPROY":
                    return handleBuscarCotizacionesPorProyecto(parameters);
                case "BUSQUOTEUSR":
                    return handleBuscarCotizacionesPorUsuario(parameters);
                case "BUSQUOTETYPE":
                    return handleBuscarCotizacionesPorTipoMetro(parameters);
                case "TOTALQUOTEAPPR":
                    return handleObtenerTotalCotizacionesAprobadasPorProyecto(parameters);
                case "CALCQUOTE":
                    return handleCalcularTotalCotizacion(parameters);
                case "APPRQUOTE":
                    return handleAprobarCotizacion(parameters);
                case "REJQUOTE":
                    return handleRechazarCotizacion(parameters);
                
                // Comandos de planes de pago
                case "LISPAYPLAN":
                    return handleListarTodosLosPlanesPago(parameters);
                case "BUSPAYPLANID":
                    return handleBuscarPlanPagoPorId(parameters);
                case "BUSPAYPLANPROY":
                    return handleBuscarPlanPagoPorProyecto(parameters);
                case "INSPAYPLAN":
                    return handleInsertarPlanPago(parameters);
                case "UPDPAYPLAN":
                    return handleActualizarPlanPago(parameters);
                case "BUSPAYPLANEST":
                    return handleBuscarPlanesPagoPorEstado(parameters);
                case "TOTDEUDAPEND":
                    return handleObtenerTotalDeudaPendiente(parameters);
                case "TOTPAGADO":
                    return handleObtenerTotalPagado(parameters);
                case "UPDDEUDATOT":
                    return handleActualizarDeudaTotal(parameters);
                case "CALCPORCPAGO":
                    return handleCalcularPorcentajePago(parameters);
                case "CAMBIOEST":
                    return handleCambiarEstado(parameters);
                case "CREARPLANPAGOS":
                    return handleCrearPlanPagoConPagos(parameters);
                case "OBTENERPLANPAGO":
                    return handleObtenerPlanPagoCompleto(parameters);
                case "RECALCPLANPAGO":
                    return handleRecalcularPlanPago(parameters);
                
                // Comandos de pagos
                case "LISPAYS":
                    return handleListarTodosLosPagos(parameters);
                case "BUSPAYID":
                    return handleBuscarPagoPorId(parameters);
                case "INSPAY":
                    return handleInsertarPago(parameters);
                case "UPDPAY":
                    return handleActualizarPago(parameters);
                case "BUSPAYCLI":
                    return handleBuscarPagosPorCliente(parameters);
                case "TOTPAGCLI":
                    return handleObtenerTotalPagadoPorCliente(parameters);
                case "PLANPAGOHAS":
                    return handlePlanPagoTienePagos(parameters);
                case "COUNTPAYPPLAN":
                    return handleContarPagosPorPlanPago(parameters);
                case "OBTPAGOSPLAN":
                    return handleObtenerPagosPorPlanPago(parameters);
                case "PAGAR":
                    return handlePagar(parameters);
                
                // Comandos de materiales-proyecto
                case "LISMATPROY":
                    return handleListarTodosMaterialesProyecto(parameters);
                case "BUSMATPROYID":
                    return handleBuscarMaterialProyectoPorId(parameters);
                case "INSMATPROY":
                    return handleInsertarMaterialProyecto(parameters);
                case "UPDMATPROY":
                    return handleActualizarMaterialProyecto(parameters);
                case "BUSMATPORPROY":
                    return handleBuscarMaterialesPorProyecto(parameters);
                case "BUSPROYPORMAT":
                    return handleBuscarProyectosPorMaterial(parameters);
                    
                // --- Comandos de gestión de stock ---
                case "DEVOLVERSOBRANTE":
                    return handleDevolverMaterialSobrante(parameters);
                case "DEVOLVERTODO":
                    return handleDevolverTodoSobrante(parameters);
                case "REPORTESTOCK":
                    return handleReporteStockProyecto(parameters);
                case "AJUSTARSOBRANTE":
                    return handleAjustarSobrantePorUsoReal(parameters);
                case "VERIFICARSTOCK":
                    return handleVerificarStockMaterial(parameters);
                    
                // --- Comandos de asignación ---
                case "LISASIG":
                    return handleListarAsignaciones(parameters);
                case "LISASIG_USR":
                    return handleAsignacionesPorUsuario(parameters);
                case "ASIGNARPROYUSR":
                    return handleAsignarProyectoAUsuario(parameters);
                case "CARGAUSR":
                    return handleCargaTrabajoUsuario(parameters);
                
                // --- Comandos de reportes ---
                case "REPORTECLIENTE":
                    return handleReporteCliente(parameters);
                case "REPORTEPROYECTO":
                    return handleReporteProyecto(parameters);
                case "REPORTEMATERIALES":
                    return handleReporteMateriales(parameters);
                case "REPORTEINT":
                    return handleReporteIntegral(parameters);
                    
                // Comando de ayuda
                case "HELP":
                    return handleHelp(parameters);
                    
                default:
                    return emailResponseService.formatUnknownCommandResponse(command);
            }

        } catch (Exception e) {
            return emailResponseService.formatErrorResponse(e.getMessage(), subject);
        }
    }

    // --- Métodos de tareas ---
    private String handleListarTodasLasTareas(String[] parameters) {
        try {
            List<Task> tareas = taskService.listarTodasLasTareas();
            return emailResponseService.formatListTareasResponse(tareas, "LISTASK");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al listar tareas: " + e.getMessage(), "LISTASK");
        }
    }

    private String handleBuscarTareaPorId(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSTASKID", "BUSTASKID[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Optional<Task> tareaOpt = taskService.buscarTareaPorId(id);
            if (tareaOpt.isPresent()) {
                return emailResponseService.formatSearchTareaSuccess(tareaOpt.get(), "BUSTASKID");
            } else {
                return emailResponseService.formatSearchTareaNotFound(id, "BUSTASKID");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar tarea por id: " + e.getMessage(), "BUSTASKID");
        }
    }

    private String handleInsertarTarea(String[] parameters) {
        if (parameters.length < 6) {
            return emailResponseService.formatInsufficientParametersResponse("INSTASK", "INSTASK[\"initHour\",\"finalHour\",\"description\",\"state\",\"idSchedule\",\"userId\"]");
        }
        try {
            Task task = new Task(
                java.time.LocalTime.parse(parameters[0]), // initHour
                java.time.LocalTime.parse(parameters[1]), // finalHour
                parameters[2], // description
                parameters[3], // state
                Long.parseLong(parameters[4]), // idSchedule
                Long.parseLong(parameters[5])  // userId
            );
            Task nueva = taskService.insertarTarea(task);
            return emailResponseService.formatInsertTareaSuccess(nueva, "INSTASK");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al insertar tarea: " + e.getMessage(), "INSTASK");
        }
    }

    private String handleActualizarTarea(String[] parameters) {
        if (parameters.length < 7) {
            return emailResponseService.formatInsufficientParametersResponse("UPDTASK", "UPDTASK[\"id\",\"initHour\",\"finalHour\",\"description\",\"state\",\"idSchedule\",\"userId\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Task datos = new Task(
                java.time.LocalTime.parse(parameters[1]), // initHour
                java.time.LocalTime.parse(parameters[2]), // finalHour
                parameters[3], // description
                parameters[4], // state
                Long.parseLong(parameters[5]), // idSchedule
                Long.parseLong(parameters[6])  // userId
            );
            Task actualizado = taskService.actualizarTarea(id, datos);
            return emailResponseService.formatUpdateTareaSuccess(actualizado, "UPDTASK");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar tarea: " + e.getMessage(), "UPDTASK");
        }
    }

    private String handleEliminarTarea(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("DELTASK", "DELTASK[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            boolean eliminado = taskService.eliminarTarea(id);
            if (eliminado) {
                return emailResponseService.formatDeleteTareaSuccess(id, "DELTASK");
            } else {
                return emailResponseService.formatSearchTareaNotFound(id, "DELTASK");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al eliminar tarea: " + e.getMessage(), "DELTASK");
        }
    }

    private String handleBuscarTareasPorCronograma(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSTASKSCH", "BUSTASKSCH[\"idSchedule\"]");
        }
        try {
            Long idSchedule = Long.parseLong(parameters[0]);
            List<Task> tareas = taskService.buscarPorCronograma(idSchedule);
            return emailResponseService.formatListTareasPorCronogramaResponse(tareas, "BUSTASKSCH");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar tareas por cronograma: " + e.getMessage(), "BUSTASKSCH");
        }
    }

    private String handleBuscarTareasPorUsuario(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSTASKUSR", "BUSTASKUSR[\"userId\"]");
        }
        try {
            Long userId = Long.parseLong(parameters[0]);
            List<Task> tareas = taskService.buscarPorUsuario(userId);
            return emailResponseService.formatListTareasPorUsuarioResponse(tareas, "BUSTASKUSR");
        } catch (NumberFormatException e) {
            return emailResponseService.formatErrorResponse("ID de usuario inválido: " + parameters[0], "BUSTASKUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar tareas por usuario: " + e.getMessage(), "BUSTASKUSR");
        }
    }

    private String handleBuscarTareasActivas(String[] parameters) {
        try {
            List<Task> tareas = taskService.buscarTareasActivas();
            return emailResponseService.formatListTareasActivasResponse(tareas, "TASKACT");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar tareas activas: " + e.getMessage(), "TASKACT");
        }
    }

    private String handleBuscarTareasCompletadas(String[] parameters) {
        try {
            List<Task> tareas = taskService.buscarTareasCompletadas();
            return emailResponseService.formatListTareasCompletadasResponse(tareas, "TASKCOMP");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar tareas completadas: " + e.getMessage(), "TASKCOMP");
        }
    }

    private String handleBuscarTareasPendientes(String[] parameters) {
        try {
            List<Task> tareas = taskService.buscarTareasPendientes();
            return emailResponseService.formatListTareasPendientesResponse(tareas, "TASKPEND");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar tareas pendientes: " + e.getMessage(), "TASKPEND");
        }
    }

    // --- Métodos de cronogramas ---
    private String handleListarTodosLosCronogramas(String[] parameters) {
        try {
            List<Schedule> cronogramas = scheduleService.listarTodosLosCronogramas();
            return emailResponseService.formatListCronogramasResponse(cronogramas, "LISSCH");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al listar cronogramas: " + e.getMessage(), "LISSCH");
        }
    }

    private String handleBuscarCronogramaPorId(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSSCHID", "BUSSCHID[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Optional<Schedule> cronogramaOpt = scheduleService.buscarCronogramaPorId(id);
            if (cronogramaOpt.isPresent()) {
                return emailResponseService.formatSearchCronogramaSuccess(cronogramaOpt.get(), "BUSSCHID");
            } else {
                return emailResponseService.formatSearchCronogramaNotFound(id, "BUSSCHID");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar cronograma por id: " + e.getMessage(), "BUSSCHID");
        }
    }

    private String handleInsertarCronograma(String[] parameters) {
        if (parameters.length < 6) {
            return emailResponseService.formatInsufficientParametersResponse("INSSCH", "INSSCH[\"initDate\",\"finalDate\",\"estimateDays\",\"state\",\"idProject\",\"userId\"]");
        }
        try {
            Schedule schedule = new Schedule(
                java.time.LocalDate.parse(parameters[0]), // initDate
                java.time.LocalDate.parse(parameters[1]), // finalDate
                Integer.parseInt(parameters[2]), // estimateDays
                parameters[3], // state
                Long.parseLong(parameters[4]), // idProject
                Long.parseLong(parameters[5])  // userId
            );
            Schedule nuevo = scheduleService.insertarCronograma(schedule);
            return emailResponseService.formatInsertCronogramaSuccess(nuevo, "INSSCH");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al insertar cronograma: " + e.getMessage(), "INSSCH");
        }
    }

    private String handleActualizarCronograma(String[] parameters) {
        if (parameters.length < 7) {
            return emailResponseService.formatInsufficientParametersResponse("UPDSCH", "UPDSCH[\"id\",\"initDate\",\"finalDate\",\"estimateDays\",\"state\",\"idProject\",\"userId\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Schedule datos = new Schedule(
                java.time.LocalDate.parse(parameters[1]), // initDate
                java.time.LocalDate.parse(parameters[2]), // finalDate
                Integer.parseInt(parameters[3]), // estimateDays
                parameters[4], // state
                Long.parseLong(parameters[5]), // idProject
                Long.parseLong(parameters[6])  // userId
            );
            Schedule actualizado = scheduleService.actualizarCronograma(id, datos);
            return emailResponseService.formatUpdateCronogramaSuccess(actualizado, "UPDSCH");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar cronograma: " + e.getMessage(), "UPDSCH");
        }
    }

    private String handleBuscarCronogramasPorProyecto(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSSCHPROY", "BUSSCHPROY[\"idProject\"]");
        }
        try {
            List<Schedule> cronogramas = scheduleService.buscarPorProyecto(Long.parseLong(parameters[0]));
            return emailResponseService.formatListCronogramasPorProyectoResponse(cronogramas, "BUSSCHPROY");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar cronogramas por proyecto: " + e.getMessage(), "BUSSCHPROY");
        }
    }

    private String handleBuscarCronogramasPorUsuario(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSSCHUSR", "BUSSCHUSR[\"userId\"]");
        }
        try {
            Long userId = Long.parseLong(parameters[0]);
            List<Schedule> cronogramas = scheduleService.buscarPorUsuario(userId);
            return emailResponseService.formatListCronogramasPorUsuarioResponse(cronogramas, "BUSSCHUSR");
        } catch (NumberFormatException e) {
            return emailResponseService.formatErrorResponse("ID de usuario inválido: " + parameters[0], "BUSSCHUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar cronogramas por usuario: " + e.getMessage(), "BUSSCHUSR");
        }
    }

    private String handleBuscarCronogramasActivos(String[] parameters) {
        try {
            List<Schedule> cronogramas = scheduleService.buscarCronogramasActivos();
            return emailResponseService.formatListCronogramasActivosResponse(cronogramas, "SCHACT");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar cronogramas activos: " + e.getMessage(), "SCHACT");
        }
    }

    private String handleBuscarCronogramasCompletados(String[] parameters) {
        try {
            List<Schedule> cronogramas = scheduleService.buscarCronogramasCompletados();
            return emailResponseService.formatListCronogramasCompletadosResponse(cronogramas, "SCHCOMP");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar cronogramas completados: " + e.getMessage(), "SCHCOMP");
        }
    }
    // --- Métodos de proyectos ---
    private String handleListarTodosLosProyectos(String[] parameters) {
        try {
            List<Project> proyectos = projectService.listarTodosLosProyectos();
            return emailResponseService.formatListProyectosResponse(proyectos, "LISPROY");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al listar proyectos: " + e.getMessage(), "LISPROY");
        }
    }

    private String handleInsertarProyecto(String[] parameters) {
        if (parameters.length < 6) {
            return emailResponseService.formatInsufficientParametersResponse("INSPROY", "INSPROY[\"nombre\",\"descripcion\",\"ubicacion\",\"estado\",\"idCliente\",\"idUsuario\"]");
        }
        try {
            Project proyecto = projectService.insertarProyecto(
                parameters[0], // nombre
                parameters[1], // descripcion
                parameters[2], // ubicacion
                parameters[3], // estado
                Long.parseLong(parameters[4]), // idCliente
                Long.parseLong(parameters[5])  // idUsuario
            );
            return emailResponseService.formatInsertProyectoSuccess(proyecto, "INSPROY");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al insertar proyecto: " + e.getMessage(), "INSPROY");
        }
    }

    private String handleBuscarProyectoPorNombre(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSPROYNOM", "BUSPROYNOM[\"nombre\"]");
        }
        try {
            Optional<Project> proyectoOpt = projectService.buscarProyectoPorNombre(parameters[0]);
            if (proyectoOpt.isPresent()) {
                return emailResponseService.formatSearchProyectoSuccess(proyectoOpt.get(), "BUSPROYNOM");
            } else {
                return emailResponseService.formatSearchProyectoNotFound(parameters[0], "BUSPROYNOM");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar proyecto por nombre: " + e.getMessage(), "BUSPROYNOM");
        }
    }

    private String handleActualizarProyecto(String[] parameters) {
        if (parameters.length < 6) {
            return emailResponseService.formatInsufficientParametersResponse("UPDPROY", "UPDPROY[\"nombre\",\"descripcion\",\"ubicacion\",\"estado\",\"idCliente\",\"idUsuario\"]");
        }
        try {
            Project proyecto = projectService.actualizarProyectoPorNombre(
                parameters[0], // nombre
                parameters[1], // descripcion
                parameters[2], // ubicacion
                parameters[3], // estado
                Long.parseLong(parameters[4]), // idCliente
                Long.parseLong(parameters[5])  // idUsuario
            );
            return emailResponseService.formatUpdateProyectoSuccess(proyecto, "UPDPROY");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar proyecto: " + e.getMessage(), "UPDPROY");
        }
    }

    private String handleBuscarProyectosPorCliente(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSPROYCLI", "BUSPROYCLI[\"idCliente\"]");
        }
        try {
            List<Project> proyectos = projectService.buscarProyectosPorCliente(Long.parseLong(parameters[0]));
            return emailResponseService.formatListProyectosPorClienteResponse(proyectos, "BUSPROYCLI");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar proyectos por cliente: " + e.getMessage(), "BUSPROYCLI");
        }
    }

    private String handleBuscarProyectosPorUsuario(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSPROYUSR", "BUSPROYUSR[\"idUsuario\"]");
        }
        try {
            List<Project> proyectos = projectService.buscarProyectosPorUsuario(Long.parseLong(parameters[0]));
            return emailResponseService.formatListProyectosPorUsuarioResponse(proyectos, "BUSPROYUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar proyectos por usuario: " + e.getMessage(), "BUSPROYUSR");
        }
    }

    private String handleBuscarProyectosPorEstado(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSPROYEST", "BUSPROYEST[\"estado\"]");
        }
        try {
            List<Project> proyectos = projectService.buscarProyectosPorEstado(parameters[0]);
            return emailResponseService.formatListProyectosPorEstadoResponse(proyectos, "BUSPROYEST");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar proyectos por estado: " + e.getMessage(), "BUSPROYEST");
        }
    }

    private String handleObtenerEstadisticasProyectos(String[] parameters) {
        try {
            String estadisticas = projectService.obtenerEstadisticasProyectos();
            return emailResponseService.formatEstadisticasProyectosResponse(estadisticas, "ESTPROY");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al obtener estadísticas de proyectos: " + e.getMessage(), "ESTPROY");
        }
    }

    private String extractCommand(String subject) {
        Pattern pattern = Pattern.compile("^([A-Z]+)\\[");
        Matcher matcher = pattern.matcher(subject.trim());
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Formato de comando inválido. Use: COMANDO[\"parametros\"]");
    }

    private String[] extractParameters(String subject) {
        System.out.println("extractParameters - Subject recibido: [" + subject + "]");
        System.out.println("extractParameters - Longitud del subject: " + subject.length());
        
        Pattern pattern = Pattern.compile("\\[(.*)\\]");
        Matcher matcher = pattern.matcher(subject.trim());
        if (matcher.find()) {
            String paramString = matcher.group(1);
            System.out.println("extractParameters - Contenido entre corchetes: [" + paramString + "]");
            
            // Caso especial para el parámetro "*"
            if (paramString.equals("*") || paramString.equals("\"*\"")) {
                return new String[]{"*"};
            }
            
            // Verificar si el contenido parece estar cortado (no termina con comillas)
            if (paramString.contains("\"") && !paramString.trim().endsWith("\"")) {
                System.out.println("extractParameters - ADVERTENCIA: El contenido parece estar cortado");
                System.out.println("extractParameters - Último carácter: [" + paramString.charAt(paramString.length()-1) + "]");
            }
            
            // Si hay múltiples parámetros separados por comas
            if (paramString.contains(",")) {
                System.out.println("extractParameters - Detectados múltiples parámetros");
                
                // Usar regex para extraer parámetros entre comillas correctamente
                Pattern paramPattern = Pattern.compile("\"([^\"]*?)\"");
                Matcher paramMatcher = paramPattern.matcher(paramString);
                
                List<String> params = new ArrayList<>();
                while (paramMatcher.find()) {
                    params.add(paramMatcher.group(1));
                }
                
                System.out.println("extractParameters - Parámetros encontrados con regex: " + params.size());
                for (int i = 0; i < params.size(); i++) {
                    System.out.println("extractParameters - Parámetro " + i + ": [" + params.get(i) + "]");
                }
                
                return params.toArray(new String[0]);
            } else {
                // Un solo parámetro
                if (paramString.startsWith("\"") && paramString.endsWith("\"")) {
                    paramString = paramString.substring(1, paramString.length() - 1);
                }
                return new String[]{paramString};
            }
        }
        System.out.println("extractParameters - No se encontraron parámetros");
        return new String[0];
    }

    private String handleListPersonas(String[] parameters) {
        try {
            List<Persona> personas = personaService.listarTodasLasPersonas();
            return emailResponseService.formatListPersonasResponse(personas, "LISPER");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al listar personas: " + e.getMessage(), "LISPER");
        }
    }

    private String handleInsertPersona(String[] parameters) {
        if (parameters.length < 7) {
            return emailResponseService.formatInsufficientParametersResponse("INSPER", 
                "INSPER[\"ci\",\"nombre\",\"apellido\",\"cargo\",\"telefono\",\"celular\",\"email\"]");
        }

        try {
            Persona persona = personaService.insertarPersona(
                parameters[0], // CI
                parameters[1], // nombre
                parameters[2], // apellido
                parameters[3], // cargo
                parameters[4], // teléfono
                parameters[5], // celular
                parameters[6]  // email
            );

            return emailResponseService.formatInsertPersonaSuccess(persona, "INSPER");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al insertar persona: " + e.getMessage(), "INSPER");
        }
    }

    private String handleUpdatePersona(String[] parameters) {
        if (parameters.length < 7) {
            return emailResponseService.formatInsufficientParametersResponse("UPDPER", 
                "UPDPER[\"ci\",\"nombre\",\"apellido\",\"cargo\",\"telefono\",\"celular\",\"email\"]");
        }

        try {
            Persona persona = personaService.actualizarPersona(
                parameters[0], // CI
                parameters[1], // nombre
                parameters[2], // apellido
                parameters[3], // cargo
                parameters[4], // teléfono
                parameters[5], // celular
                parameters[6]  // email
            );

            return emailResponseService.formatUpdatePersonaSuccess(persona, "UPDPER");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar persona: " + e.getMessage(), "UPDPER");
        }
    }

    private String handleDeletePersona(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("DELPER", 
                "DELPER[\"ci\"]");
        }

        try {
            String ci = parameters[0];
            boolean eliminado = personaService.eliminarPersona(ci);
            
            if (eliminado) {
                return emailResponseService.formatDeletePersonaSuccess(ci, "DELPER");
            } else {
                return emailResponseService.formatSearchPersonaNotFound(ci, "DELPER");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al eliminar persona: " + e.getMessage(), "DELPER");
        }
    }

    private String handleBuscarPersona(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSPER", 
                "BUSPER[\"ci\"]");
        }

        try {
            String ci = parameters[0];
            Optional<Persona> personaOpt = personaService.buscarPersonaPorCi(ci);
            
            if (personaOpt.isPresent()) {
                return emailResponseService.formatSearchPersonaSuccess(personaOpt.get(), "BUSPER");
            } else {
                return emailResponseService.formatSearchPersonaNotFound(ci, "BUSPER");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar persona: " + e.getMessage(), "BUSPER");
        }
    }
    
    private String handleListClientes(String[] parameters) {
        try {
            System.out.println("DEBUG: Iniciando handleListClientes");
            List<Client> clientes = clientService.listarTodosLosClientes();
            System.out.println("DEBUG: Clientes obtenidos: " + (clientes != null ? clientes.size() : "null"));
            String response = emailResponseService.formatListClientesResponse(clientes, "LISCLI");
            System.out.println("DEBUG: Respuesta generada, longitud: " + (response != null ? response.length() : "null"));
            return response;
        } catch (Exception e) {
            System.out.println("DEBUG: Error en handleListClientes: " + e.getMessage());
            e.printStackTrace();
            return emailResponseService.formatErrorResponse("Error al listar clientes: " + e.getMessage(), "LISCLI");
        }
    }

        private String handleBuscarClientePorEmail(String[] parameters) {
            if (parameters.length < 1) {
                return emailResponseService.formatInsufficientParametersResponse("BUSCLIEMAIL", "BUSCLIEMAIL[\"email\"]");
            }
            try {
                String email = parameters[0];
                Optional<Client> clientOpt = clientService.buscarClientePorEmail(email);
                if (clientOpt.isPresent()) {
                    return emailResponseService.formatSearchClienteSuccess(clientOpt.get(), "BUSCLIEMAIL");
                } else {
                    return emailResponseService.formatSearchClienteNotFound(email, "BUSCLIEMAIL");
                }
            } catch (Exception e) {
                return emailResponseService.formatErrorResponse("Error al buscar cliente por email: " + e.getMessage(), "BUSCLIEMAIL");
            }
        }

        private String handleInsertCliente(String[] parameters) {
            System.out.println("INSCLI - Parámetros recibidos: " + parameters.length);
            for (int i = 0; i < parameters.length; i++) {
                System.out.println("Parámetro " + i + ": [" + parameters[i] + "]");
            }
            if (parameters.length < 4) {
                return emailResponseService.formatInsufficientParametersResponse("INSCLI", "INSCLI[\"nombre\",\"email\",\"telefono\",\"direccion\"]");
            }
            try {
                Client cliente = clientService.insertarCliente(
                    parameters[0], // nombre
                    parameters[1], // email
                    parameters[2], // teléfono
                    parameters[3]  // dirección
                );
                return emailResponseService.formatInsertClienteSuccess(cliente, "INSCLI");
            } catch (Exception e) {
                return emailResponseService.formatErrorResponse("Error al insertar cliente: " + e.getMessage(), "INSCLI");
            }
        }

        private String handleActualizarCliente(String[] parameters) {
            if (parameters.length < 4) {
                return emailResponseService.formatInsufficientParametersResponse("UPDCLI", "UPDCLI[\"nombre\",\"email\",\"telefono\",\"direccion\"]");
            }
            try {
                Client cliente = clientService.actualizarCliente(
                    parameters[0], // nombre
                    parameters[1], // email
                    parameters[2], // teléfono
                    parameters[3]  // dirección
                );
                return emailResponseService.formatUpdateClienteSuccess(cliente, "UPDCLI");
            } catch (Exception e) {
                return emailResponseService.formatErrorResponse("Error al actualizar cliente: " + e.getMessage(), "UPDCLI");
            }
        }

        private String handleEliminarCliente(String[] parameters) {
            if (parameters.length < 1) {
                return emailResponseService.formatInsufficientParametersResponse("DELCLI", "DELCLI[\"nombre\"]");
            }
            try {
                String nombre = parameters[0];
                boolean eliminado = clientService.eliminarCliente(nombre);
                if (eliminado) {
                    return emailResponseService.formatDeleteClienteSuccess(nombre, "DELCLI");
                } else {
                    return emailResponseService.formatSearchClienteNotFound(nombre, "DELCLI");
                }
            } catch (Exception e) {
                return emailResponseService.formatErrorResponse("Error al eliminar cliente: " + e.getMessage(), "DELCLI");
            }
        }

        private String handleBuscarClientesConProyectos(String[] parameters) {
            try {
                List<Client> clientes = clientService.buscarClientesConProyectos();
                return emailResponseService.formatListClientesConProyectosResponse(clientes, "BUSCLIPROY");
            } catch (Exception e) {
                return emailResponseService.formatErrorResponse("Error al buscar clientes con proyectos: " + e.getMessage(), "BUSCLIPROY");
            }
        }

        private String handleObtenerEstadisticasClientes(String[] parameters) {
            try {
                String estadisticas = clientService.obtenerEstadisticasClientes();
                return emailResponseService.formatEstadisticasClientesResponse(estadisticas, "ESTCLIS");
            } catch (Exception e) {
                return emailResponseService.formatErrorResponse("Error al obtener estadísticas de clientes: " + e.getMessage(), "ESTCLIS");
            }
        }

    // --- Métodos de usuarios ---
    private String handleListarTodosLosUsuarios(String[] parameters) {
        try {
            List<User> usuarios = userService.listarTodosLosUsuarios();
            return emailResponseService.formatListUsuariosResponse(usuarios, "LISUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al listar usuarios: " + e.getMessage(), "LISUSR");
        }
    }

    private String handleBuscarUsuarioPorNombre(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSUSRNOM", "BUSUSRNOM[\"nombre\"]");
        }
        try {
            Optional<User> usuarioOpt = userService.buscarUsuarioPorNombre(parameters[0]);
            if (usuarioOpt.isPresent()) {
                return emailResponseService.formatSearchUsuarioSuccess(usuarioOpt.get(), "BUSUSRNOM");
            } else {
                return emailResponseService.formatSearchUsuarioNotFound(parameters[0], "BUSUSRNOM");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar usuario por nombre: " + e.getMessage(), "BUSUSRNOM");
        }
    }

    private String handleBuscarUsuarioPorEmail(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSUSREMAIL", "BUSUSREMAIL[\"email\"]");
        }
        try {
            Optional<User> usuarioOpt = userService.buscarUsuarioPorEmail(parameters[0]);
            if (usuarioOpt.isPresent()) {
                return emailResponseService.formatSearchUsuarioSuccess(usuarioOpt.get(), "BUSUSREMAIL");
            } else {
                return emailResponseService.formatSearchUsuarioNotFound(parameters[0], "BUSUSREMAIL");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar usuario por email: " + e.getMessage(), "BUSUSREMAIL");
        }
    }

    private String handleInsertarUsuario(String[] parameters) {
        if (parameters.length < 6) {
            return emailResponseService.formatInsufficientParametersResponse("INSUSR", "INSUSR[\"nombre\",\"email\",\"telefono\",\"direccion\",\"password\",\"rol\"]");
        }
        try {
            User usuario = userService.insertarUsuario(
                parameters[0], // nombre
                parameters[1], // email
                parameters[2], // telefono
                parameters[3], // direccion
                parameters[4], // password
                parameters[5]  // rol
            );
            return emailResponseService.formatInsertUsuarioSuccess(usuario, "INSUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al insertar usuario: " + e.getMessage(), "INSUSR");
        }
    }

    private String handleActualizarUsuario(String[] parameters) {
        if (parameters.length < 7) {
            return emailResponseService.formatInsufficientParametersResponse("UPDUSR", "UPDUSR[\"id\",\"nombre\",\"email\",\"telefono\",\"direccion\",\"password\",\"rol\"]");
        }
        try {
            User usuario = userService.actualizarUsuario(
                Long.parseLong(parameters[0]), // id
                parameters[1], // nombre
                parameters[2], // email
                parameters[3], // telefono
                parameters[4], // direccion
                parameters[5], // password
                parameters[6]  // rol
            );
            return emailResponseService.formatUpdateUsuarioSuccess(usuario, "UPDUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar usuario: " + e.getMessage(), "UPDUSR");
        }
    }

    private String handleEliminarUsuario(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("DELUSR", "DELUSR[\"nombre\"]");
        }
        try {
            String nombre = parameters[0];
            boolean eliminado = userService.eliminarUsuarioPorNombre(nombre);
            if (eliminado) {
                return emailResponseService.formatDeleteUsuarioSuccess(nombre, "DELUSR");
            } else {
                return emailResponseService.formatSearchUsuarioNotFound(nombre, "DELUSR");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al eliminar usuario: " + e.getMessage(), "DELUSR");
        }
    }

    private String handleBuscarUsuariosPorRol(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSUSRROL", "BUSUSRROL[\"rol\"]");
        }
        try {
            List<User> usuarios = userService.buscarUsuariosPorRol(parameters[0]);
            return emailResponseService.formatListUsuariosPorRolResponse(usuarios, "BUSUSRROL");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar usuarios por rol: " + e.getMessage(), "BUSUSRROL");
        }
    }

    // --- Métodos de cotizaciones ---
    private String handleListarTodasLasCotizaciones(String[] parameters) {
        try {
            List<Quote> cotizaciones = quoteService.listarTodasLasCotizaciones();
            return emailResponseService.formatListCotizacionesResponse(cotizaciones, "LISQUOTE");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al listar cotizaciones: " + e.getMessage(), "LISQUOTE");
        }
    }

    private String handleBuscarCotizacionPorId(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSQUOTEID", "BUSQUOTEID[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Optional<Quote> cotizacionOpt = quoteService.buscarCotizacionPorId(id);
            if (cotizacionOpt.isPresent()) {
                return emailResponseService.formatSearchCotizacionSuccess(cotizacionOpt.get(), "BUSQUOTEID");
            } else {
                return emailResponseService.formatSearchCotizacionNotFound(id, "BUSQUOTEID");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar cotización por id: " + e.getMessage(), "BUSQUOTEID");
        }
    }

    private String handleInsertarCotizacion(String[] parameters) {
        if (parameters.length < 10) {
            return emailResponseService.formatInsufficientParametersResponse("INSQUOTE", "INSQUOTE[\"typeMetro\",\"costMetro\",\"quantityMetro\",\"costFurniture\",\"total\",\"state\",\"furnitureNumber\",\"comments\",\"idProject\",\"userId\"]");
        }
        try {
            Quote cotizacion = quoteService.insertarCotizacion(
                parameters[0], // typeMetro
                new java.math.BigDecimal(parameters[1]), // costMetro
                new java.math.BigDecimal(parameters[2]), // quantityMetro
                new java.math.BigDecimal(parameters[3]), // costFurniture
                new java.math.BigDecimal(parameters[4]), // total
                parameters[5], // state
                Integer.parseInt(parameters[6]), // furnitureNumber
                parameters[7], // comments
                Long.parseLong(parameters[8]), // idProject
                Long.parseLong(parameters[9])  // userId
            );
            return emailResponseService.formatInsertCotizacionSuccess(cotizacion, "INSQUOTE");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al insertar cotización: " + e.getMessage(), "INSQUOTE");
        }
    }

    private String handleActualizarCotizacion(String[] parameters) {
        if (parameters.length < 11) {
            return emailResponseService.formatInsufficientParametersResponse("UPDQUOTE", "UPDQUOTE[\"id\",\"typeMetro\",\"costMetro\",\"quantityMetro\",\"costFurniture\",\"total\",\"state\",\"furnitureNumber\",\"comments\",\"idProject\",\"userId\"]");
        }
        try {
            Quote cotizacion = quoteService.actualizarCotizacion(
                Long.parseLong(parameters[0]), // id
                parameters[1], // typeMetro
                new java.math.BigDecimal(parameters[2]), // costMetro
                new java.math.BigDecimal(parameters[3]), // quantityMetro
                new java.math.BigDecimal(parameters[4]), // costFurniture
                new java.math.BigDecimal(parameters[5]), // total
                parameters[6], // state
                Integer.parseInt(parameters[7]), // furnitureNumber
                parameters[8], // comments
                Long.parseLong(parameters[9]), // idProject
                Long.parseLong(parameters[10]) // userId
            );
            return emailResponseService.formatUpdateCotizacionSuccess(cotizacion, "UPDQUOTE");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar cotización: " + e.getMessage(), "UPDQUOTE");
        }
    }

    private String handleEliminarCotizacion(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("DELQUOTE", "DELQUOTE[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            boolean eliminado = quoteService.eliminarCotizacion(id);
            if (eliminado) {
                return emailResponseService.formatDeleteCotizacionSuccess(id, "DELQUOTE");
            } else {
                return emailResponseService.formatSearchCotizacionNotFound(id, "DELQUOTE");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al eliminar cotización: " + e.getMessage(), "DELQUOTE");
        }
    }

    private String handleBuscarCotizacionesPorProyecto(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSQUOTEPROY", "BUSQUOTEPROY[\"idProject\"]");
        }
        try {
            List<Quote> cotizaciones = quoteService.buscarCotizacionesPorProyecto(Long.parseLong(parameters[0]));
            return emailResponseService.formatListCotizacionesPorProyectoResponse(cotizaciones, "BUSQUOTEPROY");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar cotizaciones por proyecto: " + e.getMessage(), "BUSQUOTEPROY");
        }
    }

    private String handleBuscarCotizacionesPorUsuario(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSQUOTEUSR", "BUSQUOTEUSR[\"userId\"]");
        }
        try {
            List<Quote> cotizaciones = quoteService.buscarCotizacionesPorUsuario(Long.parseLong(parameters[0]));
            return emailResponseService.formatListCotizacionesPorUsuarioResponse(cotizaciones, "BUSQUOTEUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar cotizaciones por usuario: " + e.getMessage(), "BUSQUOTEUSR");
        }
    }

    private String handleBuscarCotizacionesPorTipoMetro(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSQUOTETYPE", "BUSQUOTETYPE[\"typeMetro\"]");
        }
        try {
            List<Quote> cotizaciones = quoteService.buscarCotizacionesPorTipoMetro(parameters[0]);
            return emailResponseService.formatListCotizacionesPorTipoMetroResponse(cotizaciones, "BUSQUOTETYPE");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar cotizaciones por tipo metro: " + e.getMessage(), "BUSQUOTETYPE");
        }
    }

    private String handleObtenerTotalCotizacionesAprobadasPorProyecto(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("TOTALQUOTEAPPR", "TOTALQUOTEAPPR[\"idProject\"]");
        }
        try {
            java.math.BigDecimal total = quoteService.obtenerTotalCotizacionesAprobadasPorProyecto(parameters[0]);
            return emailResponseService.formatTotalCotizacionesAprobadasResponse(total, "TOTALQUOTEAPPR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al obtener total cotizaciones aprobadas: " + e.getMessage(), "TOTALQUOTEAPPR");
        }
    }

    private String handleCalcularTotalCotizacion(String[] parameters) {
        if (parameters.length < 3) {
            return emailResponseService.formatInsufficientParametersResponse("CALCQUOTE", "CALCQUOTE[\"costMetro\",\"quantityMetro\",\"costFurniture\"]");
        }
        try {
            java.math.BigDecimal total = quoteService.calcularTotalCotizacion(
                new java.math.BigDecimal(parameters[0]), // costMetro
                new java.math.BigDecimal(parameters[1]), // quantityMetro
                new java.math.BigDecimal(parameters[2])  // costFurniture
            );
            return emailResponseService.formatCalcularTotalCotizacionResponse(total, "CALCQUOTE");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al calcular total cotización: " + e.getMessage(), "CALCQUOTE");
        }
    }

    private String handleAprobarCotizacion(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("APPRQUOTE", "APPRQUOTE[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Quote cotizacion = quoteService.aprobarCotizacion(id);
            return emailResponseService.formatAprobarCotizacionSuccess(cotizacion, "APPRQUOTE");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al aprobar cotización: " + e.getMessage(), "APPRQUOTE");
        }
    }

    private String handleRechazarCotizacion(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("REJQUOTE", "REJQUOTE[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Quote cotizacion = quoteService.rechazarCotizacion(id);
            return emailResponseService.formatRechazarCotizacionSuccess(cotizacion, "REJQUOTE");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al rechazar cotización: " + e.getMessage(), "REJQUOTE");
        }
    }

    // --- Métodos de diseños ---
    private String handleListarTodosLosDisenos(String[] parameters) {
        try {
            List<Design> disenos = designService.listarTodosLosDisenos();
            return emailResponseService.formatListDisenosResponse(disenos, "LISDESIGN");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al listar diseños: " + e.getMessage(), "LISDESIGN");
        }
    }

    private String handleBuscarDisenoPorId(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSDESIGNID", "BUSDESIGNID[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Optional<Design> disenoOpt = designService.buscarDisenoPorId(id);
            if (disenoOpt.isPresent()) {
                return emailResponseService.formatSearchDisenoSuccess(disenoOpt.get(), "BUSDESIGNID");
            } else {
                return emailResponseService.formatSearchDisenoNotFound(id, "BUSDESIGNID");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar diseño por id: " + e.getMessage(), "BUSDESIGNID");
        }
    }

    private String handleBuscarDisenoPorCotizacion(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSDESIGNQUOTE", "BUSDESIGNQUOTE[\"idQuote\"]");
        }
        try {
            Long idQuote = Long.parseLong(parameters[0]);
            Optional<Design> disenoOpt = designService.buscarDisenoPorCotizacion(idQuote);
            if (disenoOpt.isPresent()) {
                return emailResponseService.formatSearchDisenoSuccess(disenoOpt.get(), "BUSDESIGNQUOTE");
            } else {
                return emailResponseService.formatSearchDisenoNotFound(idQuote, "BUSDESIGNQUOTE");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar diseño por cotización: " + e.getMessage(), "BUSDESIGNQUOTE");
        }
    }

    private String handleInsertarDiseno(String[] parameters) {
        if (parameters.length < 7) {
            return emailResponseService.formatInsufficientParametersResponse("INSDESIGN", "INSDESIGN[\"idQuote\",\"urlRender\",\"laminatedPlane\",\"approved\",\"approvedDate\",\"comments\",\"userId\"]");
        }
        try {
            Design diseno = designService.insertarDiseno(
                Long.parseLong(parameters[0]), // idQuote
                parameters[1], // urlRender
                parameters[2], // laminatedPlane
                Boolean.parseBoolean(parameters[3]), // approved
                java.time.LocalDate.parse(parameters[4]), // approvedDate
                parameters[5], // comments
                Long.parseLong(parameters[6])  // userId
            );
            return emailResponseService.formatInsertDisenoSuccess(diseno, "INSDESIGN");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al insertar diseño: " + e.getMessage(), "INSDESIGN");
        }
    }

    private String handleActualizarDiseno(String[] parameters) {
        if (parameters.length < 8) {
            return emailResponseService.formatInsufficientParametersResponse("UPDDESIGN", "UPDDESIGN[\"idDesign\",\"idQuote\",\"urlRender\",\"laminatedPlane\",\"approved\",\"approvedDate\",\"comments\",\"userId\"]");
        }
        try {
            Design diseno = designService.actualizarDiseno(
                Long.parseLong(parameters[0]), // idDesign
                Long.parseLong(parameters[1]), // idQuote
                parameters[2], // urlRender
                parameters[3], // laminatedPlane
                Boolean.parseBoolean(parameters[4]), // approved
                java.time.LocalDate.parse(parameters[5]), // approvedDate
                parameters[6], // comments
                Long.parseLong(parameters[7])  // userId
            );
            return emailResponseService.formatUpdateDisenoSuccess(diseno, "UPDDESIGN");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar diseño: " + e.getMessage(), "UPDDESIGN");
        }
    }

    private String handleEliminarDiseno(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("DELDESIGN", "DELDESIGN[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            boolean eliminado = designService.eliminarDiseno(id);
            if (eliminado) {
                return emailResponseService.formatDeleteDisenoSuccess(id, "DELDESIGN");
            } else {
                return emailResponseService.formatSearchDisenoNotFound(id, "DELDESIGN");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al eliminar diseño: " + e.getMessage(), "DELDESIGN");
        }
    }

    private String handleBuscarDisenosPorUsuario(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSDESIGNUSR", "BUSDESIGNUSR[\"userId\"]");
        }
        try {
            List<Design> disenos = designService.buscarDisenosPorUsuario(Long.parseLong(parameters[0]));
            return emailResponseService.formatListDisenosPorUsuarioResponse(disenos, "BUSDESIGNUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar diseños por usuario: " + e.getMessage(), "BUSDESIGNUSR");
        }
    }

    private String handleBuscarDisenosAprobados(String[] parameters) {
        try {
            List<Design> disenos = designService.buscarDisenosAprobados();
            return emailResponseService.formatListDisenosAprobadosResponse(disenos, "DESIGNAPPR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar diseños aprobados: " + e.getMessage(), "DESIGNAPPR");
        }
    }

    private String handleAprobarDiseno(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("APPRDESIGN", "APPRDESIGN[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Design diseno = designService.aprobarDiseno(id);
            return emailResponseService.formatAprobarDisenoSuccess(diseno, "APPRDESIGN");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al aprobar diseño: " + e.getMessage(), "APPRDESIGN");
        }
    }

    private String handleRechazarDiseno(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("REJDESIGN", "REJDESIGN[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Design diseno = designService.rechazarDiseno(id);
            return emailResponseService.formatRechazarDisenoSuccess(diseno, "REJDESIGN");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al rechazar diseño: " + e.getMessage(), "REJDESIGN");
        }
    }

    private String handleObtenerDisenosAprobadosPorUsuario(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("DESIGNAPPRUSR", "DESIGNAPPRUSR[\"userId\"]");
        }
        try {
            List<Design> disenos = designService.obtenerDisenosAprobadosPorUsuario(Long.parseLong(parameters[0]));
            return emailResponseService.formatListDisenosAprobadosPorUsuarioResponse(disenos, "DESIGNAPPRUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al obtener diseños aprobados por usuario: " + e.getMessage(), "DESIGNAPPRUSR");
        }
    }

    private String handleObtenerDisenosPendientesPorUsuario(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("DESIGNPENDUSR", "DESIGNPENDUSR[\"userId\"]");
        }
        try {
            List<Design> disenos = designService.obtenerDisenosPendientesPorUsuario(Long.parseLong(parameters[0]));
            return emailResponseService.formatListDisenosPendientesPorUsuarioResponse(disenos, "DESIGNPENDUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al obtener diseños pendientes por usuario: " + e.getMessage(), "DESIGNPENDUSR");
        }
    }

    // --- Métodos de planes de pago ---
    private String handleListarTodosLosPlanesPago(String[] parameters) {
        try {
            List<PayPlan> planesPago = payPlanService.listarTodosLosPlanesPago();
            return emailResponseService.formatListPlanesPagoResponse(planesPago, "LISPAYPLAN");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al listar planes de pago: " + e.getMessage(), "LISPAYPLAN");
        }
    }

    private String handleBuscarPlanPagoPorId(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSPAYPLANID", "BUSPAYPLANID[\"idPayPlan\"]");
        }
        try {
            Long idPayPlan = Long.parseLong(parameters[0]);
            Optional<PayPlan> planOpt = payPlanService.buscarPlanPagoPorId(idPayPlan);
            if (planOpt.isPresent()) {
                return emailResponseService.formatSearchPlanPagoSuccess(planOpt.get(), "BUSPAYPLANID");
            } else {
                return emailResponseService.formatSearchPlanPagoNotFound(idPayPlan, "BUSPAYPLANID");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar plan de pago por ID: " + e.getMessage(), "BUSPAYPLANID");
        }
    }

    private String handleBuscarPlanPagoPorProyecto(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSPAYPLANPROY", "BUSPAYPLANPROY[\"idProject\"]");
        }
        try {
            Optional<PayPlan> planOpt = payPlanService.buscarPlanPagoPorProyecto(parameters[0]);
            if (planOpt.isPresent()) {
                return emailResponseService.formatSearchPlanPagoSuccess(planOpt.get(), "BUSPAYPLANPROY");
            } else {
                return emailResponseService.formatSearchPlanPagoNotFound(parameters[0], "BUSPAYPLANPROY");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar plan de pago por proyecto: " + e.getMessage(), "BUSPAYPLANPROY");
        }
    }

    private String handleInsertarPlanPago(String[] parameters) {
        if (parameters.length < 6) {
            return emailResponseService.formatInsufficientParametersResponse("INSPAYPLAN", "INSPAYPLAN[\"idProject\",\"totalDebt\",\"totalPayed\",\"numberDebt\",\"numberPays\",\"state\"]");
        }
        try {
            PayPlan planPago = payPlanService.insertarPlanPago(
                Long.parseLong(parameters[0]), // idProject
                new java.math.BigDecimal(parameters[1]), // totalDebt
                new java.math.BigDecimal(parameters[2]), // totalPayed
                Integer.parseInt(parameters[3]), // numberDebt
                Integer.parseInt(parameters[4]), // numberPays
                parameters[5]  // state
            );
            return emailResponseService.formatInsertPlanPagoSuccess(planPago, "INSPAYPLAN");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al insertar plan de pago: " + e.getMessage(), "INSPAYPLAN");
        }
    }

    private String handleActualizarPlanPago(String[] parameters) {
        if (parameters.length < 7) {
            return emailResponseService.formatInsufficientParametersResponse("UPDPAYPLAN", "UPDPAYPLAN[\"idPayPlan\",\"idProject\",\"totalDebt\",\"totalPayed\",\"numberDebt\",\"numberPays\",\"state\"]");
        }
        try {
            PayPlan planPago = payPlanService.actualizarPlanPago(
                Long.parseLong(parameters[0]), // idPayPlan
                Long.parseLong(parameters[1]), // idProject
                new java.math.BigDecimal(parameters[2]), // totalDebt
                new java.math.BigDecimal(parameters[3]), // totalPayed
                Integer.parseInt(parameters[4]), // numberDebt
                Integer.parseInt(parameters[5]), // numberPays
                parameters[6]  // state
            );
            return emailResponseService.formatUpdatePlanPagoSuccess(planPago, "UPDPAYPLAN");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar plan de pago: " + e.getMessage(), "UPDPAYPLAN");
        }
    }

    private String handleBuscarPlanesPagoPorEstado(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSPAYPLANEST", "BUSPAYPLANEST[\"state\"]");
        }
        try {
            List<PayPlan> planesPago = payPlanService.buscarPlanesPagoPorEstado(parameters[0]);
            return emailResponseService.formatListPlanesPagoPorEstadoResponse(planesPago, "BUSPAYPLANEST");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar planes de pago por estado: " + e.getMessage(), "BUSPAYPLANEST");
        }
    }

    private String handleObtenerTotalDeudaPendiente(String[] parameters) {
        try {
            java.math.BigDecimal totalDeuda = payPlanService.obtenerTotalDeudaPendiente();
            return emailResponseService.formatTotalDeudaPendienteResponse(totalDeuda, "TOTDEUDAPEND");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al obtener total deuda pendiente: " + e.getMessage(), "TOTDEUDAPEND");
        }
    }

    private String handleObtenerTotalPagado(String[] parameters) {
        try {
            java.math.BigDecimal totalPagado = payPlanService.obtenerTotalPagado();
            return emailResponseService.formatTotalPagadoResponse(totalPagado, "TOTPAGADO");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al obtener total pagado: " + e.getMessage(), "TOTPAGADO");
        }
    }

    private String handleActualizarDeudaTotal(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("UPDDEUDATOT", "UPDDEUDATOT[\"idPayPlan\",\"nuevaDeuda\"]");
        }
        try {
            Long idPayPlan = Long.parseLong(parameters[0]);
            java.math.BigDecimal nuevaDeuda = new java.math.BigDecimal(parameters[1]);
            PayPlan planPago = payPlanService.actualizarDeudaTotal(idPayPlan, nuevaDeuda);
            return emailResponseService.formatUpdateDeudaTotalSuccess(planPago, "UPDDEUDATOT");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar deuda total: " + e.getMessage(), "UPDDEUDATOT");
        }
    }

    private String handleCalcularPorcentajePago(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("CALCPORCPAGO", "CALCPORCPAGO[\"idPayPlan\"]");
        }
        try {
            Long idPayPlan = Long.parseLong(parameters[0]);
            java.math.BigDecimal porcentaje = payPlanService.calcularPorcentajePago(idPayPlan);
            return emailResponseService.formatCalcularPorcentajePagoResponse(porcentaje, "CALCPORCPAGO");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al calcular porcentaje de pago: " + e.getMessage(), "CALCPORCPAGO");
        }
    }

    private String handleCambiarEstado(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("CAMBIOEST", "CAMBIOEST[\"idPayPlan\",\"nuevoEstado\"]");
        }
        try {
            Long idPayPlan = Long.parseLong(parameters[0]);
            PayPlan planPago = payPlanService.cambiarEstado(idPayPlan, parameters[1]);
            return emailResponseService.formatCambiarEstadoSuccess(planPago, "CAMBIOEST");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al cambiar estado: " + e.getMessage(), "CAMBIOEST");
        }
    }

    // --- Métodos de pagos ---
    private String handleListarTodosLosPagos(String[] parameters) {
        try {
            List<Pays> pagos = paysService.listarTodosLosPagos();
            return emailResponseService.formatListPagosResponse(pagos, "LISPAYS");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al listar pagos: " + e.getMessage(), "LISPAYS");
        }
    }

    private String handleBuscarPagoPorId(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSPAYID", "BUSPAYID[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Optional<Pays> pagoOpt = paysService.buscarPagoPorId(id);
            if (pagoOpt.isPresent()) {
                return emailResponseService.formatSearchPagoSuccess(pagoOpt.get(), "BUSPAYID");
            } else {
                return emailResponseService.formatSearchPagoNotFound(id, "BUSPAYID");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar pago por ID: " + e.getMessage(), "BUSPAYID");
        }
    }

    private String handleInsertarPago(String[] parameters) {
        if (parameters.length < 5) {
            return emailResponseService.formatInsufficientParametersResponse("INSPAY", "INSPAY[\"date\",\"total\",\"state\",\"idClient\",\"idPayPlan\"]");
        }
        try {
            Pays pago = paysService.insertarPago(
                java.time.LocalDate.parse(parameters[0]), // date
                new java.math.BigDecimal(parameters[1]), // total
                parameters[2], // state
                Long.parseLong(parameters[3]), // idClient
                Long.parseLong(parameters[4])  // idPayPlan
            );
            return emailResponseService.formatInsertPagoSuccess(pago, "INSPAY");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al insertar pago: " + e.getMessage(), "INSPAY");
        }
    }

    private String handleActualizarPago(String[] parameters) {
        if (parameters.length < 6) {
            return emailResponseService.formatInsufficientParametersResponse("UPDPAY", "UPDPAY[\"id\",\"date\",\"total\",\"state\",\"idClient\",\"idPayPlan\"]");
        }
        try {
            Pays pago = paysService.actualizarPago(
                Long.parseLong(parameters[0]), // id
                java.time.LocalDate.parse(parameters[1]), // date
                new java.math.BigDecimal(parameters[2]), // total
                parameters[3], // state
                Long.parseLong(parameters[4]), // idClient
                Long.parseLong(parameters[5])  // idPayPlan
            );
            return emailResponseService.formatUpdatePagoSuccess(pago, "UPDPAY");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar pago: " + e.getMessage(), "UPDPAY");
        }
    }

    private String handleBuscarPagosPorCliente(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSPAYCLI", "BUSPAYCLI[\"idClient\"]");
        }
        try {
            List<Pays> pagos = paysService.buscarPagosPorCliente(Long.parseLong(parameters[0]));
            return emailResponseService.formatListPagosPorClienteResponse(pagos, "BUSPAYCLI");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar pagos por cliente: " + e.getMessage(), "BUSPAYCLI");
        }
    }

    private String handleObtenerTotalPagadoPorCliente(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("TOTPAGCLI", "TOTPAGCLI[\"idClient\"]");
        }
        try {
            java.math.BigDecimal totalPagado = paysService.obtenerTotalPagadoPorCliente(Long.parseLong(parameters[0]));
            return emailResponseService.formatTotalPagadoPorClienteResponse(totalPagado, "TOTPAGCLI");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al obtener total pagado por cliente: " + e.getMessage(), "TOTPAGCLI");
        }
    }

    private String handlePlanPagoTienePagos(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("PLANPAGOHAS", "PLANPAGOHAS[\"idPayPlan\"]");
        }
        try {
            Long idPayPlan = Long.parseLong(parameters[0]);
            boolean tienePagos = paysService.planPagoTienePagos(idPayPlan);
            return emailResponseService.formatPlanPagoTienePagosResponse(tienePagos, idPayPlan, "PLANPAGOHAS");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al verificar si plan de pago tiene pagos: " + e.getMessage(), "PLANPAGOHAS");
        }
    }

    private String handleContarPagosPorPlanPago(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("COUNTPAYPPLAN", "COUNTPAYPPLAN[\"idPayPlan\"]");
        }
        try {
            Long idPayPlan = Long.parseLong(parameters[0]);
            long cantidadPagos = paysService.contarPagosPorPlanPago(idPayPlan);
            return emailResponseService.formatContarPagosPorPlanPagoResponse(cantidadPagos, idPayPlan, "COUNTPAYPPLAN");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al contar pagos por plan de pago: " + e.getMessage(), "COUNTPAYPPLAN");
        }
    }

    // --- Métodos de materiales ---
    private String handleListarTodosLosMateriales(String[] parameters) {
        try {
            List<Material> materiales = materialService.listarTodosLosMateriales();
            return emailResponseService.formatListMaterialesResponse(materiales, "LISMAT");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al listar materiales: " + e.getMessage(), "LISMAT");
        }
    }

    private String handleBuscarMaterialPorNombre(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSMATNOM", "BUSMATNOM[\"name\"]");
        }
        try {
            Optional<Material> materialOpt = materialService.buscarMaterialPorNombre(parameters[0]);
            if (materialOpt.isPresent()) {
                return emailResponseService.formatSearchMaterialSuccess(materialOpt.get(), "BUSMATNOM");
            } else {
                return emailResponseService.formatSearchMaterialNotFound(parameters[0], "BUSMATNOM");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar material por nombre: " + e.getMessage(), "BUSMATNOM");
        }
    }

    private String handleInsertarMaterial(String[] parameters) {
        if (parameters.length < 5) {
            return emailResponseService.formatInsufficientParametersResponse("INSMAT", "INSMAT[\"name\",\"type\",\"unitMeasure\",\"unitPrice\",\"stock\"]");
        }
        try {
            Material material = materialService.insertarMaterial(
                parameters[0], // name
                parameters[1], // type
                parameters[2], // unitMeasure
                new java.math.BigDecimal(parameters[3]), // unitPrice
                Integer.parseInt(parameters[4])  // stock
            );
            return emailResponseService.formatInsertMaterialSuccess(material, "INSMAT");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al insertar material: " + e.getMessage(), "INSMAT");
        }
    }

    private String handleActualizarMaterial(String[] parameters) {
        if (parameters.length < 5) {
            return emailResponseService.formatInsufficientParametersResponse("UPDMAT", "UPDMAT[\"name\",\"type\",\"unitMeasure\",\"unitPrice\",\"stock\"]");
        }
        try {
            Material material = materialService.actualizarMaterialPorNombre(
                parameters[0], // name
                parameters[1], // type
                parameters[2], // unitMeasure
                new java.math.BigDecimal(parameters[3]), // unitPrice
                Integer.parseInt(parameters[4])  // stock
            );
            return emailResponseService.formatUpdateMaterialSuccess(material, "UPDMAT");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar material: " + e.getMessage(), "UPDMAT");
        }
    }

    private String handleBuscarMaterialesPorTipo(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSMATTIPO", "BUSMATTIPO[\"type\"]");
        }
        try {
            List<Material> materiales = materialService.buscarMaterialesPorTipo(parameters[0]);
            return emailResponseService.formatListMaterialesPorTipoResponse(materiales, "BUSMATTIPO");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar materiales por tipo: " + e.getMessage(), "BUSMATTIPO");
        }
    }

    private String handleActualizarPrecioMaterial(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("UPDMATPRECIO", "UPDMATPRECIO[\"name\",\"nuevoPrecio\"]");
        }
        try {
            java.math.BigDecimal nuevoPrecio = new java.math.BigDecimal(parameters[1]);
            Material material = materialService.actualizarPrecioMaterial(Long.parseLong(parameters[0]), nuevoPrecio);
            return emailResponseService.formatUpdatePrecioMaterialSuccess(material, "UPDMATPRECIO");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar precio de material: " + e.getMessage(), "UPDMATPRECIO");
        }
    }

    private String handleActualizarStockMaterial(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("UPDMATSTOCK", "UPDMATSTOCK[\"name\",\"nuevoStock\"]");
        }
        try {
            Integer nuevoStock = Integer.parseInt(parameters[1]);
            Material material = materialService.actualizarStockMaterial(Long.parseLong(parameters[0]), nuevoStock);
            return emailResponseService.formatUpdateStockMaterialSuccess(material, "UPDMATSTOCK");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar stock de material: " + e.getMessage(), "UPDMATSTOCK");
        }
    }

    private String handleReducirStockMaterial(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("REDMATSTOCK", "REDMATSTOCK[\"name\",\"cantidad\"]");
        }
        try {
            Integer cantidad = Integer.parseInt(parameters[1]);
            Material material = materialService.reducirStockMaterial(Long.parseLong(parameters[0]), cantidad);
            return emailResponseService.formatReducirStockMaterialSuccess(material, "REDMATSTOCK");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al reducir stock de material: " + e.getMessage(), "REDMATSTOCK");
        }
    }

    private String handleAumentarStockMaterial(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("AUMMATSTOCK", "AUMMATSTOCK[\"name\",\"cantidad\"]");
        }
        try {
            Integer cantidad = Integer.parseInt(parameters[1]);
            Material material = materialService.aumentarStockMaterial(Long.parseLong(parameters[0]), cantidad);
            return emailResponseService.formatAumentarStockMaterialSuccess(material, "AUMMATSTOCK");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al aumentar stock de material: " + e.getMessage(), "AUMMATSTOCK");
        }
    }

    private String handleVerificarDisponibilidadMaterial(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("VERMATDISP", "VERMATDISP[\"name\",\"cantidadRequerida\"]");
        }
        try {
            Integer cantidadRequerida = Integer.parseInt(parameters[1]);
            boolean disponible = materialService.verificarDisponibilidadMaterial(Long.parseLong(parameters[0]), cantidadRequerida);
            return emailResponseService.formatVerificarDisponibilidadMaterialResponse(disponible, parameters[0], cantidadRequerida, "VERMATDISP");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al verificar disponibilidad de material: " + e.getMessage(), "VERMATDISP");
        }
    }

    // --- Métodos de materiales-proyecto ---
    private String handleListarTodosMaterialesProyecto(String[] parameters) {
        try {
            List<MaterialProject> materialesProyecto = materialProjectService.listarTodosMaterialesProyecto();
            return emailResponseService.formatListMaterialesProyectoResponse(materialesProyecto, "LISMATPROY");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al listar materiales-proyecto: " + e.getMessage(), "LISMATPROY");
        }
    }

    private String handleBuscarMaterialProyectoPorId(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSMATPROYID", "BUSMATPROYID[\"id\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Optional<MaterialProject> materialProyectoOpt = materialProjectService.buscarMaterialProyectoPorId(id);
            if (materialProyectoOpt.isPresent()) {
                return emailResponseService.formatSearchMaterialProyectoSuccess(materialProyectoOpt.get(), "BUSMATPROYID");
            } else {
                return emailResponseService.formatSearchMaterialProyectoNotFound(id, "BUSMATPROYID");
            }
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar material-proyecto por ID: " + e.getMessage(), "BUSMATPROYID");
        }
    }

    private String handleInsertarMaterialProyecto(String[] parameters) {
        if (parameters.length < 4) {
            return emailResponseService.formatInsufficientParametersResponse("INSMATPROY", "INSMATPROY[\"quantity\",\"leftOver\",\"idProject\",\"idMaterial\"]");
        }
        try {
            MaterialProject materialProyecto = materialProjectService.insertarMaterialProyecto(
                Integer.parseInt(parameters[0]), // quantity
                Integer.parseInt(parameters[1]), // leftOver
                Long.parseLong(parameters[2]), // idProject
                Long.parseLong(parameters[3])  // idMaterial
            );
            return emailResponseService.formatInsertMaterialProyectoSuccess(materialProyecto, "INSMATPROY");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al insertar material-proyecto: " + e.getMessage(), "INSMATPROY");
        }
    }

    private String handleActualizarMaterialProyecto(String[] parameters) {
        if (parameters.length < 5) {
            return emailResponseService.formatInsufficientParametersResponse("UPDMATPROY", "UPDMATPROY[\"id\",\"quantity\",\"leftOver\",\"idProject\",\"idMaterial\"]");
        }
        try {
            MaterialProject materialProyecto = materialProjectService.actualizarMaterialProyecto(
                Long.parseLong(parameters[0]), // id
                Integer.parseInt(parameters[1]), // quantity
                Integer.parseInt(parameters[2]), // leftOver
                Long.parseLong(parameters[3]), // idProject
                Long.parseLong(parameters[4])  // idMaterial
            );
            return emailResponseService.formatUpdateMaterialProyectoSuccess(materialProyecto, "UPDMATPROY");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al actualizar material-proyecto: " + e.getMessage(), "UPDMATPROY");
        }
    }

    private String handleBuscarMaterialesPorProyecto(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSMATPORPROY", "BUSMATPORPROY[\"idProject\"]");
        }
        try {
            List<MaterialProject> materialesProyecto = materialProjectService.buscarMaterialesPorProyecto(Long.parseLong(parameters[0]));
            return emailResponseService.formatListMaterialesPorProyectoResponse(materialesProyecto, "BUSMATPORPROY");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar materiales por proyecto: " + e.getMessage(), "BUSMATPORPROY");
        }
    }

    private String handleBuscarProyectosPorMaterial(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("BUSPROYPORMAT", "BUSPROYPORMAT[\"idMaterial\"]");
        }
        try {
            List<MaterialProject> proyectosMaterial = materialProjectService.buscarProyectosPorMaterial(Long.parseLong(parameters[0]));
            return emailResponseService.formatListProyectosPorMaterialResponse(proyectosMaterial, "BUSPROYPORMAT");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al buscar proyectos por material: " + e.getMessage(), "BUSPROYPORMAT");
        }
    }
    
    // --- Nuevos métodos para PayPlan y Pays ---
    
    private String handleCrearPlanPagoConPagos(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("CREARPLANPAGOS", "CREARPLANPAGOS[\"idProject\",\"monto1,monto2,monto3...\"]");
        }
        try {
            Long idProject = Long.parseLong(parameters[0]);
            String[] montosStr = parameters[1].split(",");
            List<java.math.BigDecimal> montos = new ArrayList<>();
            
            for (String montoStr : montosStr) {
                montos.add(new java.math.BigDecimal(montoStr.trim()));
            }
            
            PayPlan plan = payPlanService.crearPlanPagoConPagos(idProject, montos);
            return emailResponseService.formatCreatePlanPagoSuccess(plan, "CREARPLANPAGOS");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al crear plan de pago: " + e.getMessage(), "CREARPLANPAGOS");
        }
    }
    
    private String handleObtenerPlanPagoCompleto(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("OBTENERPLANPAGO", "OBTENERPLANPAGO[\"idPayPlan\"]");
        }
        try {
            Long idPayPlan = Long.parseLong(parameters[0]);
            PayPlan plan = payPlanService.obtenerPlanPagoCompleto(idPayPlan);
            return emailResponseService.formatSearchPlanPagoSuccess(plan, "OBTENERPLANPAGO");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al obtener plan de pago: " + e.getMessage(), "OBTENERPLANPAGO");
        }
    }
    
    private String handleRecalcularPlanPago(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("RECALCPLANPAGO", "RECALCPLANPAGO[\"idPayPlan\",\"nuevoMonto1,nuevoMonto2...\"]");
        }
        try {
            Long idPayPlan = Long.parseLong(parameters[0]);
            String[] montosStr = parameters[1].split(",");
            List<java.math.BigDecimal> nuevosMontos = new ArrayList<>();
            
            for (String montoStr : montosStr) {
                nuevosMontos.add(new java.math.BigDecimal(montoStr.trim()));
            }
            
            PayPlan plan = payPlanService.recalcularPlanPago(idPayPlan, nuevosMontos);
            return emailResponseService.formatRecalcularPlanPagoSuccess(plan, "RECALCPLANPAGO");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al recalcular plan de pago: " + e.getMessage(), "RECALCPLANPAGO");
        }
    }
    
    private String handleObtenerPagosPorPlanPago(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("OBTPAGOSPLAN", "OBTPAGOSPLAN[\"idPayPlan\"]");
        }
        try {
            Long idPayPlan = Long.parseLong(parameters[0]);
            List<Pays> pagos = paysService.obtenerPagosPorPlanPago(idPayPlan);
            return emailResponseService.formatListPagosPorPlanPagoResponse(pagos, "OBTPAGOSPLAN");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al obtener pagos del plan: " + e.getMessage(), "OBTPAGOSPLAN");
        }
    }
    
    private String handlePagar(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("PAGAR", "PAGAR[\"idPago\",\"fechaPago\",\"idClient\"]");
        }
        try {
            Long idPago = Long.parseLong(parameters[0]);
            java.time.LocalDate fechaPago = java.time.LocalDate.parse(parameters[1]);
            Long idClient = parameters.length > 2 ? Long.parseLong(parameters[2]) : null;
            
            Pays pago = paysService.pagar(idPago, fechaPago, idClient);
            return emailResponseService.formatPagarSuccess(pago, "PAGAR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al procesar pago: " + e.getMessage(), "PAGAR");
        }
    }
    
    // --- Métodos de gestión de stock para MaterialProject ---
    
    private String handleDevolverMaterialSobrante(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("DEVOLVERSOBRANTE", "DEVOLVERSOBRANTE[\"idMaterialProject\",\"cantidadDevolver\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Integer cantidadDevolver = Integer.parseInt(parameters[1]);
            
            MaterialProject materialProject = materialProjectService.devolverMaterialSobrante(id, cantidadDevolver);
            return emailResponseService.formatSuccessResponse("DEVOLUCIÓN SOBRANTE", materialProject, "DEVOLVERSOBRANTE");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al devolver material sobrante: " + e.getMessage(), "DEVOLVERSOBRANTE");
        }
    }
    
    private String handleDevolverTodoSobrante(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("DEVOLVERTODO", "DEVOLVERTODO[\"idMaterialProject\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            
            MaterialProject materialProject = materialProjectService.devolverTodoSobrante(id);
            return emailResponseService.formatSuccessResponse("DEVOLUCIÓN TOTAL SOBRANTE", materialProject, "DEVOLVERTODO");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al devolver todo el sobrante: " + e.getMessage(), "DEVOLVERTODO");
        }
    }
    
    private String handleReporteStockProyecto(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("REPORTESTOCK", "REPORTESTOCK[\"idProject\"]");
        }
        try {
            Long idProject = Long.parseLong(parameters[0]);
            
            String reporte = materialProjectService.obtenerReporteStockProyecto(idProject);
            return emailResponseService.formatSuccessResponse("REPORTE DE STOCK", reporte, "REPORTESTOCK");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al generar reporte de stock: " + e.getMessage(), "REPORTESTOCK");
        }
    }
    
    private String handleAjustarSobrantePorUsoReal(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("AJUSTARSOBRANTE", "AJUSTARSOBRANTE[\"idMaterialProject\",\"cantidadRealmenteUsada\"]");
        }
        try {
            Long id = Long.parseLong(parameters[0]);
            Integer cantidadRealUsada = Integer.parseInt(parameters[1]);
            
            MaterialProject materialProject = materialProjectService.ajustarSobrantePorUsoReal(id, cantidadRealUsada);
            return emailResponseService.formatSuccessResponse("AJUSTE DE SOBRANTE", materialProject, "AJUSTARSOBRANTE");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al ajustar sobrante: " + e.getMessage(), "AJUSTARSOBRANTE");
        }
    }
    
    private String handleVerificarStockMaterial(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("VERIFICARSTOCK", "VERIFICARSTOCK[\"idMaterial\",\"cantidadRequerida\"]");
        }
        try {
            Long idMaterial = Long.parseLong(parameters[0]);
            Integer cantidadRequerida = Integer.parseInt(parameters[1]);
            
            boolean disponible = materialProjectService.verificarDisponibilidadMaterial(idMaterial, cantidadRequerida);
            
            String mensaje = disponible ? 
                "Material DISPONIBLE. ID: " + idMaterial + ", Cantidad requerida: " + cantidadRequerida :
                "Material NO DISPONIBLE. ID: " + idMaterial + ", Cantidad requerida: " + cantidadRequerida;
                
            return emailResponseService.formatSuccessResponse("VERIFICACIÓN DE STOCK", mensaje, "VERIFICARSTOCK");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al verificar disponibilidad: " + e.getMessage(), "VERIFICARSTOCK");
        }
    }
    
    // --- Métodos de reportes ---
    private String handleReporteCliente(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("REPORTECLIENTE", "REPORTECLIENTE[\"nombre_cliente\"]");
        }
        try {
            String nombreCliente = parameters[0];
            StringBuilder reporte = new StringBuilder();
            
            // Buscar cliente
            Optional<Client> clienteOpt = clientService.buscarClientePorNombre(nombreCliente);
            if (!clienteOpt.isPresent()) {
                return emailResponseService.formatErrorResponse("Cliente no encontrado: " + nombreCliente, "REPORTECLIENTE");
            }
            
            Client cliente = clienteOpt.get();
            reporte.append("=== REPORTE COMPLETO DEL CLIENTE ===\n\n");
            reporte.append("📋 INFORMACIÓN DEL CLIENTE:\n");
            reporte.append("• Nombre: ").append(cliente.getName()).append("\n");
            reporte.append("• Email: ").append(cliente.getEmail()).append("\n");
            reporte.append("• Teléfono: ").append(cliente.getPhone()).append("\n");
            reporte.append("• Dirección: ").append(cliente.getAddress()).append("\n\n");
            
            // Buscar proyectos del cliente
            List<Project> proyectos = projectService.buscarProyectosPorCliente(cliente.getId());
            reporte.append("🏗️ PROYECTOS (Total: ").append(proyectos.size()).append("):\n");
            
            if (proyectos.isEmpty()) {
                reporte.append("• No hay proyectos registrados para este cliente\n\n");
            } else {
                for (Project proyecto : proyectos) {
                    reporte.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                    reporte.append("📌 Proyecto: ").append(proyecto.getName()).append("\n");
                    reporte.append("• Descripción: ").append(proyecto.getDescription()).append("\n");
                    reporte.append("• Ubicación: ").append(proyecto.getLocation()).append("\n");
                    reporte.append("• Estado: ").append(proyecto.getState()).append("\n\n");
                    
                    // Cotizaciones del proyecto
                    List<Quote> cotizaciones = quoteService.buscarCotizacionesPorProyecto(proyecto.getId());
                    reporte.append("💰 COTIZACIONES (").append(cotizaciones.size()).append("):\n");
                    if (cotizaciones.isEmpty()) {
                        reporte.append("  • Sin cotizaciones\n");
                    } else {
                        java.math.BigDecimal totalCotizaciones = java.math.BigDecimal.ZERO;
                        for (Quote cotizacion : cotizaciones) {
                            reporte.append("  • ID ").append(cotizacion.getIdQuote())
                                   .append(" - Tipo: ").append(cotizacion.getTypeMetro())
                                   .append(" - Total: $").append(cotizacion.getTotal())
                                   .append(" - Estado: ").append(cotizacion.getState()).append("\n");
                            totalCotizaciones = totalCotizaciones.add(cotizacion.getTotal());
                        }
                        reporte.append("  💵 TOTAL COTIZADO: $").append(totalCotizaciones).append("\n");
                    }
                    
                    // Cronogramas del proyecto
                    List<Schedule> cronogramas = scheduleService.buscarPorProyecto(proyecto.getId());
                    reporte.append("📅 CRONOGRAMAS (").append(cronogramas.size()).append("):\n");
                    if (cronogramas.isEmpty()) {
                        reporte.append("  • Sin cronogramas\n");
                    } else {
                        for (Schedule cronograma : cronogramas) {
                            reporte.append("  • ").append(cronograma.getInitDate())
                                   .append(" a ").append(cronograma.getFinalDate())
                                   .append(" (").append(cronograma.getEstimateDays()).append(" días)")
                                   .append(" - Estado: ").append(cronograma.getState()).append("\n");
                        }
                    }
                    
                    // Planes de pago del proyecto
                    Optional<PayPlan> planPagoOpt = payPlanService.buscarPlanPagoPorProyecto(proyecto.getId().toString());
                    reporte.append("💳 PLANES DE PAGO:\n");
                    if (!planPagoOpt.isPresent()) {
                        reporte.append("  • Sin planes de pago\n");
                    } else {
                        PayPlan plan = planPagoOpt.get();
                        reporte.append("  • Deuda Total: $").append(plan.getTotalDebt())
                               .append(" - Total Pagado: $").append(plan.getTotalPayed())
                               .append(" - Estado: ").append(plan.getState()).append("\n");
                    }
                    reporte.append("\n");
                }
            }
            
            reporte.append("=== FIN DEL REPORTE ===");
            return emailResponseService.formatSuccessResponse("REPORTE DE CLIENTE", reporte.toString(), "REPORTECLIENTE");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al generar reporte de cliente: " + e.getMessage(), "REPORTECLIENTE");
        }
    }
    
    private String handleReporteProyecto(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("REPORTEPROYECTO", "REPORTEPROYECTO[\"nombre_proyecto\"]");
        }
        try {
            String nombreProyecto = parameters[0];
            StringBuilder reporte = new StringBuilder();
            
            // Buscar proyecto
            Optional<Project> proyectoOpt = projectService.buscarProyectoPorNombre(nombreProyecto);
            if (!proyectoOpt.isPresent()) {
                return emailResponseService.formatErrorResponse("Proyecto no encontrado: " + nombreProyecto, "REPORTEPROYECTO");
            }
            
            Project proyecto = proyectoOpt.get();
            reporte.append("=== REPORTE COMPLETO DEL PROYECTO ===\n\n");
            reporte.append("🏗️ INFORMACIÓN DEL PROYECTO:\n");
            reporte.append("• Nombre: ").append(proyecto.getName()).append("\n");
            reporte.append("• Descripción: ").append(proyecto.getDescription()).append("\n");
            reporte.append("• Ubicación: ").append(proyecto.getLocation()).append("\n");
            reporte.append("• Estado: ").append(proyecto.getState()).append("\n");
            reporte.append("• ID Cliente: ").append(proyecto.getIdClient()).append("\n");
            reporte.append("• ID Usuario: ").append(proyecto.getUserId()).append("\n\n");
            
            // Cotizaciones del proyecto
            List<Quote> cotizaciones = quoteService.buscarCotizacionesPorProyecto(proyecto.getId());
            reporte.append("💰 COTIZACIONES DETALLADAS (Total: ").append(cotizaciones.size()).append("):\n");
            
            if (cotizaciones.isEmpty()) {
                reporte.append("• No hay cotizaciones para este proyecto\n\n");
            } else {
                java.math.BigDecimal totalGeneral = java.math.BigDecimal.ZERO;
                java.math.BigDecimal totalAprobado = java.math.BigDecimal.ZERO;
                int aprobadas = 0, pendientes = 0, rechazadas = 0;
                
                for (Quote cotizacion : cotizaciones) {
                    reporte.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                    reporte.append("📋 Cotización ID: ").append(cotizacion.getIdQuote()).append("\n");
                    reporte.append("• Tipo Metro: ").append(cotizacion.getTypeMetro()).append("\n");
                    reporte.append("• Costo por Metro: $").append(cotizacion.getCostMetro()).append("\n");
                    reporte.append("• Cantidad Metro: ").append(cotizacion.getQuantityMetro()).append(" m²\n");
                    reporte.append("• Costo Mobiliario: $").append(cotizacion.getCostFurniture()).append("\n");
                    reporte.append("• Número Muebles: ").append(cotizacion.getFurnitureNumber()).append("\n");
                    reporte.append("• TOTAL: $").append(cotizacion.getTotal()).append("\n");
                    reporte.append("• Estado: ").append(cotizacion.getState()).append("\n");
                    reporte.append("• Comentarios: ").append(cotizacion.getComments()).append("\n");
                    
                    totalGeneral = totalGeneral.add(cotizacion.getTotal());
                    if ("APROBADA".equalsIgnoreCase(cotizacion.getState())) {
                        totalAprobado = totalAprobado.add(cotizacion.getTotal());
                        aprobadas++;
                    } else if ("PENDIENTE".equalsIgnoreCase(cotizacion.getState())) {
                        pendientes++;
                    } else {
                        rechazadas++;
                    }
                    
                    // Diseños asociados a esta cotización
                    Optional<Design> disenoOpt = designService.buscarDisenoPorCotizacion(cotizacion.getIdQuote());
                    if (disenoOpt.isPresent()) {
                        Design diseno = disenoOpt.get();
                        reporte.append("🎨 DISEÑO ASOCIADO:\n");
                        reporte.append("  • ID Diseño: ").append(diseno.getIdDesign()).append("\n");
                        reporte.append("  • URL Render: ").append(diseno.getUrlRender()).append("\n");
                        reporte.append("  • Plano Laminado: ").append(diseno.getLaminatedPlane()).append("\n");
                        reporte.append("  • Aprobado: ").append(diseno.getApproved() ? "SÍ" : "NO").append("\n");
                        reporte.append("  • Fecha Aprobación: ").append(diseno.getApprovedDate()).append("\n");
                        reporte.append("  • Comentarios: ").append(diseno.getComments()).append("\n");
                    }
                    reporte.append("\n");
                }
                
                reporte.append("📊 RESUMEN DE COTIZACIONES:\n");
                reporte.append("• Total Cotizaciones: ").append(cotizaciones.size()).append("\n");
                reporte.append("• Aprobadas: ").append(aprobadas).append(" (").append(totalAprobado).append("$)\n");
                reporte.append("• Pendientes: ").append(pendientes).append("\n");
                reporte.append("• Rechazadas: ").append(rechazadas).append("\n");
                reporte.append("• VALOR TOTAL COTIZADO: $").append(totalGeneral).append("\n");
                reporte.append("• VALOR TOTAL APROBADO: $").append(totalAprobado).append("\n\n");
            }
            
            // Cronogramas y tareas
            List<Schedule> cronogramas = scheduleService.buscarPorProyecto(proyecto.getId());
            reporte.append("📅 CRONOGRAMAS Y TAREAS (").append(cronogramas.size()).append("):\n");
            if (cronogramas.isEmpty()) {
                reporte.append("• No hay cronogramas para este proyecto\n");
            } else {
                for (Schedule cronograma : cronogramas) {
                    reporte.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                    reporte.append("📋 Cronograma ID: ").append(cronograma.getId()).append("\n");
                    reporte.append("• Fecha Inicio: ").append(cronograma.getInitDate()).append("\n");
                    reporte.append("• Fecha Fin: ").append(cronograma.getFinalDate()).append("\n");
                    reporte.append("• Días Estimados: ").append(cronograma.getEstimateDays()).append("\n");
                    reporte.append("• Estado: ").append(cronograma.getState()).append("\n");
                    
                    // Tareas del cronograma
                    List<Task> tareas = taskService.buscarPorCronograma(cronograma.getId());
                    reporte.append("✅ TAREAS (").append(tareas.size()).append("):\n");
                    if (tareas.isEmpty()) {
                        reporte.append("  • Sin tareas asignadas\n");
                    } else {
                        for (Task tarea : tareas) {
                            reporte.append("  • ").append(tarea.getInitHour())
                                   .append("-").append(tarea.getFinalHour())
                                   .append(": ").append(tarea.getDescription())
                                   .append(" (").append(tarea.getState()).append(")\n");
                        }
                    }
                    reporte.append("\n");
                }
            }
            
            // Materiales del proyecto
            List<MaterialProject> materialesProyecto = materialProjectService.buscarMaterialesPorProyecto(proyecto.getId());
            reporte.append("🔧 MATERIALES UTILIZADOS (").append(materialesProyecto.size()).append("):\n");
            if (materialesProyecto.isEmpty()) {
                reporte.append("• No hay materiales asignados a este proyecto\n");
            } else {
                java.math.BigDecimal costoTotalMateriales = java.math.BigDecimal.ZERO;
                for (MaterialProject mp : materialesProyecto) {
                    Optional<Material> materialOpt = materialService.buscarMaterialPorId(mp.getIdMaterial());
                    if (materialOpt.isPresent()) {
                        Material material = materialOpt.get();
                        java.math.BigDecimal costoMaterial = material.getUnitPrice().multiply(new java.math.BigDecimal(mp.getQuantity()));
                        
                        reporte.append("• ").append(material.getName())
                               .append(" - Cantidad: ").append(mp.getQuantity())
                               .append(" ").append(material.getUnitMeasure())
                               .append(" - Precio Unitario: $").append(material.getUnitPrice())
                               .append(" - Costo Total: $").append(costoMaterial).append("\n");
                        
                        costoTotalMateriales = costoTotalMateriales.add(costoMaterial);
                    }
                }
                reporte.append("💰 COSTO TOTAL EN MATERIALES: $").append(costoTotalMateriales).append("\n");
            }
            
            reporte.append("\n=== FIN DEL REPORTE ===");
            return emailResponseService.formatSuccessResponse("REPORTE DE PROYECTO", reporte.toString(), "REPORTEPROYECTO");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al generar reporte de proyecto: " + e.getMessage(), "REPORTEPROYECTO");
        }
    }
    
    private String handleReporteMateriales(String[] parameters) {
        try {
            StringBuilder reporte = new StringBuilder();
            reporte.append("=== REPORTE COMPLETO DE MATERIALES ===\n\n");
            
            // Obtener todos los materiales
            List<Material> materiales = materialService.listarTodosLosMateriales();
            reporte.append("📦 INVENTARIO GENERAL (Total: ").append(materiales.size()).append(" materiales):\n");
            
            java.math.BigDecimal valorTotalInventario = java.math.BigDecimal.ZERO;
            int materialesBajoStock = 0;
            int materialesAgotados = 0;
            
            for (Material material : materiales) {
                java.math.BigDecimal valorMaterial = material.getUnitPrice().multiply(new java.math.BigDecimal(material.getStock()));
                valorTotalInventario = valorTotalInventario.add(valorMaterial);
                
                String estadoStock = "";
                if (material.getStock() <= 0) {
                    estadoStock = " ⚠️ AGOTADO";
                    materialesAgotados++;
                } else if (material.getStock() <= 10) {
                    estadoStock = " ⚠️ STOCK BAJO";
                    materialesBajoStock++;
                } else {
                    estadoStock = " ✅ OK";
                }
                
                reporte.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                reporte.append("📋 ").append(material.getName()).append(estadoStock).append("\n");
                reporte.append("• Descripción: ").append(material.getType()).append("\n");
                reporte.append("• Stock Actual: ").append(material.getStock()).append(" ").append(material.getUnitMeasure()).append("\n");
                reporte.append("• Precio Unitario: $").append(material.getUnitPrice()).append("\n");
                reporte.append("• Valor en Stock: $").append(valorMaterial).append("\n");
                
                // Buscar en qué proyectos se ha utilizado este material
                List<MaterialProject> usos = materialProjectService.buscarProyectosPorMaterial(material.getId());
                reporte.append("🏗️ PROYECTOS QUE USAN ESTE MATERIAL (").append(usos.size()).append("):\n");
                
                if (usos.isEmpty()) {
                    reporte.append("  • No se ha usado en ningún proyecto\n");
                } else {
                    int cantidadTotalUsada = 0;
                    for (MaterialProject uso : usos) {
                        Optional<Project> proyectoOpt = projectService.buscarProyectoPorId(uso.getIdProject());
                        if (proyectoOpt.isPresent()) {
                            Project proyecto = proyectoOpt.get();
                            reporte.append("  • ").append(proyecto.getName())
                                   .append(" - Cantidad: ").append(uso.getQuantity())
                                   .append(" ").append(material.getUnitMeasure())
                                   .append(" - Estado: ").append(proyecto.getState()).append("\n");
                            cantidadTotalUsada += uso.getQuantity();
                        }
                    }
                    reporte.append("  📊 TOTAL USADO: ").append(cantidadTotalUsada).append(" ").append(material.getUnitMeasure()).append("\n");
                }
                reporte.append("\n");
            }
            
            // Resumen general
            reporte.append("📊 RESUMEN GENERAL DEL INVENTARIO:\n");
            reporte.append("• Total de Materiales: ").append(materiales.size()).append("\n");
            reporte.append("• Materiales con Stock OK: ").append(materiales.size() - materialesBajoStock - materialesAgotados).append("\n");
            reporte.append("• Materiales con Stock Bajo: ").append(materialesBajoStock).append("\n");
            reporte.append("• Materiales Agotados: ").append(materialesAgotados).append("\n");
            reporte.append("• VALOR TOTAL DEL INVENTARIO: $").append(valorTotalInventario).append("\n\n");
            
            // Top 5 materiales más caros
            List<Material> materialesOrdenados = new ArrayList<>(materiales);
            materialesOrdenados.sort((m1, m2) -> m2.getUnitPrice().compareTo(m1.getUnitPrice()));
            reporte.append("💎 TOP 5 MATERIALES MÁS CAROS:\n");
            for (int i = 0; i < Math.min(5, materialesOrdenados.size()); i++) {
                Material m = materialesOrdenados.get(i);
                reporte.append("  ").append(i + 1).append(". ").append(m.getName())
                       .append(" - $").append(m.getUnitPrice()).append(" por ").append(m.getUnitMeasure()).append("\n");
            }
            
            // Materiales que necesitan reabastecimiento
            reporte.append("\n⚠️ MATERIALES QUE REQUIEREN REABASTECIMIENTO:\n");
            boolean hayQueReabastecer = false;
            for (Material material : materiales) {
                if (material.getStock() <= 10) {
                    reporte.append("• ").append(material.getName())
                           .append(" - Stock: ").append(material.getStock())
                           .append(" ").append(material.getUnitMeasure())
                           .append(material.getStock() <= 0 ? " (URGENTE)" : " (PRONTO)").append("\n");
                    hayQueReabastecer = true;
                }
            }
            if (!hayQueReabastecer) {
                reporte.append("• Todos los materiales tienen stock suficiente ✅\n");
            }
            
            reporte.append("\n=== FIN DEL REPORTE ===");
            return emailResponseService.formatSuccessResponse("REPORTE DE MATERIALES", reporte.toString(), "REPORTEMATERIALES");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al generar reporte de materiales: " + e.getMessage(), "REPORTEMATERIALES");
        }
    }
    
    // --- Método de ayuda ---
    // --- Métodos de gestión de asignación (PROYECTO Y PERSONAL) ---
    private String handleListarAsignaciones(String[] parameters) {
        try {
            List<Project> proyectos = projectService.listarTodosLosProyectos();
            StringBuilder asignaciones = new StringBuilder();
            asignaciones.append("=== ASIGNACIONES DE PROYECTOS ===\n\n");
            
            for (Project proyecto : proyectos) {
                Optional<User> usuarioOpt = userService.buscarUsuarioPorId(proyecto.getUserId());
                if (usuarioOpt.isPresent()) {
                    asignaciones.append("📌 ").append(proyecto.getName())
                              .append(" → Asignado a: ").append(usuarioOpt.get().getName())
                              .append(" (").append(usuarioOpt.get().getRole()).append(")")
                              .append(" | Estado: ").append(proyecto.getState()).append("\n");
                }
            }
            
            return emailResponseService.formatSuccessResponse("ASIGNACIONES ACTUALES", asignaciones.toString(), "LISASIG");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al listar asignaciones: " + e.getMessage(), "LISASIG");
        }
    }

    private String handleAsignacionesPorUsuario(String[] parameters) {
        if (parameters.length < 1) {
            return emailResponseService.formatInsufficientParametersResponse("LISASIG_USR", "LISASIG_USR[\"idUsuario\"]");
        }
        try {
            Long idUsuario = Long.parseLong(parameters[0]);
            List<Project> proyectos = projectService.buscarProyectosPorUsuario(idUsuario);
            List<Schedule> cronogramas = scheduleService.buscarPorUsuario(idUsuario);
            List<Task> tareas = taskService.buscarPorUsuario(idUsuario);
            
            StringBuilder asignaciones = new StringBuilder();
            asignaciones.append("=== ASIGNACIONES DEL USUARIO ===\n\n");
            asignaciones.append("📌 PROYECTOS ASIGNADOS: ").append(proyectos.size()).append("\n");
            for (Project p : proyectos) {
                asignaciones.append("  • ").append(p.getName()).append(" (").append(p.getState()).append(")\n");
            }
            
            asignaciones.append("\n📅 CRONOGRAMAS ASIGNADOS: ").append(cronogramas.size()).append("\n");
            for (Schedule s : cronogramas) {
                asignaciones.append("  • ").append(s.getInitDate()).append(" a ").append(s.getFinalDate())
                          .append(" (").append(s.getState()).append(")\n");
            }
            
            asignaciones.append("\n✅ TAREAS ASIGNADAS: ").append(tareas.size()).append("\n");
            for (Task t : tareas) {
                asignaciones.append("  • ").append(t.getDescription()).append(" (").append(t.getState()).append(")\n");
            }
            
            return emailResponseService.formatSuccessResponse("ASIGNACIONES POR USUARIO", asignaciones.toString(), "LISASIG_USR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al obtener asignaciones por usuario: " + e.getMessage(), "LISASIG_USR");
        }
    }

    private String handleAsignarProyectoAUsuario(String[] parameters) {
        if (parameters.length < 2) {
            return emailResponseService.formatInsufficientParametersResponse("ASIGNARPROYUSR", "ASIGNARPROYUSR[\"nombreProyecto\",\"idUsuario\"]");
        }
        try {
            String nombreProyecto = parameters[0];
            Long idUsuario = Long.parseLong(parameters[1]);
            
            Optional<Project> proyectoOpt = projectService.buscarProyectoPorNombre(nombreProyecto);
            if (!proyectoOpt.isPresent()) {
                return emailResponseService.formatErrorResponse("Proyecto no encontrado: " + nombreProyecto, "ASIGNARPROYUSR");
            }
            
            Optional<User> usuarioOpt = userService.buscarUsuarioPorId(idUsuario);
            if (!usuarioOpt.isPresent()) {
                return emailResponseService.formatErrorResponse("Usuario no encontrado con ID: " + idUsuario, "ASIGNARPROYUSR");
            }
            
            Project proyecto = proyectoOpt.get();
            proyecto.setUserId(idUsuario);
            Project actualizado = projectService.actualizarProyectoPorNombre(
                proyecto.getName(),
                proyecto.getDescription(),
                proyecto.getLocation(),
                proyecto.getState(),
                proyecto.getIdClient(),
                idUsuario
            );
            
            return emailResponseService.formatSuccessResponse("ASIGNACIÓN EXITOSA", 
                "Proyecto '" + proyecto.getName() + "' asignado a " + usuarioOpt.get().getName(), "ASIGNARPROYUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al asignar proyecto: " + e.getMessage(), "ASIGNARPROYUSR");
        }
    }

    private String handleCargaTrabajoUsuario(String[] parameters) {
        try {
            List<User> usuarios = userService.listarTodosLosUsuarios();
            StringBuilder carga = new StringBuilder();
            carga.append("=== CARGA DE TRABAJO POR USUARIO ===\n\n");
            
            for (User usuario : usuarios) {
                List<Project> proyectos = projectService.buscarProyectosPorUsuario(usuario.getId());
                List<Task> tareas = taskService.buscarPorUsuario(usuario.getId());
                long tareasCompletadas = tareas.stream().filter(t -> "Completada".equalsIgnoreCase(t.getState())).count();
                
                carga.append("👤 ").append(usuario.getName()).append(" (").append(usuario.getRole()).append(")\n");
                carga.append("  • Proyectos: ").append(proyectos.size()).append("\n");
                carga.append("  • Tareas Totales: ").append(tareas.size()).append("\n");
                carga.append("  • Tareas Completadas: ").append(tareasCompletadas).append("\n");
                carga.append("  • Progreso: ").append(tareas.isEmpty() ? 0 : (tareasCompletadas * 100 / tareas.size())).append("%\n\n");
            }
            
            return emailResponseService.formatSuccessResponse("CARGA DE TRABAJO", carga.toString(), "CARGAUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al obtener carga de trabajo: " + e.getMessage(), "CARGAUSR");
        }
    }

    // --- Métodos adicionales para reportes avanzados ---
    private String handleReporteIntegral(String[] parameters) {
        try {
            StringBuilder reporte = new StringBuilder();
            reporte.append("=== REPORTE INTEGRAL DEL SISTEMA ===\n\n");
            
            // Estadísticas generales
            List<Project> proyectos = projectService.listarTodosLosProyectos();
            List<Client> clientes = clientService.listarTodosLosClientes();
            List<User> usuarios = userService.listarTodosLosUsuarios();
            List<Material> materiales = materialService.listarTodosLosMateriales();
            
            reporte.append("📊 ESTADÍSTICAS GENERALES:\n");
            reporte.append("• Total Proyectos: ").append(proyectos.size()).append("\n");
            reporte.append("• Total Clientes: ").append(clientes.size()).append("\n");
            reporte.append("• Total Usuarios: ").append(usuarios.size()).append("\n");
            reporte.append("• Total Materiales: ").append(materiales.size()).append("\n\n");
            
            // Proyectos por estado
            long completados = proyectos.stream().filter(p -> "Completado".equalsIgnoreCase(p.getState())).count();
            long enProceso = proyectos.stream().filter(p -> "En Proceso".equalsIgnoreCase(p.getState())).count();
            long planificacion = proyectos.stream().filter(p -> "Planificación".equalsIgnoreCase(p.getState())).count();
            
            reporte.append("🏗️ ESTADO DE PROYECTOS:\n");
            reporte.append("• Completados: ").append(completados).append(" (").append(proyectos.isEmpty() ? 0 : (completados * 100 / proyectos.size())).append("%)\n");
            reporte.append("• En Proceso: ").append(enProceso).append(" (").append(proyectos.isEmpty() ? 0 : (enProceso * 100 / proyectos.size())).append("%)\n");
            reporte.append("• En Planificación: ").append(planificacion).append(" (").append(proyectos.isEmpty() ? 0 : (planificacion * 100 / proyectos.size())).append("%)\n\n");
            
            // Financiero
            List<PayPlan> planes = payPlanService.listarTodosLosPlanesPago();
            java.math.BigDecimal deudaTotal = java.math.BigDecimal.ZERO;
            java.math.BigDecimal pagadoTotal = java.math.BigDecimal.ZERO;
            
            for (PayPlan plan : planes) {
                deudaTotal = deudaTotal.add(plan.getTotalDebt());
                pagadoTotal = pagadoTotal.add(plan.getTotalPayed());
            }
            
            reporte.append("💰 INFORMACIÓN FINANCIERA:\n");
            reporte.append("• Deuda Total: $").append(deudaTotal).append("\n");
            reporte.append("• Total Pagado: $").append(pagadoTotal).append("\n");
            reporte.append("• Pendiente de Pago: $").append(deudaTotal.subtract(pagadoTotal)).append("\n");
            reporte.append("• Porcentaje Pagado: ").append(deudaTotal.compareTo(java.math.BigDecimal.ZERO) == 0 ? 0 : (pagadoTotal.multiply(new java.math.BigDecimal(100)).divide(deudaTotal, 2, java.math.RoundingMode.HALF_UP))).append("%\n\n");
            
            reporte.append("=== FIN DEL REPORTE ===");
            return emailResponseService.formatSuccessResponse("REPORTE INTEGRAL", reporte.toString(), "REPORTEINT");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al generar reporte integral: " + e.getMessage(), "REPORTEINT");
        }
    }

    private String handleHelp(String[] parameters) {
        try {
            StringBuilder help = new StringBuilder();
            help.append("=== SISTEMA DE COMANDOS POR CORREO ELECTRÓNICO ===\n\n");
            help.append("Formato: COMANDO[\"parametro1\",\"parametro2\",...]\n");
            help.append("Nota: Use \"*\" para listar todos los elementos\n\n");
            
            // Comandos de Clientes
            help.append(" CLIENTES:\n");
            help.append("* LISCLI[\"*\"] - Listar todos los clientes\n");
            help.append("* INSCLI[\"nombre\",\"email\",\"telefono\",\"direccion\"] - Insertar cliente\n");
            help.append("* UPDCLI[\"nombre\",\"email\",\"telefono\",\"direccion\"] - Actualizar cliente\n");
            help.append("* DELCLI[\"nombre\"] - Eliminar cliente\n");
            help.append("* BUSCLIEMAIL[\"email\"] - Buscar cliente por email\n");
            help.append("* BUSCLIPROY[\"*\"] - Buscar clientes con proyectos\n");
            help.append("* ESTCLIS[\"*\"] - Obtener estadísticas de clientes\n\n");
            
            // Comandos de Proyectos
            help.append(" PROYECTOS:\n");
            help.append("* LISPROY[\"*\"] - Listar todos los proyectos\n");
            help.append("* INSPROY[\"nombre\",\"descripcion\",\"ubicacion\",\"estado\",\"idCliente\",\"idUsuario\"] - Insertar proyecto\n");
            help.append("* UPDPROY[\"nombre\",\"descripcion\",\"ubicacion\",\"estado\",\"idCliente\",\"idUsuario\"] - Actualizar proyecto\n");
            help.append("* BUSPROYNOM[\"nombre\"] - Buscar proyecto por nombre\n");
            help.append("* BUSPROYCLI[\"idCliente\"] - Buscar proyectos por cliente\n");
            help.append("* BUSPROYUSR[\"idUsuario\"] - Buscar proyectos por usuario\n");
            help.append("* BUSPROYEST[\"estado\"] - Buscar proyectos por estado\n");
            help.append("* ESTPROY[\"*\"] - Obtener estadísticas de proyectos\n\n");
            
            // Comandos de Usuarios
            help.append(" USUARIOS:\n");
            help.append("* LISUSR[\"*\"] - Listar todos los usuarios\n");
            help.append("* INSUSR[\"nombre\",\"email\",\"telefono\",\"direccion\",\"password\",\"rol\"] - Insertar usuario\n");
            help.append("* UPDUSR[\"id\",\"nombre\",\"email\",\"telefono\",\"direccion\",\"password\",\"rol\"] - Actualizar usuario\n");
            help.append("* DELUSR[\"nombre\"] - Eliminar usuario\n");
            help.append("* BUSUSRNOM[\"nombre\"] - Buscar usuario por nombre\n");
            help.append("* BUSUSREMAIL[\"email\"] - Buscar usuario por email\n");
            help.append("* BUSUSRROL[\"rol\"] - Buscar usuarios por rol\n\n");
            
            // Comandos de Cronogramas
            help.append(" CRONOGRAMAS:\n");
            help.append("* LISSCH[\"*\"] - Listar todos los cronogramas\n");
            help.append("* INSSCH[\"initDate\",\"finalDate\",\"estimateDays\",\"state\",\"idProject\",\"userId\"] - Insertar cronograma\n");
            help.append("* UPDSCH[\"id\",\"initDate\",\"finalDate\",\"estimateDays\",\"state\",\"idProject\",\"userId\"] - Actualizar cronograma\n");
            help.append("* BUSSCHID[\"id\"] - Buscar cronograma por ID\n");
            help.append("* BUSSCHPROY[\"idProject\"] - Buscar cronogramas por proyecto\n");
            help.append("* BUSSCHUSR[\"userId\"] - Buscar cronogramas por usuario\n");
            help.append("* SCHACT[\"*\"] - Buscar cronogramas activos\n");
            help.append("* SCHCOMP[\"*\"] - Buscar cronogramas completados\n\n");
            
            // Comandos de Tareas
            help.append(" TAREAS:\n");
            help.append("* LISTASK[\"*\"] - Listar todas las tareas\n");
            help.append("* INSTASK[\"initHour\",\"finalHour\",\"description\",\"state\",\"idSchedule\",\"userId\"] - Insertar tarea\n");
            help.append("* UPDTASK[\"id\",\"initHour\",\"finalHour\",\"description\",\"state\",\"idSchedule\",\"userId\"] - Actualizar tarea\n");
            help.append("* DELTASK[\"id\"] - Eliminar tarea\n");
            help.append("* BUSTASKID[\"id\"] - Buscar tarea por ID\n");
            help.append("* BUSTASKSCH[\"idSchedule\"] - Buscar tareas por cronograma\n");
            help.append("* BUSTASKUSR[\"userId\"] - Buscar tareas por usuario\n");
            help.append("* TASKACT[\"*\"] - Buscar tareas activas\n");
            help.append("* TASKCOMP[\"*\"] - Buscar tareas completadas\n");
            help.append("* TASKPEND[\"*\"] - Buscar tareas pendientes\n\n");
            
            // Comandos de Cotizaciones
            help.append(" COTIZACIONES:\n");
            help.append("* LISQUOTE[\"*\"] - Listar todas las cotizaciones\n");
            help.append("* INSQUOTE[\"typeMetro\",\"costMetro\",\"quantityMetro\",\"costFurniture\",\"total\",\"state\",\"furnitureNumber\",\"comments\",\"idProject\",\"userId\"] - Insertar cotización\n");
            help.append("* UPDQUOTE[\"id\",\"typeMetro\",\"costMetro\",\"quantityMetro\",\"costFurniture\",\"total\",\"state\",\"furnitureNumber\",\"comments\",\"idProject\",\"userId\"] - Actualizar cotización\n");
            help.append("* DELQUOTE[\"id\"] - Eliminar cotización\n");
            help.append("* BUSQUOTEID[\"id\"] - Buscar cotización por ID\n");
            help.append("* BUSQUOTEPROY[\"idProject\"] - Buscar cotizaciones por proyecto\n");
            help.append("* BUSQUOTEUSR[\"userId\"] - Buscar cotizaciones por usuario\n");
            help.append("* BUSQUOTETYPE[\"typeMetro\"] - Buscar cotizaciones por tipo metro\n");
            help.append("* TOTALQUOTEAPPR[\"idProject\"] - Total cotizaciones aprobadas por proyecto\n");
            help.append("* CALCQUOTE[\"costMetro\",\"quantityMetro\",\"costFurniture\"] - Calcular total cotización\n");
            help.append("* APPRQUOTE[\"id\"] - Aprobar cotización\n");
            help.append("* REJQUOTE[\"id\"] - Rechazar cotización\n\n");
            
            // Comandos de Diseños
            help.append(" DISEÑOS:\n");
            help.append("* LISDESIGN[\"*\"] - Listar todos los diseños\n");
            help.append("* INSDESIGN[\"idQuote\",\"urlRender\",\"laminatedPlane\",\"approved\",\"approvedDate\",\"comments\",\"userId\"] - Insertar diseño\n");
            help.append("* UPDDESIGN[\"idDesign\",\"idQuote\",\"urlRender\",\"laminatedPlane\",\"approved\",\"approvedDate\",\"comments\",\"userId\"] - Actualizar diseño\n");
            help.append("* DELDESIGN[\"id\"] - Eliminar diseño\n");
            help.append("* BUSDESIGNID[\"id\"] - Buscar diseño por ID\n");
            help.append("* BUSDESIGNQUOTE[\"idQuote\"] - Buscar diseño por cotización\n");
            help.append("* BUSDESIGNUSR[\"userId\"] - Buscar diseños por usuario\n");
            help.append("* DESIGNAPPR[\"*\"] - Buscar diseños aprobados\n");
            help.append("* APPRDESIGN[\"id\"] - Aprobar diseño\n");
            help.append("* REJDESIGN[\"id\"] - Rechazar diseño\n\n");
            
            // Comandos de Planes de Pago
            help.append(" PLANES DE PAGO:\n");
            help.append("* LISPAYPLAN[\"*\"] - Listar todos los planes de pago\n");
            help.append("* INSPAYPLAN[\"proyecto_id\",\"deuda_total\",\"porcentaje_pago\",\"estado\"] - Insertar plan de pago\n");
            help.append("* UPDPAYPLAN[\"id\",\"proyecto_id\",\"deuda_total\",\"porcentaje_pago\",\"estado\"] - Actualizar plan de pago\n");
            help.append("* BUSPAYPLANID[\"id\"] - Buscar plan de pago por ID\n");
            help.append("* BUSPAYPLANPROY[\"proyecto_id\"] - Buscar plan de pago por proyecto\n");
            help.append("* BUSPAYPLANEST[\"estado\"] - Buscar planes de pago por estado\n");
            help.append("* TOTDEUDAPEND[\"*\"] - Total deuda pendiente\n");
            help.append("* TOTPAGADO[\"*\"] - Total pagado\n");
            help.append("* UPDDEUDATOT[\"id\",\"nueva_deuda\"] - Actualizar deuda total\n");
            help.append("* CALCPORCPAGO[\"id\"] - Calcular porcentaje de pago\n");
            help.append("* CAMBIOEST[\"id\",\"nuevo_estado\"] - Cambiar estado\n");
            help.append("* CREARPLANPAGOS[\"proyecto_id\",\"deuda_total\",\"num_pagos\"] - Crear plan completo con pagos\n");
            help.append("* OBTENERPLANPAGO[\"id\"] - Obtener plan de pago completo\n");
            help.append("* RECALCPLANPAGO[\"id\"] - Recalcular plan de pago\n\n");
            
            // Comandos de Pagos
            help.append(" PAGOS:\n");
            help.append("* LISPAYS[\"*\"] - Listar todos los pagos\n");
            help.append("* INSPAY[\"plan_pago_id\",\"monto\",\"fecha_pago\",\"metodo_pago\",\"estado\"] - Insertar pago\n");
            help.append("* UPDPAY[\"id\",\"plan_pago_id\",\"monto\",\"fecha_pago\",\"metodo_pago\",\"estado\"] - Actualizar pago\n");
            help.append("* BUSPAYID[\"id\"] - Buscar pago por ID\n");
            help.append("* BUSPAYCLI[\"cliente_nombre\"] - Buscar pagos por cliente\n");
            help.append("* TOTPAGCLI[\"cliente_nombre\"] - Total pagado por cliente\n");
            help.append("* PLANPAGOHAS[\"plan_pago_id\"] - Verificar si plan tiene pagos\n");
            help.append("* COUNTPAYPPLAN[\"plan_pago_id\"] - Contar pagos por plan\n");
            help.append("* OBTPAGOSPLAN[\"plan_pago_id\"] - Obtener pagos por plan\n");
            help.append("* PAGAR[\"plan_pago_id\",\"monto\",\"metodo_pago\"] - Realizar pago automático\n\n");
            
            // Comandos de Materiales
            help.append(" MATERIALES:\n");
            help.append("* LISMAT[\"*\"] - Listar todos los materiales\n");
            help.append("* INSMAT[\"nombre\",\"descripcion\",\"unidad_medida\",\"precio_unitario\",\"stock_actual\"] - Insertar material\n");
            help.append("* UPDMAT[\"id\",\"nombre\",\"descripcion\",\"unidad_medida\",\"precio_unitario\",\"stock_actual\"] - Actualizar material\n");
            help.append("* BUSMATNOM[\"nombre\"] - Buscar material por nombre\n");
            help.append("* BUSMATTIPO[\"tipo\"] - Buscar materiales por tipo\n");
            help.append("* UPDMATPRECIO[\"id\",\"nuevo_precio\"] - Actualizar precio material\n");
            help.append("* UPDMATSTOCK[\"id\",\"nuevo_stock\"] - Actualizar stock material\n");
            help.append("* REDMATSTOCK[\"id\",\"cantidad\"] - Reducir stock material\n");
            help.append("* AUMMATSTOCK[\"id\",\"cantidad\"] - Aumentar stock material\n");
            help.append("* VERMATDISP[\"id\",\"cantidad_requerida\"] - Verificar disponibilidad material\n\n");
            
            // Comandos de Material-Proyecto (GESTIÓN DE INVENTARIO)
            help.append(" GESTIÓN DE INVENTARIO (MATERIAL-PROYECTO):\n");
            help.append("* LISMATPROY[\"*\"] - Listar todas las asignaciones material-proyecto\n");
            help.append("* INSMATPROY[\"proyecto_id\",\"material_id\",\"cantidad_requerida\"] - Asignar material (DESCUENTA STOCK)\n");
            help.append("* UPDMATPROY[\"id\",\"proyecto_id\",\"material_id\",\"cantidad_requerida\"] - Actualizar asignación (AJUSTA STOCK)\n");
            help.append("* DELMATPROY[\"id\"] - Eliminar asignación (DEVUELVE STOCK)\n");
            help.append("* BUSMATPROYID[\"id\"] - Buscar asignación por ID\n");
            help.append("* BUSMATPORPROY[\"proyecto_id\"] - Buscar materiales por proyecto\n");
            help.append("* BUSPROYPORMAT[\"material_id\"] - Buscar proyectos por material\n\n");
            
            // Nuevos comandos de gestión de stock
            help.append(" GESTIÓN AVANZADA DE STOCK:\n");
            help.append("* DEVOLVERSOBRANTE[\"materialproject_id\",\"cantidad_sobrante\"] - Devolver material no usado\n");
            help.append("* DEVOLVERTODO[\"proyecto_id\"] - Devolver todo el material sobrante de un proyecto\n");
            help.append("* REPORTESTOCK[\"proyecto_id\"] - Ver reporte de stock del proyecto\n");
            help.append("* AJUSTARSOBRANTE[\"materialproject_id\",\"uso_real\"] - Ajustar por uso real vs asignado\n");
            help.append("* VERIFICARSTOCK[\"material_id\"] - Verificar disponibilidad actual de material\n\n");
            
            // Nuevos comandos de gestión de stock
            help.append(" GESTIÓN AVANZADA DE STOCK:\n");
            help.append("* DEVOLVERSOBRANTE[\"materialproject_id\",\"cantidad_sobrante\"] - Devolver material no usado\n");
            help.append("* DEVOLVERTODO[\"proyecto_id\"] - Devolver todo el material sobrante de un proyecto\n");
            help.append("* REPORTESTOCK[\"proyecto_id\"] - Ver reporte de stock del proyecto\n");
            help.append("* AJUSTARSOBRANTE[\"materialproject_id\",\"uso_real\"] - Ajustar por uso real vs asignado\n");
            help.append("* VERIFICARSTOCK[\"material_id\"] - Verificar disponibilidad actual de material\n\n");
            
            // Comandos de asignación
            help.append(" GESTIÓN DE ASIGNACIÓN (PROYECTO Y PERSONAL):\n");
            help.append("* LISASIG[\"*\"] - Listar todas las asignaciones de proyectos\n");
            help.append("* LISASIG_USR[\"id_usuario\"] - Listar asignaciones de un usuario específico\n");
            help.append("* ASIGNARPROYUSR[\"nombre_proyecto\",\"id_usuario\"] - Asignar un proyecto a un usuario\n");
            help.append("* CARGAUSR[\"*\"] - Ver carga de trabajo de todos los usuarios\n\n");
            
            // Comandos de reportes
            help.append(" REPORTES EJECUTIVOS:\n");
            help.append("* REPORTECLIENTE[\"nombre_cliente\"] - Reporte completo del cliente con todos sus proyectos\n");
            help.append("* REPORTEPROYECTO[\"nombre_proyecto\"] - Reporte detallado del proyecto con cotizaciones y materiales\n");
            help.append("* REPORTEMATERIALES[\"*\"] - Reporte completo del inventario y uso de materiales\n");
            help.append("* REPORTEINT[\"*\"] - Reporte integral del sistema (estadísticas, financiero, etc)\n\n");
            
            // Comando de ayuda
            help.append(" AYUDA:\n");
            help.append("* HELP[\"*\"] - Mostrar esta ayuda completa\n\n");
            
            help.append("=== NOTAS IMPORTANTES ===\n");
            help.append("* Todos los parámetros deben ir entre comillas dobles\n");
            help.append("* Las fechas deben estar en formato YYYY-MM-DD\n");
            help.append("* Las horas deben estar en formato HH:MM\n");
            help.append("* Los IDs deben ser números enteros\n");
            help.append("* Los montos pueden tener decimales (usar punto como separador)\n");
            help.append("* Los valores booleanos deben ser true/false\n");
            help.append("* El sistema gestiona automáticamente el stock al asignar/devolver materiales\n\n");
            help.append("Sistema desarrollado por Grupo03SA - TecnoWeb 2025");
            
            return emailResponseService.formatSuccessResponse("SISTEMA DE AYUDA", help.toString(), "HELP");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al generar ayuda: " + e.getMessage(), "HELP");
        }
    }
}