package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Persona;
import com.example.tecno_proyect.model.Client;
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
    public String formatListClientesResponse(List<Client> clientes, String command) {
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
            for (Client cliente : clientes) {
                response.append(" CLIENTE #").append(contador).append("\n");
                response.append("   • Nombre: ").append(cliente.getName()).append("\n");
                response.append("   • Email: ").append(cliente.getEmail()).append("\n");
                response.append("   • Teléfono: ").append(cliente.getPhone()).append("\n");
                response.append("   • Dirección: ").append(cliente.getAddress()).append("\n");
                response.append("\n");
                contador++;
            }
        }

        return response.toString();
    }

    /**
     * Formatea respuesta para inserción exitosa de cliente
     */
    public String formatInsertClienteSuccess(Client cliente, String command) {
        StringBuilder response = new StringBuilder();
        response.append(generateHeader(command));
        
        response.append(" INSERCIÓN EXITOSA\n");
        response.append(MINI_SEPARATOR).append("\n");
        response.append("El cliente ha sido registrado correctamente en el sistema.\n\n");
        
        response.append(" DATOS REGISTRADOS:\n");
        response.append("   • Nombre: ").append(cliente.getName()).append("\n");
        response.append("   • Email: ").append(cliente.getEmail()).append("\n");
        response.append("   • Teléfono: ").append(cliente.getPhone()).append("\n");
        response.append("   • Dirección: ").append(cliente.getAddress()).append("\n");

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
}