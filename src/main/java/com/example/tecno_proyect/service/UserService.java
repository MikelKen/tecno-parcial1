package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.Usuario;
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
    public List<Usuario> listarTodosLosUsuarios() {
        return userRepository.findAll();
    }
    
    /**
     * Buscar usuario por ID
     */
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Buscar usuario por nombre
     */
    public Optional<Usuario> buscarUsuarioPorNombre(String nombre) {
        return userRepository.findByNombre(nombre);
    }
    
    /**
     * Buscar usuario por email
     */
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Insertar nuevo usuario con validaciones
     */
    public Usuario insertarUsuario(String nombre, String email, String telefono, String direccion, String password, String rol) {
        // Validar nombre
        if (!ValidationUtil.isValidName(nombre)) {
            throw new RuntimeException("El nombre es inválido. Debe tener entre 3 y 100 caracteres.");
        }
        
        // Validar email
        if (!ValidationUtil.isValidEmail(email)) {
            throw new RuntimeException("El email tiene un formato inválido. Ej: usuario@ejemplo.com");
        }
        
        // Validar teléfono
        if (!ValidationUtil.isValidPhone(telefono)) {
            throw new RuntimeException("El teléfono tiene un formato inválido. Debe tener al menos 8 dígitos.");
        }
        
        // Validar dirección
        if (!ValidationUtil.isValidAddress(direccion)) {
            throw new RuntimeException("La dirección es inválida. Debe tener entre 5 y 255 caracteres.");
        }
        
        // Validar contraseña
        if (!ValidationUtil.isValidPassword(password)) {
            throw new RuntimeException(ValidationUtil.getPasswordRequirements());
        }
        
        // Validar rol
        if (!ValidationUtil.isValidRole(rol)) {
            throw new RuntimeException("El rol es inválido. Roles válidos: " + String.join(", ", ValidationUtil.getValidRoles()));
        }
        
        // Verificar si ya existe un usuario con ese nombre
        if (userRepository.existsByNombre(nombre)) {
            throw new RuntimeException("Ya existe un usuario con nombre: " + nombre);
        }
        
        // Verificar si ya existe un usuario con ese email
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Ya existe un usuario con email: " + email);
        }
        
        // Codificar la contraseña antes de guardar
        String encodedPassword = passwordEncoderUtil.encodePassword(password);
        
        Usuario usuario = new Usuario(nombre, email, telefono, direccion, encodedPassword, rol.toUpperCase());
        return userRepository.save(usuario);
    }
    
    /**
     * Actualizar usuario existente por ID con validaciones
     */
    public Usuario actualizarUsuario(Long id, String nombre, String email, String telefono, String direccion, String password, String rol) {
        // Validar nombre
        if (!ValidationUtil.isValidName(nombre)) {
            throw new RuntimeException("El nombre es inválido. Debe tener entre 3 y 100 caracteres.");
        }
        
        // Validar email
        if (!ValidationUtil.isValidEmail(email)) {
            throw new RuntimeException("El email tiene un formato inválido. Ej: usuario@ejemplo.com");
        }
        
        // Validar teléfono
        if (!ValidationUtil.isValidPhone(telefono)) {
            throw new RuntimeException("El teléfono tiene un formato inválido. Debe tener al menos 8 dígitos.");
        }
        
        // Validar dirección
        if (!ValidationUtil.isValidAddress(direccion)) {
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
        if (!ValidationUtil.isValidRole(rol)) {
            throw new RuntimeException("El rol es inválido. Roles válidos: " + String.join(", ", ValidationUtil.getValidRoles()));
        }
        
        Optional<Usuario> usuarioExistente = userRepository.findById(id);
        
        if (usuarioExistente.isEmpty()) {
            throw new RuntimeException("No se encontró usuario con ID: " + id);
        }
        
        Usuario usuario = usuarioExistente.get();
        
        // Verificar si el nuevo nombre ya existe en otro usuario
        if (!usuario.getNombre().equals(nombre) && userRepository.existsByNombre(nombre)) {
            throw new RuntimeException("Ya existe otro usuario con nombre: " + nombre);
        }
        
        // Verificar si el nuevo email ya existe en otro usuario
        if (!usuario.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new RuntimeException("Ya existe otro usuario con email: " + email);
        }
        
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        if (password != null && !password.trim().isEmpty()) {
            usuario.setPassword(passwordToUse);
        }
        usuario.setRol(rol.toUpperCase());
        
        return userRepository.save(usuario);
    }
    
    /**
     * Actualizar usuario existente por nombre con validaciones
     */
    public Usuario actualizarUsuarioPorNombre(String nombre, String email, String telefono, String direccion, String password, String rol) {
        // Validar nombre actual
        if (!ValidationUtil.isValidName(nombre)) {
            throw new RuntimeException("El nombre es inválido. Debe tener entre 3 y 100 caracteres.");
        }
        
        Optional<Usuario> usuarioExistente = userRepository.findByNombre(nombre);
        
        if (usuarioExistente.isEmpty()) {
            throw new RuntimeException("No se encontró usuario con nombre: " + nombre);
        }
        
        // Validar email
        if (!ValidationUtil.isValidEmail(email)) {
            throw new RuntimeException("El email tiene un formato inválido. Ej: usuario@ejemplo.com");
        }
        
        // Validar teléfono
        if (!ValidationUtil.isValidPhone(telefono)) {
            throw new RuntimeException("El teléfono tiene un formato inválido. Debe tener al menos 8 dígitos.");
        }
        
        // Validar dirección
        if (!ValidationUtil.isValidAddress(direccion)) {
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
        if (!ValidationUtil.isValidRole(rol)) {
            throw new RuntimeException("El rol es inválido. Roles válidos: " + String.join(", ", ValidationUtil.getValidRoles()));
        }
        
        Usuario usuario = usuarioExistente.get();
        
        // Verificar si el nuevo email ya existe en otro usuario
        if (!usuario.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new RuntimeException("Ya existe otro usuario con email: " + email);
        }
        
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        if (passwordToUse != null) {
            usuario.setPassword(passwordToUse);
        }
        usuario.setRol(rol.toUpperCase());
        
        return userRepository.save(usuario);
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
    public boolean eliminarUsuarioPorNombre(String nombre) {
        Optional<Usuario> usuarioOpt = userRepository.findByNombre(nombre);
        if (usuarioOpt.isPresent()) {
            userRepository.delete(usuarioOpt.get());
            return true;
        }
        return false;
    }
    
    /**
     * Buscar usuarios por rol
     */
    public List<Usuario> buscarUsuariosPorRol(String rol) {
        return userRepository.findByRol(rol);
    }
    
    /**
     * Buscar usuarios por nombre (parcial)
     */
    public List<Usuario> buscarUsuariosPorNombreParcial(String nombre) {
        return userRepository.findByNombreContainingIgnoreCase(nombre);
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
    public boolean existeUsuarioPorNombre(String nombre) {
        return userRepository.existsByNombre(nombre);
    }
    
    /**
     * Contar usuarios por rol
     */
    public long contarUsuariosPorRol(String rol) {
        return userRepository.countByRol(rol);
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
        Optional<Usuario> user = userRepository.findByEmail(email);
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
        
        Optional<Usuario> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();
            // Codificar la nueva contraseña antes de guardar
            user.setPassword(passwordEncoderUtil.encodePassword(nuevaContrasena));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}