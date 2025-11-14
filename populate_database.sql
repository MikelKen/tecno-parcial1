-- ============================================================
-- SCRIPT DE POBLACIÓN DE BASE DE DATOS - TECNO PROYECTO
-- ============================================================
-- Orden de inserción: Respetando relaciones entre tablas

-- ============================================================
-- 1. TABLA: persona
-- ============================================================
INSERT INTO persona (ci, nombre, apellido, cargo, telefono, celular, email) VALUES
('1234567', 'Juan', 'García', 'Gerente', '3333333', '70123456', 'juan.garcia@example.com'),
('2345678', 'María', 'López', 'Diseñadora', '3334444', '70234567', 'maria.lopez@example.com'),
('3456789', 'Carlos', 'Rodríguez', 'Instalador', '3335555', '70345678', 'carlos.rodriguez@example.com'),
('4567890', 'Ana', 'Martínez', 'Administradora', '3336666', '70456789', 'ana.martinez@example.com'),
('5678901', 'Pedro', 'Fernández', 'Técnico', '3337777', '70567890', 'pedro.fernandez@example.com'),
('6789012', 'Laura', 'González', 'Coordinadora', '3338888', '70678901', 'laura.gonzalez@example.com'),
('7890123', 'Roberto', 'Díaz', 'Supervisor', '3339999', '70789012', 'roberto.diaz@example.com'),
('8901234', 'Sofía', 'Herrera', 'Diseñadora', '3330000', '70890123', 'sofia.herrera@example.com'),
('9012345', 'Miguel', 'Sánchez', 'Instalador', '3331111', '70901234', 'miguel.sanchez@example.com'),
('0123456', 'Elena', 'Torres', 'Asistente', '3332222', '71012345', 'elena.torres@example.com');

-- ============================================================
-- 2. TABLA: app_user
-- ============================================================
INSERT INTO app_user (name, email, phone, address, password, role) VALUES
('juan_admin', 'juan.admin@sistema.com', '3333333', 'Av. Principal 123', 'pass123', 'ADMIN'),
('maria_designer', 'maria.designer@sistema.com', '3334444', 'Calle 2 456', 'pass123', 'DESIGNER'),
('carlos_installer', 'carlos.installer@sistema.com', '3335555', 'Calle 3 789', 'pass123', 'INSTALLER'),
('ana_admin', 'ana.admin@sistema.com', '3336666', 'Calle 4 101', 'pass123', 'ADMIN'),
('pedro_designer', 'pedro.designer@sistema.com', '3337777', 'Calle 5 202', 'pass123', 'DESIGNER'),
('laura_installer', 'laura.installer@sistema.com', '3338888', 'Calle 6 303', 'pass123', 'INSTALLER'),
('roberto_admin', 'roberto.admin@sistema.com', '3339999', 'Calle 7 404', 'pass123', 'ADMIN'),
('sofia_designer', 'sofia.designer@sistema.com', '3330000', 'Calle 8 505', 'pass123', 'DESIGNER'),
('miguel_installer', 'miguel.installer@sistema.com', '3331111', 'Calle 9 606', 'pass123', 'INSTALLER'),
('elena_designer', 'elena.designer@sistema.com', '3332222', 'Calle 10 707', 'pass123', 'DESIGNER');

-- ============================================================
-- 3. TABLA: client
-- ============================================================
INSERT INTO client (name, email, phone, address) VALUES
('Construcciones Andinas S.A.', 'contacto@andinas.com', '33123456', 'Av. 6 de Agosto 789, La Paz'),
('Empresa Comercial Bolivia', 'ventas@ecb.bo', '33234567', 'Calle Bolívar 456, Santa Cruz'),
('Servicios Inmobiliarios Plus', 'info@sipplus.com', '33345678', 'Av. Arce 321, La Paz'),
('Tienda Moderna S.A.', 'tienda@moderna.com', '33456789', 'Paseo Prado 654, La Paz'),
('Consultora Empresarial', 'consulta@empresarial.com', '33567890', 'Av. Montes 987, La Paz'),
('Corporación Industrial Ltda.', 'corp@industrial.com', '33678901', 'Calle Comercio 123, Cochabamba'),
('Desarrollo Urbano S.A.', 'desarrollo@urbano.com', '33789012', 'Av. Cristo Redentor 456, La Paz'),
('Negocios Integrados Bolivia', 'negocio@integrados.com', '33890123', 'Calle Ayacucho 789, Sucre'),
('Importaciones Directas', 'importa@directas.com', '33901234', 'Av. Ballivián 321, La Paz'),
('Soluciones Empresariales', 'solucion@empresas.com', '33012345', 'Calle Potosí 654, Oruro');

