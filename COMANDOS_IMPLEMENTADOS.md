# 📋 GUÍA COMPLETA DE COMANDOS - TECNO PROYECTO

## 🎯 Descripción General

Este documento describe todos los comandos implementados en el sistema de procesamiento de comandos por correo electrónico.

**Formato General:** `COMANDO["parametro1","parametro2",...,]`

---

## 👥 CU1: GESTIÓN DE USUARIOS

### Listar Usuarios

```
LISUSR["*"]
```

- Retorna todos los usuarios registrados en el sistema con sus roles

### Buscar Usuario por Nombre

```
BUSUSRNOM["nombre"]
```

- Parámetros:
  - `nombre`: Nombre del usuario

### Buscar Usuario por Email

```
BUSUSREMAIL["email"]
```

- Parámetros:
  - `email`: Email del usuario

### Insertar Usuario

```
INSUSR["nombre","email","telefono","direccion","password","rol"]
```

- Parámetros:
  - `nombre`: Nombre único del usuario (3-100 caracteres)
  - `email`: Email válido con formato correcto (ej: usuario@ejemplo.com)
  - `telefono`: Teléfono con mínimo 8 dígitos (sin +591)
  - `direccion`: Dirección (5-255 caracteres)
  - `password`: Contraseña fuerte (8+ caracteres, mayúscula, minúscula, dígito, carácter especial)
  - `rol`: ADMIN, DESIGNER, INSTALLER

### Actualizar Usuario

```
UPDUSR["id","nombre","email","telefono","direccion","password","rol"]
```

- Parámetros:
  - `id`: ID del usuario a actualizar
  - `nombre`: Nombre único del usuario (3-100 caracteres)
  - `email`: Email válido con formato correcto
  - `telefono`: Teléfono con mínimo 8 dígitos (sin +591)
  - `direccion`: Dirección (5-255 caracteres)
  - `password`: Contraseña fuerte o vacío para mantener la actual
  - `rol`: ADMIN, DESIGNER, INSTALLER


### Buscar Usuarios por Rol

```
BUSUSRROL["rol"]
```

- Parámetros:
  - `rol`: Rol a filtrar (ADMIN, DESIGNER, etc.)

### Listar Roles Disponibles

```
LISTROLES["*"]
```

- Retorna lista de todos los roles disponibles en el sistema con descripción detallada de cada uno
- Los roles disponibles son: ADMIN, DESIGNER, INSTALLER


### Listar Clientes

```
LISCLI["*"]
```

- Retorna todos los clientes registrados en el sistema con sus contactos
- **Estado**: ✅ Implementado

### Buscar Cliente por ID

```
BUSCLIID["id"]
```

- Parámetros:
  - `id`: ID del cliente (número entero)
- Retorna información completa del cliente
- **Estado**: ✅ Implementado

### Buscar Cliente por Email

```
BUSCLIEMAIL["email"]
```

- Parámetros:
  - `email`: Email del cliente
- Retorna cliente específico con su información
- **Estado**: ✅ Implementado

### Buscar Cliente por Teléfono

```
BUSCLITEL["telefono"]
```

- Parámetros:
  - `telefono`: Teléfono del cliente
- Retorna cliente que coincide con el número
- **Estado**: ✅ Implementado


### Buscar Clientes con Proyectos

```
BUSCLIPROY["*"]
```

- Retorna todos los clientes que tienen al menos un proyecto asignado
- **Estado**: ✅ Implementado

### Insertar Cliente

```
INSCLI["nombre","email","telefono","direccion"]
```

- Parámetros:
  - `nombre`: Nombre único del cliente (3-100 caracteres)
  - `email`: Email válido con formato correcto (ej: cliente@ejemplo.com)
  - `telefono`: Teléfono con mínimo 8 dígitos (sin +591)
  - `direccion`: Dirección (5-255 caracteres)
- Crea un nuevo cliente en el sistema
- **Validaciones**:
  - Email debe tener formato válido (ej: cliente@ejemplo.com)
  - Teléfono debe tener mínimo 8 dígitos
  - Nombre entre 3-100 caracteres (obligatorio)
  - Dirección entre 5-255 caracteres (obligatorio)
  - No se puede repetir nombre
  - No se puede repetir email
