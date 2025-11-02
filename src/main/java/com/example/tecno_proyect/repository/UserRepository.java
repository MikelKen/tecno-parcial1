package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    // Buscar usuario por email
    Optional<User> findByEmail(String email);
    
    // Buscar usuarios por rol
    List<User> findByRole(String role);
    
    // Buscar usuario por teléfono
    Optional<User> findByPhone(String phone);
    
    // Verificar si existe un usuario con ese email
    boolean existsByEmail(String email);
    
    // Buscar usuarios por nombre (parcial, ignorando mayúsculas)
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // Buscar usuarios por dirección
    @Query("SELECT u FROM User u WHERE LOWER(u.address) LIKE LOWER(CONCAT('%', :address, '%'))")
    List<User> findByAddressContainingIgnoreCase(@Param("address") String address);
    
    // Contar usuarios por rol
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    long countByRole(@Param("role") String role);
}