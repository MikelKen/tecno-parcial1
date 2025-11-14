package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Producto;
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
    public List<Producto> listarTodosLosMateriales() {
        return materialRepository.findAll();
    }
    
    /**
     * Buscar material por ID
     */
    public Optional<Producto> buscarMaterialPorId(Long id) {
        return materialRepository.findById(id);
    }
    
    /**
     * Buscar material por nombre
     */
    public Optional<Producto> buscarMaterialPorNombre(String nombre) {
        return materialRepository.findByNombre(nombre);
    }
    
    /**
     * Insertar nuevo material
     */
    public Producto insertarMaterial(String nombre, String tipo, String unidadMedida, BigDecimal precioUnitario, Integer stock) {
        Producto producto = new Producto(nombre, tipo, unidadMedida, precioUnitario, stock);
        return materialRepository.save(producto);
    }
    
    /**
     * Actualizar material existente por ID
     */
    public Producto actualizarMaterial(Long id, String nombre, String tipo, String unidadMedida, BigDecimal precioUnitario, Integer stock) {
        Optional<Producto> productoExistente = materialRepository.findById(id);
        
        if (productoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró material con ID: " + id);
        }
        
        Producto producto = productoExistente.get();
        producto.setNombre(nombre);
        producto.setTipo(tipo);
        producto.setUnidadMedida(unidadMedida);
        producto.setPrecioUnitario(precioUnitario);
        producto.setStock(stock);
        
        return materialRepository.save(producto);
    }
    
    /**
     * Actualizar material existente por nombre
     */
    public Producto actualizarMaterialPorNombre(String nombre, String tipo, String unidadMedida, BigDecimal precioUnitario, Integer stock) {
        Optional<Producto> productoExistente = materialRepository.findByNombre(nombre);
        
        if (productoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró material con nombre: " + nombre);
        }
        
        Producto producto = productoExistente.get();
        producto.setTipo(tipo);
        producto.setUnidadMedida(unidadMedida);
        producto.setPrecioUnitario(precioUnitario);
        producto.setStock(stock);
        
        return materialRepository.save(producto);
    }
    
    /**
     * Eliminar material por ID
     */
    public boolean eliminarMaterial(Long id) {
        if (materialRepository.existsById(id)) {
            materialRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Eliminar material por nombre
     */
    public boolean eliminarMaterialPorNombre(String nombre) {
        Optional<Producto> productoOpt = materialRepository.findByNombre(nombre);
        if (productoOpt.isPresent()) {
            materialRepository.delete(productoOpt.get());
            return true;
        }
        return false;
    }
    
    /**
     * Buscar materiales por tipo
     */
    public List<Producto> buscarMaterialesPorTipo(String tipo) {
        return materialRepository.findByTipo(tipo);
    }
    
    /**
     * Buscar materiales por unidad de medida
     */
    public List<Producto> buscarMaterialesPorUnidadMedida(String unidadMedida) {
        return materialRepository.findByUnidadMedida(unidadMedida);
    }
    
    /**
     * Buscar materiales por rango de precio
     */
    public List<Producto> buscarMaterialesPorRangoPrecio(BigDecimal precioMin, BigDecimal precioMax) {
        return materialRepository.findByPrecioUnitarioBetween(precioMin, precioMax);
    }
    
    /**
     * Buscar materiales por nombre (contiene texto)
     */
    public List<Producto> buscarMaterialesPorNombre(String nombre) {
        return materialRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    /**
     * Buscar materiales con stock bajo
     */
    public List<Producto> buscarMaterialesStockBajo(Integer stockMinimo) {
        return materialRepository.findLowStockProducts(stockMinimo);
    }
    
    /**
     * Buscar materiales sin stock
     */
    public List<Producto> buscarMaterialesSinStock() {
        return materialRepository.findOutOfStockProducts();
    }
    
    /**
     * Buscar materiales disponibles
     */
    public List<Producto> buscarMaterialesDisponibles() {
        return materialRepository.findAvailableProducts();
    }
    
    /**
     * Buscar materiales caros (precio mayor a un valor)
     */
    public List<Producto> buscarMaterialesCaros(BigDecimal precio) {
        return materialRepository.findExpensiveProducts(precio);
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
    public long contarMaterialesPorTipo(String tipo) {
        return materialRepository.countByTipo(tipo);
    }
    
    /**
     * Buscar materiales disponibles por tipo
     */
    public List<Producto> buscarMaterialesDisponiblesPorTipo(String tipo) {
        return materialRepository.findAvailableProductsByType(tipo);
    }
    
    /**
     * Buscar materiales ordenados por precio ascendente
     */
    public List<Producto> buscarMaterialesOrdenadosPorPrecio() {
        return materialRepository.findProductsOrderedByPriceAsc();
    }
    
    /**
     * Buscar materiales ordenados por stock descendente
     */
    public List<Producto> buscarMaterialesOrdenadosPorStock() {
        return materialRepository.findProductsOrderedByStockDesc();
    }
    
    /**
     * Verificar si existe un material por ID
     */
    public boolean existeMaterial(Long id) {
        return materialRepository.existsById(id);
    }
    
    /**
     * Verificar si existe un material por nombre
     */
    public boolean existeMaterialPorNombre(String nombre) {
        return materialRepository.existsByNombre(nombre);
    }
    
    /**
     * Contar total de materiales
     */
    public long contarMateriales() {
        return materialRepository.count();
    }
    
    /**
     * Actualizar precio de material por ID
     */
    public Producto actualizarPrecioMaterial(Long id, BigDecimal nuevoPrecio) {
        Optional<Producto> productoOpt = materialRepository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setPrecioUnitario(nuevoPrecio);
            return materialRepository.save(producto);
        }
        throw new RuntimeException("No se encontró material con ID: " + id);
    }
    
    /**
     * Actualizar precio de material por nombre
     */
    public Producto actualizarPrecioMaterialPorNombre(String nombre, BigDecimal nuevoPrecio) {
        Optional<Producto> productoOpt = materialRepository.findByNombre(nombre);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setPrecioUnitario(nuevoPrecio);
            return materialRepository.save(producto);
        }
        throw new RuntimeException("No se encontró material con nombre: " + nombre);
    }
    
    /**
     * Actualizar stock de material por ID
     */
    public Producto actualizarStockMaterial(Long id, Integer nuevoStock) {
        Optional<Producto> productoOpt = materialRepository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setStock(nuevoStock);
            return materialRepository.save(producto);
        }
        throw new RuntimeException("No se encontró material con ID: " + id);
    }
    
    /**
     * Actualizar stock de material por nombre
     */
    public Producto actualizarStockMaterialPorNombre(String nombre, Integer nuevoStock) {
        Optional<Producto> productoOpt = materialRepository.findByNombre(nombre);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setStock(nuevoStock);
            return materialRepository.save(producto);
        }
        throw new RuntimeException("No se encontró material con nombre: " + nombre);
    }
    
    /**
     * Reducir stock de material por ID
     */
    public Producto reducirStockMaterial(Long id, Integer cantidad) {
        Optional<Producto> productoOpt = materialRepository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            int stockActual = producto.getStock() != null ? producto.getStock() : 0;
            int nuevoStock = Math.max(0, stockActual - cantidad);
            producto.setStock(nuevoStock);
            return materialRepository.save(producto);
        }
        throw new RuntimeException("No se encontró material con ID: " + id);
    }
    
    /**
     * Reducir stock de material por nombre
     */
    public Producto reducirStockMaterialPorNombre(String nombre, Integer cantidad) {
        Optional<Producto> productoOpt = materialRepository.findByNombre(nombre);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            int stockActual = producto.getStock() != null ? producto.getStock() : 0;
            int nuevoStock = Math.max(0, stockActual - cantidad);
            producto.setStock(nuevoStock);
            return materialRepository.save(producto);
        }
        throw new RuntimeException("No se encontró material con nombre: " + nombre);
    }
    
    /**
     * Aumentar stock de material por ID
     */
    public Producto aumentarStockMaterial(Long id, Integer cantidad) {
        Optional<Producto> productoOpt = materialRepository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            int stockActual = producto.getStock() != null ? producto.getStock() : 0;
            producto.setStock(stockActual + cantidad);
            return materialRepository.save(producto);
        }
        throw new RuntimeException("No se encontró material con ID: " + id);
    }
    
    /**
     * Aumentar stock de material por nombre
     */
    public Producto aumentarStockMaterialPorNombre(String nombre, Integer cantidad) {
        Optional<Producto> productoOpt = materialRepository.findByNombre(nombre);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            int stockActual = producto.getStock() != null ? producto.getStock() : 0;
            producto.setStock(stockActual + cantidad);
            return materialRepository.save(producto);
        }
        throw new RuntimeException("No se encontró material con nombre: " + nombre);
    }
    
    /**
     * Validar datos de material
     */
    public boolean validarDatosMaterial(String nombre, String tipo, String unidadMedida, BigDecimal precioUnitario, Integer stock) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            return false;
        }
        if (unidadMedida == null || unidadMedida.trim().isEmpty()) {
            return false;
        }
        if (precioUnitario == null || precioUnitario.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        if (stock == null || stock < 0) {
            return false;
        }
        return true;
    }
    
    /**
     * Verificar disponibilidad de material por ID
     */
    public boolean verificarDisponibilidadMaterial(Long id, Integer cantidadRequerida) {
        Optional<Producto> productoOpt = materialRepository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            int stockActual = producto.getStock() != null ? producto.getStock() : 0;
            return stockActual >= cantidadRequerida;
        }
        return false;
    }
    
    /**
     * Verificar disponibilidad de material por nombre
     */
    public boolean verificarDisponibilidadMaterialPorNombre(String nombre, Integer cantidadRequerida) {
        Optional<Producto> productoOpt = materialRepository.findByNombre(nombre);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            int stockActual = producto.getStock() != null ? producto.getStock() : 0;
            return stockActual >= cantidadRequerida;
        }
        return false;
    }
}

