package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    
    // Buscar cliente por email
    Optional<Client> findByEmail(String email);
    
    // Buscar cliente por teléfono
    Optional<Client> findByPhone(String phone);
    
    // Verificar si existe un cliente con ese email
    boolean existsByEmail(String email);
    
    // Buscar clientes por nombre (parcial, ignorando mayúsculas)
    @Query("SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Client> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // Buscar clientes por dirección
    @Query("SELECT c FROM Client c WHERE LOWER(c.address) LIKE LOWER(CONCAT('%', :address, '%'))")
    List<Client> findByAddressContainingIgnoreCase(@Param("address") String address);
    
    // Buscar clientes que tengan proyectos
    @Query("SELECT DISTINCT c FROM Client c JOIN c.projects p")
    List<Client> findClientsWithProjects();
}