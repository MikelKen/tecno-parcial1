package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    
    // Buscar usuario por nombre (único)
    Optional<Usuario> findByNombre(String nombre);
    
    // Verificar si existe por nombre
    boolean existsByNombre(String nombre);
    
    // Buscar usuario por email
    Optional<Usuario> findByEmail(String email);
    
    // Buscar usuarios por rol
    List<Usuario> findByRol(String rol);
    
    // Buscar usuario por teléfono
    Optional<Usuario> findByTelefono(String telefono);
    
    // Verificar si existe un usuario con ese email
    boolean existsByEmail(String email);
    
    // Buscar usuarios por nombre (parcial, ignorando mayúsculas)
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Usuario> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    // Buscar usuarios por dirección
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.direccion) LIKE LOWER(CONCAT('%', :direccion, '%'))")
    List<Usuario> findByDireccionContainingIgnoreCase(@Param("direccion") String direccion);
    
    // Contar usuarios por rol
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol = :rol")
    long countByRol(@Param("rol") String rol);
}