-- ============================================================
-- 4. TABLA: project
-- ============================================================
INSERT INTO project (name, description, location, state, id_client, user_id) VALUES
('Oficinas Centro Comercial Andino', 'Diseño e instalación de oficinas corporativas', 'La Paz - Centro', 'En Proceso', 1, 1),
('Tienda Retail Santa Cruz', 'Remodelación y equipamiento de tienda', 'Santa Cruz - Centro', 'Completado', 2, 2),
('Showroom Inmobiliario', 'Diseño de showroom para venta de propiedades', 'La Paz - Zona Sur', 'Planificación', 3, 1),
('Oficinas Corporativas Cochabamba', 'Instalación de divisiones y decoración', 'Cochabamba', 'En Proceso', 6, 3),
('Boutique Exclusiva', 'Diseño e instalación boutique de lujo', 'La Paz - Zona Posh', 'Completado', 4, 2),
('Consultorio Médico Premium', 'Equipamiento de consultorio especializado', 'La Paz - Sopocachi', 'En Proceso', 5, 4),
('Centro de Distribución', 'Divisiones y almacenaje para centro logístico', 'Cochabamba Industrial', 'Planificación', 7, 5),
('Galería de Arte Moderno', 'Instalación de paneles y decoración artística', 'La Paz - Casco Viejo', 'En Proceso', 8, 2),
('Oficinas Administrativas Sucre', 'Remodelación completa de sede administrativa', 'Sucre', 'Completado', 9, 6),
('Espacio Comercial Multipropósito', 'Diseño flexible para múltiples usos', 'Oruro Centro', 'En Proceso', 10, 7);

-- ============================================================
-- 5. TABLA: schedule
-- ============================================================
INSERT INTO schedule (init_date, final_date, estimate_days, state, id_project, user_id) VALUES
('2025-01-10', '2025-02-10', 31, 'En Proceso', 1, 1),
('2024-11-01', '2024-12-15', 44, 'Completado', 2, 2),
('2025-02-01', '2025-04-01', 60, 'Planificación', 3, 1),
('2025-01-15', '2025-03-15', 60, 'En Proceso', 4, 3),
('2024-10-01', '2024-11-30', 60, 'Completado', 5, 2),
('2025-01-20', '2025-03-20', 60, 'En Proceso', 6, 4),
('2025-03-01', '2025-05-30', 90, 'Planificación', 7, 5),
('2025-01-05', '2025-02-28', 54, 'En Proceso', 8, 2),
('2024-09-15', '2024-11-15', 61, 'Completado', 9, 6),
('2025-02-10', '2025-04-10', 59, 'En Proceso', 10, 7);

-- ============================================================
-- 6. TABLA: task
-- ============================================================
INSERT INTO task (init_hour, final_hour, description, state, id_schedule, user_id, id_project) VALUES
('08:00:00', '10:00:00', 'Medición y diseño inicial', 'Completada', 1, 1, 1),
('10:30:00', '13:30:00', 'Confección de planos digitales', 'En Progreso', 1, 2, 1),
('14:00:00', '16:30:00', 'Presentación de diseño al cliente', 'Completada', 2, 2, 2),
('09:00:00', '12:00:00', 'Preparación de materiales', 'En Progreso', 4, 3, 4),
('13:00:00', '16:00:00', 'Instalación de estructura base', 'Completada', 5, 3, 5),
('08:30:00', '11:30:00', 'Revisión de especificaciones', 'Pendiente', 3, 4, 3),
('07:00:00', '10:00:00', 'Recepción y clasificación de materiales', 'En Progreso', 7, 5, 7),
('15:00:00', '17:00:00', 'Acabados finales', 'Completada', 8, 2, 8),
('08:00:00', '17:00:00', 'Instalación completa', 'Completada', 9, 6, 9),
('09:00:00', '14:00:00', 'Diseño de espacio modular', 'En Progreso', 10, 7, 10);

