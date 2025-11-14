package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Proveedor, String> {
    
    // Buscar proveedor por email
    Optional<Proveedor> findByEmail(String email);
    
    // Buscar proveedor por teléfono
    Optional<Proveedor> findByTelefono(String telefono);
    
    // Buscar proveedor por contacto
    Optional<Proveedor> findByContacto(String contacto);
    
    // Verificar si existe un proveedor con ese email
    boolean existsByEmail(String email);
    
    // Buscar proveedores por nombre (parcial, ignorando mayúsculas)
    @Query("SELECT s FROM Proveedor s WHERE LOWER(s.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Proveedor> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    // Buscar proveedores por contacto (parcial, ignorando mayúsculas)
    @Query("SELECT s FROM Proveedor s WHERE LOWER(s.contacto) LIKE LOWER(CONCAT('%', :contacto, '%'))")
    List<Proveedor> findByContactoContainingIgnoreCase(@Param("contacto") String contacto);
    
    // Buscar proveedores por dirección
    @Query("SELECT s FROM Proveedor s WHERE LOWER(s.direccion) LIKE LOWER(CONCAT('%', :direccion, '%'))")
    List<Proveedor> findByDireccionContainingIgnoreCase(@Param("direccion") String direccion);
    
    // Buscar proveedores que suministran productos
    @Query("SELECT DISTINCT s FROM Proveedor s JOIN s.productoProveedores ms")
    List<Proveedor> findProvidersWithProducts();
    
    // Buscar proveedores que suministran un producto específico
    @Query("SELECT DISTINCT s FROM Proveedor s JOIN s.productoProveedores ms WHERE ms.idProducto = :idProducto")
    List<Proveedor> findProvidersByProduct(@Param("idProducto") Long idProducto);
    
    // Buscar proveedores ordenados por nombre
    @Query("SELECT s FROM Proveedor s ORDER BY s.nombre ASC")
    List<Proveedor> findAllOrderByName();
}