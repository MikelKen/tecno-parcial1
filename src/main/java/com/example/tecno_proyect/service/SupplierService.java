package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Proveedor;
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
    public List<Proveedor> listarTodosLosProveedores() {
        return supplierRepository.findAll();
    }
    
    /**
     * Buscar proveedor por nombre
     */
    public Optional<Proveedor> buscarProveedorPorNombre(String nombre) {
        return supplierRepository.findById(nombre);
    }
    
    /**
     * Insertar nuevo proveedor
     */
    public Proveedor insertarProveedor(String nombre, String contacto, String telefono, String email, String direccion) {
        Proveedor proveedor = new Proveedor(nombre, contacto, telefono, email, direccion);
        return supplierRepository.save(proveedor);
    }
    
    /**
     * Actualizar proveedor existente
     */
    public Proveedor actualizarProveedor(String nombre, String contacto, String telefono, String email, String direccion) {
        Optional<Proveedor> proveedorExistente = supplierRepository.findById(nombre);
        
        if (proveedorExistente.isEmpty()) {
            throw new RuntimeException("No se encontró proveedor con nombre: " + nombre);
        }
        
        Proveedor proveedor = proveedorExistente.get();
        proveedor.setContacto(contacto);
        proveedor.setTelefono(telefono);
        proveedor.setEmail(email);
        proveedor.setDireccion(direccion);
        
        return supplierRepository.save(proveedor);
    }
    
    /**
     * Eliminar proveedor por nombre
     */
    public boolean eliminarProveedor(String nombre) {
        if (supplierRepository.existsById(nombre)) {
            supplierRepository.deleteById(nombre);
            return true;
        }
        return false;
    }
    
    /**
     * Buscar proveedores por nombre (contiene texto)
     */
    public List<Proveedor> buscarProveedoresPorNombreParcial(String nombre) {
        return supplierRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    /**
     * Buscar proveedor por email
     */
    public Optional<Proveedor> buscarProveedorPorEmail(String email) {
        return supplierRepository.findByEmail(email);
    }
    
    /**
     * Buscar proveedor por teléfono
     */
    public Optional<Proveedor> buscarProveedorPorTelefono(String telefono) {
        return supplierRepository.findByTelefono(telefono);
    }
    
    /**
     * Buscar proveedor por contacto
     */
    public Optional<Proveedor> buscarProveedorPorContacto(String contacto) {
        return supplierRepository.findByContacto(contacto);
    }
    
    /**
     * Buscar proveedores por contacto (contiene texto)
     */
    public List<Proveedor> buscarProveedoresPorContactoParcial(String contacto) {
        return supplierRepository.findByContactoContainingIgnoreCase(contacto);
    }
    
    /**
     * Buscar proveedores por dirección (contiene texto)
     */
    public List<Proveedor> buscarProveedoresPorDireccion(String direccion) {
        return supplierRepository.findByDireccionContainingIgnoreCase(direccion);
    }
    
    /**
     * Buscar proveedores ordenados por nombre
     */
    public List<Proveedor> buscarProveedoresOrdenadosPorNombre() {
        return supplierRepository.findAllOrderByName();
    }
    
    /**
     * Buscar proveedores que suministran productos
     */
    public List<Proveedor> buscarProveedoresConProductos() {
        return supplierRepository.findProvidersWithProducts();
    }
    
    /**
     * Buscar proveedores que suministran un producto específico
     */
    public List<Proveedor> buscarProveedoresPorProducto(Long idProducto) {
        return supplierRepository.findProvidersByProduct(idProducto);
    }
    
    /**
     * Verificar si existe un proveedor
     */
    public boolean existeProveedor(String nombre) {
        return supplierRepository.existsById(nombre);
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
    public Proveedor actualizarEmailProveedor(String nombre, String nuevoEmail) {
        Optional<Proveedor> proveedorOpt = supplierRepository.findById(nombre);
        if (proveedorOpt.isPresent()) {
            Proveedor proveedor = proveedorOpt.get();
            proveedor.setEmail(nuevoEmail);
            return supplierRepository.save(proveedor);
        }
        throw new RuntimeException("No se encontró proveedor con nombre: " + nombre);
    }
    
    /**
     * Actualizar teléfono de proveedor
     */
    public Proveedor actualizarTelefonoProveedor(String nombre, String nuevoTelefono) {
        Optional<Proveedor> proveedorOpt = supplierRepository.findById(nombre);
        if (proveedorOpt.isPresent()) {
            Proveedor proveedor = proveedorOpt.get();
            proveedor.setTelefono(nuevoTelefono);
            return supplierRepository.save(proveedor);
        }
        throw new RuntimeException("No se encontró proveedor con nombre: " + nombre);
    }
    
    /**
     * Actualizar contacto de proveedor
     */
    public Proveedor actualizarContactoProveedor(String nombre, String nuevoContacto) {
        Optional<Proveedor> proveedorOpt = supplierRepository.findById(nombre);
        if (proveedorOpt.isPresent()) {
            Proveedor proveedor = proveedorOpt.get();
            proveedor.setContacto(nuevoContacto);
            return supplierRepository.save(proveedor);
        }
        throw new RuntimeException("No se encontró proveedor con nombre: " + nombre);
    }
    
    /**
     * Actualizar dirección de proveedor
     */
    public Proveedor actualizarDireccionProveedor(String nombre, String nuevaDireccion) {
        Optional<Proveedor> proveedorOpt = supplierRepository.findById(nombre);
        if (proveedorOpt.isPresent()) {
            Proveedor proveedor = proveedorOpt.get();
            proveedor.setDireccion(nuevaDireccion);
            return supplierRepository.save(proveedor);
        }
        throw new RuntimeException("No se encontró proveedor con nombre: " + nombre);
    }
    
    /**
     * Validar datos de proveedor
     */
    public boolean validarDatosProveedor(String nombre, String contacto, String telefono, String email, String direccion) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        if (contacto == null || contacto.trim().isEmpty()) {
            return false;
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return false;
        }
        if (direccion == null || direccion.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    
    /**
     * Verificar si email ya existe (para otro proveedor)
     */
    public boolean existeEmailEnOtroProveedor(String nombre, String email) {
        Optional<Proveedor> proveedorConEmail = supplierRepository.findByEmail(email);
        return proveedorConEmail.isPresent() && !proveedorConEmail.get().getNombre().equals(nombre);
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
    public boolean validarFormatoTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        // Validación básica: números, espacios, guiones y paréntesis
        return telefono.matches("^[0-9\\s\\-\\(\\)\\+]{7,15}$");
    }
    
    /**
     * Buscar proveedores activos (con productos)
     */
    public List<Proveedor> buscarProveedoresActivos() {
        return buscarProveedoresConProductos();
    }
    
    /**
     * Buscar proveedores inactivos (sin productos)
     */
    public List<Proveedor> buscarProveedoresInactivos() {
        List<Proveedor> todosProveedores = listarTodosLosProveedores();
        List<Proveedor> proveedoresActivos = buscarProveedoresConProductos();
        
        return todosProveedores.stream()
                .filter(proveedor -> !proveedoresActivos.contains(proveedor))
                .toList();
    }
}