-- ============================================================
-- 7. TABLA: supplier
-- ============================================================
INSERT INTO supplier (name, contact, phone, email, address) VALUES
('Proveedores Textiles Bolivia', 'Juan Pérez', '3333333', 'textiles@proveedores.com', 'Av. Villazon 456, La Paz'),
('Materiales de Construcción S.A.', 'Carlos Morales', '3334444', 'materiales@construccion.com', 'Zona Industrial La Paz 123'),
('Importadora de Telas Premium', 'Laura Rodríguez', '3335555', 'telas@premium.com', 'Calle Comercio 789, Santa Cruz'),
('Herrajes y Accesorios', 'Miguel Flores', '3336666', 'herrajes@accesorios.com', 'Av. 6 de Agosto 321, La Paz'),
('Distribuidora de Muebles Modernos', 'Ana García', '3337777', 'muebles@modernos.com', 'Av. Arce 654, La Paz'),
('Pinturas y Acabados Profesionales', 'Roberto Silva', '3338888', 'pinturas@profesional.com', 'Calle Cochabamba 987, La Paz'),
('Vidriería Especializada', 'Sofía Martínez', '3339999', 'vidrios@especial.com', 'Zona Industrial Cochabamba 456'),
('Accesorios para Interiores', 'Elena López', '3330000', 'accesorios@interiores.com', 'Calle Bolívar 123, Sucre'),
('Estructuras Metálicas Industrial', 'Pedro Vélez', '3331111', 'estructuras@industrial.com', 'Av. Montes 789, Cochabamba'),
('Textiles Importados Directo', 'Valentina Cruz', '3332222', 'textiles@directo.com', 'Av. Ballivián 321, La Paz');

-- ============================================================
-- 8. TABLA: material
-- ============================================================
INSERT INTO material (name, type, unit_measure, unit_price, stock) VALUES
('Tela Linaza Premium', 'Textil', 'm2', 45.50, 500),
('Estructura Aluminio 40x40', 'Herraje', 'ml', 78.00, 300),
('Pintura Acrílica Industrial', 'Acabado', 'gl', 125.00, 150),
('Vidrio Templado 8mm', 'Vidrio', 'm2', 95.75, 200),
('Mueble Modular Estándar', 'Mueble', 'un', 350.00, 80),
('Cortinero Aluminio Anodizado', 'Herraje', 'ml', 32.50, 400),
('Espuma Acústica 50mm', 'Textil', 'm2', 28.00, 250),
('Herraje de Fijación Inox', 'Herraje', 'kg', 18.75, 600),
('Papel Tapiz Decorativo', 'Acabado', 'rollo', 55.00, 180),
('Piso Laminado Premium', 'Material', 'm2', 65.00, 350);

-- ============================================================
-- 9. TABLA: material_supplier
-- ============================================================
INSERT INTO material_supplier (quantity, unit_price, total, id_supplier, id_material) VALUES
(100, 45.50, 4550.00, 'Proveedores Textiles Bolivia', 1),
(50, 78.00, 3900.00, 'Materiales de Construcción S.A.', 2),
(30, 125.00, 3750.00, 'Pinturas y Acabados Profesionales', 3),
(40, 95.75, 3830.00, 'Vidriería Especializada', 4),
(20, 350.00, 7000.00, 'Distribuidora de Muebles Modernos', 5),
(80, 32.50, 2600.00, 'Herrajes y Accesorios', 6),
(60, 28.00, 1680.00, 'Importadora de Telas Premium', 7),
(120, 18.75, 2250.00, 'Estructuras Metálicas Industrial', 8),
(45, 55.00, 2475.00, 'Accesorios para Interiores', 9),
(70, 65.00, 4550.00, 'Textiles Importados Directo', 10);

