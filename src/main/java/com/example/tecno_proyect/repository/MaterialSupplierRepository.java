package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.MaterialSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MaterialSupplierRepository extends JpaRepository<MaterialSupplier, Long> {
    
    // Buscar por proveedor
    List<MaterialSupplier> findByIdSupplier(String idSupplier);
    
    // Buscar por material
    List<MaterialSupplier> findByIdMaterial(String idMaterial);
    
    // Buscar por proveedor y material específico
    List<MaterialSupplier> findByIdSupplierAndIdMaterial(String idSupplier, String idMaterial);
    
    // Buscar por rango de precios unitarios
    @Query("SELECT ms FROM MaterialSupplier ms WHERE ms.unitPrice BETWEEN :minPrice AND :maxPrice")
    List<MaterialSupplier> findByUnitPriceBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    // Buscar por rango de totales
    @Query("SELECT ms FROM MaterialSupplier ms WHERE ms.total BETWEEN :minTotal AND :maxTotal")
    List<MaterialSupplier> findByTotalBetween(@Param("minTotal") BigDecimal minTotal, @Param("maxTotal") BigDecimal maxTotal);
    
    // Buscar por cantidad específica
    List<MaterialSupplier> findByQuantity(Integer quantity);
    
    // Buscar por cantidad mayor a un valor
    @Query("SELECT ms FROM MaterialSupplier ms WHERE ms.quantity > :minQuantity")
    List<MaterialSupplier> findByQuantityGreaterThan(@Param("minQuantity") Integer minQuantity);
    
    // Obtener el precio más bajo para un material
    @Query("SELECT MIN(ms.unitPrice) FROM MaterialSupplier ms WHERE ms.idMaterial = :idMaterial")
    BigDecimal getLowestPriceForMaterial(@Param("idMaterial") String idMaterial);
    
    // Obtener el precio más alto para un material
    @Query("SELECT MAX(ms.unitPrice) FROM MaterialSupplier ms WHERE ms.idMaterial = :idMaterial")
    BigDecimal getHighestPriceForMaterial(@Param("idMaterial") String idMaterial);
    
    // Buscar proveedores con mejor precio para un material
    @Query("SELECT ms FROM MaterialSupplier ms WHERE ms.idMaterial = :idMaterial ORDER BY ms.unitPrice ASC")
    List<MaterialSupplier> findBestPriceSuppliersForMaterial(@Param("idMaterial") String idMaterial);
    
    // Obtener total de compras a un proveedor
    @Query("SELECT SUM(ms.total) FROM MaterialSupplier ms WHERE ms.idSupplier = :idSupplier")
    BigDecimal getTotalPurchasesFromSupplier(@Param("idSupplier") String idSupplier);
    
    // Obtener total gastado en un material
    @Query("SELECT SUM(ms.total) FROM MaterialSupplier ms WHERE ms.idMaterial = :idMaterial")
    BigDecimal getTotalSpentOnMaterial(@Param("idMaterial") String idMaterial);
    
    // Buscar relaciones ordenadas por precio (mejores ofertas primero)
    @Query("SELECT ms FROM MaterialSupplier ms ORDER BY ms.unitPrice ASC")
    List<MaterialSupplier> findAllOrderedByPriceAsc();
    
    // Buscar relaciones con mayor volumen de compra
    @Query("SELECT ms FROM MaterialSupplier ms ORDER BY ms.quantity DESC")
    List<MaterialSupplier> findLargestPurchases();
    
    // Contar proveedores para un material específico
    @Query("SELECT COUNT(DISTINCT ms.idSupplier) FROM MaterialSupplier ms WHERE ms.idMaterial = :idMaterial")
    long countSuppliersForMaterial(@Param("idMaterial") String idMaterial);
}