- **Estado**: ✅ Implementado

### Actualizar Cliente

```
UPDCLI["nombre","email","telefono","direccion"]
```

- Parámetros:
  - `nombre`: Nombre del cliente a actualizar (búsqueda por nombre)
  - `email`: Email nuevo (válido con formato correcto)
  - `telefono`: Teléfono nuevo (mínimo 8 dígitos)
  - `direccion`: Dirección nueva (5-255 caracteres)
- Actualiza información del cliente existente buscando por nombre
- **Validaciones**: 
  - Email debe tener formato válido (ej: cliente@ejemplo.com)
  - Teléfono debe tener mínimo 8 dígitos
  - Dirección entre 5-255 caracteres
- **Estado**: ✅ Implementado


### Verificar Existencia de Cliente

```
ESTCLI["id"]
```

- Parámetros:
  - `id`: ID del cliente
- Retorna true si el cliente existe, false si no
- **Estado**: ✅ Implementado

### Contar Clientes

```
CONTCLI["*"]
```

- Retorna el número total de clientes en el sistema
- **Estado**: ✅ Implementado

### Estadísticas de Clientes

```
ESTCLIS["*"]
```

- Retorna estadísticas generales de clientes:
  - Total de clientes
  - Clientes con proyectos
  - Clientes sin proyectos
  - Información adicional
- **Estado**: ✅ Implementado

---

## 🏗️ CU2: GESTIÓN DE PROYECTOS Y CRONOGRAMAS

### Listar Proyectos

```
LISPROY["*"]
```

- Retorna todos los proyectos con información del cliente y usuario asignado

### Insertar Proyecto

```
INSPROY["nombre","descripcion","ubicacion","estado","idCliente","idUsuario"]
```

- Parámetros:
  - `nombre`: Nombre único del proyecto
  - `descripcion`: Descripción del proyecto (500 caracteres máx)
  - `ubicacion`: Ubicación del proyecto (255 caracteres máx)
  - `estado`: Planificación, En Proceso, Completado
  - `idCliente`: ID del cliente (debe existir)
  - `idUsuario`: ID del usuario responsable (debe existir)

### Buscar Proyecto por Nombre

```
BUSPROYNOM["nombre"]
```

- Retorna un proyecto específico por su nombre

### Actualizar Proyecto

```
UPDPROY["nombre","descripcion","ubicacion","estado","idCliente","idUsuario"]
```

- Actualiza proyecto existente por nombre

### Buscar Proyectos por Cliente

```
BUSPROYCLI["idCliente"]
```

- Retorna todos los proyectos de un cliente específico

### Buscar Proyectos por Usuario

```
BUSPROYUSR["idUsuario"]
```

- Retorna todos los proyectos asignados a un usuario

### Buscar Proyectos por Estado

```
BUSPROYEST["estado"]
```

- Retorna proyectos filtrados por estado

### Estadísticas de Proyectos

```
ESTPROY["*"]
```

- Retorna cantidad de proyectos por estado

### Listar Cronogramas

```
LISSCH["*"]
```

- Retorna todos los cronogramas con tareas asociadas

### Insertar Cronograma

```
INSSCH["initDate","finalDate","estimateDays","state","idProject","userId"]
```

- Parámetros:
  - `initDate`: Fecha de inicio (YYYY-MM-DD)
  - `finalDate`: Fecha de fin (YYYY-MM-DD)
  - `estimateDays`: Días estimados
  - `state`: En Proceso, Planificación, Completado
  - `idProject`: ID del proyecto
  - `userId`: ID del usuario responsable

### Actualizar Cronograma

```
UPDSCH["id","initDate","finalDate","estimateDays","state","idProject","userId"]
```

- Actualiza cronograma existente

### Buscar Cronograma por ID

```
BUSSCHID["id"]
```

- Retorna cronograma específico

### Buscar Cronogramas por Proyecto

```
BUSSCHPROY["idProject"]
```

- Retorna todos los cronogramas de un proyecto

### Buscar Cronogramas por Usuario

```
BUSSCHUSR["userId"]
```

