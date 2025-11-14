package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Producto, Long> {
    
    // Buscar producto por nombre (único)
    Optional<Producto> findByNombre(String nombre);
    
    // Verificar si existe por nombre
    boolean existsByNombre(String nombre);
    
    // Buscar productos por tipo
    List<Producto> findByTipo(String tipo);
    
    // Buscar productos por unidad de medida
    List<Producto> findByUnidadMedida(String unidadMedida);
    
    // Buscar productos por nombre (parcial, ignorando mayúsculas)
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Producto> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    // Buscar productos por rango de precios
    @Query("SELECT p FROM Producto p WHERE p.precioUnitario BETWEEN :minPrice AND :maxPrice")
    List<Producto> findByPrecioUnitarioBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    // Buscar productos con stock menor a un valor
    @Query("SELECT p FROM Producto p WHERE p.stock < :minStock")
    List<Producto> findLowStockProducts(@Param("minStock") Integer minStock);
    
    // Buscar productos sin stock
    @Query("SELECT p FROM Producto p WHERE p.stock = 0 OR p.stock IS NULL")
    List<Producto> findOutOfStockProducts();
    
    // Buscar productos con stock disponible
    @Query("SELECT p FROM Producto p WHERE p.stock > 0")
    List<Producto> findAvailableProducts();
    
    // Buscar productos más caros
    @Query("SELECT p FROM Producto p WHERE p.precioUnitario > :price")
    List<Producto> findExpensiveProducts(@Param("price") BigDecimal price);
    
    // Obtener valor total del inventario
    @Query("SELECT SUM(p.precioUnitario * p.stock) FROM Producto p WHERE p.stock > 0")
    BigDecimal getTotalInventoryValue();
    
    // Contar productos por tipo
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.tipo = :tipo")
    long countByTipo(@Param("tipo") String tipo);
    
    // Buscar productos por tipo y stock disponible
    @Query("SELECT p FROM Producto p WHERE p.tipo = :tipo AND p.stock > 0")
    List<Producto> findAvailableProductsByType(@Param("tipo") String tipo);
    
    // Buscar productos ordenados por precio (ascendente)
    @Query("SELECT p FROM Producto p ORDER BY p.precioUnitario ASC")
    List<Producto> findProductsOrderedByPriceAsc();
    
    // Buscar productos ordenados por stock (descendente)
    @Query("SELECT p FROM Producto p ORDER BY p.stock DESC")
    List<Producto> findProductsOrderedByStockDesc();
}