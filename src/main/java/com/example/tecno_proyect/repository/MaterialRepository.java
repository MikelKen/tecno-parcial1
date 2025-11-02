package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {
    
    // Buscar materiales por tipo
    List<Material> findByType(String type);
    
    // Buscar materiales por unidad de medida
    List<Material> findByUnitMeasure(String unitMeasure);
    
    // Buscar materiales por nombre (parcial, ignorando mayúsculas)
    @Query("SELECT m FROM Material m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Material> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // Buscar materiales por rango de precios
    @Query("SELECT m FROM Material m WHERE m.unitPrice BETWEEN :minPrice AND :maxPrice")
    List<Material> findByUnitPriceBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    // Buscar materiales con stock menor a un valor
    @Query("SELECT m FROM Material m WHERE m.stock < :minStock")
    List<Material> findLowStockMaterials(@Param("minStock") Integer minStock);
    
    // Buscar materiales sin stock
    @Query("SELECT m FROM Material m WHERE m.stock = 0 OR m.stock IS NULL")
    List<Material> findOutOfStockMaterials();
    
    // Buscar materiales con stock disponible
    @Query("SELECT m FROM Material m WHERE m.stock > 0")
    List<Material> findAvailableMaterials();
    
    // Buscar materiales más caros
    @Query("SELECT m FROM Material m WHERE m.unitPrice > :price")
    List<Material> findExpensiveMaterials(@Param("price") BigDecimal price);
    
    // Obtener valor total del inventario
    @Query("SELECT SUM(m.unitPrice * m.stock) FROM Material m WHERE m.stock > 0")
    BigDecimal getTotalInventoryValue();
    
    // Contar materiales por tipo
    @Query("SELECT COUNT(m) FROM Material m WHERE m.type = :type")
    long countByType(@Param("type") String type);
    
    // Buscar materiales por tipo y stock disponible
    @Query("SELECT m FROM Material m WHERE m.type = :type AND m.stock > 0")
    List<Material> findAvailableMaterialsByType(@Param("type") String type);
    
    // Buscar materiales ordenados por precio (ascendente)
    @Query("SELECT m FROM Material m ORDER BY m.unitPrice ASC")
    List<Material> findMaterialsOrderedByPriceAsc();
    
    // Buscar materiales ordenados por stock (descendente)
    @Query("SELECT m FROM Material m ORDER BY m.stock DESC")
    List<Material> findMaterialsOrderedByStockDesc();
}