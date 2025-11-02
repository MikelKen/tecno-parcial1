package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {
    
    // Buscar persona por CI
    Optional<Persona> findByCi(String ci);
    
    // Verificar si existe una persona con ese CI
    boolean existsByCi(String ci);
    
    // Buscar personas por nombre (para búsquedas parciales)
    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Persona> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar personas por apellido
    @Query("SELECT p FROM Persona p WHERE LOWER(p.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))")
    List<Persona> findByApellidoContainingIgnoreCase(String apellido);
    
    // Buscar personas por cargo
    @Query("SELECT p FROM Persona p WHERE LOWER(p.cargo) LIKE LOWER(CONCAT('%', :cargo, '%'))")
    List<Persona> findByCargoContainingIgnoreCase(String cargo);
}