- Retorna cronogramas asignados a un usuario

### Cronogramas Activos

```
SCHACT["*"]
```

- Retorna cronogramas en proceso o planificación

### Cronogramas Completados

```
SCHCOMP["*"]
```

- Retorna cronogramas completados

---

## 🔧 CU3: GESTIÓN DE PRODUCTOS

### Listar Productos

```
LISPROD["*"]
```

- Retorna todos los productos con información de stock e inventario

### Buscar Producto por Nombre

```
BUSPRODNOM["nombre"]
```

- Retorna producto específico con su información

### Insertar Producto

```
INSPROD["nombre","tipo","unidadMedida","precioBases","stock"]
```

- Parámetros:
  - `nombre`: Nombre único del producto
  - `tipo`: Tipo de producto (Textil, Herraje, Acabado, etc.)
  - `unidadMedida`: Unidad de medida (m2, ml, un, kg, etc.)
  - `precioUnitario`: Precio por unidad (decimal)
  - `stock`: Stock inicial

### Actualizar Producto

```
UPDPROD["nombre","tipo","unidadMedida","precioUnitario","stock"]
```

- Actualiza producto completo

### Buscar Productos por Tipo

```
BUSPRODTIPO["tipo"]
```

- Retorna todos los productos de un tipo específico

### Actualizar Precio Producto

```
UPDPRODPRECIO["id","nuevoPrecio"]
```

- Actualiza solo el precio unitario

### Actualizar Stock Producto

```
UPDPRODSTOCK["id","nuevoStock"]
```

- Actualiza solo el stock

### Reducir Stock Producto

```
REDPRODSTOCK["id","cantidad"]
```

- Reduce el stock en la cantidad especificada

### Aumentar Stock Producto

```
AUMPRODSTOCK["id","cantidad"]
```

- Aumenta el stock en la cantidad especificada

### Verificar Disponibilidad

```
VERPRODDISP["id","cantidadRequerida"]
```

- Verifica si hay suficiente stock del producto

---

## 🎨 CU4: GESTIÓN DE DISEÑOS

### Listar Diseños

```
LISDESIGN["*"]
```

- Retorna todos los diseños registrados

### Buscar Diseño por ID

```
BUSDESIGNID["id"]
```

- Retorna un diseño específico

### Buscar Diseño por Cotización

```
BUSDESIGNQUOTE["idQuote"]
```

- Retorna el diseño asociado a una cotización

### Insertar Diseño

```
INSDESIGN["idQuote","urlRender","laminatedPlane","approved","approvedDate","comments","userId"]
```

- Parámetros:
  - `idQuote`: ID de la cotización
  - `urlRender`: URL del render
  - `laminatedPlane`: URL del plano laminado
  - `approved`: true/false
  - `approvedDate`: Fecha aprobación (YYYY-MM-DD)
  - `comments`: Comentarios
  - `userId`: ID del diseñador

### Actualizar Diseño

```
UPDDESIGN["idDesign","idQuote","urlRender","laminatedPlane","approved","approvedDate","comments","userId"]
```

- Actualiza diseño existente

### Eliminar Diseño

```
DELDESIGN["id"]
```

- Elimina un diseño

### Buscar Diseños por Usuario

```
BUSDESIGNUSR["userId"]
```

- Retorna todos los diseños del usuario

### Diseños Aprobados

```
DESIGNAPPR["*"]
```

- Retorna todos los diseños aprobados

### Aprobar Diseño

```
APPRDESIGN["id"]
```

- Marca un diseño como aprobado

### Rechazar Diseño

```
REJDESIGN["id"]
```

- Marca un diseño como rechazado

### Diseños Aprobados por Usuario

```
DESIGNAPPRUSR["userId"]
```

- Retorna diseños aprobados de un usuario

### Diseños Pendientes por Usuario

```
DESIGNPENDUSR["userId"]
```

- Retorna diseños pendientes de un usuario

---

## ✅ CU5: GESTIÓN DE ASIGNACIÓN (PROYECTO Y PERSONAL)

### Listar Asignaciones

```
LISASIG["*"]
```

- Retorna todas las asignaciones de proyectos a usuarios