-- ============================================================
-- 10. TABLA: material_project
-- ============================================================
INSERT INTO material_project (quantity, left_over, id_project, id_material) VALUES
(80, 20, 1, 1),
(30, 5, 1, 2),
(20, 2, 2, 3),
(25, 8, 2, 4),
(15, 0, 3, 5),
(60, 10, 4, 6),
(45, 5, 5, 7),
(100, 15, 6, 8),
(35, 3, 7, 9),
(50, 10, 8, 10);

-- ============================================================
-- 11. TABLA: quote (Cotizaciones)
-- ============================================================
INSERT INTO quote (type_metro, cost_metro, quantity_metro, cost_furniture, total, state, furniture_number, comments, id_project, user_id) VALUES
('Standard', 150.00, 120.00, 5000.00, 23000.00, 'Aprobada', 12, 'Cotización para oficinas centro', 1, 1),
('Premium', 200.00, 85.50, 8500.00, 25575.00, 'Aprobada', 15, 'Cotización tienda retail', 2, 2),
('Estándar', 120.00, 150.00, 4500.00, 22500.00, 'Pendiente', 10, 'Cotización showroom', 3, 1),
('Premium', 180.00, 100.00, 6000.00, 24000.00, 'Aprobada', 14, 'Cotización oficinas corporativas', 4, 3),
('Deluxe', 250.00, 60.00, 9000.00, 24000.00, 'Aprobada', 18, 'Cotización boutique exclusiva', 5, 2),
('Standard', 140.00, 110.00, 5500.00, 20900.00, 'Aprobada', 8, 'Cotización consultorio médico', 6, 4),
('Industrial', 100.00, 200.00, 3000.00, 23000.00, 'Pendiente', 5, 'Cotización centro distribución', 7, 5),
('Premium', 190.00, 95.00, 7500.00, 25550.00, 'Aprobada', 16, 'Cotización galería de arte', 8, 2),
('Standard', 130.00, 140.00, 5000.00, 23820.00, 'Aprobada', 11, 'Cotización oficinas administrativas', 9, 6),
('Modular', 160.00, 125.00, 6000.00, 26000.00, 'Pendiente', 9, 'Cotización espacio multipropósito', 10, 7);

-- ============================================================
-- 12. TABLA: design (Diseños)
-- ============================================================
INSERT INTO design (id_quote, url_render, laminated_plane, approved, approved_date, comments, user_id) VALUES
(1, 'https://render.proyecto1.com/oficinas.jpg', 'https://planes.proyecto1.com/oficinas_laminado.pdf', true, '2025-01-12', 'Diseño aprobado por cliente', 2),
(2, 'https://render.proyecto2.com/tienda.jpg', 'https://planes.proyecto2.com/tienda_laminado.pdf', true, '2024-11-10', 'Diseño final aprobado', 2),
(3, 'https://render.proyecto3.com/showroom.jpg', 'https://planes.proyecto3.com/showroom_laminado.pdf', false, NULL, 'Aguardando revisión del cliente', 1),
(4, 'https://render.proyecto4.com/oficinas_corp.jpg', 'https://planes.proyecto4.com/oficinas_corp_laminado.pdf', true, '2025-01-18', 'Diseño aprobado con cambios menores', 2),
(5, 'https://render.proyecto5.com/boutique.jpg', 'https://planes.proyecto5.com/boutique_laminado.pdf', true, '2024-11-05', 'Diseño de lujo aprobado', 8),
(6, 'https://render.proyecto6.com/consultorio.jpg', 'https://planes.proyecto6.com/consultorio_laminado.pdf', true, '2025-01-25', 'Diseño médico aprobado', 2),
(7, 'https://render.proyecto7.com/distribucion.jpg', 'https://planes.proyecto7.com/distribucion_laminado.pdf', false, NULL, 'En evaluación', 1),
(8, 'https://render.proyecto8.com/galeria.jpg', 'https://planes.proyecto8.com/galeria_laminado.pdf', true, '2025-01-20', 'Diseño artístico aprobado', 8),
(9, 'https://render.proyecto9.com/sucre.jpg', 'https://planes.proyecto9.com/sucre_laminado.pdf', true, '2024-11-20', 'Diseño administrativo finalizado', 2),
(10, 'https://render.proyecto10.com/multipropósito.jpg', 'https://planes.proyecto10.com/multipropósito_laminado.pdf', false, NULL, 'Diseño modular en revisión', 1);

