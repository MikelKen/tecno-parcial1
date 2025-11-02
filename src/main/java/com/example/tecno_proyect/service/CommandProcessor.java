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
    private PersonaService personaService;

    @Autowired
    private ClientService clientService;

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
                
                case "LISCLI":
                    return handleListClientes(parameters);
                
                case "INSCLI":
                    return handleInsertCliente(parameters);
                
                default:
                    return emailResponseService.formatUnknownCommandResponse(command);
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
}