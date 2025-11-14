package com.example.tecno_proyect.repository;

import com.example.tecno_proyect.model.ProductoProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoProjectRepository extends JpaRepository<ProductoProject, Long> {
    
    // Buscar productos de un proyecto
    List<ProductoProject> findByIdProyecto(Long idProyecto);
    
    // Buscar proyectos que usan un producto específico
    List<ProductoProject> findByIdProducto(Long idProducto);
    
    // Buscar por proyecto y producto específico
    List<ProductoProject> findByIdProyectoAndIdProducto(Long idProyecto, Long idProducto);
    
    // Buscar productos con sobrante en proyectos
    @Query("SELECT mp FROM ProductoProject mp WHERE mp.sobrante > 0")
    List<ProductoProject> findProductsWithLeftOver();
    
    // Buscar productos usados completamente (sin sobrante)
    @Query("SELECT mp FROM ProductoProject mp WHERE mp.sobrante = 0 OR mp.sobrante IS NULL")
    List<ProductoProject> findFullyUsedProducts();
    
    // Buscar por cantidad específica
    List<ProductoProject> findByCantidad(Integer cantidad);
    
    // Buscar productos con cantidad mayor a un valor
    @Query("SELECT mp FROM ProductoProject mp WHERE mp.cantidad > :minQuantity")
    List<ProductoProject> findByQuantityGreaterThan(@Param("minQuantity") Integer minQuantity);
    
    // Obtener total de productos usados en un proyecto
    @Query("SELECT SUM(mp.cantidad) FROM ProductoProject mp WHERE mp.idProyecto = :idProyecto")
    Integer getTotalProductsUsedInProject(@Param("idProyecto") Long idProyecto);
    
    // Obtener total de sobrantes en un proyecto
    @Query("SELECT SUM(mp.sobrante) FROM ProductoProject mp WHERE mp.idProyecto = :idProyecto")
    Integer getTotalLeftOverInProject(@Param("idProyecto") Long idProyecto);
    
    // Contar proyectos que usan un producto específico
    @Query("SELECT COUNT(DISTINCT mp.idProyecto) FROM ProductoProject mp WHERE mp.idProducto = :idProducto")
    long countProjectsUsingProduct(@Param("idProducto") Long idProducto);
    
    // Buscar productos con sobrante específico
    List<ProductoProject> findBySobrante(Integer sobrante);
    
    // Buscar productos más utilizados (ordenados por cantidad descendente)
    @Query("SELECT mp FROM ProductoProject mp ORDER BY mp.cantidad DESC")
    List<ProductoProject> findMostUsedProducts();
    
    // Obtener eficiencia de uso de producto (porcentaje usado vs sobrante)
    @Query("SELECT mp FROM ProductoProject mp WHERE (mp.cantidad - mp.sobrante) * 100.0 / mp.cantidad >= :efficiency")
    List<ProductoProject> findProductsWithEfficiencyAbove(@Param("efficiency") Double efficiency);
}