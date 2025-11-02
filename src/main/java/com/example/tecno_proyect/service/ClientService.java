package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Client;
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
    public List<Client> listarTodosLosClientes() {
        return clientRepository.findAll();
    }
    
    /**
     * Buscar cliente por nombre (ID)
     */
    public Optional<Client> buscarClientePorNombre(String name) {
        return clientRepository.findById(name);
    }
    
    /**
     * Buscar cliente por email
     */
    public Optional<Client> buscarClientePorEmail(String email) {
        return clientRepository.findByEmail(email);
    }
    
    /**
     * Insertar nuevo cliente
     */
    public Client insertarCliente(String name, String email, String phone, String address) {
        // Verificar si ya existe un cliente con ese nombre
        if (clientRepository.existsById(name)) {
            throw new RuntimeException("Ya existe un cliente con nombre: " + name);
        }
        
        // Verificar si ya existe un cliente con ese email
        if (clientRepository.existsByEmail(email)) {
            throw new RuntimeException("Ya existe un cliente con email: " + email);
        }
        
        Client client = new Client(name, email, phone, address);
        return clientRepository.save(client);
    }
    
    /**
     * Actualizar cliente existente
     */
    public Client actualizarCliente(String name, String email, String phone, String address) {
        Optional<Client> clienteExistente = clientRepository.findById(name);
        
        if (clienteExistente.isEmpty()) {
            throw new RuntimeException("No se encontró cliente con nombre: " + name);
        }
        
        Client client = clienteExistente.get();
        client.setEmail(email);
        client.setPhone(phone);
        client.setAddress(address);
        
        return clientRepository.save(client);
    }
    
    /**
     * Eliminar cliente por nombre
     */
    public boolean eliminarCliente(String name) {
        if (clientRepository.existsById(name)) {
            clientRepository.deleteById(name);
            return true;
        }
        return false;
    }
    
    /**
     * Buscar clientes por nombre (parcial)
     */
    public List<Client> buscarClientesPorNombreParcial(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * Buscar clientes por dirección
     */
    public List<Client> buscarClientesPorDireccion(String address) {
        return clientRepository.findByAddressContainingIgnoreCase(address);
    }
    
    /**
     * Buscar clientes que tienen proyectos
     */
    public List<Client> buscarClientesConProyectos() {
        return clientRepository.findClientsWithProjects();
    }
    
    /**
     * Verificar si existe un cliente
     */
    public boolean existeCliente(String name) {
        return clientRepository.existsById(name);
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
    public Optional<Client> buscarClientePorTelefono(String phone) {
        return clientRepository.findByPhone(phone);
    }
    
    /**
     * Validar datos de cliente
     */
    public boolean validarDatosCliente(String name, String email, String phone) {
        if (name == null || name.trim().isEmpty()) return false;
        if (email == null || !email.contains("@")) return false;
        if (phone == null || phone.trim().isEmpty()) return false;
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