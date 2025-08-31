# API de Encuestas - Spring Boot

## 📋 Descripción del Proyecto

Esta es una API REST desarrollada con Spring Boot para la gestión de encuestas en un sistema educativo. Permite a administradores, empleados y alumnos interactuar con encuestas, preguntas, respuestas y más, utilizando autenticación JWT y control de acceso basado en roles.

## ✨ Características

- **Autenticación JWT**: Sistema seguro de autenticación con tokens JWT.
- **Control de Acceso Basado en Roles**: Roles definidos (AdminGeneral, Empleado, Alumno) con permisos específicos.
- **Gestión Completa de Encuestas**: Crear, actualizar, eliminar y gestionar encuestas con secciones, preguntas y respuestas posibles.
- **Estadísticas de Respuestas**: Obtener estadísticas de respuestas de alumnos.
- **Soft Delete**: Eliminación suave para mantener integridad de datos.
- **CORS Configurado**: Soporte para solicitudes desde diferentes orígenes.
- **Validación de Datos**: Validación automática con anotaciones Jakarta.

## 🛠 Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Security** (Autenticación y autorización)
- **Spring JDBC** (Acceso a base de datos)
- **MySQL** (Base de datos)
- **JWT (JSON Web Tokens)** (Autenticación)
- **Lombok** (Reducción de código boilerplate)
- **Maven** (Gestión de dependencias)

## 📋 Prerrequisitos

Antes de ejecutar la aplicación, asegúrate de tener instalados:

- **Java 21** o superior
- **Maven 3.6+**
- **MySQL 8.0+**
- Un IDE como IntelliJ IDEA, Eclipse o VS Code (opcional)

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone <url-del-repositorio>
cd encuestas
```

### 2. Configurar la Base de Datos

- Instala MySQL y crea una base de datos llamada `SistemaEncuestas`.
- Actualiza las credenciales en `src/main/resources/application.properties` si es necesario.

### 3. Ejecutar la Aplicación

Usando Maven:

```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`.

### 4. Verificar la Conexión

Haz una solicitud GET a `/api/ping` para verificar que la API esté funcionando:

```bash
curl http://localhost:8080/api/ping
```

## ⚙️ Configuración

El archivo `src/main/resources/application.properties` contiene la configuración principal:

```properties
# Nombre de la aplicación
spring.application.name=encuestas

# Puerto del servidor
server.port=8080
server.address=0.0.0.0

# Configuración de base de datos MySQL
spring.datasource.url=ruta_de_la_base_de_datos_de_mysql
spring.datasource.username=usuario
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuración de Spring Security (para desarrollo)
spring.security.user.name=name
spring.security.user.password=pass
spring.security.user.roles=ADMIN

# Configuración de logging
logging.level.org.springframework.jdbc.core.JdbcTemplate=DEBUG
logging.level.com.igsi.encuestas=DEBUG

