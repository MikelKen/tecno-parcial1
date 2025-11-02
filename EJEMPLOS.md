# Ejemplos de Uso del Sistema de Comandos por Correo

## Formato de Correos para Enviar Comandos

### 1. Ejemplo: Listar todas las personas

**Correo a enviar:**

```
De: tu-email@gmail.com
Para: grupo03sa@tecnoweb.org.bo
Asunto: LISPER["*"]
Cuerpo: (puede estar vacío)
```

**Respuesta esperada:**

```
De: grupo03sa@tecnoweb.org.bo
Para: tu-email@gmail.com
Asunto: Re: LISPER["*"]

Listando las Personas:

CI: 12345678, Nombre: Juan Carlos Perez Lopez, Cargo: Estudiante, Teléfono: 33554433, Celular: 71055123, Email: juan.perez@uagrm.edu.bo
CI: 87654321, Nombre: Maria Elena Garcia Torres, Cargo: Docente, Teléfono: 33221100, Celular: 72334455, Email: maria.garcia@uagrm.edu.bo
...
```

### 2. Ejemplo: Insertar una nueva persona

**Correo a enviar:**

```
De: luirfer.camacho@gmail.com
Para: grupo03sa@tecnoweb.org.bo
Asunto: INSPER["4715292","Juan Carlos","Perez Seras","Estudiante","33554433","71055123","juanperez@uagrm.edu.bo"]
Cuerpo: (puede estar vacío)
```

**Respuesta esperada:**

```
De: grupo03sa@tecnoweb.org.bo
Para: luirfer.camacho@gmail.com
Asunto: Re: INSPER["4715292","Juan Carlos","Perez Seras","Estudiante","33554433","71055123","juanperez@uagrm.edu.bo"]

Éxito: Persona insertada correctamente.
```

### 3. Ejemplo: Buscar una persona por CI

**Correo a enviar:**

```
De: usuario@ejemplo.com
Para: grupo03sa@tecnoweb.org.bo
Asunto: BUSPER["4715292"]
Cuerpo: (puede estar vacío)
```

**Respuesta esperada:**

```
De: grupo03sa@tecnoweb.org.bo
Para: usuario@ejemplo.com
Asunto: Re: BUSPER["4715292"]

Persona encontrada:
CI: 4715292
Nombre: Juan Carlos Perez Seras
Cargo: Estudiante
Teléfono: 33554433
Celular: 71055123
Email: juanperez@uagrm.edu.bo
```

### 4. Ejemplo: Actualizar una persona

**Correo a enviar:**

```
De: admin@empresa.com
Para: grupo03sa@tecnoweb.org.bo
Asunto: UPDPER["4715292","Juan Carlos","Perez Actualizado","Docente","33554433","71055123","juan.actualizado@uagrm.edu.bo"]
Cuerpo: (puede estar vacío)
```

**Respuesta esperada:**

```
De: grupo03sa@tecnoweb.org.bo
Para: admin@empresa.com
Asunto: Re: UPDPER["4715292","Juan Carlos","Perez Actualizado","Docente","33554433","71055123","juan.actualizado@uagrm.edu.bo"]

Éxito: Persona actualizada correctamente.
```

### 5. Ejemplo: Eliminar una persona

**Correo a enviar:**

```
De: admin@empresa.com
Para: grupo03sa@tecnoweb.org.bo
Asunto: DELPER["4715292"]
Cuerpo: (puede estar vacío)
```

**Respuesta esperada:**

```
De: grupo03sa@tecnoweb.org.bo
Para: admin@empresa.com
Asunto: Re: DELPER["4715292"]

Éxito: Persona eliminada correctamente.
```

## Ejemplos de Errores Comunes

### Error: Comando malformado

**Correo enviado:**

```
Asunto: LISPER *
```

**Respuesta:**

```
Error: Formato de comando inválido. Use: COMANDO["parametros"]
```

### Error: Parámetros insuficientes

**Correo enviado:**

```
Asunto: INSPER["4715292","Juan Carlos"]
```

**Respuesta:**

```
Error: Parámetros insuficientes. Se requieren: CI, nombre, apellido, cargo, teléfono, celular, email
```

### Error: Persona no encontrada

**Correo enviado:**

```
Asunto: BUSPER["9999999"]
```

**Respuesta:**

```
No se encontró la persona con CI: 9999999
```

### Error: Comando no reconocido

**Correo enviado:**

```
Asunto: OBTENER["*"]
```

**Respuesta:**

```
Error: Comando no reconocido. Comandos disponibles: LISPER, INSPER, UPDPER, DELPER, BUSPER
```

## Notas Importantes

1. **Formato Estricto**: Los comandos deben seguir exactamente el formato especificado
2. **Comillas**: Los parámetros deben estar entre comillas dobles
3. **Separadores**: Los parámetros se separan con comas
4. **Respuesta Automática**: El sistema responde automáticamente al remitente
5. **Tiempo de Respuesta**: Las respuestas se envían dentro de 30 segundos (tiempo de monitoreo)

## Pruebas con el Endpoint REST (Opcional)

Si tienes acceso al servidor donde corre la aplicación, puedes probar los comandos usando curl:

```bash
# Verificar estado del sistema
curl http://localhost:8081/api/health

# Ver comandos disponibles
curl http://localhost:8081/api/commands

# Probar comando LISPER
curl -X POST "http://localhost:8081/api/test-command?subject=LISPER[\"*\"]&senderEmail=test@ejemplo.com"

# Probar comando INSPER
curl -X POST "http://localhost:8081/api/test-command?subject=INSPER[\"123456\",\"Test\",\"User\",\"Cargo\",\"123456\",\"987654\",\"test@test.com\"]&senderEmail=test@ejemplo.com"

# Probar comando BUSPER
curl -X POST "http://localhost:8081/api/test-command?subject=BUSPER[\"123456\"]&senderEmail=test@ejemplo.com"

# Probar comando UPDPER
curl -X POST "http://localhost:8081/api/test-command?subject=UPDPER[\"123456\",\"Test Updated\",\"User Updated\",\"Nuevo Cargo\",\"123456\",\"987654\",\"updated@test.com\"]&senderEmail=test@ejemplo.com"

# Probar comando DELPER
curl -X POST "http://localhost:8081/api/test-command?subject=DELPER[\"123456\"]&senderEmail=test@ejemplo.com"

# Procesar correos manualmente (fuerza el procesamiento)
curl -X POST http://localhost:8081/api/process-emails
```

### Para Windows PowerShell:

```powershell
# Verificar estado del sistema
Invoke-RestMethod -Uri "http://localhost:8081/api/health" -Method GET

# Ver comandos disponibles
Invoke-RestMethod -Uri "http://localhost:8081/api/commands" -Method GET

# Probar comando LISPER
Invoke-RestMethod -Uri "http://localhost:8081/api/test-command?subject=LISPER[`"*`"]&senderEmail=test@ejemplo.com" -Method POST

# Probar comando INSPER
Invoke-RestMethod -Uri "http://localhost:8081/api/test-command?subject=INSPER[`"123456`",`"Test`",`"User`",`"Cargo`",`"123456`",`"987654`",`"test@test.com`"]&senderEmail=test@ejemplo.com" -Method POST
```
