package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Cliente, Long> {
    
    // Buscar cliente por nombre
    Optional<Cliente> findByNombre(String nombre);
    
    // Verificar si existe un cliente con ese nombre
    boolean existsByNombre(String nombre);
    
    // Buscar cliente por email
    Optional<Cliente> findByEmail(String email);
    
    // Buscar cliente por teléfono
    Optional<Cliente> findByTelefono(String telefono);
    
    // Verificar si existe un cliente con ese email
    boolean existsByEmail(String email);
    
    // Buscar clientes por nombre (parcial, ignorando mayúsculas)
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Cliente> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    // Buscar clientes por dirección
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.direccion) LIKE LOWER(CONCAT('%', :direccion, '%'))")
    List<Cliente> findByDireccionContainingIgnoreCase(@Param("direccion") String direccion);
    
    // Buscar clientes que tengan proyectos
    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.projects p")
    List<Cliente> findClientesWithProjects();
}