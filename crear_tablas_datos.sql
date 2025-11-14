-- ===============================================
-- SCRIPT DE CREACIÓN DE TABLAS Y DATOS DE PRUEBA
-- ===============================================

-- Tabla Usuario
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(150),
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    password VARCHAR(255),
    rol VARCHAR(50)
);

-- Tabla Cliente
CREATE TABLE IF NOT EXISTS cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(150),
    phone VARCHAR(20),
    address VARCHAR(255)
);

-- Tabla Producto
CREATE TABLE IF NOT EXISTS producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    tipo VARCHAR(50),
    unidad_medida VARCHAR(20),
    precio_unitario DECIMAL(10,2),
    stock INT
);

-- Tabla Proveedor
CREATE TABLE IF NOT EXISTS proveedor (
    nombre VARCHAR(100) PRIMARY KEY,
    contacto VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(150),
    direccion VARCHAR(255)
);

-- Tabla Proyecto
CREATE TABLE IF NOT EXISTS proyecto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion VARCHAR(500),
    ubicacion VARCHAR(255),
    estado VARCHAR(50),
    id_cliente BIGINT,
    usuario_id BIGINT,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Tabla Cronograma
CREATE TABLE IF NOT EXISTS cronograma (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_inicio DATE,
    fecha_final DATE,
    dias_estimados INT,
    estado VARCHAR(50),
    id_proyecto BIGINT,
    usuario_id BIGINT,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Tabla Tarea
CREATE TABLE IF NOT EXISTS tarea (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hora_inicio TIME,
    hora_final TIME,
    descripcion VARCHAR(500),
    estado VARCHAR(50),
    id_cronograma BIGINT,
    usuario_id BIGINT,
    id_proyecto BIGINT,
    FOREIGN KEY (id_cronograma) REFERENCES cronograma(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id)
);

-- Tabla Cuota
CREATE TABLE IF NOT EXISTS cuota (
    id_cuota BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_metro VARCHAR(50),
    costo_metro DECIMAL(10,2),
    cantidad_metro DECIMAL(10,2),
    costo_muebles DECIMAL(10,2),
    total DECIMAL(12,2),
    estado VARCHAR(50),
    numero_muebles INT,
    comentarios VARCHAR(500),
    id_proyecto BIGINT,
    usuario_id BIGINT,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Tabla Diseno
CREATE TABLE IF NOT EXISTS diseno (
    id_diseno BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cuota BIGINT,
    url_render VARCHAR(500),
    plano_laminar VARCHAR(500),
    aprobado BOOLEAN,
    fecha_aprobacion DATE,
    commentarios VARCHAR(500),
    usuario_id BIGINT,
    FOREIGN KEY (id_cuota) REFERENCES cuota(id_cuota),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Tabla Plan de Pago
CREATE TABLE IF NOT EXISTS plan_pago (
    id_plan_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto BIGINT,
    total_deuda DECIMAL(12,2),
    total_pagado DECIMAL(12,2),
    numero_deuda INT,
    numero_pagos INT,
    estado VARCHAR(50),
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id)
);

-- Tabla Pago
CREATE TABLE IF NOT EXISTS pago (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    total DECIMAL(10,2),
    estado VARCHAR(50),
    id_cliente BIGINT,
    id_plan_pago BIGINT,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    FOREIGN KEY (id_plan_pago) REFERENCES plan_pago(id_plan_pago)
);

-- Tabla Producto_Project
CREATE TABLE IF NOT EXISTS producto_project (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT,
    sobrante INT,
    id_proyecto BIGINT,
    id_producto BIGINT,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id),
    FOREIGN KEY (id_producto) REFERENCES producto(id)
);

-- Tabla Producto_Proveedor
CREATE TABLE IF NOT EXISTS producto_proveedor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    total DECIMAL(12,2),
    id_supplier VARCHAR(100),
    id_material BIGINT,
    FOREIGN KEY (id_supplier) REFERENCES proveedor(nombre),
    FOREIGN KEY (id_material) REFERENCES producto(id)
);

