package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {
    
    // Buscar proveedor por email
    Optional<Supplier> findByEmail(String email);
    
    // Buscar proveedor por teléfono
    Optional<Supplier> findByPhone(String phone);
    
    // Buscar proveedor por contacto
    Optional<Supplier> findByContact(String contact);
    
    // Verificar si existe un proveedor con ese email
    boolean existsByEmail(String email);
    
    // Buscar proveedores por nombre (parcial, ignorando mayúsculas)
    @Query("SELECT s FROM Supplier s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Supplier> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // Buscar proveedores por contacto (parcial, ignorando mayúsculas)
    @Query("SELECT s FROM Supplier s WHERE LOWER(s.contact) LIKE LOWER(CONCAT('%', :contact, '%'))")
    List<Supplier> findByContactContainingIgnoreCase(@Param("contact") String contact);
    
    // Buscar proveedores por dirección
    @Query("SELECT s FROM Supplier s WHERE LOWER(s.address) LIKE LOWER(CONCAT('%', :address, '%'))")
    List<Supplier> findByAddressContainingIgnoreCase(@Param("address") String address);
    
    // Buscar proveedores que suministran materiales
    @Query("SELECT DISTINCT s FROM Supplier s JOIN s.materialSuppliers ms")
    List<Supplier> findSuppliersWithMaterials();
    
    // Buscar proveedores que suministran un material específico
    @Query("SELECT DISTINCT s FROM Supplier s JOIN s.materialSuppliers ms WHERE ms.idMaterial = :idMaterial")
    List<Supplier> findSuppliersByMaterial(@Param("idMaterial") String idMaterial);
    
    // Contar materiales suministrados por proveedor
    @Query("SELECT COUNT(ms) FROM MaterialSupplier ms WHERE ms.idSupplier = :supplierId")
    long countMaterialsBySupplier(@Param("supplierId") String supplierId);
    
    // Buscar proveedores ordenados por nombre
    @Query("SELECT s FROM Supplier s ORDER BY s.name ASC")
    List<Supplier> findAllOrderByName();
}