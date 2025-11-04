package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.MaterialProject;
import com.example.tecno_proyect.repository.MaterialProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaterialProjectService {
    
    @Autowired
    private MaterialProjectRepository materialProjectRepository;
    
    @Autowired
    private MaterialService materialService;
    
    /**
     * Listar todos los materiales por proyecto
     */
    public List<MaterialProject> listarTodosMaterialesProyecto() {
        return materialProjectRepository.findAll();
    }
    
    /**
     * Buscar material-proyecto por ID
     */
    public Optional<MaterialProject> buscarMaterialProyectoPorId(Long id) {
        return materialProjectRepository.findById(id);
    }
    
    /**
     * Insertar nueva relación material-proyecto con control de stock
     */
    public MaterialProject insertarMaterialProyecto(Integer quantity, Integer leftOver, Long idProject, Long idMaterial) {
        // Validar datos básicos
        if (!validarDatosMaterialProyecto(quantity, leftOver, idProject, idMaterial)) {
            throw new RuntimeException("Datos de material-proyecto inválidos");
        }
        
        // Verificar que el material existe y tiene suficiente stock
        Optional<com.example.tecno_proyect.model.Material> materialOpt = materialService.buscarMaterialPorId(idMaterial);
        if (materialOpt.isEmpty()) {
            throw new RuntimeException("Material con ID " + idMaterial + " no existe");
        }
        
        com.example.tecno_proyect.model.Material material = materialOpt.get();
        if (material.getStock() == null || material.getStock() < quantity) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + 
                (material.getStock() != null ? material.getStock() : 0) + 
                ", Requerido: " + quantity);
        }
        
        // Crear la relación material-proyecto
        MaterialProject materialProject = new MaterialProject(quantity, leftOver, idProject, idMaterial);
        MaterialProject savedMaterialProject = materialProjectRepository.save(materialProject);
        
        // Descontar del stock del material
        materialService.reducirStockMaterial(idMaterial, quantity);
        
        System.out.println("Material asignado a proyecto. Material ID: " + idMaterial + 
                          ", Cantidad: " + quantity + ", Stock restante: " + 
                          (material.getStock() - quantity));
        
        return savedMaterialProject;
    }
    
    /**
     * Actualizar relación material-proyecto existente con control de stock
     */
    public MaterialProject actualizarMaterialProyecto(Long id, Integer quantity, Integer leftOver, Long idProject, Long idMaterial) {
        Optional<MaterialProject> materialProyectoExistente = materialProjectRepository.findById(id);
        
        if (materialProyectoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró material-proyecto con ID: " + id);
        }
        
        MaterialProject materialProjectAntiguo = materialProyectoExistente.get();
        Integer cantidadAntigua = materialProjectAntiguo.getQuantity();
        Long materialIdAntiguo = materialProjectAntiguo.getIdMaterial();
        
        // Validar datos básicos
        if (!validarDatosMaterialProyecto(quantity, leftOver, idProject, idMaterial)) {
            throw new RuntimeException("Datos de material-proyecto inválidos");
        }
        
        // Si cambió el material o la cantidad, necesitamos ajustar los stocks
        if (!materialIdAntiguo.equals(idMaterial) || !cantidadAntigua.equals(quantity)) {
            
            // Devolver stock del material anterior
            if (cantidadAntigua != null && cantidadAntigua > 0) {
                materialService.aumentarStockMaterial(materialIdAntiguo, cantidadAntigua);
                System.out.println("Stock devuelto al material " + materialIdAntiguo + ": " + cantidadAntigua);
            }
            
            // Verificar stock del nuevo material
            Optional<com.example.tecno_proyect.model.Material> materialOpt = materialService.buscarMaterialPorId(idMaterial);
            if (materialOpt.isEmpty()) {
                throw new RuntimeException("Material con ID " + idMaterial + " no existe");
            }
            
            com.example.tecno_proyect.model.Material material = materialOpt.get();
            if (material.getStock() == null || material.getStock() < quantity) {
                // Revertir la devolución si no hay suficiente stock en el nuevo material
                if (cantidadAntigua != null && cantidadAntigua > 0) {
                    materialService.reducirStockMaterial(materialIdAntiguo, cantidadAntigua);
                }
                throw new RuntimeException("Stock insuficiente en nuevo material. Disponible: " + 
                    (material.getStock() != null ? material.getStock() : 0) + 
                    ", Requerido: " + quantity);
            }
            
            // Descontar del stock del nuevo material
            materialService.reducirStockMaterial(idMaterial, quantity);
            System.out.println("Stock descontado del material " + idMaterial + ": " + quantity);
        }
        
        // Actualizar la relación
        materialProjectAntiguo.setQuantity(quantity);
        materialProjectAntiguo.setLeftOver(leftOver);
        materialProjectAntiguo.setIdProject(idProject);
        materialProjectAntiguo.setIdMaterial(idMaterial);
        
        return materialProjectRepository.save(materialProjectAntiguo);
    }
    
    /**
     * Eliminar relación material-proyecto por ID con devolución de stock
     */
    public boolean eliminarMaterialProyecto(Long id) {
        Optional<MaterialProject> materialProjectOpt = materialProjectRepository.findById(id);
        
        if (materialProjectOpt.isPresent()) {
            MaterialProject materialProject = materialProjectOpt.get();
            
            // Devolver el stock al material antes de eliminar
            if (materialProject.getQuantity() != null && materialProject.getQuantity() > 0) {
                materialService.aumentarStockMaterial(materialProject.getIdMaterial(), materialProject.getQuantity());
                System.out.println("Stock devuelto al eliminar material-proyecto. Material ID: " + 
                                  materialProject.getIdMaterial() + ", Cantidad: " + materialProject.getQuantity());
            }
            
            materialProjectRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Buscar materiales por proyecto
     */
    public List<MaterialProject> buscarMaterialesPorProyecto(Long idProject) {
        return materialProjectRepository.findByIdProject(idProject);
    }
    
    /**
     * Buscar proyectos por material
     */
    public List<MaterialProject> buscarProyectosPorMaterial(Long idMaterial) {
        return materialProjectRepository.findByIdMaterial(idMaterial);
    }
    
    /**
     * Buscar por proyecto y material específicos
     */
    public List<MaterialProject> buscarPorProyectoYMaterial(Long idProject, Long idMaterial) {
        return materialProjectRepository.findByIdProjectAndIdMaterial(idProject, idMaterial);
    }
    
    /**
     * Buscar materiales con sobrante
     */
    public List<MaterialProject> buscarMaterialesConSobrante() {
        return materialProjectRepository.findMaterialsWithLeftOver();
    }
    
    /**
     * Buscar materiales usados completamente
     */
    public List<MaterialProject> buscarMaterialesUsadosCompletamente() {
        return materialProjectRepository.findFullyUsedMaterials();
    }
    
    /**
     * Buscar por cantidad específica
     */
    public List<MaterialProject> buscarPorCantidad(Integer quantity) {
        return materialProjectRepository.findByQuantity(quantity);
    }
    
    /**
     * Buscar por cantidad mayor a un valor
     */
    public List<MaterialProject> buscarPorCantidadMayor(Integer minQuantity) {
        return materialProjectRepository.findByQuantityGreaterThan(minQuantity);
    }
    
    /**
     * Buscar por sobrante específico
     */
    public List<MaterialProject> buscarPorSobrante(Integer leftOver) {
        return materialProjectRepository.findByLeftOver(leftOver);
    }
    
    /**
     * Buscar materiales más utilizados
     */
    public List<MaterialProject> buscarMaterialesMasUtilizados() {
        return materialProjectRepository.findMostUsedMaterials();
    }
    
    /**
     * Buscar materiales con eficiencia alta
     */
    public List<MaterialProject> buscarMaterialesConEficienciaAlta(Double efficiency) {
        return materialProjectRepository.findMaterialsWithEfficiencyAbove(efficiency);
    }
    
    /**
     * Obtener total de materiales usados en un proyecto
     */
    public Integer obtenerTotalMaterialesUsadosEnProyecto(Long idProject) {
        Integer total = materialProjectRepository.getTotalMaterialsUsedInProject(idProject);
        return total != null ? total : 0;
    }
    
    /**
     * Obtener total de sobrantes en un proyecto
     */
    public Integer obtenerTotalSobrantesEnProyecto(Long idProject) {
        Integer total = materialProjectRepository.getTotalLeftOverInProject(idProject);
        return total != null ? total : 0;
    }
    
    /**
     * Contar proyectos que usan un material específico
     */
    public long contarProyectosQuUsanMaterial(Long idMaterial) {
        return materialProjectRepository.countProjectsUsingMaterial(idMaterial);
    }
    
    /**
     * Verificar si existe una relación
     */
    public boolean existeMaterialProyecto(Long id) {
        return materialProjectRepository.existsById(id);
    }
    
    /**
     * Contar total de relaciones
     */
    public long contarMaterialesProyecto() {
        return materialProjectRepository.count();
    }
    
    /**
     * Actualizar cantidad de material en proyecto
     */
    public MaterialProject actualizarCantidad(Long id, Integer nuevaCantidad) {
        Optional<MaterialProject> materialProyectoOpt = materialProjectRepository.findById(id);
        if (materialProyectoOpt.isPresent()) {
            MaterialProject materialProject = materialProyectoOpt.get();
            materialProject.setQuantity(nuevaCantidad);
            return materialProjectRepository.save(materialProject);
        }
        throw new RuntimeException("No se encontró material-proyecto con ID: " + id);
    }
    
    /**
     * Actualizar sobrante de material
     */
    public MaterialProject actualizarSobrante(Long id, Integer nuevoSobrante) {
        Optional<MaterialProject> materialProyectoOpt = materialProjectRepository.findById(id);
        if (materialProyectoOpt.isPresent()) {
            MaterialProject materialProject = materialProyectoOpt.get();
            materialProject.setLeftOver(nuevoSobrante);
            return materialProjectRepository.save(materialProject);
        }
        throw new RuntimeException("No se encontró material-proyecto con ID: " + id);
    }
    
    /**
     * Calcular eficiencia de uso de material
     */
    public Double calcularEficienciaUso(Long id) {
        Optional<MaterialProject> materialProyectoOpt = materialProjectRepository.findById(id);
        if (materialProyectoOpt.isPresent()) {
            MaterialProject materialProject = materialProyectoOpt.get();
            Integer cantidad = materialProject.getQuantity();
            Integer sobrante = materialProject.getLeftOver() != null ? materialProject.getLeftOver() : 0;
            
            if (cantidad == null || cantidad == 0) {
                return 0.0;
            }
            
            return ((double) (cantidad - sobrante) / cantidad) * 100.0;
        }
        return 0.0;
    }
    
    /**
     * Validar datos de material-proyecto
     */
    public boolean validarDatosMaterialProyecto(Integer quantity, Integer leftOver, Long idProject, Long idMaterial) {
        if (quantity == null || quantity <= 0) {
            return false;
        }
        if (leftOver == null || leftOver < 0) {
            return false;
        }
        if (leftOver > quantity) {
            return false; // El sobrante no puede ser mayor que la cantidad
        }
        if (idProject == null) {
            return false;
        }
        if (idMaterial == null) {
            return false;
        }
        return true;
    }
    
    /**
     * Verificar si un proyecto tiene materiales asignados
     */
    public boolean proyectoTieneMateriales(Long idProject) {
        return !buscarMaterialesPorProyecto(idProject).isEmpty();
    }
    
    /**
     * Verificar si un material está asignado a proyectos
     */
    public boolean materialEstaAsignado(Long idMaterial) {
        return !buscarProyectosPorMaterial(idMaterial).isEmpty();
    }
    
    /**
     * Buscar material con mayor cantidad por proyecto
     */
    public Optional<MaterialProject> buscarMaterialMayorCantidadPorProyecto(Long idProject) {
        List<MaterialProject> materiales = buscarMaterialesPorProyecto(idProject);
        return materiales.stream()
                .max((m1, m2) -> Integer.compare(m1.getQuantity(), m2.getQuantity()));
    }
    
    /**
     * Buscar material con menor eficiencia por proyecto
     */
    public Optional<MaterialProject> buscarMaterialMenorEficienciaPorProyecto(Long idProject) {
        List<MaterialProject> materiales = buscarMaterialesPorProyecto(idProject);
        return materiales.stream()
                .filter(mp -> mp.getQuantity() != null && mp.getQuantity() > 0)
                .min((m1, m2) -> {
                    Double eff1 = calcularEficienciaUso(m1.getId());
                    Double eff2 = calcularEficienciaUso(m2.getId());
                    return Double.compare(eff1, eff2);
                });
    }
    
    /**
     * Obtener resumen de materiales por proyecto
     */
    public String obtenerResumenMaterialesPorProyecto(Long idProject) {
        List<MaterialProject> materiales = buscarMaterialesPorProyecto(idProject);
        Integer totalUsado = obtenerTotalMaterialesUsadosEnProyecto(idProject);
        Integer totalSobrante = obtenerTotalSobrantesEnProyecto(idProject);
        
        return String.format("Proyecto %s: %d materiales diferentes, %d unidades usadas, %d sobrantes", 
                           idProject, materiales.size(), totalUsado, totalSobrante);
    }
    
    /**
     * Eliminar todos los materiales de un proyecto
     */
    public void eliminarTodosMaterialesDeProyecto(Long idProject) {
        List<MaterialProject> materiales = buscarMaterialesPorProyecto(idProject);
        materialProjectRepository.deleteAll(materiales);
    }
    
    /**
     * Verificar disponibilidad de material para un proyecto
     */
    public boolean verificarDisponibilidadMaterial(Long idMaterial, Integer cantidadRequerida) {
        if (cantidadRequerida == null || cantidadRequerida <= 0) {
            return false;
        }
        
        Optional<com.example.tecno_proyect.model.Material> materialOpt = materialService.buscarMaterialPorId(idMaterial);
        if (materialOpt.isEmpty()) {
            return false;
        }
        
        com.example.tecno_proyect.model.Material material = materialOpt.get();
        Integer stockDisponible = material.getStock() != null ? material.getStock() : 0;
        
        return stockDisponible >= cantidadRequerida;
    }
    
    /**
     * Devolver material sobrante al stock
     */
    public MaterialProject devolverMaterialSobrante(Long id, Integer cantidadDevolver) {
        Optional<MaterialProject> materialProjectOpt = materialProjectRepository.findById(id);
        
        if (materialProjectOpt.isEmpty()) {
            throw new RuntimeException("No se encontró material-proyecto con ID: " + id);
        }
        
        MaterialProject materialProject = materialProjectOpt.get();
        Integer sobrante = materialProject.getLeftOver() != null ? materialProject.getLeftOver() : 0;
        
        if (cantidadDevolver == null || cantidadDevolver <= 0) {
            throw new RuntimeException("Cantidad a devolver debe ser mayor a 0");
        }
        
        if (cantidadDevolver > sobrante) {
            throw new RuntimeException("No se puede devolver más de lo que sobra. Sobrante: " + sobrante);
        }
        
        // Devolver al stock
        materialService.aumentarStockMaterial(materialProject.getIdMaterial(), cantidadDevolver);
        
        // Actualizar el sobrante
        materialProject.setLeftOver(sobrante - cantidadDevolver);
        
        // También ajustar la cantidad total usada
        Integer cantidadTotal = materialProject.getQuantity();
        materialProject.setQuantity(cantidadTotal - cantidadDevolver);
        
        MaterialProject saved = materialProjectRepository.save(materialProject);
        
        System.out.println("Material sobrante devuelto. Material ID: " + materialProject.getIdMaterial() + 
                          ", Cantidad devuelta: " + cantidadDevolver + 
                          ", Nuevo sobrante: " + (sobrante - cantidadDevolver));
        
        return saved;
    }
    
    /**
     * Devolver todo el material sobrante al stock
     */
    public MaterialProject devolverTodoSobrante(Long id) {
        Optional<MaterialProject> materialProjectOpt = materialProjectRepository.findById(id);
        
        if (materialProjectOpt.isEmpty()) {
            throw new RuntimeException("No se encontró material-proyecto con ID: " + id);
        }
        
        MaterialProject materialProject = materialProjectOpt.get();
        Integer sobrante = materialProject.getLeftOver() != null ? materialProject.getLeftOver() : 0;
        
        if (sobrante <= 0) {
            throw new RuntimeException("No hay material sobrante para devolver");
        }
        
        return devolverMaterialSobrante(id, sobrante);
    }
    
    /**
     * Obtener reporte de stock de materiales usados en un proyecto
     */
    public String obtenerReporteStockProyecto(Long idProject) {
        List<MaterialProject> materiales = buscarMaterialesPorProyecto(idProject);
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE STOCK - PROYECTO ").append(idProject).append(" ===\n");
        
        for (MaterialProject mp : materiales) {
            Optional<com.example.tecno_proyect.model.Material> materialOpt = 
                materialService.buscarMaterialPorId(mp.getIdMaterial());
            
            if (materialOpt.isPresent()) {
                com.example.tecno_proyect.model.Material material = materialOpt.get();
                reporte.append("Material: ").append(material.getName())
                       .append(" (ID: ").append(material.getId()).append(")\n")
                       .append("  - Stock actual: ").append(material.getStock()).append(" ")
                       .append(material.getUnitMeasure()).append("\n")
                       .append("  - Usado en proyecto: ").append(mp.getQuantity()).append("\n")
                       .append("  - Sobrante: ").append(mp.getLeftOver()).append("\n")
                       .append("  - Precio unitario: $").append(material.getUnitPrice()).append("\n\n");
            }
        }
        
        return reporte.toString();
    }
    
    /**
     * Verificar y ajustar sobrantes basado en uso real
     */
    public MaterialProject ajustarSobrantePorUsoReal(Long id, Integer cantidadRealmentUsada) {
        Optional<MaterialProject> materialProjectOpt = materialProjectRepository.findById(id);
        
        if (materialProjectOpt.isEmpty()) {
            throw new RuntimeException("No se encontró material-proyecto con ID: " + id);
        }
        
        MaterialProject materialProject = materialProjectOpt.get();
        Integer cantidadAsignada = materialProject.getQuantity();
        
        if (cantidadRealmentUsada == null || cantidadRealmentUsada < 0) {
            throw new RuntimeException("Cantidad realmente usada debe ser >= 0");
        }
        
        if (cantidadRealmentUsada > cantidadAsignada) {
            throw new RuntimeException("Cantidad usada no puede ser mayor a la asignada. Asignada: " + cantidadAsignada);
        }
        
        Integer nuevoSobrante = cantidadAsignada - cantidadRealmentUsada;
        Integer sobranteAnterior = materialProject.getLeftOver() != null ? materialProject.getLeftOver() : 0;
        
        // Si hay diferencia en el sobrante, ajustar el stock
        Integer diferenciaSobrante = nuevoSobrante - sobranteAnterior;
        if (diferenciaSobrante != 0) {
            if (diferenciaSobrante > 0) {
                // Hay más sobrante del esperado, devolver al stock
                materialService.aumentarStockMaterial(materialProject.getIdMaterial(), diferenciaSobrante);
                System.out.println("Stock ajustado (devuelto): " + diferenciaSobrante);
            } else {
                // Hay menos sobrante, descontar del stock
                materialService.reducirStockMaterial(materialProject.getIdMaterial(), Math.abs(diferenciaSobrante));
                System.out.println("Stock ajustado (descontado): " + Math.abs(diferenciaSobrante));
            }
        }
        
        materialProject.setLeftOver(nuevoSobrante);
        
        return materialProjectRepository.save(materialProject);
    }
}