package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.User;
import com.example.tecno_proyect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Listar todos los usuarios
     */
    public List<User> listarTodosLosUsuarios() {
        return userRepository.findAll();
    }
    
    /**
     * Buscar usuario por nombre (ID)
     */
    public Optional<User> buscarUsuarioPorNombre(String name) {
        return userRepository.findById(name);
    }
    
    /**
     * Buscar usuario por email
     */
    public Optional<User> buscarUsuarioPorEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Insertar nuevo usuario
     */
    public User insertarUsuario(String name, String email, String phone, String address, String password, String role) {
        // Verificar si ya existe un usuario con ese nombre
        if (userRepository.existsById(name)) {
            throw new RuntimeException("Ya existe un usuario con nombre: " + name);
        }
        
        // Verificar si ya existe un usuario con ese email
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Ya existe un usuario con email: " + email);
        }
        
        User user = new User(name, email, phone, address, password, role);
        return userRepository.save(user);
    }
    
    /**
     * Actualizar usuario existente
     */
    public User actualizarUsuario(String name, String email, String phone, String address, String password, String role) {
        Optional<User> userExistente = userRepository.findById(name);
        
        if (userExistente.isEmpty()) {
            throw new RuntimeException("No se encontró usuario con nombre: " + name);
        }
        
        User user = userExistente.get();
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setPassword(password);
        user.setRole(role);
        
        return userRepository.save(user);
    }
    
    /**
     * Eliminar usuario por nombre
     */
    public boolean eliminarUsuario(String name) {
        if (userRepository.existsById(name)) {
            userRepository.deleteById(name);
            return true;
        }
        return false;
    }
    
    /**
     * Buscar usuarios por rol
     */
    public List<User> buscarUsuariosPorRol(String role) {
        return userRepository.findByRole(role);
    }
    
    /**
     * Buscar usuarios por nombre (parcial)
     */
    public List<User> buscarUsuariosPorNombreParcial(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * Verificar si existe un usuario
     */
    public boolean existeUsuario(String name) {
        return userRepository.existsById(name);
    }
    
    /**
     * Contar usuarios por rol
     */
    public long contarUsuariosPorRol(String role) {
        return userRepository.countByRole(role);
    }
    
    /**
     * Contar total de usuarios
     */
    public long contarUsuarios() {
        return userRepository.count();
    }
    
    /**
     * Validar credenciales de usuario
     */
    public boolean validarCredenciales(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }
    
    /**
     * Cambiar contraseña de usuario
     */
    public boolean cambiarContrasena(String email, String nuevaContrasena) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(nuevaContrasena);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}