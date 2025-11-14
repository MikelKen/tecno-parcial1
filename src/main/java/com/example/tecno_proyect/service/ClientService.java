package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Client;
import com.example.tecno_proyect.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    // Patrón para validar email
    private static final String EMAIL_PATTERN = 
        "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_REGEX = Pattern.compile(EMAIL_PATTERN);
    
    // Patrón para validar teléfono (solo dígitos)
    private static final String PHONE_PATTERN = "^[0-9]+$";
    private static final Pattern PHONE_REGEX = Pattern.compile(PHONE_PATTERN);
    
    /**
     * Validar formato de email
     */
    private boolean esEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_REGEX.matcher(email.trim()).matches();
    }
    
    /**
     * Validar formato de teléfono (mínimo 8 dígitos)
     */
    private boolean esTelefonoValido(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        String phoneClean = phone.trim().replaceAll("[^0-9]", "");
        return PHONE_REGEX.matcher(phoneClean).matches() && phoneClean.length() >= 8;
    }
    
    /**
     * Validar nombre (3-100 caracteres)
     */
    private boolean esNombreValido(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String nameTrimmed = name.trim();
        return nameTrimmed.length() >= 3 && nameTrimmed.length() <= 100;
    }
    
    /**
     * Validar dirección (5-255 caracteres)
     */
    private boolean esDireccionValida(String address) {
        if (address == null || address.trim().isEmpty()) {
            return false;
        }
        String addressTrimmed = address.trim();
        return addressTrimmed.length() >= 5 && addressTrimmed.length() <= 255;
    }
    
    /**
     * Listar todos los clientes
     */
    public List<Client> listarTodosLosClientes() {
        return clientRepository.findAll();
    }
    
    /**
     * Buscar cliente por ID
     */
    public Optional<Client> buscarClientePorId(Long id) {
        return clientRepository.findById(id);
    }
    
    /**
     * Buscar cliente por nombre
     */
    public Optional<Client> buscarClientePorNombre(String name) {
        return clientRepository.findByName(name);
    }
    
    /**
     * Buscar cliente por email
     */
    public Optional<Client> buscarClientePorEmail(String email) {
        return clientRepository.findByEmail(email);
    }
    
    /**
     * Insertar nuevo cliente con validaciones
     */
    public Client insertarCliente(String name, String email, String phone, String address) {
        // Validar nombre
        if (!esNombreValido(name)) {
            throw new RuntimeException("Nombre inválido: debe tener entre 3 y 100 caracteres");
        }
        
        // Validar email
        if (!esEmailValido(email)) {
            throw new RuntimeException("Email inválido: formato no válido (ej: usuario@ejemplo.com)");
        }
        
        // Validar teléfono
        if (!esTelefonoValido(phone)) {
            throw new RuntimeException("Teléfono inválido: debe tener mínimo 8 dígitos");
        }
        
        // Validar dirección
        if (!esDireccionValida(address)) {
            throw new RuntimeException("Dirección inválida: debe tener entre 5 y 255 caracteres");
        }
        
        // Verificar si ya existe un cliente con ese nombre
        if (clientRepository.existsByName(name.trim())) {
            throw new RuntimeException("Ya existe un cliente con nombre: " + name);
        }
        
        // Verificar si ya existe un cliente con ese email
        if (clientRepository.existsByEmail(email.trim())) {
            throw new RuntimeException("Ya existe un cliente con email: " + email);
        }
        
        Client client = new Client(name.trim(), email.trim(), phone.trim(), address.trim());
        return clientRepository.save(client);
    }
    
    /**
     * Actualizar cliente existente por ID con validaciones
     */
    public Client actualizarClientePorId(Long id, String name, String email, String phone, String address) {
        Optional<Client> clienteExistente = clientRepository.findById(id);
        
        if (clienteExistente.isEmpty()) {
            throw new RuntimeException("No se encontró cliente con ID: " + id);
        }
        
        // Validar nombre
        if (!esNombreValido(name)) {
            throw new RuntimeException("Nombre inválido: debe tener entre 3 y 100 caracteres");
        }
        
        // Validar email
        if (!esEmailValido(email)) {
            throw new RuntimeException("Email inválido: formato no válido (ej: usuario@ejemplo.com)");
        }
        
        // Validar teléfono
        if (!esTelefonoValido(phone)) {
            throw new RuntimeException("Teléfono inválido: debe tener mínimo 8 dígitos");
        }
        
        // Validar dirección
        if (!esDireccionValida(address)) {
            throw new RuntimeException("Dirección inválida: debe tener entre 5 y 255 caracteres");
        }
        
        Client client = clienteExistente.get();
        client.setName(name.trim());
        client.setEmail(email.trim());
        client.setPhone(phone.trim());
        client.setAddress(address.trim());
        
        return clientRepository.save(client);
    }
    
    /**
     * Actualizar cliente existente por nombre (método legacy) con validaciones
     */
    public Client actualizarCliente(String name, String email, String phone, String address) {
        Optional<Client> clienteExistente = clientRepository.findByName(name);
        
        if (clienteExistente.isEmpty()) {
            throw new RuntimeException("No se encontró cliente con nombre: " + name);
        }
        
        // Validar email
        if (!esEmailValido(email)) {
            throw new RuntimeException("Email inválido: formato no válido (ej: usuario@ejemplo.com)");
        }
        
        // Validar teléfono
        if (!esTelefonoValido(phone)) {
            throw new RuntimeException("Teléfono inválido: debe tener mínimo 8 dígitos");
        }
        
        // Validar dirección
        if (!esDireccionValida(address)) {
            throw new RuntimeException("Dirección inválida: debe tener entre 5 y 255 caracteres");
        }
        
        Client client = clienteExistente.get();
        client.setEmail(email.trim());
        client.setPhone(phone.trim());
        client.setAddress(address.trim());
        
        return clientRepository.save(client);
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
    public boolean eliminarCliente(String name) {
        Optional<Client> cliente = clientRepository.findByName(name);
        if (cliente.isPresent()) {
            clientRepository.deleteById(cliente.get().getId());
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
     * Verificar si existe un cliente por ID
     */
    public boolean existeClientePorId(Long id) {
        return clientRepository.existsById(id);
    }
    
    /**
     * Verificar si existe un cliente por nombre
     */
    public boolean existeCliente(String name) {
        return clientRepository.existsByName(name);
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
     * Validar datos de cliente (método mejorado)
     */
    public boolean validarDatosCliente(String name, String email, String phone) {
        return esNombreValido(name) && 
               esEmailValido(email) && 
               esTelefonoValido(phone);
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
