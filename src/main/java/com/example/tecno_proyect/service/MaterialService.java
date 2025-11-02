package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Material;
import com.example.tecno_proyect.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaterialService {
    
    @Autowired
    private MaterialRepository materialRepository;
    
    /**
     * Listar todos los materiales
     */
    public List<Material> listarTodosLosMateriales() {
        return materialRepository.findAll();
    }
    
    /**
     * Buscar material por nombre (ID)
     */
    public Optional<Material> buscarMaterialPorNombre(String name) {
        return materialRepository.findById(name);
    }
    
    /**
     * Insertar nuevo material
     */
    public Material insertarMaterial(String name, String type, String unitMeasure, BigDecimal unitPrice, Integer stock) {
        Material material = new Material(name, type, unitMeasure, unitPrice, stock);
        return materialRepository.save(material);
    }
    
    /**
     * Actualizar material existente
     */
    public Material actualizarMaterial(String name, String type, String unitMeasure, BigDecimal unitPrice, Integer stock) {
        Optional<Material> materialExistente = materialRepository.findById(name);
        
        if (materialExistente.isEmpty()) {
            throw new RuntimeException("No se encontró material con nombre: " + name);
        }
        
        Material material = materialExistente.get();
        material.setType(type);
        material.setUnitMeasure(unitMeasure);
        material.setUnitPrice(unitPrice);
        material.setStock(stock);
        
        return materialRepository.save(material);
    }
    
    /**
     * Eliminar material por nombre
     */
    public boolean eliminarMaterial(String name) {
        if (materialRepository.existsById(name)) {
            materialRepository.deleteById(name);
            return true;
        }
        return false;
    }
    
    /**
     * Buscar materiales por tipo
     */
    public List<Material> buscarMaterialesPorTipo(String type) {
        return materialRepository.findByType(type);
    }
    
    /**
     * Buscar materiales por unidad de medida
     */
    public List<Material> buscarMaterialesPorUnidadMedida(String unitMeasure) {
        return materialRepository.findByUnitMeasure(unitMeasure);
    }
    
    /**
     * Buscar materiales por rango de precio
     */
    public List<Material> buscarMaterialesPorRangoPrecio(BigDecimal minPrice, BigDecimal maxPrice) {
        return materialRepository.findByUnitPriceBetween(minPrice, maxPrice);
    }
    
    /**
     * Buscar materiales por nombre (contiene texto)
     */
    public List<Material> buscarMaterialesPorNombre(String nombre) {
        return materialRepository.findByNameContainingIgnoreCase(nombre);
    }
    
    /**
     * Buscar materiales con stock bajo
     */
    public List<Material> buscarMaterialesStockBajo(Integer minStock) {
        return materialRepository.findLowStockMaterials(minStock);
    }
    
    /**
     * Buscar materiales sin stock
     */
    public List<Material> buscarMaterialesSinStock() {
        return materialRepository.findOutOfStockMaterials();
    }
    
    /**
     * Buscar materiales disponibles
     */
    public List<Material> buscarMaterialesDisponibles() {
        return materialRepository.findAvailableMaterials();
    }
    
    /**
     * Buscar materiales caros (precio mayor a un valor)
     */
    public List<Material> buscarMaterialesCaros(BigDecimal precio) {
        return materialRepository.findExpensiveMaterials(precio);
    }
    
    /**
     * Obtener valor total del inventario
     */
    public BigDecimal obtenerValorTotalInventario() {
        BigDecimal valor = materialRepository.getTotalInventoryValue();
        return valor != null ? valor : BigDecimal.ZERO;
    }
    
    /**
     * Contar materiales por tipo
     */
    public long contarMaterialesPorTipo(String type) {
        return materialRepository.countByType(type);
    }
    
    /**
     * Buscar materiales disponibles por tipo
     */
    public List<Material> buscarMaterialesDisponiblesPorTipo(String type) {
        return materialRepository.findAvailableMaterialsByType(type);
    }
    
    /**
     * Buscar materiales ordenados por precio ascendente
     */
    public List<Material> buscarMaterialesOrdenadosPorPrecio() {
        return materialRepository.findMaterialsOrderedByPriceAsc();
    }
    
    /**
     * Buscar materiales ordenados por stock descendente
     */
    public List<Material> buscarMaterialesOrdenadosPorStock() {
        return materialRepository.findMaterialsOrderedByStockDesc();
    }
    
    /**
     * Verificar si existe un material
     */
    public boolean existeMaterial(String name) {
        return materialRepository.existsById(name);
    }
    
    /**
     * Contar total de materiales
     */
    public long contarMateriales() {
        return materialRepository.count();
    }
    
    /**
     * Actualizar precio de material
     */
    public Material actualizarPrecioMaterial(String name, BigDecimal nuevoPrecio) {
        Optional<Material> materialOpt = materialRepository.findById(name);
        if (materialOpt.isPresent()) {
            Material material = materialOpt.get();
            material.setUnitPrice(nuevoPrecio);
            return materialRepository.save(material);
        }
        throw new RuntimeException("No se encontró material con nombre: " + name);
    }
    
    /**
     * Actualizar stock de material
     */
    public Material actualizarStockMaterial(String name, Integer nuevoStock) {
        Optional<Material> materialOpt = materialRepository.findById(name);
        if (materialOpt.isPresent()) {
            Material material = materialOpt.get();
            material.setStock(nuevoStock);
            return materialRepository.save(material);
        }
        throw new RuntimeException("No se encontró material con nombre: " + name);
    }
    
    /**
     * Reducir stock de material
     */
    public Material reducirStockMaterial(String name, Integer cantidad) {
        Optional<Material> materialOpt = materialRepository.findById(name);
        if (materialOpt.isPresent()) {
            Material material = materialOpt.get();
            int stockActual = material.getStock() != null ? material.getStock() : 0;
            int nuevoStock = Math.max(0, stockActual - cantidad);
            material.setStock(nuevoStock);
            return materialRepository.save(material);
        }
        throw new RuntimeException("No se encontró material con nombre: " + name);
    }
    
    /**
     * Aumentar stock de material
     */
    public Material aumentarStockMaterial(String name, Integer cantidad) {
        Optional<Material> materialOpt = materialRepository.findById(name);
        if (materialOpt.isPresent()) {
            Material material = materialOpt.get();
            int stockActual = material.getStock() != null ? material.getStock() : 0;
            material.setStock(stockActual + cantidad);
            return materialRepository.save(material);
        }
        throw new RuntimeException("No se encontró material con nombre: " + name);
    }
    
    /**
     * Validar datos de material
     */
    public boolean validarDatosMaterial(String name, String type, String unitMeasure, BigDecimal unitPrice, Integer stock) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (type == null || type.trim().isEmpty()) {
            return false;
        }
        if (unitMeasure == null || unitMeasure.trim().isEmpty()) {
            return false;
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        if (stock == null || stock < 0) {
            return false;
        }
        return true;
    }
    
    /**
     * Verificar disponibilidad de material
     */
    public boolean verificarDisponibilidadMaterial(String name, Integer cantidadRequerida) {
        Optional<Material> materialOpt = materialRepository.findById(name);
        if (materialOpt.isPresent()) {
            Material material = materialOpt.get();
            int stockActual = material.getStock() != null ? material.getStock() : 0;
            return stockActual >= cantidadRequerida;
        }
        return false;
    }
}
