package com.example.tecno_proyect.service_email;

import com.example.tecno_proyect.service.CommandProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ClientPOP {
    private String HOST = ""; 
    private final int PORT = 110;
    private String USER = "";  
    private String PASSWORD = ""; 

    private Socket connection;
    private BufferedReader input;
    private DataOutputStream output;
    private CommandProcessor commandProcessor;
    private ClientSMTP smtpClient;

    @Autowired
    public ClientPOP(CommandProcessor commandProcessor) {
        this.HOST = "mail.tecnoweb.org.bo";
        this.USER = "grupo03sa";
        this.PASSWORD = "grup003grup003*";
        this.commandProcessor = commandProcessor;
        this.smtpClient = new ClientSMTP();
    }

    public void connect() throws Exception {
        connection = new Socket(HOST, PORT);
        input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        output = new DataOutputStream(connection.getOutputStream());
        System.out.println("S: " + input.readLine());
    }

    public void close() throws IOException {
        if (this.connection != null) this.connection.close();
        if (this.input != null) this.input.close();
        if (this.output != null) this.output.close();
    }

    public void login() throws IOException {
        sendCommand("USER " + USER + "\r\n");
        sendCommand("PASS " + PASSWORD + "\r\n");
        System.out.println("Login exitoso");
    }

    public void checkAndProcessEmails() throws IOException {
        // Obtener número de mensajes
        String statResponse = sendCommand("STAT\r\n");
        int messageCount = extractMessageCount(statResponse);
        
        System.out.println("Número de mensajes: " + messageCount);
        
        // Procesar cada mensaje
        for (int i = 1; i <= messageCount; i++) {
            processMessage(i);
        }
    }

    private void processMessage(int messageNumber) throws IOException {
        // Obtener el mensaje completo
        String emailContent = sendCommand("RETR " + messageNumber + "\r\n");
        
        System.out.println("DEBUG ClientPOP: Contenido completo del email recibido (primeros 500 chars): ");
        System.out.println(emailContent.substring(0, Math.min(500, emailContent.length())));
        System.out.println("...");
        
        // Extraer información del correo
        EmailInfo emailInfo = parseEmail(emailContent);
        
        if (emailInfo != null) {
            System.out.println("DEBUG ClientPOP: From extraído: [" + emailInfo.from + "]");
            System.out.println("DEBUG ClientPOP: Subject extraído: [" + emailInfo.subject + "]");
            System.out.println("DEBUG ClientPOP: Longitud del subject: " + (emailInfo.subject != null ? emailInfo.subject.length() : 0));
        }
        
        if (emailInfo != null && emailInfo.subject != null && !emailInfo.subject.trim().isEmpty()) {
            System.out.println("Procesando comando: " + emailInfo.subject);
            
            // Procesar el comando
            System.out.println("DEBUG: Llamando al CommandProcessor...");
            String response;
            try {
                response = commandProcessor.processCommand(emailInfo.subject, emailInfo.from);
                System.out.println("DEBUG: Respuesta del CommandProcessor recibida: " + (response != null ? response.substring(0, Math.min(100, response.length())) + "..." : "null"));
            } catch (Exception e) {
                System.out.println("DEBUG: ERROR en CommandProcessor: " + e.getMessage());
                e.printStackTrace();
                response = "Error interno al procesar comando";
            }
            
            // Enviar respuesta por SMTP
            System.out.println("DEBUG: Iniciando envío de respuesta por SMTP...");
            System.out.println("DEBUG: Host SMTP = " + smtpClient.getServer() + ", Port = " + smtpClient.getPort());
            smtpClient.sendEmail(emailInfo.from, "Re: " + emailInfo.subject, response);
            
            System.out.println("Respuesta enviada a: " + emailInfo.from);
        }
        
        // Marcar mensaje para eliminación después de procesarlo
        sendCommand("DELE " + messageNumber + "\r\n");
    }

    private EmailInfo parseEmail(String emailContent) {
        EmailInfo info = new EmailInfo();
        
        // Extraer From
        Pattern fromPattern = Pattern.compile("From: (.+)", Pattern.CASE_INSENSITIVE);
        Matcher fromMatcher = fromPattern.matcher(emailContent);
        if (fromMatcher.find()) {
            info.from = extractEmailAddress(fromMatcher.group(1));
        }
        
        // Extraer Subject - manejo de líneas continuas (word-wrapped)
        // El Subject puede continuar en líneas siguientes si están indentadas con espacios/tabs
        Pattern subjectPattern = Pattern.compile("Subject: (.+?)(?=\\r?\\nFrom:|\\r?\\nTo:|\\r?\\nDate:|\\r?\\nContent-|\\r?\\n[^ \\t]|\\r?\\n\\r?\\n|$)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher subjectMatcher = subjectPattern.matcher(emailContent);
        if (subjectMatcher.find()) {
            String subject = subjectMatcher.group(1).trim();
            // Remover saltos de línea y espacios múltiples de word-wrapping
            subject = subject.replaceAll("\\r?\\n[ \\t]+", "").replaceAll("\\s+", " ");
            info.subject = subject.trim();
        }
        
        return info;
    }

    private String extractEmailAddress(String fromField) {
        // Extraer email de campos como "Name <email@domain.com>" o "email@domain.com"
        Pattern emailPattern = Pattern.compile("<(.+?)>|([\\w.-]+@[\\w.-]+\\.[a-zA-Z]+)");
        Matcher matcher = emailPattern.matcher(fromField);
        if (matcher.find()) {
            return matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
        }
        return fromField.trim();
    }

    private int extractMessageCount(String statResponse) {
        Pattern pattern = Pattern.compile("\\+OK (\\d+)");
        Matcher matcher = pattern.matcher(statResponse);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    private String sendCommand(String command) throws IOException {
        this.output.writeBytes(command);
        
        if (command.startsWith("RETR") || command.startsWith("LIST")) {
            return readMultilineResponse(input);
        }
        
        return this.input.readLine();
    }

    static protected String readMultilineResponse(BufferedReader text) throws IOException {
        StringBuilder lines = new StringBuilder();
        while (true) {
            String line = text.readLine();
            if (line == null) 
                throw new IOException("S: Server unawares closed the connection");
            if (line.equals(".")) 
                break;
            if (line.startsWith("."))
                line = line.substring(1);
            lines.append("\n").append(line);
        }
        return lines.toString();
    }

    // Clase interna para información del email
    private static class EmailInfo {
        String from;
        String subject;
    }

    // Método para ejecutar el procesamiento completo
    public void processEmails() {
        try {
            connect();
            login();
            checkAndProcessEmails();
            sendCommand("QUIT\r\n");
        } catch (Exception e) {
            System.err.println("Error procesando emails: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (IOException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }
}