-- Tabla Servicio_Evaluacion
CREATE TABLE IF NOT EXISTS servicio_evaluacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comentarios VARCHAR(500),
    id_proyecto BIGINT,
    calificacion_diseno INT,
    calificacion_fabricacion INT,
    calificacion_instalacion INT,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id)
);

-- Tabla Persona
CREATE TABLE IF NOT EXISTS persona (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    email VARCHAR(150),
    rol VARCHAR(50)
);

-- ===============================================
-- INSERCIÓN DE DATOS DE PRUEBA
-- ===============================================

-- Insertar datos en tabla Usuario
INSERT INTO usuario (nombre, email, telefono, direccion, password, rol) VALUES
('Juan Pérez', 'juan.perez@example.com', '78912345', 'Calle Principal 123', 'pass123', 'DISEÑADOR'),
('María García', 'maria.garcia@example.com', '78912346', 'Avenida Central 456', 'pass123', 'COORDINADOR'),
('Carlos López', 'carlos.lopez@example.com', '78912347', 'Calle Secundaria 789', 'pass123', 'SUPERVISOR'),
('Ana Martínez', 'ana.martinez@example.com', '78912348', 'Avenida Principal 321', 'pass123', 'DISEÑADOR'),
('Pedro Rodríguez', 'pedro.rodriguez@example.com', '78912349', 'Calle Tercera 654', 'pass123', 'TÉCNICO'),
('Laura Sánchez', 'laura.sanchez@example.com', '78912350', 'Avenida Cuarta 987', 'pass123', 'COORDINADOR'),
('Roberto Díaz', 'roberto.diaz@example.com', '78912351', 'Calle Quinta 147', 'pass123', 'SUPERVISOR'),
('Sofía Ramírez', 'sofia.ramirez@example.com', '78912352', 'Avenida Sexta 258', 'pass123', 'DISEÑADOR'),
('Miguel Torres', 'miguel.torres@example.com', '78912353', 'Calle Séptima 369', 'pass123', 'TÉCNICO'),
('Elena Flores', 'elena.flores@example.com', '78912354', 'Avenida Octava 741', 'pass123', 'COORDINADOR');

-- Insertar datos en tabla Cliente
INSERT INTO cliente (name, email, phone, address) VALUES
('Empresa ABC', 'contacto@empresaabc.com', '32123456', 'La Paz, Bolivia'),
('Constructora XYZ', 'info@constructora.com', '32123457', 'Cochabamba, Bolivia'),
('Inmobiliaria DEF', 'ventas@inmobiliaria.com', '32123458', 'Santa Cruz, Bolivia'),
('Empresa de Diseño', 'diseño@empresa.com', '32123459', 'La Paz, Bolivia'),
('Consultoría GHI', 'consulta@consultoria.com', '32123460', 'Oruro, Bolivia'),
('Constructora JKL', 'obra@constructora2.com', '32123461', 'Potosí, Bolivia'),
('Retail ABC', 'tienda@retail.com', '32123462', 'Sucre, Bolivia'),
('Hotel XYZ', 'reserva@hotel.com', '32123463', 'Tarija, Bolivia'),
('Oficinas Modernas', 'admin@oficinas.com', '32123464', 'La Paz, Bolivia'),
('Centro Comercial', 'gerencia@centro.com', '32123465', 'Cochabamba, Bolivia');