### Asignaciones por Usuario

```
LISASIG_USR["idUsuario"]
```

- Retorna proyectos, cronogramas y tareas asignadas a un usuario

### Asignar Proyecto a Usuario

```
ASIGNARPROYUSR["nombreProyecto","idUsuario"]
```

- Asigna un proyecto a un usuario específico

### Carga de Trabajo por Usuario

```
CARGAUSR["*"]
```

- Muestra la carga de trabajo actual de cada usuario con progreso

---

## 📦 CU6: GESTIÓN DE INVENTARIO (INGRESO/SALIDA)

### Listar Producto-Proyecto

```
LISPRODPROY["*"]
```

- Retorna todas las asignaciones de productos a proyectos

### Buscar Producto-Proyecto por ID

```
BUSPRODPROYID["id"]
```

- Retorna una asignación específica

### Insertar Producto-Proyecto

```
INSPRODPROY["cantidad","leftOver","idProject","idProducto"]
```

- **IMPORTANTE**: Descuenta automáticamente del stock general
- Parámetros:
  - `cantidad`: Cantidad requerida para el proyecto
  - `leftOver`: Cantidad sobrante (inicial 0)
  - `idProject`: ID del proyecto
  - `idProducto`: ID del producto

### Actualizar Producto-Proyecto

```
UPDPRODPROY["id","cantidad","leftOver","idProject","idProducto"]
```

- **IMPORTANTE**: Ajusta automáticamente el stock
- Actualiza una asignación

### Buscar Productos por Proyecto

```
BUSPRODPORPROY["idProject"]
```

- Retorna todos los productos asignados a un proyecto

### Buscar Proyectos por Producto

```
BUSPROYPORPORD["idProducto"]
```

- Retorna todos los proyectos que usan un producto

### Devolver Producto Sobrante

```
DEVOLVERSOBRANTE["idProductoProject","cantidadDevolver"]
```

- Devuelve al stock general el producto no utilizado
- Actualiza automáticamente el stock

### Devolver Todo Sobrante

```
DEVOLVERTODO["idProject"]
```

- Devuelve todo el producto sobrante de un proyecto

### Reporte de Stock del Proyecto

```
REPORTESTOCK["idProject"]
```

- Genera reporte detallado del stock del proyecto

### Ajustar Sobrante por Uso Real

```
AJUSTARSOBRANTE["idMaterialProject","cantidadRealmenteUsada"]
```

- Ajusta el sobrante basado en lo realmente usado

### Verificar Stock de Material

```
VERIFICARSTOCK["idMaterial"]
```

- Verifica disponibilidad actual de un material

---

## 💳 CU7: GESTIÓN DE PAGOS (PLAN DE PAGOS Y MORA)

### Listar Planes de Pago

```
LISPAYPLAN["*"]
```

- Retorna todos los planes de pago con estados

### Buscar Plan de Pago por ID

```
BUSPAYPLANID["idPayPlan"]
```

- Retorna un plan específico

### Buscar Plan de Pago por Proyecto

```
BUSPAYPLANPROY["idProject"]
```

- Retorna el plan de pago de un proyecto

### Insertar Plan de Pago

```
INSPAYPLAN["idProject","totalDebt","totalPayed","numberDebt","numberPays","state"]
```

- Parámetros:
  - `idProject`: ID del proyecto
  - `totalDebt`: Deuda total (decimal)
  - `totalPayed`: Total pagado inicial (generalmente 0)
  - `numberDebt`: Cuotas pendientes
  - `numberPays`: Pagos realizados (inicial 0)
  - `state`: Activo, Pagado, Pendiente

### Actualizar Plan de Pago

```
UPDPAYPLAN["idPayPlan","idProject","totalDebt","totalPayed","numberDebt","numberPays","state"]
```

- Actualiza un plan de pago

### Buscar Planes por Estado

```
BUSPAYPLANEST["estado"]
```

- Retorna planes filtrados por estado

### Total Deuda Pendiente

```
TOTDEUDAPEND["*"]
```

- Retorna la deuda total pendiente del sistema

### Total Pagado

```
TOTPAGADO["*"]
```

- Retorna el total pagado en el sistema

