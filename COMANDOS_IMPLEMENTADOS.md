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
  - `nombre`: Nombre único del usuario
  - `email`: Email válido
  - `telefono`: Teléfono (20 caracteres máx)
  - `direccion`: Dirección (255 caracteres máx)
  - `password`: Contraseña
  - `rol`: ADMIN, DESIGNER, INSTALLER, TECHNICIAN, COORDINATOR, SUPERVISOR, ASSISTANT

### Actualizar Usuario

```
UPDUSR["id","nombre","email","telefono","direccion","password","rol"]
```

- Parámetros:
  - `id`: ID del usuario a actualizar
  - (resto de parámetros igual a insertar)

### Eliminar Usuario

```
DELUSR["nombre"]
```

- Parámetros:
  - `nombre`: Nombre del usuario a eliminar

### Buscar Usuarios por Rol

```
BUSUSRROL["rol"]
```

- Parámetros:
  - `rol`: Rol a filtrar (ADMIN, DESIGNER, etc.)

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

## 🔧 CU3: GESTIÓN DE PRODUCTOS (MATERIALES)

### Listar Materiales

```
LISMAT["*"]
```

- Retorna todos los materiales con información de stock e inventario

### Buscar Material por Nombre

```
BUSMATNOM["nombre"]
```

- Retorna material específico con su información

### Insertar Material

```
INSMAT["nombre","tipo","unidadMedida","precioBases","stock"]
```

- Parámetros:
  - `nombre`: Nombre único del material
  - `tipo`: Tipo de material (Textil, Herraje, Acabado, etc.)
  - `unidadMedida`: Unidad de medida (m2, ml, un, kg, etc.)
  - `precioUnitario`: Precio por unidad (decimal)
  - `stock`: Stock inicial

### Actualizar Material

```
UPDMAT["nombre","tipo","unidadMedida","precioUnitario","stock"]
```

- Actualiza material completo

### Buscar Materiales por Tipo

```
BUSMATTIPO["tipo"]
```

- Retorna todos los materiales de un tipo específico

### Actualizar Precio Material

```
UPDMATPRECIO["id","nuevoPrecio"]
```

- Actualiza solo el precio unitario

### Actualizar Stock Material

```
UPDMATSTOCK["id","nuevoStock"]
```

- Actualiza solo el stock

### Reducir Stock Material

```
REDMATSTOCK["id","cantidad"]
```

- Reduce el stock en la cantidad especificada

### Aumentar Stock Material

```
AUMMATSTOCK["id","cantidad"]
```

- Aumenta el stock en la cantidad especificada

### Verificar Disponibilidad

```
VERMATDISP["id","cantidadRequerida"]
```

- Verifica si hay suficiente stock del material

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

### Listar Material-Proyecto

```
LISMATPROY["*"]
```

- Retorna todas las asignaciones de materiales a proyectos

### Buscar Material-Proyecto por ID

```
BUSMATPROYID["id"]
```

- Retorna una asignación específica

### Insertar Material-Proyecto

```
INSMATPROY["cantidad","leftOver","idProject","idMaterial"]
```

- **IMPORTANTE**: Descuenta automáticamente del stock general
- Parámetros:
  - `cantidad`: Cantidad requerida para el proyecto
  - `leftOver`: Cantidad sobrante (inicial 0)
  - `idProject`: ID del proyecto
  - `idMaterial`: ID del material

### Actualizar Material-Proyecto

```
UPDMATPROY["id","cantidad","leftOver","idProject","idMaterial"]
```

- **IMPORTANTE**: Ajusta automáticamente el stock
- Actualiza una asignación

### Buscar Materiales por Proyecto

```
BUSMATPORPROY["idProject"]
```

- Retorna todos los materiales asignados a un proyecto

### Buscar Proyectos por Material

```
BUSPROYPORMAT["idMaterial"]
```

- Retorna todos los proyectos que usan un material

### Devolver Material Sobrante

```
DEVOLVERSOBRANTE["idMaterialProject","cantidadDevolver"]
```

- Devuelve al stock general el material no utilizado
- Actualiza automáticamente el stock

### Devolver Todo Sobrante

```
DEVOLVERTODO["idProject"]
```

- Devuelve todo el material sobrante de un proyecto

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

---

## 🔗 EJEMPLOS DE USO

### Crear un Cliente

```
INSCLI["Constructora ABC","info@abc.com","3334567","Av. Principal 123"]
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