-- ============================================================
-- 13. TABLA: pay_plan (Planes de Pago)
-- ============================================================
INSERT INTO pay_plan (id_project, total_debt, total_payed, number_debt, number_pays, state) VALUES
(1, 23000.00, 11500.00, 2, 1, 'Activo'),
(2, 25575.00, 25575.00, 0, 3, 'Pagado'),
(3, 22500.00, 0.00, 3, 0, 'Pendiente'),
(4, 24000.00, 8000.00, 2, 1, 'Activo'),
(5, 24000.00, 24000.00, 0, 2, 'Pagado'),
(6, 20900.00, 10450.00, 1, 1, 'Activo'),
(7, 23000.00, 0.00, 2, 0, 'Pendiente'),
(8, 25550.00, 12775.00, 1, 1, 'Activo'),
(9, 23820.00, 23820.00, 0, 4, 'Pagado'),
(10, 26000.00, 6500.00, 3, 1, 'Activo');

-- ============================================================
-- 14. TABLA: pays (Pagos Realizados)
-- ============================================================
INSERT INTO pays (date, total, state, id_client, id_pay_plan) VALUES
('2025-01-15', 11500.00, 'Completado', 1, 1),
('2024-12-20', 8525.00, 'Completado', 2, 2),
('2024-11-15', 8525.00, 'Completado', 2, 2),
('2025-01-10', 8000.00, 'Completado', 3, 4),
('2024-12-01', 12000.00, 'Completado', 4, 5),
('2024-11-30', 6000.00, 'Completado', 4, 5),
('2025-01-20', 10450.00, 'Completado', 5, 6),
('2025-01-08', 12775.00, 'Completado', 7, 8),
('2024-11-30', 5955.00, 'Completado', 8, 9),
('2025-01-25', 6500.00, 'Completado', 9, 10);

-- ============================================================
-- 15. TABLA: service_evaluation (Evaluaciones de Servicio)
-- ============================================================
INSERT INTO service_evaluation (comments, id_project, design_qualification, fabric_qualification, installation_qualification) VALUES
('Excelente trabajo, muy conforme con el resultado', 2, 5, 5, 5),
('Buen servicio, pequeñas variaciones en colores', 5, 4, 4, 5),
('Profesionales y puntuales', 9, 5, 5, 4),
('Muy satisfecho con el diseño final', 1, 5, 4, 4),
('Material de buena calidad', 4, 4, 5, 4),
('Instalación perfecta', 6, 5, 5, 5),
('Buen servicio en general', 8, 4, 4, 5),
('Cumplimiento de cronograma', 10, 4, 4, 4),
('Atención al cliente excelente', 2, 5, 5, 5),
('Recomendable para futuros proyectos', 5, 5, 4, 5);

-- ============================================================
-- FIN DEL SCRIPT DE POBLACIÓN
-- ============================================================
-- Resumen:
-- - 10 registros en tabla persona
-- - 10 registros en tabla app_user
-- - 10 registros en tabla client
-- - 10 registros en tabla project
-- - 10 registros en tabla schedule
-- - 10 registros en tabla task
-- - 10 registros en tabla supplier
-- - 10 registros en tabla material
-- - 10 registros en tabla material_supplier
-- - 10 registros en tabla material_project
-- - 10 registros en tabla quote
-- - 10 registros en tabla design
-- - 10 registros en tabla pay_plan
-- - 10 registros en tabla pays
-- - 10 registros en tabla service_evaluation
-- ============================================================