### Actualizar Deuda Total

```
UPDDEUDATOT["idPayPlan","nuevaDeuda"]
```

- Actualiza la deuda de un plan

### Calcular Porcentaje de Pago

```
CALCPORCPAGO["idPayPlan"]
```

- Calcula el porcentaje pagado de un plan

### Cambiar Estado del Plan

```
CAMBIOEST["idPayPlan","nuevoEstado"]
```

- Cambia el estado de un plan

### Crear Plan de Pago Completo

```
CREARPLANPAGOS["idProject","monto1,monto2,monto3"]
```

- Crea un plan con múltiples cuotas

### Obtener Plan de Pago Completo

```
OBTENERPLANPAGO["idPayPlan"]
```

- Retorna plan completo con todos sus pagos

### Recalcular Plan de Pago

```
RECALCPLANPAGO["idPayPlan","nuevoMonto1,nuevoMonto2"]
```

- Recalcula las cuotas de un plan

### Listar Pagos

```
LISPAYS["*"]
```

- Retorna todos los pagos registrados

### Buscar Pago por ID

```
BUSPAYID["id"]
```

- Retorna un pago específico

### Insertar Pago

```
INSPAY["date","total","state","idClient","idPayPlan"]
```

- Parámetros:
  - `date`: Fecha del pago (YYYY-MM-DD)
  - `total`: Monto pagado (decimal)
  - `state`: Completado, Pendiente
  - `idClient`: ID del cliente
  - `idPayPlan`: ID del plan de pago

### Actualizar Pago

```
UPDPAY["id","date","total","state","idClient","idPayPlan"]
```

- Actualiza un pago

### Buscar Pagos por Cliente

```
BUSPAYCLI["idClient"]
```

- Retorna todos los pagos de un cliente

### Total Pagado por Cliente

```
TOTPAGCLI["idClient"]
```

- Retorna el total pagado por un cliente

### Verificar Pagos del Plan

```
PLANPAGOHAS["idPayPlan"]
```

- Verifica si un plan tiene pagos registrados

### Contar Pagos del Plan

```
COUNTPAYPPLAN["idPayPlan"]
```

- Cuenta cuántos pagos tiene un plan

### Obtener Pagos del Plan

```
OBTPAGOSPLAN["idPayPlan"]
```

- Retorna todos los pagos de un plan

### Realizar Pago

```
PAGAR["idPago","fechaPago","idClient"]
```

- Procesa un pago y actualiza el estado

---

## 💰 CU8: REPORTES Y ESTADÍSTICAS

### Reporte de Cliente

```
REPORTECLIENTE["nombreCliente"]
```

- Retorna reporte completo del cliente incluyendo:
  - Información del cliente
  - Proyectos
  - Cotizaciones
  - Cronogramas
  - Planes de pago

### Reporte de Proyecto

```
REPORTEPROYECTO["nombreProyecto"]
```

- Retorna reporte detallado del proyecto incluyendo:
  - Información general
  - Cotizaciones con estados
  - Diseños asociados
  - Cronogramas y tareas
  - Materiales utilizados y costos

### Reporte de Materiales

```
REPORTEMATERIALES["*"]
```

- Retorna reporte completo del inventario incluyendo:
  - Estado de cada material
  - Stock disponible
  - Valor total del inventario
  - Materiales en proyectos
  - Top 5 materiales más caros
  - Materiales que requieren reabastecimiento

### Reporte Integral

```
REPORTEINT["*"]
```

- Retorna reporte general del sistema incluyendo:
  - Estadísticas generales (proyectos, clientes, usuarios, materiales)
  - Estado de proyectos (completados, en proceso, en planificación)
  - Información financiera (deuda, pagado, pendiente)

---

## ❓ SISTEMA DE AYUDA

### Mostrar Ayuda Completa

```
HELP["*"]
```

- Muestra todos los comandos disponibles con ejemplos

---

## 📝 NOTAS IMPORTANTES

