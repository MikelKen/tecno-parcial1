package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Supplier;
import com.example.tecno_proyect.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierService {
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    /**
     * Listar todos los proveedores
     */
    public List<Supplier> listarTodosLosProveedores() {
        return supplierRepository.findAll();
    }
    
    /**
     * Buscar proveedor por nombre (ID)
     */
    public Optional<Supplier> buscarProveedorPorNombre(String name) {
        return supplierRepository.findById(name);
    }
    
    /**
     * Insertar nuevo proveedor
     */
    public Supplier insertarProveedor(String name, String contact, String phone, String email, String address) {
        Supplier supplier = new Supplier(name, contact, phone, email, address);
        return supplierRepository.save(supplier);
    }
    
    /**
     * Actualizar proveedor existente
     */
    public Supplier actualizarProveedor(String name, String contact, String phone, String email, String address) {
        Optional<Supplier> proveedorExistente = supplierRepository.findById(name);
        
        if (proveedorExistente.isEmpty()) {
            throw new RuntimeException("No se encontró proveedor con nombre: " + name);
        }
        
        Supplier supplier = proveedorExistente.get();
        supplier.setContact(contact);
        supplier.setPhone(phone);
        supplier.setEmail(email);
        supplier.setAddress(address);
        
        return supplierRepository.save(supplier);
    }
    
    /**
     * Eliminar proveedor por nombre
     */
    public boolean eliminarProveedor(String name) {
        if (supplierRepository.existsById(name)) {
            supplierRepository.deleteById(name);
            return true;
        }
        return false;
    }
    
    /**
     * Buscar proveedores por nombre (contiene texto)
     */
    public List<Supplier> buscarProveedoresPorNombreParcial(String name) {
        return supplierRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * Buscar proveedor por email
     */
    public Optional<Supplier> buscarProveedorPorEmail(String email) {
        return supplierRepository.findByEmail(email);
    }
    
    /**
     * Buscar proveedor por teléfono
     */
    public Optional<Supplier> buscarProveedorPorTelefono(String phone) {
        return supplierRepository.findByPhone(phone);
    }
    
    /**
     * Buscar proveedor por contacto
     */
    public Optional<Supplier> buscarProveedorPorContacto(String contact) {
        return supplierRepository.findByContact(contact);
    }
    
    /**
     * Buscar proveedores por contacto (contiene texto)
     */
    public List<Supplier> buscarProveedoresPorContactoParcial(String contact) {
        return supplierRepository.findByContactContainingIgnoreCase(contact);
    }
    
    /**
     * Buscar proveedores por dirección (contiene texto)
     */
    public List<Supplier> buscarProveedoresPorDireccion(String address) {
        return supplierRepository.findByAddressContainingIgnoreCase(address);
    }
    
    /**
     * Buscar proveedores ordenados por nombre
     */
    public List<Supplier> buscarProveedoresOrdenadosPorNombre() {
        return supplierRepository.findAllOrderByName();
    }
    
    /**
     * Buscar proveedores que suministran materiales
     */
    public List<Supplier> buscarProveedoresConMateriales() {
        return supplierRepository.findSuppliersWithMaterials();
    }
    
    /**
     * Buscar proveedores que suministran un material específico
     */
    public List<Supplier> buscarProveedoresPorMaterial(String idMaterial) {
        return supplierRepository.findSuppliersByMaterial(idMaterial);
    }
    
    /**
     * Contar materiales suministrados por proveedor
     */
    public long contarMaterialesPorProveedor(String name) {
        return supplierRepository.countMaterialsBySupplier(name);
    }
    
    /**
     * Verificar si existe un proveedor
     */
    public boolean existeProveedor(String name) {
        return supplierRepository.existsById(name);
    }
    
    /**
     * Verificar si existe email
     */
    public boolean existeEmail(String email) {
        return supplierRepository.existsByEmail(email);
    }
    
    /**
     * Contar total de proveedores
     */
    public long contarProveedores() {
        return supplierRepository.count();
    }
    
    /**
     * Actualizar email de proveedor
     */
    public Supplier actualizarEmailProveedor(String name, String nuevoEmail) {
        Optional<Supplier> proveedorOpt = supplierRepository.findById(name);
        if (proveedorOpt.isPresent()) {
            Supplier supplier = proveedorOpt.get();
            supplier.setEmail(nuevoEmail);
            return supplierRepository.save(supplier);
        }
        throw new RuntimeException("No se encontró proveedor con nombre: " + name);
    }
    
    /**
     * Actualizar teléfono de proveedor
     */
    public Supplier actualizarTelefonoProveedor(String name, String nuevoTelefono) {
        Optional<Supplier> proveedorOpt = supplierRepository.findById(name);
        if (proveedorOpt.isPresent()) {
            Supplier supplier = proveedorOpt.get();
            supplier.setPhone(nuevoTelefono);
            return supplierRepository.save(supplier);
        }
        throw new RuntimeException("No se encontró proveedor con nombre: " + name);
    }
    
    /**
     * Actualizar contacto de proveedor
     */
    public Supplier actualizarContactoProveedor(String name, String nuevoContacto) {
        Optional<Supplier> proveedorOpt = supplierRepository.findById(name);
        if (proveedorOpt.isPresent()) {
            Supplier supplier = proveedorOpt.get();
            supplier.setContact(nuevoContacto);
            return supplierRepository.save(supplier);
        }
        throw new RuntimeException("No se encontró proveedor con nombre: " + name);
    }
    
    /**
     * Actualizar dirección de proveedor
     */
    public Supplier actualizarDireccionProveedor(String name, String nuevaDireccion) {
        Optional<Supplier> proveedorOpt = supplierRepository.findById(name);
        if (proveedorOpt.isPresent()) {
            Supplier supplier = proveedorOpt.get();
            supplier.setAddress(nuevaDireccion);
            return supplierRepository.save(supplier);
        }
        throw new RuntimeException("No se encontró proveedor con nombre: " + name);
    }
    
    /**
     * Validar datos de proveedor
     */
    public boolean validarDatosProveedor(String name, String contact, String phone, String email, String address) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (contact == null || contact.trim().isEmpty()) {
            return false;
        }
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return false;
        }
        if (address == null || address.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    
    /**
     * Verificar si email ya existe (para otro proveedor)
     */
    public boolean existeEmailEnOtroProveedor(String name, String email) {
        Optional<Supplier> proveedorConEmail = supplierRepository.findByEmail(email);
        return proveedorConEmail.isPresent() && !proveedorConEmail.get().getName().equals(name);
    }
    
    /**
     * Verificar formato de email
     */
    public boolean validarFormatoEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Validación básica de email
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    /**
     * Verificar formato de teléfono
     */
    public boolean validarFormatoTelefono(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        // Validación básica: números, espacios, guiones y paréntesis
        return phone.matches("^[0-9\\s\\-\\(\\)\\+]{7,15}$");
    }
    
    /**
     * Verificar si un proveedor tiene materiales asignados
     */
    public boolean proveedorTieneMateriales(String name) {
        return contarMaterialesPorProveedor(name) > 0;
    }
    
    /**
     * Buscar proveedores activos (con materiales)
     */
    public List<Supplier> buscarProveedoresActivos() {
        return buscarProveedoresConMateriales();
    }
    
    /**
     * Buscar proveedores inactivos (sin materiales)
     */
    public List<Supplier> buscarProveedoresInactivos() {
        List<Supplier> todosProveedores = listarTodosLosProveedores();
        List<Supplier> proveedoresActivos = buscarProveedoresConMateriales();
        
        return todosProveedores.stream()
                .filter(proveedor -> !proveedoresActivos.contains(proveedor))
                .toList();
    }
}
