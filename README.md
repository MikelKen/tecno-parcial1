# Sistema de Comandos por Correo Electrónico

## Descripción

Este sistema permite ejecutar comandos CRUD (Create, Read, Update, Delete) sobre una base de datos PostgreSQL mediante correos electrónicos. Los usuarios pueden enviar comandos en el asunto del correo al sistema, y recibirán una respuesta automática con el resultado de la operación.

## Configuración del Sistema

### Credenciales del Servidor

- **Servidor**: www.tecnoweb.org.bo
- **Usuario**: grupo03sa
- **Contraseña**: grup003grup003\*
- **Email del Sistema**: grupo03sa@tecnoweb.org.bo
- **Base de Datos**: db_grupo03sa

### Tabla de Base de Dato

La tabla `persona` tiene la siguiente estructura:

```sql
CREATE TABLE persona (
    ci VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    cargo VARCHAR(100),
    telefono VARCHAR(20),
    celular VARCHAR(20),
    email VARCHAR(150)
);
```

## Comandos Disponibles

### 1. LISPER - Listar Personas

**Formato**: `LISPER["*"]`

**Ejemplo**:

```
De: usuario@ejemplo.com
Para: grupo03sa@tecnoweb.org.bo
Asunto: LISPER["*"]
```

**Respuesta**: Lista todas las personas en la base de datos.

### 2. INSPER - Insertar Persona

**Formato**: `INSPER["ci","nombre","apellido","cargo","telefono","celular","email"]`

**Ejemplo**:

```
De: luirfer.camacho@gmail.com
Para: grupo03sa@tecnoweb.org.bo
Asunto: INSPER["4715292","Juan Carlos","Perez Seras","Estudiante","33554433","71055123","juanperez@uagrm.edu.bo"]
```

**Respuesta**: "Éxito: Persona insertada correctamente." o mensaje de error.

### 3. UPDPER - Actualizar Persona

**Formato**: `UPDPER["ci","nombre","apellido","cargo","telefono","celular","email"]`

**Ejemplo**:

```
De: usuario@ejemplo.com
Para: grupo03sa@tecnoweb.org.bo
Asunto: UPDPER["4715292","Juan Carlos","Perez Updated","Docente","33554433","71055123","juan.updated@uagrm.edu.bo"]
```

**Respuesta**: "Éxito: Persona actualizada correctamente." o mensaje de error.

### 4. DELPER - Eliminar Persona

**Formato**: `DELPER["ci"]`

**Ejemplo**:

```
De: usuario@ejemplo.com
Para: grupo03sa@tecnoweb.org.bo
Asunto: DELPER["4715292"]
```

**Respuesta**: "Éxito: Persona eliminada correctamente." o mensaje de error.

### 5. BUSPER - Buscar Persona

**Formato**: `BUSPER["ci"]`

**Ejemplo**:

```
De: usuario@ejemplo.com
Para: grupo03sa@tecnoweb.org.bo
Asunto: BUSPER["4715292"]
```

**Respuesta**: Información completa de la persona encontrada o mensaje de error.

## Instalación y Ejecución

### Prerrequisitos

- Java 17 o superior
- Maven 3.6 o superior
- Acceso a la base de datos PostgreSQL en tecnoweb.org.bo

### Pasos de Instalación

1. **Clonar o descargar el proyecto**

2. **Configurar la base de datos**

   - Ejecutar el script SQL en `database/create_persona_table.sql`
   - Verificar que la tabla `persona` esté creada correctamente

3. **Compilar el proyecto**

   ```bash
   mvn clean compile
   ```

4. **Ejecutar el proyecto**

   ```bash
   mvn spring-boot:run
   ```

5. **Verificar funcionamiento**
   - El sistema iniciará y mostrará información sobre comandos disponibles
   - Comenzará a monitorear correos automáticamente cada 30 segundos

## Endpoints de Testing (Opcional)

El sistema incluye endpoints REST para testing manual:

- `GET /api/health` - Verificar estado del sistema
- `GET /api/commands` - Obtener lista de comandos disponibles
- `POST /api/test-command` - Probar un comando manualmente
- `POST /api/process-emails` - Procesar correos manualmente

**Ejemplo de uso con curl**:

```bash
curl -X POST "http://localhost:8080/api/test-command?subject=LISPER[\"*\"]&senderEmail=test@ejemplo.com"
```

## Arquitectura del Sistema

### Componentes Principales

1. **ClientPOP**: Conecta al servidor POP3 para leer correos entrantes
2. **ClientSMTP**: Envía respuestas por SMTP
3. **CommandProcessor**: Procesa y valida comandos recibidos
4. **PersonaDAO**: Maneja operaciones de base de datos
5. **EmailMonitoringService**: Servicio que monitorea correos periódicamente

### Flujo de Procesamiento

1. **Monitoreo**: El sistema verifica nuevos correos cada 30 segundos
2. **Lectura**: Extrae el asunto y remitente de cada correo
3. **Procesamiento**: Valida el comando y ejecuta la operación correspondiente
4. **Respuesta**: Envía el resultado al remitente por correo
5. **Limpieza**: Marca el correo procesado para eliminación

## Manejo de Errores

El sistema maneja varios tipos de errores:

- **Comandos malformados**: Responde con formato esperado
- **Parámetros faltantes**: Indica qué parámetros se requieren
- **Errores de base de datos**: Reporta problemas de conexión o SQL
- **Errores de correo**: Problemas de conectividad SMTP/POP3

## Logs y Debugging

- Los logs se muestran en consola durante la ejecución
- Configuración de logging en `application.properties`
- Nivel DEBUG activado para componentes del sistema

## Seguridad

- Validación de formato de comandos
- Uso de PreparedStatements para prevenir SQL injection
- Verificación de parámetros antes de operaciones de BD

## Notas Importantes

1. **Formato de Comandos**: Debe respetarse exactamente el formato especificado
2. **Parámetros**: Los parámetros deben estar entre comillas dobles y separados por comas
3. **Respuestas**: El sistema responde al remitente original del correo
4. **Monitoreo**: El sistema procesa correos automáticamente, no requiere intervención manual

## Solución de Problemas

### Error de Conexión a Base de Datos

- Verificar credenciales en `application.properties`
- Confirmar acceso al servidor tecnoweb.org.bo
- Revisar que la tabla `persona` exista

### Error de Conexión de Correo

- Verificar configuración de servidor mail.tecnoweb.org.bo
- Confirmar credenciales de email
- Revisar puertos POP3 (110) y SMTP (25)

### Comandos No Reconocidos

- Verificar formato exacto del comando
- Asegurar que los parámetros estén correctamente entre comillas
- Revisar ejemplos en esta documentación