1. **Formato de Parámetros**

   - Todos los parámetros deben ir entre comillas dobles
   - Las comas separan los parámetros
   - NO incluir espacios antes o después de los parámetros
   - ⚠️ **IMPORTANTE**: Los comandos muy largos pueden ser divididos en múltiples líneas por el cliente de correo. El sistema los reconstruye automáticamente, pero se recomienda mantener los comandos lo más compactos posible

2. **Formatos Específicos**

   - Fechas: YYYY-MM-DD (ej: 2025-01-15)
   - Horas: HH:MM (ej: 14:30)
   - IDs: Números enteros (ej: 1, 2, 3)
   - Montos: Decimales con punto (ej: 1500.50)
   - Booleanos: true/false (minúsculas)

3. **Estados Válidos**

   - Proyectos: Planificación, En Proceso, Completado
   - Tareas: Pendiente, En Progreso, Completada
   - Cronogramas: Planificación, En Proceso, Completado
   - Cotizaciones: Aprobada, Pendiente, Rechazada
   - Pagos: Completado, Pendiente
   - Planes de Pago: Activo, Pagado, Pendiente

4. **Gestión Automática de Stock**

   - Cuando se asigna material a un proyecto (INSMATPROY), se descuenta automáticamente del stock
   - Cuando se actualiza asignación (UPDMATPROY), se ajusta automáticamente el stock
   - Cuando se devuelve material (DEVOLVERSOBRANTE), se suma al stock
   - El sistema nunca permite que el stock sea negativo

5. **Seguridad**
   - Los IDs de cliente y usuario deben existir en el sistema
   - No se pueden eliminar registros que tienen relaciones activas
   - Los nombres únicos no pueden repetirse

6. **Validaciones de Cliente (INSCLI/UPDCLI)** ✅
   - **Nombre**: Entre 3 y 100 caracteres (obligatorio)
   - **Email**: Formato válido con @ y dominio (ej: cliente@ejemplo.com)
   - **Teléfono**: Mínimo 8 dígitos, sin contar prefijo +591 (obligatorio)
   - **Dirección**: Entre 5 y 255 caracteres (obligatorio)
   - **Unicidad**: 
     - No se puede insertar cliente con nombre duplicado
     - No se puede insertar cliente con email duplicado
   
7. **Validaciones de Usuario (INSUSR/UPDUSR)**
   - **Nombre**: Entre 3 y 100 caracteres (obligatorio)
   - **Email**: Formato válido con @ y dominio (obligatorio)
   - **Teléfono**: Mínimo 8 dígitos, sin contar prefijo +591 (obligatorio)
   - **Dirección**: Entre 5 y 255 caracteres (obligatorio)
   - **Contraseña**: 
     - Mínimo 8 caracteres
     - Al menos 1 letra mayúscula
     - Al menos 1 letra minúscula
     - Al menos 1 dígito (número)
     - Al menos 1 carácter especial (!@#$%^&* etc)
   - **Rol**: Solo valores permitidos: ADMIN, DESIGNER, INSTALLER

---

## 🔗 EJEMPLOS DE USO

### Listar Roles Disponibles

```
LISTROLES["*"]
```

### Crear un Usuario con rol DESIGNER

```
INSUSR["Maria Diseñadora","maria@ejemplo.com","76543210","Av. Principal 123","Abc123!@#","DESIGNER"]
```

### Crear un Cliente

```
INSCLI["Constructora ABC","info@abc.com","3334567","Av. Principal 123"]
```

### Actualizar Cliente (por nombre)

```
UPDCLI["Constructora ABC","newemail@abc.com","3334568","Av. Nueva 456"]
```

### Crear un Proyecto para ese Cliente

```
INSPROY["Oficinas Centro","Remodelación de oficinas","La Paz","Planificación","1","1"]
```

### Asignar Material a Proyecto (descuenta stock)

```
INSMATPROY["100","0","1","5"]
```

- Asigna 100 unidades del material 5 al proyecto 1

### Devolver Material no Usado

```
DEVOLVERSOBRANTE["1","25"]
```

- Devuelve 25 unidades del material asignado

### Ver Reporte del Proyecto

```
REPORTEPROYECTO["Oficinas Centro"]
```

---

**Última Actualización:** Noviembre 2025
**Versión:** 1.0
**Autor:** Grupo 03 - TecnoWeb
