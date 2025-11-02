package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Persona;
import com.example.tecno_proyect.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonaService {
    
    @Autowired
    private PersonaRepository personaRepository;
    
    /**
     * Listar todas las personas
     */
    public List<Persona> listarTodasLasPersonas() {
        return personaRepository.findAll();
    }
    
    /**
     * Buscar persona por CI
     */
    public Optional<Persona> buscarPersonaPorCi(String ci) {
        return personaRepository.findByCi(ci);
    }
    
    /**
     * Insertar nueva persona
     */
    public Persona insertarPersona(String ci, String nombre, String apellido, String cargo, 
                                  String telefono, String celular, String email) {
        // Verificar si ya existe una persona con ese CI
        if (personaRepository.existsByCi(ci)) {
            throw new RuntimeException("Ya existe una persona con CI: " + ci);
        }
        
        Persona persona = new Persona(ci, nombre, apellido, cargo, telefono, celular, email);
        return personaRepository.save(persona);
    }
    
    /**
     * Actualizar persona existente
     */
    public Persona actualizarPersona(String ci, String nombre, String apellido, String cargo, 
                                   String telefono, String celular, String email) {
        Optional<Persona> personaExistente = personaRepository.findByCi(ci);
        
        if (personaExistente.isEmpty()) {
            throw new RuntimeException("No se encontró persona con CI: " + ci);
        }
        
        Persona persona = personaExistente.get();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setCargo(cargo);
        persona.setTelefono(telefono);
        persona.setCelular(celular);
        persona.setEmail(email);
        
        return personaRepository.save(persona);
    }
    
    /**
     * Eliminar persona por CI
     */
    public boolean eliminarPersona(String ci) {
        if (personaRepository.existsByCi(ci)) {
            personaRepository.deleteById(ci);
            return true;
        }
        return false;
    }
    
    /**
     * Verificar si existe una persona con el CI dado
     */
    public boolean existePersona(String ci) {
        return personaRepository.existsByCi(ci);
    }
    
    /**
     * Contar total de personas en la base de datos
     */
    public long contarPersonas() {
        return personaRepository.count();
    }
    
    /**
     * Insertar datos de prueba si la tabla está vacía
     */
    public void insertarDatosDePruebaSiEsNecesario() {
        if (personaRepository.count() == 0) {
            System.out.println("Insertando datos de prueba...");
            
            personaRepository.save(new Persona("12345678", "Juan Carlos", "Perez Lopez", 
                "Estudiante", "33554433", "71055123", "juan.perez@uagrm.edu.bo"));
            
            personaRepository.save(new Persona("87654321", "Maria Elena", "Garcia Torres", 
                "Docente", "33221100", "72334455", "maria.garcia@uagrm.edu.bo"));
            
            personaRepository.save(new Persona("11223344", "Carlos Alberto", "Rodriguez Vega", 
                "Administrativo", "33445566", "73556677", "carlos.rodriguez@uagrm.edu.bo"));
            
            System.out.println("Datos de prueba insertados correctamente.");
        }
    }
}