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
     * Insertar nueva relación material-proyecto
     */
    public MaterialProject insertarMaterialProyecto(Integer quantity, Integer leftOver, String idProject, Long idMaterial) {
        MaterialProject materialProject = new MaterialProject(quantity, leftOver, idProject, idMaterial);
        return materialProjectRepository.save(materialProject);
    }
    
    /**
     * Actualizar relación material-proyecto existente
     */
    public MaterialProject actualizarMaterialProyecto(Long id, Integer quantity, Integer leftOver, String idProject, Long idMaterial) {
        Optional<MaterialProject> materialProyectoExistente = materialProjectRepository.findById(id);
        
        if (materialProyectoExistente.isEmpty()) {
            throw new RuntimeException("No se encontró material-proyecto con ID: " + id);
        }
        
        MaterialProject materialProject = materialProyectoExistente.get();
        materialProject.setQuantity(quantity);
        materialProject.setLeftOver(leftOver);
        materialProject.setIdProject(idProject);
        materialProject.setIdMaterial(idMaterial);
        
        return materialProjectRepository.save(materialProject);
    }
    
    /**
     * Eliminar relación material-proyecto por ID
     */
    public boolean eliminarMaterialProyecto(Long id) {
        if (materialProjectRepository.existsById(id)) {
            materialProjectRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Buscar materiales por proyecto
     */
    public List<MaterialProject> buscarMaterialesPorProyecto(String idProject) {
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
    public List<MaterialProject> buscarPorProyectoYMaterial(String idProject, Long idMaterial) {
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
    public Integer obtenerTotalMaterialesUsadosEnProyecto(String idProject) {
        Integer total = materialProjectRepository.getTotalMaterialsUsedInProject(idProject);
        return total != null ? total : 0;
    }
    
    /**
     * Obtener total de sobrantes en un proyecto
     */
    public Integer obtenerTotalSobrantesEnProyecto(String idProject) {
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
    public boolean validarDatosMaterialProyecto(Integer quantity, Integer leftOver, String idProject, Long idMaterial) {
        if (quantity == null || quantity <= 0) {
            return false;
        }
        if (leftOver == null || leftOver < 0) {
            return false;
        }
        if (leftOver > quantity) {
            return false; // El sobrante no puede ser mayor que la cantidad
        }
        if (idProject == null || idProject.trim().isEmpty()) {
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
    public boolean proyectoTieneMateriales(String idProject) {
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
    public Optional<MaterialProject> buscarMaterialMayorCantidadPorProyecto(String idProject) {
        List<MaterialProject> materiales = buscarMaterialesPorProyecto(idProject);
        return materiales.stream()
                .max((m1, m2) -> Integer.compare(m1.getQuantity(), m2.getQuantity()));
    }
    
    /**
     * Buscar material con menor eficiencia por proyecto
     */
    public Optional<MaterialProject> buscarMaterialMenorEficienciaPorProyecto(String idProject) {
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
    public String obtenerResumenMaterialesPorProyecto(String idProject) {
        List<MaterialProject> materiales = buscarMaterialesPorProyecto(idProject);
        Integer totalUsado = obtenerTotalMaterialesUsadosEnProyecto(idProject);
        Integer totalSobrante = obtenerTotalSobrantesEnProyecto(idProject);
        
        return String.format("Proyecto %s: %d materiales diferentes, %d unidades usadas, %d sobrantes", 
                           idProject, materiales.size(), totalUsado, totalSobrante);
    }
    
    /**
     * Eliminar todos los materiales de un proyecto
     */
    public void eliminarTodosMaterialesDeProyecto(String idProject) {
        List<MaterialProject> materiales = buscarMaterialesPorProyecto(idProject);
        materialProjectRepository.deleteAll(materiales);
    }
    
    /**
     * Verificar disponibilidad de material para un proyecto
     */
    public boolean verificarDisponibilidadMaterial(Long idMaterial, Integer cantidadRequerida) {
        List<MaterialProject> usosMaterial = buscarProyectosPorMaterial(idMaterial);
        Integer totalUsado = usosMaterial.stream()
                .mapToInt(mp -> mp.getQuantity() != null ? mp.getQuantity() : 0)
                .sum();
        
        // Verificamos que la cantidad requerida sea válida
        if (cantidadRequerida == null || cantidadRequerida <= 0) {
            return false;
        }
        
        // Para implementación futura: verificar contra stock disponible
        // Por ejemplo: stockDisponible >= (totalUsado + cantidadRequerida)
        // Por ahora, simplemente reportamos el uso actual en logs
        System.out.println("Material " + idMaterial + " - Total usado: " + totalUsado + ", Requerido: " + cantidadRequerida);
        
        return true; // Siempre disponible por ahora
    }
}