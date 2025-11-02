-- Script para crear la tabla persona en PostgreSQL
-- Base de datos: db_grupo03sa

CREATE TABLE IF NOT EXISTS persona (
    ci VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    cargo VARCHAR(100),
    telefono VARCHAR(20),
    celular VARCHAR(20),
    email VARCHAR(150)
);

-- Insertar datos de ejemplo
INSERT INTO persona (ci, nombre, apellido, cargo, telefono, celular, email) VALUES
('12345678', 'Juan Carlos', 'Perez Lopez', 'Estudiante', '33554433', '71055123', 'juan.perez@uagrm.edu.bo'),
('87654321', 'Maria Elena', 'Garcia Torres', 'Docente', '33221100', '72334455', 'maria.garcia@uagrm.edu.bo'),
('11223344', 'Carlos Alberto', 'Rodriguez Vega', 'Administrativo', '33445566', '73556677', 'carlos.rodriguez@uagrm.edu.bo')
ON CONFLICT (ci) DO NOTHING;

-- Verificar los datos insertados
SELECT * FROM persona;