package com.example.tecno_proyect.util;

import java.util.regex.Pattern;

/**
 * Utility class para validaciones de campos de Usuario
 */
public class ValidationUtil {
    
    // Expresión regular para validar email (RFC 5322 simplificada)
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    // Expresión regular para validar teléfono (permite formatos comunes)
    // Acepta: +1234567890, 1234567890, (123)456-7890, etc.
    private static final String PHONE_REGEX = "^[\\d\\s()+-]*$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    
    // Roles válidos en el sistema
    private static final String[] VALID_ROLES = {"ADMIN", "DESIGNER", "INSTALLER"};
    
    /**
     * Valida que el nombre no sea nulo, vacío o muy corto
     * @param name nombre a validar
     * @return true si es válido
     */
    public static boolean isValidName(String name) {
        return name != null && name.trim().length() >= 3 && name.trim().length() <= 100;
    }
    
    /**
     * Valida que el email tenga formato correcto
     * @param email email a validar
     * @return true si es válido
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Valida que el teléfono tenga un formato razonable
     * Requiere mínimo 8 dígitos (sin contar el prefijo +591)
     * @param phone teléfono a validar
     * @return true si es válido
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return true; // El teléfono es opcional
        }
        // Verifica que tenga caracteres válidos y al menos 8 dígitos
        String digitsOnly = phone.replaceAll("\\D", "");
        return PHONE_PATTERN.matcher(phone).matches() && digitsOnly.length() >= 8;
    }
    
    /**
     * Valida que el rol sea uno de los permitidos
     * @param role rol a validar
     * @return true si es válido
     */
    public static boolean isValidRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            return false;
        }
        String normalizedRole = role.trim().toUpperCase();
        for (String validRole : VALID_ROLES) {
            if (validRole.equals(normalizedRole)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Valida que la contraseña cumpla con los requisitos mínimos
     * - Al menos 8 caracteres
     * - Al menos una letra mayúscula
     * - Al menos una letra minúscula
     * - Al menos un dígito
     * - Al menos un carácter especial (!@#$%^&*)
     * @param password contraseña a validar
     * @return true si es válida
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>?/].*");
        
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
    
    /**
     * Valida que la dirección no sea nula o muy corta
     * @param address dirección a validar
     * @return true si es válida
     */
    public static boolean isValidAddress(String address) {
        return address != null && address.trim().length() >= 5 && address.trim().length() <= 255;
    }
    
    /**
     * Retorna los roles válidos disponibles
     * @return array de roles válidos
     */
    public static String[] getValidRoles() {
        return VALID_ROLES;
    }
    
    /**
     * Retorna un mensaje detallado sobre los requisitos de contraseña
     * @return mensaje descriptivo
     */
    public static String getPasswordRequirements() {
        return "La contraseña debe tener: " +
               "- Al menos 8 caracteres, " +
               "- 1 letra mayúscula, " +
               "- 1 letra minúscula, " +
               "- 1 dígito, " +
               "- 1 carácter especial (!@#$%^&* etc.)";
    }
}