-- Insertar datos en tabla Producto
INSERT INTO producto (nombre, tipo, unidad_medida, precio_unitario, stock) VALUES
('Pisos de Cerámica', 'REVESTIMIENTO', 'm²', 120.00, 500),
('Pintura Látex', 'PINTURA', 'GALÓN', 85.50, 200),
('Puertas de Madera', 'CARPINTERÍA', 'UNIDAD', 350.00, 50),
('Ventanas de Aluminio', 'HERRERÍA', 'UNIDAD', 450.00, 40),
('Tuberías PVC', 'PLOMERÍA', 'METRO', 45.00, 1000),
('Cables Eléctricos', 'ELECTRICIDAD', 'METRO', 12.50, 800),
('Sanitarios', 'PLOMERÍA', 'UNIDAD', 280.00, 60),
('Láminas de Yeso', 'ACABADOS', 'UNIDAD', 55.00, 300),
('Tornillos y Clavos', 'FERRETERÍA', 'KILOGRAMO', 25.00, 500),
('Vidrio Templado', 'ACABADOS', 'm²', 150.00, 150);

-- Insertar datos en tabla Proveedor
INSERT INTO proveedor (nombre, contacto, telefono, email, direccion) VALUES
('Proveedor Ace', 'Roberto García', '78654321', 'ventas@ace.com', 'Av. Arce 500, La Paz'),
('Distribuidora Central', 'Carlos Mendez', '78654322', 'info@distribuidora.com', 'Calle Comercial 123, Cochabamba'),
('Materiales del Oriente', 'Jorge López', '78654323', 'contacto@materiales.com', 'Av. Santa Cruz 456, Santa Cruz'),
('Ferretería Mayor', 'Patricia Sáenz', '78654324', 'ventas@ferreteria.com', 'Calle Principal 789, La Paz'),
('Distribuidor Técnico', 'Marco Rodríguez', '78654325', 'info@tecnico.com', 'Av. Viedma 321, Cochabamba'),
('Importadora América', 'Diana Flores', '78654326', 'ventas@importadora.com', 'Calle Oruro 654, La Paz'),
('Proveedor del Norte', 'Luis Ramírez', '78654327', 'contacto@norte.com', 'Av. Montes 987, Oruro'),
('Materiales Industriales', 'Susana Torres', '78654328', 'info@industrial.com', 'Calle Industrial 147, Potosí'),
('Proveedora Global', 'Ricardo Díaz', '78654329', 'ventas@global.com', 'Av. Libertad 258, Sucre'),
('Suministros ABC', 'Verónica Álvarez', '78654330', 'contacto@suministros.com', 'Calle Comercio 369, Tarija');

-- Insertar datos en tabla Proyecto
INSERT INTO proyecto (nombre, descripcion, ubicacion, estado, id_cliente, usuario_id) VALUES
('Proyecto Residencial La Paz', 'Construcción de complejo residencial de 50 apartamentos', 'Av. Costanera, La Paz', 'EN_PROGRESO', 1, 1),
('Centro Comercial Cochabamba', 'Remodelación de centro comercial existente', 'Calle Heroínas, Cochabamba', 'EN_DISEÑO', 2, 2),
('Oficinas Modernas Santa Cruz', 'Construcción de edificio de oficinas de 15 pisos', 'Av. San Martín, Santa Cruz', 'COMPLETADO', 3, 3),
('Renovación Hotel XYZ', 'Renovación completa de instalaciones hoteleras', 'Centro histórico, Oruro', 'EN_PROGRESO', 4, 4),
('Tienda Retail La Paz', 'Construcción de tienda retail de 2000 m²', 'Zona de Los Pinos, La Paz', 'EN_DISEÑO', 5, 5),
('Remodelación Oficinas', 'Remodelación de oficinas corporativas', 'Av. Arce, La Paz', 'EN_PROGRESO', 6, 6),
('Centro de Distribución', 'Construcción de almacén y centro de distribución', 'Zona Industrial, Cochabamba', 'COMPLETADO', 7, 7),
('Residencias Ejecutivas', 'Proyecto de viviendas de lujo', 'Av. Costanera, Santa Cruz', 'EN_DISEÑO', 8, 8),
('Ampliación Clínica', 'Ampliación de instalaciones clínicas', 'Calle Médica, La Paz', 'EN_PROGRESO', 9, 9),
('Remodelación Escuela', 'Remodelación y actualización de infraestructura educativa', 'Centro educativo, Cochabamba', 'COMPLETADO', 10, 10);

