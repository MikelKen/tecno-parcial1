package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.ProductoProject;
import com.example.tecno_proyect.model.Producto;
import com.example.tecno_proyect.repository.ProductoProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaterialProjectService {
    
    @Autowired
    private ProductoProjectRepository productoProjectRepository;
    
    @Autowired
    private MaterialService materialService;
    
    /**
     * Listar todos los productos por proyecto
     */
    public List<ProductoProject> listarTodosMaterialesProyecto() {
        return productoProjectRepository.findAll();
    }
    
    /**
     * Buscar producto-proyecto por ID
     */
    public Optional<ProductoProject> buscarMaterialProyectoPorId(Long id) {
        return productoProjectRepository.findById(id);
    }
    
    /**
     * Insertar nueva relación producto-proyecto con control de stock
     */
    public ProductoProject insertarMaterialProyecto(Integer cantidad, Integer sobrante, Long idProyecto, Long idProducto) {
        // Validar datos básicos
        if (!validarDatosMaterialProyecto(cantidad, sobrante, idProyecto, idProducto)) {
            throw new RuntimeException("Datos de producto-proyecto inválidos");
        }
        
        // Verificar que el producto existe y tiene suficiente stock
        Optional<Producto> productoOpt = materialService.buscarMaterialPorId(idProducto);
        if (productoOpt.isEmpty()) {
            throw new RuntimeException("Producto con ID " + idProducto + " no existe");
        }
        
        Producto producto = productoOpt.get();
        if (producto.getStock() == null || producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + 
                (producto.getStock() != null ? producto.getStock() : 0) + 
                ", Requerido: " + cantidad);
        }
        
        // Crear la relación producto-proyecto
        ProductoProject productoProject = new ProductoProject(cantidad, sobrante, idProyecto, idProducto);
        ProductoProject savedMaterialProject = productoProjectRepository.save(productoProject);
        
        // Descontar del stock del producto
        materialService.reducirStockMaterial(idProducto, cantidad);
        
        System.out.println("Producto asignado a proyecto. Producto ID: " + idProducto + 
                          ", Cantidad: " + cantidad + ", Stock restante: " + 
                          (producto.getStock() - cantidad));
        
        return savedMaterialProject;
    }
    
    /**
     * Actualizar relación producto-proyecto existente con control de stock
     */
    public ProductoProject actualizarMaterialProyecto(Long id, Integer cantidad, Integer sobrante, Long idProyecto, Long idProducto) {
        Optional<ProductoProject> materialProyectoExistente = productoProjectRepository.findById(id);
        
        if (materialProyectoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró producto-proyecto con ID: " + id);
        }
        
        ProductoProject materialProjectAntiguo = materialProyectoExistente.get();
        Integer cantidadAntigua = materialProjectAntiguo.getCantidad();
        Long materialIdAntiguo = materialProjectAntiguo.getIdProducto();
        
        // Validar datos básicos
        if (!validarDatosMaterialProyecto(cantidad, sobrante, idProyecto, idProducto)) {
            throw new RuntimeException("Datos de producto-proyecto inválidos");
        }
        
        // Si cambió el producto o la cantidad, necesitamos ajustar los stocks
        if (!materialIdAntiguo.equals(idProducto) || !cantidadAntigua.equals(cantidad)) {
            
            // Devolver stock del producto anterior
            if (cantidadAntigua != null && cantidadAntigua > 0) {
                materialService.aumentarStockMaterial(materialIdAntiguo, cantidadAntigua);
                System.out.println("Stock devuelto al producto " + materialIdAntiguo + ": " + cantidadAntigua);
            }
            
            // Verificar stock del nuevo producto
            Optional<Producto> productoOpt = materialService.buscarMaterialPorId(idProducto);
            if (productoOpt.isEmpty()) {
                throw new RuntimeException("Producto con ID " + idProducto + " no existe");
            }
            
            Producto producto = productoOpt.get();
            if (producto.getStock() == null || producto.getStock() < cantidad) {
                // Revertir la devolución si no hay suficiente stock en el nuevo producto
                if (cantidadAntigua != null && cantidadAntigua > 0) {
                    materialService.reducirStockMaterial(materialIdAntiguo, cantidadAntigua);
                }
                throw new RuntimeException("Stock insuficiente en nuevo producto. Disponible: " + 
                    (producto.getStock() != null ? producto.getStock() : 0) + 
                    ", Requerido: " + cantidad);
            }
            
            // Descontar del stock del nuevo producto
            materialService.reducirStockMaterial(idProducto, cantidad);
            System.out.println("Stock descontado del producto " + idProducto + ": " + cantidad);
        }
        
        // Actualizar la relación
        materialProjectAntiguo.setCantidad(cantidad);
        materialProjectAntiguo.setSobrante(sobrante);
        materialProjectAntiguo.setIdProyecto(idProyecto);
        materialProjectAntiguo.setIdProducto(idProducto);
        
        return productoProjectRepository.save(materialProjectAntiguo);
    }
    
    /**
     * Eliminar relación producto-proyecto por ID con devolución de stock
     */
    public boolean eliminarMaterialProyecto(Long id) {
        Optional<ProductoProject> materialProjectOpt = productoProjectRepository.findById(id);
        
        if (materialProjectOpt.isPresent()) {
            ProductoProject productoProject = materialProjectOpt.get();
            
            // Devolver el stock al producto antes de eliminar
            if (productoProject.getCantidad() != null && productoProject.getCantidad() > 0) {
                materialService.aumentarStockMaterial(productoProject.getIdProducto(), productoProject.getCantidad());
                System.out.println("Stock devuelto al eliminar producto-proyecto. Producto ID: " + 
                                  productoProject.getIdProducto() + ", Cantidad: " + productoProject.getCantidad());
            }
            
            productoProjectRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Buscar productos por proyecto
     */
    public List<ProductoProject> buscarMaterialesPorProyecto(Long idProyecto) {
        return productoProjectRepository.findByIdProyecto(idProyecto);
    }
    
    /**
     * Buscar proyectos por producto
     */
    public List<ProductoProject> buscarProyectosPorMaterial(Long idProducto) {
        return productoProjectRepository.findByIdProducto(idProducto);
    }
    
    /**
     * Buscar por proyecto y producto específicos
     */
    public List<ProductoProject> buscarPorProyectoYMaterial(Long idProyecto, Long idProducto) {
        return productoProjectRepository.findByIdProyectoAndIdProducto(idProyecto, idProducto);
    }
    
    /**
     * Buscar productos con sobrante
     */
    public List<ProductoProject> buscarMaterialesConSobrante() {
        return productoProjectRepository.findProductsWithLeftOver();
    }
    
    /**
     * Buscar productos usados completamente
     */
    public List<ProductoProject> buscarMaterialesUsadosCompletamente() {
        return productoProjectRepository.findFullyUsedProducts();
    }
    
    /**
     * Buscar por cantidad específica
     */
    public List<ProductoProject> buscarPorCantidad(Integer cantidad) {
        return productoProjectRepository.findByCantidad(cantidad);
    }
    
    /**
     * Buscar por cantidad mayor a un valor
     */
    public List<ProductoProject> buscarPorCantidadMayor(Integer minCantidad) {
        return productoProjectRepository.findByQuantityGreaterThan(minCantidad);
    }
    
    /**
     * Buscar por sobrante específico
     */
    public List<ProductoProject> buscarPorSobrante(Integer sobrante) {
        return productoProjectRepository.findBySobrante(sobrante);
    }
    
    /**
     * Buscar productos más utilizados
     */
    public List<ProductoProject> buscarMaterialesMasUtilizados() {
        return productoProjectRepository.findMostUsedProducts();
    }
    
    /**
     * Buscar productos con eficiencia alta
     */
    public List<ProductoProject> buscarMaterialesConEficienciaAlta(Double efficiency) {
        return productoProjectRepository.findProductsWithEfficiencyAbove(efficiency);
    }
    
    /**
     * Obtener total de productos usados en un proyecto
     */
    public Integer obtenerTotalMaterialesUsadosEnProyecto(Long idProyecto) {
        Integer total = productoProjectRepository.getTotalProductsUsedInProject(idProyecto);
        return total != null ? total : 0;
    }
    
    /**
     * Obtener total de sobrantes en un proyecto
     */
    public Integer obtenerTotalSobrantesEnProyecto(Long idProyecto) {
        Integer total = productoProjectRepository.getTotalLeftOverInProject(idProyecto);
        return total != null ? total : 0;
    }
    
    /**
     * Contar proyectos que usan un producto específico
     */
    public long contarProyectosQueUsanMaterial(Long idProducto) {
        return productoProjectRepository.countProjectsUsingProduct(idProducto);
    }
    
    /**
     * Verificar si existe una relación
     */
    public boolean existeMaterialProyecto(Long id) {
        return productoProjectRepository.existsById(id);
    }
    
    /**
     * Contar total de relaciones
     */
    public long contarMaterialesProyecto() {
        return productoProjectRepository.count();
    }
    
    /**
     * Actualizar cantidad de producto en proyecto
     */
    public ProductoProject actualizarCantidad(Long id, Integer nuevaCantidad) {
        Optional<ProductoProject> materialProyectoOpt = productoProjectRepository.findById(id);
        if (materialProyectoOpt.isPresent()) {
            ProductoProject productoProject = materialProyectoOpt.get();
            productoProject.setCantidad(nuevaCantidad);
            return productoProjectRepository.save(productoProject);
        }
        throw new RuntimeException("No se encontró producto-proyecto con ID: " + id);
    }
    
    /**
     * Actualizar sobrante de producto
     */
    public ProductoProject actualizarSobrante(Long id, Integer nuevoSobrante) {
        Optional<ProductoProject> materialProyectoOpt = productoProjectRepository.findById(id);
        if (materialProyectoOpt.isPresent()) {
            ProductoProject productoProject = materialProyectoOpt.get();
            productoProject.setSobrante(nuevoSobrante);
            return productoProjectRepository.save(productoProject);
        }
        throw new RuntimeException("No se encontró producto-proyecto con ID: " + id);
    }
    
    /**
     * Calcular eficiencia de uso de producto
     */
    public Double calcularEficienciaUso(Long id) {
        Optional<ProductoProject> materialProyectoOpt = productoProjectRepository.findById(id);
        if (materialProyectoOpt.isPresent()) {
            ProductoProject productoProject = materialProyectoOpt.get();
            Integer cantidad = productoProject.getCantidad();
            Integer sobrante = productoProject.getSobrante() != null ? productoProject.getSobrante() : 0;
            
            if (cantidad == null || cantidad == 0) {
                return 0.0;
            }
            
            return ((double) (cantidad - sobrante) / cantidad) * 100.0;
        }
        return 0.0;
    }
    
    /**
     * Validar datos de producto-proyecto
     */
    public boolean validarDatosMaterialProyecto(Integer cantidad, Integer sobrante, Long idProyecto, Long idProducto) {
        if (cantidad == null || cantidad <= 0) {
            return false;
        }
        if (sobrante == null || sobrante < 0) {
            return false;
        }
        if (sobrante > cantidad) {
            return false; // El sobrante no puede ser mayor que la cantidad
        }
        if (idProyecto == null) {
            return false;
        }
        if (idProducto == null) {
            return false;
        }
        return true;
    }
    
    /**
     * Verificar si un proyecto tiene productos asignados
     */
    public boolean proyectoTieneMateriales(Long idProyecto) {
        return !buscarMaterialesPorProyecto(idProyecto).isEmpty();
    }
    
    /**
     * Verificar si un producto está asignado a proyectos
     */
    public boolean materialEstaAsignado(Long idProducto) {
        return !buscarProyectosPorMaterial(idProducto).isEmpty();
    }
    
    /**
     * Buscar producto con mayor cantidad por proyecto
     */
    public Optional<ProductoProject> buscarMaterialMayorCantidadPorProyecto(Long idProyecto) {
        List<ProductoProject> materiales = buscarMaterialesPorProyecto(idProyecto);
        return materiales.stream()
                .max((m1, m2) -> Integer.compare(m1.getCantidad(), m2.getCantidad()));
    }
    
    /**
     * Buscar producto con menor eficiencia por proyecto
     */
    public Optional<ProductoProject> buscarMaterialMenorEficienciaPorProyecto(Long idProyecto) {
        List<ProductoProject> materiales = buscarMaterialesPorProyecto(idProyecto);
        return materiales.stream()
                .filter(mp -> mp.getCantidad() != null && mp.getCantidad() > 0)
                .min((m1, m2) -> {
                    Double eff1 = calcularEficienciaUso(m1.getId());
                    Double eff2 = calcularEficienciaUso(m2.getId());
                    return Double.compare(eff1, eff2);
                });
    }
    
    /**
     * Obtener resumen de productos por proyecto
     */
    public String obtenerResumenMaterialesPorProyecto(Long idProyecto) {
        List<ProductoProject> materiales = buscarMaterialesPorProyecto(idProyecto);
        Integer totalUsado = obtenerTotalMaterialesUsadosEnProyecto(idProyecto);
        Integer totalSobrante = obtenerTotalSobrantesEnProyecto(idProyecto);
        
        return String.format("Proyecto %s: %d productos diferentes, %d unidades usadas, %d sobrantes", 
                           idProyecto, materiales.size(), totalUsado, totalSobrante);
    }
    
    /**
     * Eliminar todos los productos de un proyecto
     */
    public void eliminarTodosMaterialesDeProyecto(Long idProyecto) {
        List<ProductoProject> materiales = buscarMaterialesPorProyecto(idProyecto);
        productoProjectRepository.deleteAll(materiales);
    }
    
    /**
     * Verificar disponibilidad de producto para un proyecto
     */
    public boolean verificarDisponibilidadMaterial(Long idProducto, Integer cantidadRequerida) {
        if (cantidadRequerida == null || cantidadRequerida <= 0) {
            return false;
        }
        
        Optional<Producto> productoOpt = materialService.buscarMaterialPorId(idProducto);
        if (productoOpt.isEmpty()) {
            return false;
        }
        
        Producto producto = productoOpt.get();
        Integer stockDisponible = producto.getStock() != null ? producto.getStock() : 0;
        
        return stockDisponible >= cantidadRequerida;
    }
    
    /**
     * Devolver producto sobrante al stock
     */
    public ProductoProject devolverMaterialSobrante(Long id, Integer cantidadDevolver) {
        Optional<ProductoProject> materialProjectOpt = productoProjectRepository.findById(id);
        
        if (materialProjectOpt.isEmpty()) {
            throw new RuntimeException("No se encontró producto-proyecto con ID: " + id);
        }
        
        ProductoProject productoProject = materialProjectOpt.get();
        Integer sobrante = productoProject.getSobrante() != null ? productoProject.getSobrante() : 0;
        
        if (cantidadDevolver == null || cantidadDevolver <= 0) {
            throw new RuntimeException("Cantidad a devolver debe ser mayor a 0");
        }
        
        if (cantidadDevolver > sobrante) {
            throw new RuntimeException("No se puede devolver más de lo que sobra. Sobrante: " + sobrante);
        }
        
        // Devolver al stock
        materialService.aumentarStockMaterial(productoProject.getIdProducto(), cantidadDevolver);
        
        // Actualizar el sobrante
        productoProject.setSobrante(sobrante - cantidadDevolver);
        
        // También ajustar la cantidad total usada
        Integer cantidadTotal = productoProject.getCantidad();
        productoProject.setCantidad(cantidadTotal - cantidadDevolver);
        
        ProductoProject saved = productoProjectRepository.save(productoProject);
        
        System.out.println("Producto sobrante devuelto. Producto ID: " + productoProject.getIdProducto() + 
                          ", Cantidad devuelta: " + cantidadDevolver + 
                          ", Nuevo sobrante: " + (sobrante - cantidadDevolver));
        
        return saved;
    }
    
    /**
     * Devolver todo el producto sobrante al stock
     */
    public ProductoProject devolverTodoSobrante(Long id) {
        Optional<ProductoProject> materialProjectOpt = productoProjectRepository.findById(id);
        
        if (materialProjectOpt.isEmpty()) {
            throw new RuntimeException("No se encontró producto-proyecto con ID: " + id);
        }
        
        ProductoProject productoProject = materialProjectOpt.get();
        Integer sobrante = productoProject.getSobrante() != null ? productoProject.getSobrante() : 0;
        
        if (sobrante <= 0) {
            throw new RuntimeException("No hay producto sobrante para devolver");
        }
        
        return devolverMaterialSobrante(id, sobrante);
    }
    
    /**
     * Obtener reporte de stock de productos usados en un proyecto
     */
    public String obtenerReporteStockProyecto(Long idProyecto) {
        List<ProductoProject> materiales = buscarMaterialesPorProyecto(idProyecto);
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE STOCK - PROYECTO ").append(idProyecto).append(" ===\n");
        
        for (ProductoProject mp : materiales) {
            Optional<Producto> productoOpt = 
                materialService.buscarMaterialPorId(mp.getIdProducto());
            
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                reporte.append("Producto: ").append(producto.getNombre())
                       .append(" (ID: ").append(producto.getId()).append(")\n")
                       .append("  - Stock actual: ").append(producto.getStock()).append(" ")
                       .append(producto.getUnidadMedida()).append("\n")
                       .append("  - Usado en proyecto: ").append(mp.getCantidad()).append("\n")
                       .append("  - Sobrante: ").append(mp.getSobrante()).append("\n")
                       .append("  - Precio unitario: $").append(producto.getPrecioUnitario()).append("\n\n");
            }
        }
        
        return reporte.toString();
    }
    
    /**
     * Verificar y ajustar sobrantes basado en uso real
     */
    public ProductoProject ajustarSobrantePorUsoReal(Long id, Integer cantidadRealmentUsada) {
        Optional<ProductoProject> materialProjectOpt = productoProjectRepository.findById(id);
        
        if (materialProjectOpt.isEmpty()) {
            throw new RuntimeException("No se encontró producto-proyecto con ID: " + id);
        }
        
        ProductoProject productoProject = materialProjectOpt.get();
        Integer cantidadAsignada = productoProject.getCantidad();
        
        if (cantidadRealmentUsada == null || cantidadRealmentUsada < 0) {
            throw new RuntimeException("Cantidad realmente usada debe ser >= 0");
        }
        
        if (cantidadRealmentUsada > cantidadAsignada) {
            throw new RuntimeException("Cantidad usada no puede ser mayor a la asignada. Asignada: " + cantidadAsignada);
        }
        
        Integer nuevoSobrante = cantidadAsignada - cantidadRealmentUsada;
        Integer sobranteAnterior = productoProject.getSobrante() != null ? productoProject.getSobrante() : 0;
        
        // Si hay diferencia en el sobrante, ajustar el stock
        Integer diferenciaSobrante = nuevoSobrante - sobranteAnterior;
        if (diferenciaSobrante != 0) {
            if (diferenciaSobrante > 0) {
                // Hay más sobrante del esperado, devolver al stock
                materialService.aumentarStockMaterial(productoProject.getIdProducto(), diferenciaSobrante);
                System.out.println("Stock ajustado (devuelto): " + diferenciaSobrante);
            } else {
                // Hay menos sobrante, descontar del stock
                materialService.reducirStockMaterial(productoProject.getIdProducto(), Math.abs(diferenciaSobrante));
                System.out.println("Stock ajustado (descontado): " + Math.abs(diferenciaSobrante));
            }
        }
        
        productoProject.setSobrante(nuevoSobrante);
        
        return productoProjectRepository.save(productoProject);
    }
}
