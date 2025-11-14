package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MaterialSupplierRepository extends JpaRepository<ProductoProveedor, Long> {
    
    // Buscar por proveedor
    List<ProductoProveedor> findByNombre(String nombre);
    
    // Buscar por producto
    List<ProductoProveedor> findByIdProducto(Long idProducto);
    
    // Buscar por proveedor y producto específico
    List<ProductoProveedor> findByNombreAndIdProducto(String nombre, Long idProducto);
    
    // Obtener el precio más bajo para un producto
    @Query("SELECT MIN(ms.precioUnitario) FROM ProductoProveedor ms WHERE ms.idProducto = :idProducto")
    BigDecimal getLowestPriceForProduct(@Param("idProducto") Long idProducto);
    
    // Obtener el precio más alto para un producto
    @Query("SELECT MAX(ms.precioUnitario) FROM ProductoProveedor ms WHERE ms.idProducto = :idProducto")
    BigDecimal getHighestPriceForProduct(@Param("idProducto") Long idProducto);
    
    // Buscar proveedores con mejor precio para un producto
    @Query("SELECT ms FROM ProductoProveedor ms WHERE ms.idProducto = :idProducto ORDER BY ms.precioUnitario ASC")
    List<ProductoProveedor> findBestPriceSuppliersForProduct(@Param("idProducto") Long idProducto);
    
    // Buscar relaciones ordenadas por precio (mejores ofertas primero)
    @Query("SELECT ms FROM ProductoProveedor ms ORDER BY ms.precioUnitario ASC")
    List<ProductoProveedor> findAllOrderedByPriceAsc();
    
    // Contar proveedores para un producto específico
    @Query("SELECT COUNT(DISTINCT ms.nombre) FROM ProductoProveedor ms WHERE ms.idProducto = :idProducto")
    long countSuppliersForProduct(@Param("idProducto") Long idProducto);
}