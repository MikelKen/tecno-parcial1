package com.example.tecno_proyect.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Utility class para codificar y validar contraseñas usando BCrypt
 */
@Component
public class PasswordEncoderUtil {
    
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    /**
     * Codifica una contraseña en texto plano usando BCrypt
     * @param rawPassword contraseña en texto plano
     * @return contraseña codificada
     */
    public String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }
    
    /**
     * Verifica si una contraseña en texto plano coincide con la contraseña codificada
     * @param rawPassword contraseña en texto plano
     * @param encodedPassword contraseña codificada almacenada
     * @return true si coinciden, false en caso contrario
     */
    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