# Configuración de JWT
jwt.secret=debe_ser_una-clave_de_almenos_32_caracteres
# 10800000 ~ 3 horas
jwt.expiration-ms=10800000
```

**Notas de Configuración:**
- Cambia la URL de la base de datos, usuario y contraseña según tu entorno.
- El secreto JWT debe tener al menos 32 caracteres para seguridad.
- El tiempo de expiración del JWT está en milisegundos (10800000 ms = 3 horas).

## 📚 Documentación de la API

A continuación se detalla la documentación completa de los endpoints de la API, organizada por módulos.

### 🔐 Seguridad / Autenticación

| Método | Ruta                  | Descripción                   |
|--------|-----------------------|-------------------------------|
| POST   | `/api/alumnos/login`  | Login de alumno, retorna JWT  |
| POST   | `/api/usuarios/login` | Login de usuario, retorna JWT |

### 👨‍🎓 Alumnos

| Método | Ruta                           | Descripción                  |
|--------|--------------------------------|------------------------------|
| GET    | `/api/alumnos`                 | Listar todos los alumnos     |
| GET    | `/api/alumnos/{id}`            | Obtener un alumno por ID     |
| GET    | `/api/alumnos/nombre/{nombre}` | Obtener un alumno por nombre |
| POST   | `/api/alumnos`                 | Registrar un alumno          |
| DELETE | `/api/alumnos/{id}`            | Eliminar un alumno           |

### 🏢 Departamentos

| Método | Ruta                                  | Descripción                                     |
|--------|---------------------------------------|-------------------------------------------------|
| GET    | `/api/departamentos`                  | Listar todos los departamentos                  |
| GET    | `/api/departamentos/{id}`             | Obtener un departamento por ID                  |
| POST   | `/api/departamentos`                  | Crear un departamento (AdminGeneral)            |
| PUT    | `/api/departamentos/{id}`             | Actualizar un departamento (AdminGeneral)       |
| DELETE | `/api/departamentos/{id}`             | Eliminar un departamento (AdminGeneral)         |
| PATCH  | `/api/departamentos/{id}/soft-delete` | Soft delete de departamento (AdminGeneral)      |
| PATCH  | `/api/departamentos/{id}/restaurar`   | Restaurar departamento eliminado (AdminGeneral) |

### 📋 Encuestas

| Método | Ruta                                           | Descripción                                                              |
|--------|------------------------------------------------|--------------------------------------------------------------------------|
| GET    | `/api/encuestas/master`                        | Listar todas las encuestas (admin)                                       |
| GET    | `/api/encuestas/deleted`                       | Listar todas las encuestas en la papelera (admin)                        |
| GET    | `/api/encuestas/alumnos`                       | Listar encuestas habilitadas para alumnos                                |
| GET    | `/api/encuestas/{id}`                          | Obtener encuesta por ID                                                  |
| GET    | `/api/encuestas/departamento/{idDepartamento}` | Obtener encuestas por departamento                                       |
| POST   | `/api/encuestas`                               | Crear encuesta (AdminGeneral, Empleado)                                  |
| PUT    | `/api/encuestas/{id}`                          | Actualizar encuesta (AdminGeneral, Empleado)                             |
| DELETE | `/api/encuestas/{id}`                          | Eliminar encuesta (hard delete, AdminGeneral)                            |
| PATCH  | `/api/encuestas/{id}/soft-delete`              | Soft delete encuesta (AdminGeneral, Empleado)                            |
| PATCH  | `/api/encuestas/{id}/restaurar`                | Restaurar encuesta (AdminGeneral, Empleado)                              |
| GET    | `/api/encuestas/{id}/completa`                 | Obtener encuesta completa con secciones, preguntas y respuestas posibles |

### 📚 Secciones de Encuesta

| Método | Ruta                                                | Descripción                                 |
|--------|-----------------------------------------------------|---------------------------------------------|
| GET    | `/api/encuestas/{idEncuesta}/secciones`             | Listar todas las secciones de una encuesta  |
| GET    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}` | Obtener una sección por ID                  |
| POST   | `/api/encuestas/{idEncuesta}/secciones`             | Crear sección (AdminGeneral, Empleado)      |
| PUT    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}` | Actualizar sección (AdminGeneral, Empleado) |
| DELETE | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}` | Eliminar sección (AdminGeneral, Empleado)   |

### ❓ Preguntas

| Método | Ruta                                                | Descripción                                  |
|--------|-----------------------------------------------------|----------------------------------------------|
| GET    | `/api/secciones/{idSeccion}/preguntas`              | Listar todas las preguntas de una sección    |
| GET    | `/api/secciones/{idSeccion}/preguntas/{idPregunta}` | Obtener pregunta por ID                      |
| POST   | `/api/secciones/{idSeccion}/preguntas`              | Crear pregunta (AdminGeneral, Empleado)      |
| PUT    | `/api/secciones/{idSeccion}/preguntas/{idPregunta}` | Actualizar pregunta (AdminGeneral, Empleado) |
| DELETE | `/api/secciones/{idSeccion}/preguntas/{idPregunta}` | Eliminar pregunta (AdminGeneral, Empleado)   |

### ✅ Respuestas de Alumnos

| Método | Ruta                                                   | Descripción                                                              |
|--------|--------------------------------------------------------|--------------------------------------------------------------------------|
| POST   | `/api/preguntas/{idPregunta}/respuestas`               | Guardar respuesta de un alumno                                           |
| PUT    | `/api/preguntas/{idPregunta}/respuestas/{idRespuesta}` | Actualizar respuesta de un alumno                                        |
| GET    | `/api/preguntas/{idPregunta}/respuestas/respuestas`    | Obtener estadísticas de respuestas (cuántas veces se eligió cada opción) |

### 📝 Respuestas Posibles

| Método | Ruta                                                           | Descripción                                           |
|--------|----------------------------------------------------------------|-------------------------------------------------------|
| GET    | `/api/preguntas/{idPregunta}/respuestasPosibles`               | Listar todas las respuestas posibles de una pregunta  |
| POST   | `/api/preguntas/{idPregunta}/respuestasPosibles`               | Crear una respuesta posible (AdminGeneral, Empleado)  |
| PUT    | `/api/preguntas/{idPregunta}/respuestasPosibles/{idRespuesta}` | Actualizar respuesta posible (AdminGeneral, Empleado) |
| DELETE | `/api/preguntas/{idPregunta}/respuestasPosibles/{idRespuesta}` | Eliminar respuesta posible (AdminGeneral, Empleado)   |