-- Insertar datos en tabla Cronograma
INSERT INTO cronograma (fecha_inicio, fecha_final, dias_estimados, estado, id_proyecto, usuario_id) VALUES
('2024-01-15', '2024-06-30', 167, 'EN_PROGRESO', 1, 1),
('2024-02-01', '2024-04-30', 89, 'EN_PROGRESO', 2, 2),
('2023-06-01', '2023-12-31', 214, 'COMPLETADO', 3, 3),
('2024-03-01', '2024-07-15', 137, 'EN_PROGRESO', 4, 4),
('2024-02-15', '2024-05-31', 106, 'PENDIENTE', 5, 5),
('2024-01-20', '2024-05-20', 121, 'EN_PROGRESO', 6, 6),
('2023-08-01', '2023-11-30', 122, 'COMPLETADO', 7, 7),
('2024-03-10', '2024-08-10', 153, 'EN_PROGRESO', 8, 8),
('2024-02-01', '2024-06-30', 180, 'EN_PROGRESO', 9, 9),
('2023-09-01', '2023-12-15', 106, 'COMPLETADO', 10, 10);

-- Insertar datos en tabla Tarea
INSERT INTO tarea (hora_inicio, hora_final, descripcion, estado, id_cronograma, usuario_id, id_proyecto) VALUES
('08:00:00', '12:00:00', 'Excavación y preparación del terreno', 'COMPLETADO', 1, 1, 1),
('09:00:00', '17:00:00', 'Instalación de estructuras metálicas', 'EN_PROGRESO', 2, 2, 2),
('08:30:00', '16:30:00', 'Trabajos de albañilería', 'COMPLETADO', 3, 3, 3),
('10:00:00', '14:00:00', 'Instalación de sistemas eléctricos', 'EN_PROGRESO', 4, 4, 4),
('08:00:00', '12:00:00', 'Diseño de planos arquitectónicos', 'PENDIENTE', 5, 5, 5),
('09:00:00', '17:00:00', 'Pintura y acabados finales', 'EN_PROGRESO', 6, 6, 6),
('08:00:00', '16:00:00', 'Instalación de sistemas de climatización', 'COMPLETADO', 7, 7, 7),
('10:00:00', '18:00:00', 'Trabajos de carpintería y carpintería metálica', 'EN_PROGRESO', 8, 8, 8),
('08:00:00', '12:00:00', 'Instalación de plomería sanitaria', 'EN_PROGRESO', 9, 9, 9),
('09:00:00', '17:00:00', 'Instalación de vidriería', 'COMPLETADO', 10, 10, 10);

-- Insertar datos en tabla Cuota
INSERT INTO cuota (tipo_metro, costo_metro, cantidad_metro, costo_muebles, total, estado, numero_muebles, comentarios, id_proyecto, usuario_id) VALUES
('MODERNO', 1200.00, 2500.00, 150000.00, 3150000.00, 'APROBADO', 50, 'Cotización aprobada por cliente', 1, 1),
('CONTEMPORÁNEO', 1500.00, 3000.00, 200000.00, 4700000.00, 'PENDIENTE', 60, 'Esperando revisión', 2, 2),
('CLÁSICO', 1000.00, 2000.00, 120000.00, 2120000.00, 'APROBADO', 40, 'Proyecto completado', 3, 3),
('MINIMALISTA', 1100.00, 1800.00, 100000.00, 1980000.00, 'RECHAZADO', 35, 'Cliente solicitó cambios', 4, 4),
('MODERNO', 1300.00, 2200.00, 180000.00, 3040000.00, 'PENDIENTE', 45, 'En revisión de presupuesto', 5, 5),
('INDUSTRIAL', 950.00, 2800.00, 140000.00, 2806000.00, 'APROBADO', 55, 'Cotización final confirmada', 6, 6),
('MODERNO', 1250.00, 3500.00, 220000.00, 4595000.00, 'APROBADO', 70, 'Centro comercial - proyecto completado', 7, 7),
('CONTEMPORÁNEO', 1400.00, 2900.00, 210000.00, 4270000.00, 'PENDIENTE', 65, 'Cliente revisa propuesta', 8, 8),
('MODERNO', 1150.00, 2400.00, 160000.00, 2920000.00, 'APROBADO', 50, 'Clínica - proyecto en progreso', 9, 9),
('CLÁSICO', 1050.00, 2100.00, 130000.00, 2335000.00, 'APROBADO', 42, 'Escuela - proyecto completado', 10, 10);

