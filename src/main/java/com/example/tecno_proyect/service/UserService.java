package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.User;
import com.example.tecno_proyect.repository.UserRepository;
import com.example.tecno_proyect.util.PasswordEncoderUtil;
import com.example.tecno_proyect.util.ValidationUtil;
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
    
    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;
    
    /**
     * Listar todos los usuarios
     */
    public List<User> listarTodosLosUsuarios() {
        return userRepository.findAll();
    }
    
    /**
     * Buscar usuario por ID
     */
    public Optional<User> buscarUsuarioPorId(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Buscar usuario por nombre
     */
    public Optional<User> buscarUsuarioPorNombre(String name) {
        return userRepository.findByName(name);
    }
    
    /**
     * Buscar usuario por email
     */
    public Optional<User> buscarUsuarioPorEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Insertar nuevo usuario con validaciones
     */
    public User insertarUsuario(String name, String email, String phone, String address, String password, String role) {
        // Validar nombre
        if (!ValidationUtil.isValidName(name)) {
            throw new RuntimeException("El nombre es inválido. Debe tener entre 3 y 100 caracteres.");
        }
        
        // Validar email
        if (!ValidationUtil.isValidEmail(email)) {
            throw new RuntimeException("El email tiene un formato inválido. Ej: usuario@ejemplo.com");
        }
        
        // Validar teléfono
        if (!ValidationUtil.isValidPhone(phone)) {
            throw new RuntimeException("El teléfono tiene un formato inválido. Debe tener al menos 8 dígitos.");
        }
        
        // Validar dirección
        if (!ValidationUtil.isValidAddress(address)) {
            throw new RuntimeException("La dirección es inválida. Debe tener entre 5 y 255 caracteres.");
        }
        
        // Validar contraseña
        if (!ValidationUtil.isValidPassword(password)) {
            throw new RuntimeException(ValidationUtil.getPasswordRequirements());
        }
        
        // Validar rol
        if (!ValidationUtil.isValidRole(role)) {
            throw new RuntimeException("El rol es inválido. Roles válidos: " + String.join(", ", ValidationUtil.getValidRoles()));
        }
        
        // Verificar si ya existe un usuario con ese nombre
        if (userRepository.existsByName(name)) {
            throw new RuntimeException("Ya existe un usuario con nombre: " + name);
        }
        
        // Verificar si ya existe un usuario con ese email
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Ya existe un usuario con email: " + email);
        }
        
        // Codificar la contraseña antes de guardar
        String encodedPassword = passwordEncoderUtil.encodePassword(password);
        
        User user = new User(name, email, phone, address, encodedPassword, role.toUpperCase());
        return userRepository.save(user);
    }
    
    /**
     * Actualizar usuario existente por ID con validaciones
     */
    public User actualizarUsuario(Long id, String name, String email, String phone, String address, String password, String role) {
        // Validar nombre
        if (!ValidationUtil.isValidName(name)) {
            throw new RuntimeException("El nombre es inválido. Debe tener entre 3 y 100 caracteres.");
        }
        
        // Validar email
        if (!ValidationUtil.isValidEmail(email)) {
            throw new RuntimeException("El email tiene un formato inválido. Ej: usuario@ejemplo.com");
        }
        
        // Validar teléfono
        if (!ValidationUtil.isValidPhone(phone)) {
            throw new RuntimeException("El teléfono tiene un formato inválido. Debe tener al menos 8 dígitos.");
        }
        
        // Validar dirección
        if (!ValidationUtil.isValidAddress(address)) {
            throw new RuntimeException("La dirección es inválida. Debe tener entre 5 y 255 caracteres.");
        }
        
        // Validar contraseña (solo si no es vacía, permitiendo mantener la anterior)
        String passwordToUse = password;
        if (password != null && !password.trim().isEmpty()) {
            if (!ValidationUtil.isValidPassword(password)) {
                throw new RuntimeException(ValidationUtil.getPasswordRequirements());
            }
            passwordToUse = passwordEncoderUtil.encodePassword(password);
        }
        
        // Validar rol
        if (!ValidationUtil.isValidRole(role)) {
            throw new RuntimeException("El rol es inválido. Roles válidos: " + String.join(", ", ValidationUtil.getValidRoles()));
        }
        
        Optional<User> userExistente = userRepository.findById(id);
        
        if (userExistente.isEmpty()) {
            throw new RuntimeException("No se encontró usuario con ID: " + id);
        }
        
        User user = userExistente.get();
        
        // Verificar si el nuevo nombre ya existe en otro usuario
        if (!user.getName().equals(name) && userRepository.existsByName(name)) {
            throw new RuntimeException("Ya existe otro usuario con nombre: " + name);
        }
        
        // Verificar si el nuevo email ya existe en otro usuario
        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new RuntimeException("Ya existe otro usuario con email: " + email);
        }
        
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(passwordToUse);
        }
        user.setRole(role.toUpperCase());
        
        return userRepository.save(user);
    }
    
    /**
     * Actualizar usuario existente por nombre con validaciones
     */
    public User actualizarUsuarioPorNombre(String name, String email, String phone, String address, String password, String role) {
        // Validar nombre actual
        if (!ValidationUtil.isValidName(name)) {
            throw new RuntimeException("El nombre es inválido. Debe tener entre 3 y 100 caracteres.");
        }
        
        Optional<User> userExistente = userRepository.findByName(name);
        
        if (userExistente.isEmpty()) {
            throw new RuntimeException("No se encontró usuario con nombre: " + name);
        }
        
        // Validar email
        if (!ValidationUtil.isValidEmail(email)) {
            throw new RuntimeException("El email tiene un formato inválido. Ej: usuario@ejemplo.com");
        }
        
        // Validar teléfono
        if (!ValidationUtil.isValidPhone(phone)) {
            throw new RuntimeException("El teléfono tiene un formato inválido. Debe tener al menos 8 dígitos.");
        }
        
        // Validar dirección
        if (!ValidationUtil.isValidAddress(address)) {
            throw new RuntimeException("La dirección es inválida. Debe tener entre 5 y 255 caracteres.");
        }
        
        // Validar contraseña (solo si no es vacía)
        String passwordToUse = null;
        if (password != null && !password.trim().isEmpty()) {
            if (!ValidationUtil.isValidPassword(password)) {
                throw new RuntimeException(ValidationUtil.getPasswordRequirements());
            }
            passwordToUse = passwordEncoderUtil.encodePassword(password);
        }
        
        // Validar rol
        if (!ValidationUtil.isValidRole(role)) {
            throw new RuntimeException("El rol es inválido. Roles válidos: " + String.join(", ", ValidationUtil.getValidRoles()));
        }
        
        User user = userExistente.get();
        
        // Verificar si el nuevo email ya existe en otro usuario
        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new RuntimeException("Ya existe otro usuario con email: " + email);
        }
        
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        if (passwordToUse != null) {
            user.setPassword(passwordToUse);
        }
        user.setRole(role.toUpperCase());
        
        return userRepository.save(user);
    }
    
    /**
     * Eliminar usuario por ID
     */
    public boolean eliminarUsuario(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Eliminar usuario por nombre
     */
    public boolean eliminarUsuarioPorNombre(String name) {
        Optional<User> userOpt = userRepository.findByName(name);
        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
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
     * Verificar si existe un usuario por ID
     */
    public boolean existeUsuario(Long id) {
        return userRepository.existsById(id);
    }
    
    /**
     * Verificar si existe un usuario por nombre
     */
    public boolean existeUsuarioPorNombre(String name) {
        return userRepository.existsByName(name);
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
     * Validar credenciales de usuario (email + contraseña)
     */
    public boolean validarCredenciales(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        // Usar passwordEncoderUtil para validar la contraseña hasheada
        return passwordEncoderUtil.matchesPassword(password, user.get().getPassword());
    }
    
    /**
     * Cambiar contraseña de usuario con validación
     */
    public boolean cambiarContrasena(String email, String nuevaContrasena) {
        // Validar la nueva contraseña
        if (!ValidationUtil.isValidPassword(nuevaContrasena)) {
            throw new RuntimeException(ValidationUtil.getPasswordRequirements());
        }
        
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Codificar la nueva contraseña antes de guardar
            user.setPassword(passwordEncoderUtil.encodePassword(nuevaContrasena));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}