package com.example.tecno_proyect;

import com.example.tecno_proyect.service.EmailMonitoringService;
import com.example.tecno_proyect.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TecnoProyectApplication implements CommandLineRunner {

	@Autowired
	private EmailMonitoringService emailMonitoringService;
	
	@Autowired
	private PersonaService personaService;

	public static void main(String[] args) {
		SpringApplication.run(TecnoProyectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("=== Sistema de Comandos por Correo Electrónico ===");
		System.out.println("Servidor de correo: mail.tecnoweb.org.bo");
		System.out.println("Email del sistema: grupo03sa@tecnoweb.org.bo");
		System.out.println("Base de datos: PostgreSQL - db_grupo03sa");
		System.out.println("Comandos disponibles:");
		System.out.println("- LISPER[\"*\"] - Listar todas las personas");
		System.out.println("- INSPER[\"ci\",\"nombre\",\"apellido\",\"cargo\",\"telefono\",\"celular\",\"email\"] - Insertar persona");
		System.out.println("- UPDPER[\"ci\",\"nombre\",\"apellido\",\"cargo\",\"telefono\",\"celular\",\"email\"] - Actualizar persona");
		System.out.println("- DELPER[\"ci\"] - Eliminar persona");
		System.out.println("- BUSPER[\"ci\"] - Buscar persona por CI");
		System.out.println("==========================================");
		
		// Insertar datos de prueba si es necesario
		personaService.insertarDatosDePruebaSiEsNecesario();
		
		// Mostrar estadísticas de la base de datos
		long totalPersonas = personaService.contarPersonas();
		System.out.println("Total de personas en la base de datos: " + totalPersonas);
		
		System.out.println("El sistema está ejecutándose y monitoreando correos...");
		
		// Procesar correos inmediatamente al iniciar
		emailMonitoringService.processEmailsManually();
	}
}