-- Insertar datos en tabla Diseno
INSERT INTO diseno (id_cuota, url_render, plano_laminar, aprobado, fecha_aprobacion, commentarios, usuario_id) VALUES
(1, 'http://render.example.com/diseno1.jpg', 'http://planos.example.com/plano1.pdf', TRUE, '2024-01-20', 'Diseño aprobado por cliente', 1),
(2, 'http://render.example.com/diseno2.jpg', 'http://planos.example.com/plano2.pdf', FALSE, NULL, 'Pendiente de revisión', 2),
(3, 'http://render.example.com/diseno3.jpg', 'http://planos.example.com/plano3.pdf', TRUE, '2023-08-15', 'Diseño ejecutado correctamente', 3),
(4, 'http://render.example.com/diseno4.jpg', 'http://planos.example.com/plano4.pdf', FALSE, NULL, 'Rechazado - requiere revisión', 4),
(5, 'http://render.example.com/diseno5.jpg', 'http://planos.example.com/plano5.pdf', FALSE, NULL, 'En revisión', 5),
(6, 'http://render.example.com/diseno6.jpg', 'http://planos.example.com/plano6.pdf', TRUE, '2024-02-10', 'Diseño incorporado al proyecto', 6),
(7, 'http://render.example.com/diseno7.jpg', 'http://planos.example.com/plano7.pdf', TRUE, '2023-11-20', 'Centro comercial - diseño final', 7),
(8, 'http://render.example.com/diseno8.jpg', 'http://planos.example.com/plano8.pdf', FALSE, NULL, 'Esperando aprobación cliente', 8),
(9, 'http://render.example.com/diseno9.jpg', 'http://planos.example.com/plano9.pdf', TRUE, '2024-03-05', 'Clínica - diseño aprobado', 9),
(10, 'http://render.example.com/diseno10.jpg', 'http://planos.example.com/plano10.pdf', TRUE, '2023-12-10', 'Escuela - diseño ejecutado', 10);

-- Insertar datos en tabla Plan_Pago
INSERT INTO plan_pago (id_proyecto, total_deuda, total_pagado, numero_deuda, numero_pagos, estado) VALUES
(1, 3150000.00, 1575000.00, 1, 1, 'EN_PROGRESO'),
(2, 4700000.00, 0.00, 1, 0, 'PENDIENTE'),
(3, 2120000.00, 2120000.00, 1, 1, 'PAGADO'),
(4, 1980000.00, 0.00, 1, 0, 'PENDIENTE'),
(5, 3040000.00, 912000.00, 1, 1, 'EN_PROGRESO'),
(6, 2806000.00, 1403000.00, 1, 1, 'EN_PROGRESO'),
(7, 4595000.00, 4595000.00, 1, 1, 'PAGADO'),
(8, 4270000.00, 1285000.00, 1, 1, 'EN_PROGRESO'),
(9, 2920000.00, 730000.00, 1, 1, 'EN_PROGRESO'),
(10, 2335000.00, 2335000.00, 1, 1, 'PAGADO');

