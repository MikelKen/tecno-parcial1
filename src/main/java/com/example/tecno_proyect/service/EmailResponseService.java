package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Persona;
import com.example.tecno_proyect.model.Cliente;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailResponseService {

    private static final String SEPARATOR = "================================================";
    private static final String MINI_SEPARATOR = "------------------------";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Genera el encabezado estándar para todas las respuestas
     */
    private String generateHeader(String comando) {
        StringBuilder header = new StringBuilder();
        header.append(SEPARATOR).append("\n");
        header.append("    SISTEMA DE GESTIÓN - GRUPO 03SA\n");
        header.append("    Respuesta para comando: ").append(comando).append("\n");
        header.append("    Fecha: ").append(LocalDateTime.now().format(DATE_FORMATTER)).append("\n");
        header.append(SEPARATOR).append("\n\n");
        return header.toString();
    }

    /**
     * Formatea respuesta para listado de personas
     */
    public String formatListPersonasResponse(List<Persona> personas, String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));

        if (personas.isEmpty()) {
            response.append("📋 RESULTADO DEL LISTADO\n");
            response.append(MINI_SEPARATOR).append("\n");
            response.append(" No se encontraron personas en la base de datos.\n");
        } else {
            response.append(" LISTADO DE PERSONAS\n");
            response.append(MINI_SEPARATOR).append("\n");
            response.append("Total de registros encontrados: ").append(personas.size()).append("\n\n");

            int contador = 1;
            for (Persona persona : personas) {
                response.append(" PERSONA #").append(contador).append("\n");
                response.append("   • CI: ").append(persona.getCi()).append("\n");
                response.append("   • Nombre: ").append(persona.getNombre()).append(" ").append(persona.getApellido()).append("\n");
                response.append("   • Cargo: ").append(persona.getCargo()).append("\n");
                response.append("   • Teléfono: ").append(persona.getTelefono()).append("\n");
                response.append("   • Celular: ").append(persona.getCelular()).append("\n");
                response.append("   • Email: ").append(persona.getEmail()).append("\n");
                response.append("\n");
                contador++;
            }
        }
        return response.toString();
    }

    /**
     * Formatea respuesta para inserción exitosa de persona
     */
    public String formatInsertPersonaSuccess(Persona persona, String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));
        
        response.append(" INSERCIÓN EXITOSA\n");
        response.append(MINI_SEPARATOR).append("\n");
        response.append("La persona ha sido registrada correctamente en el sistema.\n\n");
        
        response.append(" DATOS REGISTRADOS:\n");
        response.append("   • CI: ").append(persona.getCi()).append("\n");
        response.append("   • Nombre Completo: ").append(persona.getNombre()).append(" ").append(persona.getApellido()).append("\n");
        response.append("   • Cargo: ").append(persona.getCargo()).append("\n");
        response.append("   • Teléfono: ").append(persona.getTelefono()).append("\n");
        response.append("   • Celular: ").append(persona.getCelular()).append("\n");
        response.append("   • Email: ").append(persona.getEmail()).append("\n");

        return response.toString();
    }

    /**
     * Formatea respuesta para actualización exitosa de persona
     */
    public String formatUpdatePersonaSuccess(Persona persona, String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));
        
        response.append(" ACTUALIZACIÓN EXITOSA\n");
        response.append(MINI_SEPARATOR).append("\n");
        response.append("Los datos de la persona han sido actualizados correctamente.\n\n");
        
        response.append(" DATOS ACTUALIZADOS:\n");
        response.append("   • CI: ").append(persona.getCi()).append("\n");
        response.append("   • Nombre Completo: ").append(persona.getNombre()).append(" ").append(persona.getApellido()).append("\n");
        response.append("   • Cargo: ").append(persona.getCargo()).append("\n");
        response.append("   • Teléfono: ").append(persona.getTelefono()).append("\n");
        response.append("   • Celular: ").append(persona.getCelular()).append("\n");
        response.append("   • Email: ").append(persona.getEmail()).append("\n");

        return response.toString();
    }

    /**
     * Formatea respuesta para eliminación exitosa de persona
     */
    public String formatDeletePersonaSuccess(String ci, String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));
        
        response.append(" ELIMINACIÓN EXITOSA\n");
        response.append(MINI_SEPARATOR).append("\n");
        response.append("La persona con CI: ").append(ci).append(" ha sido eliminada correctamente del sistema.\n");

        return response.toString();
    }

    /**
     * Formatea respuesta para búsqueda exitosa de persona
     */
    public String formatSearchPersonaSuccess(Persona persona, String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));
        
        response.append(" BÚSQUEDA EXITOSA\n");
        response.append(MINI_SEPARATOR).append("\n");
        response.append("Se encontró la persona solicitada:\n\n");
        
        response.append(" INFORMACIÓN PERSONAL:\n");
        response.append("   • CI: ").append(persona.getCi()).append("\n");
        response.append("   • Nombre Completo: ").append(persona.getNombre()).append(" ").append(persona.getApellido()).append("\n");
        response.append("   • Cargo: ").append(persona.getCargo()).append("\n");
        response.append("   • Teléfono: ").append(persona.getTelefono()).append("\n");
        response.append("   • Celular: ").append(persona.getCelular()).append("\n");
        response.append("   • Email: ").append(persona.getEmail()).append("\n");

        return response.toString();
    }

    /**
     * Formatea respuesta para búsqueda sin resultados de persona
     */
    public String formatSearchPersonaNotFound(String ci, String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));
        
        response.append(" NO ENCONTRADO\n");
        response.append(MINI_SEPARATOR).append("\n");
        response.append("No se encontró ninguna persona con el CI: ").append(ci).append("\n");
        response.append("\n Verifique que el número de CI sea correcto y esté registrado en el sistema.\n");

        return response.toString();
    }

    /**
     * Formatea respuesta para listado de clientes
     */
    public String formatListClientesResponse(List<Cliente> clientes, String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));

        if (clientes.isEmpty()) {
            response.append(" RESULTADO DEL LISTADO\n");
            response.append(MINI_SEPARATOR).append("\n");
            response.append(" No se encontraron clientes en la base de datos.\n");
        } else {
            response.append(" LISTADO DE CLIENTES\n");
            response.append(MINI_SEPARATOR).append("\n");
            response.append("Total de registros encontrados: ").append(clientes.size()).append("\n\n");

            int contador = 1;
            for (Cliente cliente : clientes) {
                response.append(" CLIENTE #").append(contador).append("\n");
                response.append("   • Nombre: ").append(cliente.getNombre()).append("\n");
                response.append("   • Email: ").append(cliente.getEmail()).append("\n");
                response.append("   • Teléfono: ").append(cliente.getTelefono()).append("\n");
                response.append("   • Dirección: ").append(cliente.getDireccion()).append("\n");
                response.append("\n");
                contador++;
            }
        }

        return response.toString();
    }

    /**
     * Formatea respuesta para inserción exitosa de cliente
     */
    public String formatInsertClienteSuccess(Cliente cliente, String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));
        
        response.append(" INSERCIÓN EXITOSA\n");
        response.append(MINI_SEPARATOR).append("\n");
        response.append("El cliente ha sido registrado correctamente en el sistema.\n\n");
        
        response.append(" DATOS REGISTRADOS:\n");
        response.append("   • Nombre: ").append(cliente.getNombre()).append("\n");
        response.append("   • Email: ").append(cliente.getEmail()).append("\n");
        response.append("   • Teléfono: ").append(cliente.getTelefono()).append("\n");
        response.append("   • Dirección: ").append(cliente.getDireccion()).append("\n");

        return response.toString();
    }

    /**
     * Formatea respuesta para errores
     */
    public String formatErrorResponse(String error, String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));
        
        response.append(" ERROR EN LA OPERACIÓN\n");
        response.append(MINI_SEPARATOR).append("\n");
        response.append("Se produjo un error al procesar su solicitud:\n\n");
        response.append(" Detalle del error:\n");
        response.append("   ").append(error).append("\n\n");
        
        response.append(" AYUDA:\n");
        response.append("   • Verifique que todos los parámetros sean correctos\n");
        response.append("   • Asegúrese de usar el formato correcto del comando\n");
        response.append("   • Contacte al administrador si el problema persiste\n");

        return response.toString();
    }

    /**
     * Formatea respuesta para comandos no reconocidos
     */
    public String formatUnknownCommandResponse(String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader("COMANDO NO RECONOCIDO"));
        
        response.append(" COMANDO NO VÁLIDO\n");
        response.append(MINI_SEPARATOR).append("\n");
        response.append("El comando '").append(command).append("' no es reconocido por el sistema.\n\n");
        
        response.append(" COMANDOS DISPONIBLES:\n\n");
        response.append(" GESTIÓN DE PERSONAS:\n");
        response.append("   • LISPER[\"*\"] - Listar todas las personas\n");
        response.append("   • INSPER[\"ci\",\"nombre\",\"apellido\",\"cargo\",\"telefono\",\"celular\",\"email\"]\n");
        response.append("   • UPDPER[\"ci\",\"nombre\",\"apellido\",\"cargo\",\"telefono\",\"celular\",\"email\"]\n");
        response.append("   • DELPER[\"ci\"] - Eliminar persona\n");
        response.append("   • BUSPER[\"ci\"] - Buscar persona por CI\n\n");
        
        response.append(" GESTIÓN DE CLIENTES:\n");
        response.append("   • LISCLI[\"*\"] - Listar todos los clientes\n");
        response.append("   • INSCLI[\"nombre\",\"email\",\"telefono\",\"direccion\"]\n\n");
        
        response.append(" NOTA: Todos los parámetros deben ir entre comillas dobles.\n");

        return response.toString();
    }

    /**
     * Formatea respuesta para parámetros insuficientes
     */
    public String formatInsufficientParametersResponse(String command, String expectedFormat) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));
        
        response.append(" PARÁMETROS INSUFICIENTES\n");
        response.append(MINI_SEPARATOR).append("\n");
        response.append("El comando no tiene los parámetros necesarios.\n\n");
        
        response.append(" Formato correcto:\n");
        response.append("   ").append(expectedFormat).append("\n\n");
        
        response.append(" EJEMPLO:\n");
        if (command.startsWith("INSPER")) {
            response.append("   INSPER[\"12345678\",\"Juan\",\"Pérez\",\"Gerente\",\"2234567\",\"70123456\",\"juan@email.com\"]\n");
        } else if (command.startsWith("INSCLI")) {
            response.append("   INSCLI[\"Empresa ABC\",\"contacto@empresa.com\",\"2345678\",\"Av. Principal 123\"]\n");
        } else if (command.startsWith("BUSPER")) {
            response.append("   BUSPER[\"12345678\"]\n");
        } else if (command.startsWith("DELPER")) {
            response.append("   DELPER[\"12345678\"]\n");
        }

        return response.toString();
    }

    /**
     * Método genérico para formatear respuestas de listas
     */
    public String formatListResponse(List<?> items, String command, String itemName) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));

        if (items.isEmpty()) {
            response.append(" RESULTADO DEL LISTADO\n");
            response.append(MINI_SEPARATOR).append("\n");
            response.append(" No se encontraron ").append(itemName).append(" en la base de datos.\n");
        } else {
            response.append(" LISTADO DE ").append(itemName.toUpperCase()).append("\n");
            response.append(MINI_SEPARATOR).append("\n");
            response.append("Total de registros encontrados: ").append(items.size()).append("\n\n");
            
            int contador = 1;
            for (Object item : items) {
                response.append(" ").append(itemName.toUpperCase()).append(" #").append(contador).append("\n");
                response.append("   • Datos: ").append(item.toString()).append("\n\n");
                contador++;
            }
        }
        return response.toString();
    }

    /**
     * Método genérico para respuestas de operaciones exitosas
     */
    public String formatSuccessResponse(String operation, Object item, String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));
        
        response.append(" ").append(operation.toUpperCase()).append(" EXITOSA\n");
        response.append(MINI_SEPARATOR).append("\n");
        response.append("La operación se completó correctamente.\n\n");
        
        if (item != null) {
            response.append(" DATOS:\n");
            response.append("   ").append(item.toString()).append("\n");
        }

        return response.toString();
    }

    /**
     * Método genérico para respuestas de búsqueda no encontrada
     */
    public String formatNotFoundResponse(String itemType, String searchValue, String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));
        
        response.append(" NO ENCONTRADO\n");
        response.append(MINI_SEPARATOR).append("\n");
        response.append("No se encontró ").append(itemType).append(" con el valor: ").append(searchValue).append("\n");
        response.append("\n Verifique que los datos sean correctos.\n");

        return response.toString();
    }

    // Métodos específicos que delegan a los genéricos
    public String formatListTareasResponse(List<?> tareas, String command) {
        return formatListResponse(tareas, command, "tareas");
    }

    public String formatListUsuariosResponse(List<?> usuarios, String command) {
        return formatListResponse(usuarios, command, "usuarios");
    }

    public String formatListCotizacionesResponse(List<?> cotizaciones, String command) {
        return formatListResponse(cotizaciones, command, "cotizaciones");
    }

    public String formatInsertUsuarioSuccess(Object usuario, String command) {
        return formatSuccessResponse("INSERCIÓN", usuario, command);
    }

    public String formatUpdateUsuarioSuccess(Object usuario, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN", usuario, command);
    }

    public String formatUpdateClienteSuccess(Object cliente, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN", cliente, command);
    }

    public String formatDeleteClienteSuccess(String nombre, String command) {
        return formatSuccessResponse("ELIMINACIÓN", "Cliente: " + nombre, command);
    }

    public String formatSearchClienteNotFound(String nombre, String command) {
        return formatNotFoundResponse("cliente", nombre, command);
    }

    public String formatListClientesConProyectosResponse(List<?> clientes, String command) {
        return formatListResponse(clientes, command, "clientes con proyectos");
    }

    public String formatEstadisticasClientesResponse(String estadisticas, String command) {
        return formatSuccessResponse("ESTADÍSTICAS", estadisticas, command);
    }

    public String formatSearchClienteSuccess(Object cliente, String command) {
        return formatSuccessResponse("BÚSQUEDA", cliente, command);
    }

    // Métodos adicionales para completar la funcionalidad básica
    public String formatDeleteUsuarioSuccess(String nombre, String command) {
        return formatSuccessResponse("ELIMINACIÓN", "Usuario: " + nombre, command);
    }

    public String formatSearchUsuarioNotFound(String nombre, String command) {
        return formatNotFoundResponse("usuario", nombre, command);
    }

    public String formatListUsuariosPorRolResponse(List<?> usuarios, String command) {
        return formatListResponse(usuarios, command, "usuarios por rol");
    }

    public String formatInsertCotizacionSuccess(Object cotizacion, String command) {
        return formatSuccessResponse("INSERCIÓN", cotizacion, command);
    }

    public String formatUpdateCotizacionSuccess(Object cotizacion, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN", cotizacion, command);
    }

    public String formatSearchCotizacionNotFound(Long id, String command) {
        return formatNotFoundResponse("cotización", String.valueOf(id), command);
    }

    public String formatDeleteCotizacionSuccess(Long id, String command) {
        return formatSuccessResponse("ELIMINACIÓN", "Cotización ID: " + id, command);
    }

    public String formatListCotizacionesPorProyectoResponse(List<?> cotizaciones, String command) {
        return formatListResponse(cotizaciones, command, "cotizaciones por proyecto");
    }

    public String formatListCotizacionesPorUsuarioResponse(List<?> cotizaciones, String command) {
        return formatListResponse(cotizaciones, command, "cotizaciones por usuario");
    }

    public String formatListCotizacionesPorTipoMetroResponse(List<?> cotizaciones, String command) {
        return formatListResponse(cotizaciones, command, "cotizaciones por tipo metro");
    }

    public String formatTotalCotizacionesAprobadasResponse(java.math.BigDecimal total, String command) {
        return formatSuccessResponse("TOTAL CALCULADO", "Total: " + total, command);
    }

    public String formatCalcularTotalCotizacionResponse(java.math.BigDecimal total, String command) {
        return formatSuccessResponse("CÁLCULO", "Total: " + total, command);
    }

    public String formatAprobarCotizacionSuccess(Object cotizacion, String command) {
        return formatSuccessResponse("APROBACIÓN", cotizacion, command);
    }

    public String formatRechazarCotizacionSuccess(Object cotizacion, String command) {
        return formatSuccessResponse("RECHAZO", cotizacion, command);
    }

    // Métodos para diseños, planes de pago, pagos, materiales, etc.
    // Todos usan los métodos genéricos para mantener consistencia
    public String formatListDisenosResponse(List<?> disenos, String command) {
        return formatListResponse(disenos, command, "diseños");
    }

    public String formatSearchDisenoSuccess(Object diseno, String command) {
        return formatSuccessResponse("BÚSQUEDA", diseno, command);
    }

    public String formatSearchDisenoNotFound(Long id, String command) {
        return formatNotFoundResponse("diseño", String.valueOf(id), command);
    }

    public String formatInsertDisenoSuccess(Object diseno, String command) {
        return formatSuccessResponse("INSERCIÓN", diseno, command);
    }

    public String formatUpdateDisenoSuccess(Object diseno, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN", diseno, command);
    }

    public String formatDeleteDisenoSuccess(Long id, String command) {
        return formatSuccessResponse("ELIMINACIÓN", "Diseño ID: " + id, command);
    }

    public String formatListDisenosPorUsuarioResponse(List<?> disenos, String command) {
        return formatListResponse(disenos, command, "diseños por usuario");
    }

    public String formatListDisenosAprobadosResponse(List<?> disenos, String command) {
        return formatListResponse(disenos, command, "diseños aprobados");
    }

    public String formatAprobarDisenoSuccess(Object diseno, String command) {
        return formatSuccessResponse("APROBACIÓN", diseno, command);
    }

    public String formatRechazarDisenoSuccess(Object diseno, String command) {
        return formatSuccessResponse("RECHAZO", diseno, command);
    }

    public String formatListDisenosAprobadosPorUsuarioResponse(List<?> disenos, String command) {
        return formatListResponse(disenos, command, "diseños aprobados por usuario");
    }

    public String formatListDisenosPendientesPorUsuarioResponse(List<?> disenos, String command) {
        return formatListResponse(disenos, command, "diseños pendientes por usuario");
    }

    // Métodos para planes de pago
    public String formatListPlanesPagoResponse(List<?> planes, String command) {
        return formatListResponse(planes, command, "planes de pago");
    }

    public String formatSearchPlanPagoSuccess(Object plan, String command) {
        return formatSuccessResponse("BÚSQUEDA", plan, command);
    }

    public String formatSearchPlanPagoNotFound(Object id, String command) {
        return formatNotFoundResponse("plan de pago", String.valueOf(id), command);
    }

    public String formatInsertPlanPagoSuccess(Object plan, String command) {
        return formatSuccessResponse("INSERCIÓN", plan, command);
    }

    public String formatUpdatePlanPagoSuccess(Object plan, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN", plan, command);
    }

    public String formatListPlanesPagoPorEstadoResponse(List<?> planes, String command) {
        return formatListResponse(planes, command, "planes de pago por estado");
    }

    public String formatTotalDeudaPendienteResponse(java.math.BigDecimal total, String command) {
        return formatSuccessResponse("TOTAL CALCULADO", "Deuda pendiente: " + total, command);
    }

    public String formatTotalPagadoResponse(java.math.BigDecimal total, String command) {
        return formatSuccessResponse("TOTAL CALCULADO", "Total pagado: " + total, command);
    }

    public String formatUpdateDeudaTotalSuccess(Object plan, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN DE DEUDA", plan, command);
    }

    public String formatCalcularPorcentajePagoResponse(java.math.BigDecimal porcentaje, String command) {
        return formatSuccessResponse("CÁLCULO", "Porcentaje: " + porcentaje + "%", command);
    }

    public String formatCambiarEstadoSuccess(Object plan, String command) {
        return formatSuccessResponse("CAMBIO DE ESTADO", plan, command);
    }

    // Métodos para pagos
    public String formatListPagosResponse(List<?> pagos, String command) {
        return formatListResponse(pagos, command, "pagos");
    }

    public String formatSearchPagoSuccess(Object pago, String command) {
        return formatSuccessResponse("BÚSQUEDA", pago, command);
    }

    public String formatSearchPagoNotFound(Long id, String command) {
        return formatNotFoundResponse("pago", String.valueOf(id), command);
    }

    public String formatInsertPagoSuccess(Object pago, String command) {
        return formatSuccessResponse("INSERCIÓN", pago, command);
    }

    public String formatUpdatePagoSuccess(Object pago, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN", pago, command);
    }

    public String formatListPagosPorClienteResponse(List<?> pagos, String command) {
        return formatListResponse(pagos, command, "pagos por cliente");
    }

    public String formatTotalPagadoPorClienteResponse(java.math.BigDecimal total, String command) {
        return formatSuccessResponse("TOTAL CALCULADO", "Total pagado por cliente: " + total, command);
    }

    public String formatPlanPagoTienePagosResponse(boolean tienePagos, Long idPlan, String command) {
        String mensaje = tienePagos ? "SÍ tiene pagos" : "NO tiene pagos";
        return formatSuccessResponse("VERIFICACIÓN", "Plan " + idPlan + ": " + mensaje, command);
    }

    public String formatContarPagosPorPlanPagoResponse(long cantidad, Long idPlan, String command) {
        return formatSuccessResponse("CONTEO", "Plan " + idPlan + " tiene " + cantidad + " pagos", command);
    }

    // Métodos para materiales
    public String formatListMaterialesResponse(List<?> materiales, String command) {
        return formatListResponse(materiales, command, "materiales");
    }

    public String formatSearchMaterialSuccess(Object material, String command) {
        return formatSuccessResponse("BÚSQUEDA", material, command);
    }

    public String formatSearchMaterialNotFound(String nombre, String command) {
        return formatNotFoundResponse("material", nombre, command);
    }

    public String formatInsertMaterialSuccess(Object material, String command) {
        return formatSuccessResponse("INSERCIÓN", material, command);
    }

    public String formatUpdateMaterialSuccess(Object material, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN", material, command);
    }

    public String formatListMaterialesPorTipoResponse(List<?> materiales, String command) {
        return formatListResponse(materiales, command, "materiales por tipo");
    }

    public String formatUpdatePrecioMaterialSuccess(Object material, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN DE PRECIO", material, command);
    }

    public String formatUpdateStockMaterialSuccess(Object material, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN DE STOCK", material, command);
    }

    public String formatReducirStockMaterialSuccess(Object material, String command) {
        return formatSuccessResponse("REDUCCIÓN DE STOCK", material, command);
    }

    public String formatAumentarStockMaterialSuccess(Object material, String command) {
        return formatSuccessResponse("AUMENTO DE STOCK", material, command);
    }

    public String formatVerificarDisponibilidadMaterialResponse(boolean disponible, String nombre, Integer cantidad, String command) {
        String mensaje = disponible ? "DISPONIBLE" : "NO DISPONIBLE";
        return formatSuccessResponse("VERIFICACIÓN", nombre + " - Cantidad " + cantidad + ": " + mensaje, command);
    }

    // Métodos para material-proyecto
    public String formatListMaterialesProyectoResponse(List<?> materiales, String command) {
        return formatListResponse(materiales, command, "materiales-proyecto");
    }

    public String formatSearchMaterialProyectoSuccess(Object materialProyecto, String command) {
        return formatSuccessResponse("BÚSQUEDA", materialProyecto, command);
    }

    public String formatSearchMaterialProyectoNotFound(Long id, String command) {
        return formatNotFoundResponse("material-proyecto", String.valueOf(id), command);
    }

    public String formatInsertMaterialProyectoSuccess(Object materialProyecto, String command) {
        return formatSuccessResponse("INSERCIÓN", materialProyecto, command);
    }

    public String formatUpdateMaterialProyectoSuccess(Object materialProyecto, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN", materialProyecto, command);
    }

    public String formatListMaterialesPorProyectoResponse(List<?> materiales, String command) {
        return formatListResponse(materiales, command, "materiales por proyecto");
    }

    public String formatListProyectosPorMaterialResponse(List<?> proyectos, String command) {
        return formatListResponse(proyectos, command, "proyectos por material");
    }

    // Métodos específicos para tareas
    public String formatSearchTareaSuccess(Object tarea, String command) {
        return formatSuccessResponse("BÚSQUEDA", tarea, command);
    }

    public String formatSearchTareaNotFound(Long id, String command) {
        return formatNotFoundResponse("tarea", String.valueOf(id), command);
    }

    public String formatInsertTareaSuccess(Object tarea, String command) {
        return formatSuccessResponse("INSERCIÓN", tarea, command);
    }

    public String formatUpdateTareaSuccess(Object tarea, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN", tarea, command);
    }

    public String formatDeleteTareaSuccess(Long id, String command) {
        return formatSuccessResponse("ELIMINACIÓN", "Tarea ID: " + id, command);
    }

    public String formatListTareasPorCronogramaResponse(List<?> tareas, String command) {
        return formatListResponse(tareas, command, "tareas por cronograma");
    }

    public String formatListTareasPorUsuarioResponse(List<?> tareas, String command) {
        return formatListResponse(tareas, command, "tareas por usuario");
    }

    public String formatListTareasActivasResponse(List<?> tareas, String command) {
        return formatListResponse(tareas, command, "tareas activas");
    }

    public String formatListTareasCompletadasResponse(List<?> tareas, String command) {
        return formatListResponse(tareas, command, "tareas completadas");
    }

    public String formatListTareasPendientesResponse(List<?> tareas, String command) {
        return formatListResponse(tareas, command, "tareas pendientes");
    }

    // Métodos específicos para cronogramas
    public String formatListCronogramasResponse(List<?> cronogramas, String command) {
        return formatListResponse(cronogramas, command, "cronogramas");
    }

    public String formatSearchCronogramaSuccess(Object cronograma, String command) {
        return formatSuccessResponse("BÚSQUEDA", cronograma, command);
    }

    public String formatSearchCronogramaNotFound(Long id, String command) {
        return formatNotFoundResponse("cronograma", String.valueOf(id), command);
    }

    public String formatInsertCronogramaSuccess(Object cronograma, String command) {
        return formatSuccessResponse("INSERCIÓN", cronograma, command);
    }

    public String formatUpdateCronogramaSuccess(Object cronograma, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN", cronograma, command);
    }

    public String formatListCronogramasPorProyectoResponse(List<?> cronogramas, String command) {
        return formatListResponse(cronogramas, command, "cronogramas por proyecto");
    }

    public String formatListCronogramasPorUsuarioResponse(List<?> cronogramas, String command) {
        return formatListResponse(cronogramas, command, "cronogramas por usuario");
    }

    public String formatListCronogramasActivosResponse(List<?> cronogramas, String command) {
        return formatListResponse(cronogramas, command, "cronogramas activos");
    }

    public String formatListCronogramasCompletadosResponse(List<?> cronogramas, String command) {
        return formatListResponse(cronogramas, command, "cronogramas completados");
    }

    // Métodos específicos para proyectos
    public String formatListProyectosResponse(List<?> proyectos, String command) {
        return formatListResponse(proyectos, command, "proyectos");
    }

    public String formatSearchProyectoSuccess(Object proyecto, String command) {
        return formatSuccessResponse("BÚSQUEDA", proyecto, command);
    }

    public String formatSearchProyectoNotFound(String nombre, String command) {
        return formatNotFoundResponse("proyecto", nombre, command);
    }

    public String formatInsertProyectoSuccess(Object proyecto, String command) {
        return formatSuccessResponse("INSERCIÓN", proyecto, command);
    }

    public String formatUpdateProyectoSuccess(Object proyecto, String command) {
        return formatSuccessResponse("ACTUALIZACIÓN", proyecto, command);
    }

    public String formatListProyectosPorClienteResponse(List<?> proyectos, String command) {
        return formatListResponse(proyectos, command, "proyectos por cliente");
    }

    public String formatListProyectosPorUsuarioResponse(List<?> proyectos, String command) {
        return formatListResponse(proyectos, command, "proyectos por usuario");
    }

    public String formatListProyectosPorEstadoResponse(List<?> proyectos, String command) {
        return formatListResponse(proyectos, command, "proyectos por estado");
    }

    public String formatEstadisticasProyectosResponse(String estadisticas, String command) {
        return formatSuccessResponse("ESTADÍSTICAS", estadisticas, command);
    }

    // Métodos específicos para usuarios
    public String formatSearchUsuarioSuccess(Object usuario, String command) {
        return formatSuccessResponse("BÚSQUEDA", usuario, command);
    }

    // Métodos específicos para cotizaciones
    public String formatSearchCotizacionSuccess(Object cotizacion, String command) {
        return formatSuccessResponse("BÚSQUEDA", cotizacion, command);
    }
    
    // --- Nuevos métodos para PayPlan y Pays ---
    
    public String formatCreatePlanPagoSuccess(Object plan, String command) {
        return formatSuccessResponse("CREACIÓN PLAN DE PAGO", plan, command);
    }
    
    public String formatRecalcularPlanPagoSuccess(Object plan, String command) {
        return formatSuccessResponse("RECÁLCULO PLAN DE PAGO", plan, command);
    }
    
    public String formatListPagosPorPlanPagoResponse(List<?> pagos, String command) {
        return formatListResponse(pagos, command, "pagos por plan de pago");
    }
    
    public String formatPagarSuccess(Object pago, String command) {
        return formatSuccessResponse("PAGO PROCESADO", pago, command);
    }
}