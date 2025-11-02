package com.example.tecno_proyect.controller;

import com.example.tecno_proyect.service.CommandProcessor;
import com.example.tecno_proyect.service_email.ClientSMTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class EmailTestController {
    
    private final CommandProcessor commandProcessor;
    private final ClientSMTP smtpClient;
    
    @Autowired
    public EmailTestController(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
        this.smtpClient = new ClientSMTP();
    }
    
    /**
     * Endpoint para probar el comando INSPER manualmente
     * Simula el procesamiento de un correo con el comando INSPER
     */
    @PostMapping("/insper")
    public String testInsperCommand(@RequestParam String senderEmail) {
        
        // El comando del ejemplo 2
        String subject = "INSPER[\"4715292\",\"Juan Carlos\",\"Perez Seras\",\"Estudiante\",\"33554433\",\"71055123\",\"juanperez@uagrm.edu.bo\"]";
        
        // Procesar el comando
        String response = commandProcessor.processCommand(subject, senderEmail);
        
        // Enviar respuesta por email (comentado para testing)
        try {
            smtpClient.sendEmail(senderEmail, "Re: " + subject, response);
            return "Comando procesado: " + response + "\nRespuesta enviada a: " + senderEmail;
        } catch (Exception e) {
            return "Comando procesado: " + response + "\nError enviando email: " + e.getMessage();
        }
    }
    
    /**
     * Endpoint para probar cualquier comando personalizado
     */
    @PostMapping("/command")
    public String testCustomCommand(@RequestParam String subject, @RequestParam String senderEmail) {
        
        // Procesar el comando
        String response = commandProcessor.processCommand(subject, senderEmail);
        
        // Enviar respuesta por email
        try {
            smtpClient.sendEmail(senderEmail, "Re: " + subject, response);
            return "Comando procesado: " + response + "\nRespuesta enviada a: " + senderEmail;
        } catch (Exception e) {
            return "Comando procesado: " + response + "\nError enviando email: " + e.getMessage();
        }
    }
    
    /**
     * Endpoint para solo procesar el comando sin enviar email
     */
    @PostMapping("/command-only")
    public String testCommandOnly(@RequestParam String subject, @RequestParam String senderEmail) {
        return commandProcessor.processCommand(subject, senderEmail);
    }
    
    /**
     * Endpoint para listar todas las personas actuales en memoria
     */
    @GetMapping("/list-personas")
    public String listPersonas() {
        return commandProcessor.processCommand("LISPER[\"*\"]", "test@test.com");
    }
}