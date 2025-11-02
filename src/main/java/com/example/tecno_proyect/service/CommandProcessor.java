package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Persona;
import com.example.tecno_proyect.model.Client;
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
    private EmailResponseService emailResponseService;    public String processCommand(String subject, String senderEmail) {
        try {
            if (subject == null || subject.trim().isEmpty()) {
                return "Error: Comando vacío. Formato esperado: COMANDO[\"parametros\"]";
            }

            // Identificar el comando
            String command = extractCommand(subject);
            String[] parameters = extractParameters(subject);

            switch (command.toUpperCase()) {
                case "LISPER":
                    return handleListPersonas(parameters);
                case "INSPER":
                    return handleInsertPersona(parameters);
                case "UPDPER":
                    return handleUpdatePersona(parameters);
                case "DELPER":
                    return handleDeletePersona(parameters);
                case "BUSPER":
                    return handleBuscarPersona(parameters);
                // Clientes
                case "LISCLI":
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
                parameters[5]  // userId
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
                parameters[6]  // userId
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
            List<Task> tareas = taskService.buscarPorUsuario(parameters[0]);
            return emailResponseService.formatListTareasPorUsuarioResponse(tareas, "BUSTASKUSR");
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
                    return emailResponseService.formatUnknownCommandResponse(command);
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
                parameters[4], // idProject
                parameters[5]  // userId
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
                parameters[5], // idProject
                parameters[6]  // userId
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
            List<Schedule> cronogramas = scheduleService.buscarPorProyecto(parameters[0]);
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
            List<Schedule> cronogramas = scheduleService.buscarPorUsuario(parameters[0]);
            return emailResponseService.formatListCronogramasPorUsuarioResponse(cronogramas, "BUSSCHUSR");
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
                parameters[4], // idCliente
                parameters[5]  // idUsuario
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
            Project proyecto = projectService.actualizarProyecto(
                parameters[0], // nombre
                parameters[1], // descripcion
                parameters[2], // ubicacion
                parameters[3], // estado
                parameters[4], // idCliente
                parameters[5]  // idUsuario
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
            List<Project> proyectos = projectService.buscarProyectosPorCliente(parameters[0]);
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
            List<Project> proyectos = projectService.buscarProyectosPorUsuario(parameters[0]);
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

        } catch (Exception e) {
            return emailResponseService.formatErrorResponse(e.getMessage(), subject);
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
            List<Client> clientes = clientService.listarTodosLosClientes();
            return emailResponseService.formatListClientesResponse(clientes, "LISCLI");
        } catch (Exception e) {
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

    private String handleInsertCliente(String[] parameters) {
        // Debugging: log the parameters received
        System.out.println("INSCLI - Parámetros recibidos: " + parameters.length);
        for (int i = 0; i < parameters.length; i++) {
            System.out.println("Parámetro " + i + ": [" + parameters[i] + "]");
        }
        
        if (parameters.length < 4) {
            return emailResponseService.formatInsufficientParametersResponse("INSCLI", 
                "INSCLI[\"nombre\",\"email\",\"telefono\",\"direccion\"]");
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
        if (parameters.length < 6) {
            return emailResponseService.formatInsufficientParametersResponse("UPDUSR", "UPDUSR[\"nombre\",\"email\",\"telefono\",\"direccion\",\"password\",\"rol\"]");
        }
        try {
            User usuario = userService.actualizarUsuario(
                parameters[0], // nombre
                parameters[1], // email
                parameters[2], // telefono
                parameters[3], // direccion
                parameters[4], // password
                parameters[5]  // rol
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
            boolean eliminado = userService.eliminarUsuario(nombre);
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
                parameters[8], // idProject
                parameters[9]  // userId
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
                parameters[9], // idProject
                parameters[10] // userId
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
            List<Quote> cotizaciones = quoteService.buscarCotizacionesPorProyecto(parameters[0]);
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
            List<Quote> cotizaciones = quoteService.buscarCotizacionesPorUsuario(parameters[0]);
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
                parameters[6]  // userId
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
                parameters[7]  // userId
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
            List<Design> disenos = designService.buscarDisenosPorUsuario(parameters[0]);
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
            List<Design> disenos = designService.obtenerDisenosAprobadosPorUsuario(parameters[0]);
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
            List<Design> disenos = designService.obtenerDisenosPendientesPorUsuario(parameters[0]);
            return emailResponseService.formatListDisenosPendientesPorUsuarioResponse(disenos, "DESIGNPENDUSR");
        } catch (Exception e) {
            return emailResponseService.formatErrorResponse("Error al obtener diseños pendientes por usuario: " + e.getMessage(), "DESIGNPENDUSR");
        }
    }
}