-- Insertar datos en tabla Pago
INSERT INTO pago (fecha, total, estado, id_cliente, id_plan_pago) VALUES
('2024-02-15', 1575000.00, 'Pagado', 1, 1),
('2024-03-20', 0.00, 'Pendiente', 2, 2),
('2023-12-10', 2120000.00, 'Pagado', 3, 3),
('2024-03-15', 0.00, 'Pendiente', 4, 4),
('2024-02-28', 912000.00, 'Pagado', 5, 5),
('2024-03-05', 1403000.00, 'Pagado', 6, 6),
('2023-12-20', 4595000.00, 'Pagado', 7, 7),
('2024-03-10', 1285000.00, 'Pagado', 8, 8),
('2024-02-20', 730000.00, 'Pagado', 9, 9),
('2023-12-15', 2335000.00, 'Pagado', 10, 10);

-- Insertar datos en tabla Producto_Project
INSERT INTO producto_project (cantidad, sobrante, id_proyecto, id_producto) VALUES
(100, 20, 1, 1),
(50, 5, 2, 2),
(25, 2, 3, 3),
(40, 8, 4, 4),
(200, 30, 5, 5),
(150, 25, 6, 6),
(80, 15, 7, 7),
(120, 20, 8, 8),
(300, 50, 9, 9),
(90, 10, 10, 10);

-- Insertar datos en tabla Producto_Proveedor
INSERT INTO producto_proveedor (cantidad, precio_unitario, total, id_supplier, id_material) VALUES
(500, 120.00, 60000.00, 'Proveedor Ace', 1),
(200, 85.50, 17100.00, 'Distribuidora Central', 2),
(50, 350.00, 17500.00, 'Materiales del Oriente', 3),
(40, 450.00, 18000.00, 'Ferretería Mayor', 4),
(1000, 45.00, 45000.00, 'Distribuidor Técnico', 5),
(800, 12.50, 10000.00, 'Importadora América', 6),
(60, 280.00, 16800.00, 'Proveedor del Norte', 7),
(300, 55.00, 16500.00, 'Materiales Industriales', 8),
(500, 25.00, 12500.00, 'Proveedora Global', 9),
(150, 150.00, 22500.00, 'Suministros ABC', 10);

-- Insertar datos en tabla Servicio_Evaluacion
INSERT INTO servicio_evaluacion (comentarios, id_proyecto, calificacion_diseno, calificacion_fabricacion, calificacion_instalacion) VALUES
('Proyecto bien ejecutado', 1, 4, 4, 4),
('Proyecto en evaluación', 2, 0, 0, 0),
('Proyecto completado satisfactoriamente', 3, 5, 5, 5),
('Necesita revisión de detalles', 4, 2, 3, 2),
('Proyecto en desarrollo', 5, 3, 3, 3),
('Avance adecuado', 6, 4, 4, 4),
('Proyecto completado exitosamente', 7, 5, 5, 5),
('Progreso satisfactorio', 8, 4, 4, 3),
('Evaluación en progreso', 9, 4, 4, 4),
('Proyecto exitoso', 10, 5, 5, 5);

-- Insertar datos en tabla Persona
INSERT INTO persona (nombre, email, rol) VALUES
('Juan Pérez', 'juan@persona.com', 'INGENIERO'),
('María García', 'maria@persona.com', 'ARQUITECTA'),
('Carlos López', 'carlos@persona.com', 'SUPERVISOR'),
('Ana Martínez', 'ana@persona.com', 'DISEÑADORA'),
('Pedro Rodríguez', 'pedro@persona.com', 'TÉCNICO'),
('Laura Sánchez', 'laura@persona.com', 'COORDINADORA'),
('Roberto Díaz', 'roberto@persona.com', 'CAPATAZ'),
('Sofía Ramírez', 'sofia@persona.com', 'DISEÑADORA'),
('Miguel Torres', 'miguel@persona.com', 'OPERARIO'),
('Elena Flores', 'elena@persona.com', 'DIRECTORA');

-- ===============================================
-- FIN DEL SCRIPT
-- ===============================================
