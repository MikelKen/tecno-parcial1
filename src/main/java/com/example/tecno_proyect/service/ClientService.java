package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Cliente;
import com.example.tecno_proyect.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    /**
     * Listar todos los clientes
     */
    public List<Cliente> listarTodosLosClientes() {
        return clientRepository.findAll();
    }
    
    /**
     * Buscar cliente por ID
     */
    public Optional<Cliente> buscarClientePorId(Long id) {
        return clientRepository.findById(id);
    }
    
    /**
     * Buscar cliente por nombre
     */
    public Optional<Cliente> buscarClientePorNombre(String nombre) {
        return clientRepository.findByNombre(nombre);
    }
    
    /**
     * Buscar cliente por email
     */
    public Optional<Cliente> buscarClientePorEmail(String email) {
        return clientRepository.findByEmail(email);
    }
    
    /**
     * Insertar nuevo cliente
     */
    public Cliente insertarCliente(String nombre, String email, String telefono, String direccion) {
        // Verificar si ya existe un cliente con ese nombre
        if (clientRepository.existsByNombre(nombre)) {
            throw new RuntimeException("Ya existe un cliente con nombre: " + nombre);
        }
        
        // Verificar si ya existe un cliente con ese email
        if (clientRepository.existsByEmail(email)) {
            throw new RuntimeException("Ya existe un cliente con email: " + email);
        }
        
        Cliente cliente = new Cliente(nombre, email, telefono, direccion);
        return clientRepository.save(cliente);
    }
    
    /**
     * Actualizar cliente existente por ID
     */
    public Cliente actualizarClientePorId(Long id, String nombre, String email, String telefono, String direccion) {
        Optional<Cliente> clienteExistente = clientRepository.findById(id);
        
        if (clienteExistente.isEmpty()) {
            throw new RuntimeException("No se encontró cliente con ID: " + id);
        }
        
        Cliente cliente = clienteExistente.get();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setDireccion(direccion);
        
        return clientRepository.save(cliente);
    }
    
    /**
     * Actualizar cliente existente por nombre (método legacy)
     */
    public Cliente actualizarCliente(String nombre, String email, String telefono, String direccion) {
        Optional<Cliente> clienteExistente = clientRepository.findByNombre(nombre);
        
        if (clienteExistente.isEmpty()) {
            throw new RuntimeException("No se encontró cliente con nombre: " + nombre);
        }
        
        Cliente cliente = clienteExistente.get();
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setDireccion(direccion);
        
        return clientRepository.save(cliente);
    }
    
    /**
     * Eliminar cliente por ID
     */
    public boolean eliminarClientePorId(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Eliminar cliente por nombre (método legacy)
     */
    public boolean eliminarCliente(String nombre) {
        Optional<Cliente> cliente = clientRepository.findByNombre(nombre);
        if (cliente.isPresent()) {
            clientRepository.deleteById(cliente.get().getId());
            return true;
        }
        return false;
    }
    
    /**
     * Buscar clientes por nombre (parcial)
     */
    public List<Cliente> buscarClientesPorNombreParcial(String nombre) {
        return clientRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    /**
     * Buscar clientes por dirección
     */
    public List<Cliente> buscarClientesPorDireccion(String direccion) {
        return clientRepository.findByDireccionContainingIgnoreCase(direccion);
    }
    
    /**
     * Buscar clientes que tienen proyectos
     */
    public List<Cliente> buscarClientesConProyectos() {
        return clientRepository.findClientesWithProjects();
    }
    
    /**
     * Verificar si existe un cliente por ID
     */
    public boolean existeClientePorId(Long id) {
        return clientRepository.existsById(id);
    }
    
    /**
     * Verificar si existe un cliente por nombre
     */
    public boolean existeCliente(String nombre) {
        return clientRepository.existsByNombre(nombre);
    }
    
    /**
     * Contar total de clientes
     */
    public long contarClientes() {
        return clientRepository.count();
    }
    
    /**
     * Buscar cliente por teléfono
     */
    public Optional<Cliente> buscarClientePorTelefono(String telefono) {
        return clientRepository.findByTelefono(telefono);
    }
    
    /**
     * Validar datos de cliente
     */
    public boolean validarDatosCliente(String nombre, String email, String telefono) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (email == null || !email.contains("@")) return false;
        if (telefono == null || telefono.trim().isEmpty()) return false;
        return true;
    }
    
    /**
     * Obtener estadísticas de clientes
     */
    public String obtenerEstadisticasClientes() {
        long totalClientes = contarClientes();
        long clientesConProyectos = buscarClientesConProyectos().size();
        
        return String.format("Total de clientes: %d, Clientes con proyectos: %d", 
                           totalClientes, clientesConProyectos);
    }
}