### 🏷 Tipo de Pregunta

| Método | Ruta                      | Descripción                                          |
|--------|---------------------------|------------------------------------------------------|
| GET    | `/api/tipo-pregunta`      | Listar todos los tipos de pregunta                   |
| GET    | `/api/tipo-pregunta/{id}` | Obtener tipo de pregunta por ID                      |
| POST   | `/api/tipo-pregunta`      | Crear tipo de pregunta (AdminGeneral, Empleado)      |
| PUT    | `/api/tipo-pregunta/{id}` | Actualizar tipo de pregunta (AdminGeneral, Empleado) |
| DELETE | `/api/tipo-pregunta/{id}` | Eliminar tipo de pregunta (AdminGeneral, Empleado)   |

### 👤 Usuarios

| Método | Ruta                            | Descripción                                        |
|--------|---------------------------------|----------------------------------------------------|
| GET    | `/api/usuarios`                 | Listar todos los usuarios (AdminGeneral, Empleado) |
| GET    | `/api/usuarios/{id}`            | Obtener usuario por ID (AdminGeneral, Empleado)    |
| GET    | `/api/usuarios/correo/{correo}` | Obtener usuario por correo                         |
| POST   | `/api/usuarios`                 | Crear usuario (AdminGeneral)                       |
| PUT    | `/api/usuarios/{id}`            | Actualizar usuario (AdminGeneral)                  |
| DELETE | `/api/usuarios/{id}`            | Eliminar usuario (AdminGeneral)                    |
| POST   | `/api/usuarios/login`           | Login de usuario, retorna JWT                      |

### ⚡ Demo / Ping

| Método | Ruta        | Descripción                                            |
|--------|-------------|--------------------------------------------------------|
| GET    | `/api/ping` | Endpoint de prueba para verificar conexión del backend |

## 🔐 Autenticación y Seguridad

### Roles de Usuario

- **AdminGeneral**: Acceso completo a todas las operaciones.
- **Empleado**: Acceso a operaciones de gestión de encuestas, preguntas, etc., pero no a usuarios o alumnos.
- **Alumno**: Acceso limitado a ver encuestas asignadas y enviar respuestas.

### Uso de JWT

1. **Login**: Envía credenciales a `/api/alumnos/login` o `/api/usuarios/login`.
2. **Recibe Token**: El servidor retorna un JWT en la respuesta.
3. **Usa Token**: Incluye el token en el header `Authorization: Bearer <token>` para solicitudes autenticadas.
4. **Expiración**: Los tokens expiran después de 3 horas por defecto.

Ejemplo de login de alumno:

```bash
curl -X POST http://localhost:8080/api/alumnos/login \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Manuel", "password": "Backend"}'
```

Respuesta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "idAlumno": 1,
  "nombre": "Manuel"
}
```

## 📖 Ejemplos de Uso

### Crear una Encuesta

```bash
curl -X POST http://localhost:8080/api/encuestas \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Encuesta de Satisfacción",
    "descripcion": "Evalúa la satisfacción del servicio",
    "idDepartamento": 1,
    "habilitada": true
  }'
```

### Listar Encuestas para Alumnos

```bash
curl -X GET http://localhost:8080/api/encuestas/alumnos \
  -H "Authorization: Bearer <token>"
```

### Enviar Respuesta a una Pregunta

```bash
curl -X POST http://localhost:8080/api/preguntas/1/respuestas \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "idRespuestaPosible": 2,
    "comentario": "Comentario opcional"
  }'
```

## 🧪 Notas de Desarrollo

### Estructura del Proyecto

- `controllers/`: Controladores REST para endpoints.
- `services/`: Lógica de negocio.
- `repositories/`: Interfaces para acceso a datos con JDBC.
- `models/`: Entidades del dominio.
- `dto/`: Objetos de transferencia de datos.
- `security/`: Configuración de seguridad y JWT.
- `exceptions/`: Manejo de excepciones personalizadas.

### Pruebas

Ejecuta las pruebas con:

```bash
mvn test
```

### Mejores Prácticas

- Usa validación de entrada con anotaciones `@Valid`.
- Maneja excepciones con `@ControllerAdvice`.
- Registra logs importantes para debugging.
- Mantén la separación de responsabilidades entre capas.

### Contribución

1. Crea una rama para tu feature: `git checkout -b feature/nueva-funcionalidad`
2. Realiza commits descriptivos.
3. Envía un pull request con descripción detallada.

---

**Desarrollado por SMART-HILL**
