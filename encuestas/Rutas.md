

## 🔐 Seguridad / Autenticación

| Método | Ruta                  | Descripción                   |
|--------|-----------------------|-------------------------------|
| POST   | `/api/alumnos/login`  | Login de alumno, retorna JWT  |
| POST   | `/api/usuarios/login` | Login de usuario, retorna JWT |

---

## 👨‍🎓 Alumnos

| Método | Ruta                           | Descripción                  |
|--------|--------------------------------|------------------------------|
| GET    | `/api/alumnos`                 | Listar todos los alumnos     |
| GET    | `/api/alumnos/{id}`            | Obtener un alumno por ID     |
| GET    | `/api/alumnos/nombre/{nombre}` | Obtener un alumno por nombre |
| POST   | `/api/alumnos`                 | Registrar un alumno          |
| DELETE | `/api/alumnos/{id}`            | Eliminar un alumno           |

---

## 🏢 Departamentos

| Método | Ruta                                  | Descripción                                     |
|--------|---------------------------------------|-------------------------------------------------|
| GET    | `/api/departamentos`                  | Listar todos los departamentos                  |
| GET    | `/api/departamentos/{id}`             | Obtener un departamento por ID                  |
| POST   | `/api/departamentos`                  | Crear un departamento (AdminGeneral)            |
| PUT    | `/api/departamentos/{id}`             | Actualizar un departamento (AdminGeneral)       |
| DELETE | `/api/departamentos/{id}`             | Eliminar un departamento (AdminGeneral)         |
| PATCH  | `/api/departamentos/{id}/soft-delete` | Soft delete de departamento (AdminGeneral)      |
| PATCH  | `/api/departamentos/{id}/restaurar`   | Restaurar departamento eliminado (AdminGeneral) |

---

## 📋 Encuestas

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

---

## 📚 Secciones de Encuesta

| Método | Ruta                                                | Descripción                                 |
|--------|-----------------------------------------------------|---------------------------------------------|
| GET    | `/api/encuestas/{idEncuesta}/secciones`             | Listar todas las secciones de una encuesta  |
| GET    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}` | Obtener una sección por ID                  |
| POST   | `/api/encuestas/{idEncuesta}/secciones`             | Crear sección (AdminGeneral, Empleado)      |
| PUT    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}` | Actualizar sección (AdminGeneral, Empleado) |
| DELETE | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}` | Eliminar sección (AdminGeneral, Empleado)   |

---

## ❓ Preguntas

| Método | Ruta                                                | Descripción                                  |
|--------|-----------------------------------------------------|----------------------------------------------|
| GET    | `/api/secciones/{idSeccion}/preguntas`              | Listar todas las preguntas de una sección    |
| GET    | `/api/secciones/{idSeccion}/preguntas/{idPregunta}` | Obtener pregunta por ID                      |
| POST   | `/api/secciones/{idSeccion}/preguntas`              | Crear pregunta (AdminGeneral, Empleado)      |
| PUT    | `/api/secciones/{idSeccion}/preguntas/{idPregunta}` | Actualizar pregunta (AdminGeneral, Empleado) |
| DELETE | `/api/secciones/{idSeccion}/preguntas/{idPregunta}` | Eliminar pregunta (AdminGeneral, Empleado)   |

---

## ✅ Respuestas de Alumnos

| Método | Ruta                                                   | Descripción                                                              |
|--------|--------------------------------------------------------|--------------------------------------------------------------------------|
| POST   | `/api/preguntas/{idPregunta}/respuestas`               | Guardar respuesta de un alumno                                           |
| PUT    | `/api/preguntas/{idPregunta}/respuestas/{idRespuesta}` | Actualizar respuesta de un alumno                                        |
| GET    | `/api/preguntas/{idPregunta}/respuestas/respuestas`    | Obtener estadísticas de respuestas (cuántas veces se eligió cada opción) |

---

## 📝 Respuestas Posibles

| Método | Ruta                                                           | Descripción                                           |
|--------|----------------------------------------------------------------|-------------------------------------------------------|
| GET    | `/api/preguntas/{idPregunta}/respuestasPosibles`               | Listar todas las respuestas posibles de una pregunta  |
| POST   | `/api/preguntas/{idPregunta}/respuestasPosibles`               | Crear una respuesta posible (AdminGeneral, Empleado)  |
| PUT    | `/api/preguntas/{idPregunta}/respuestasPosibles/{idRespuesta}` | Actualizar respuesta posible (AdminGeneral, Empleado) |
| DELETE | `/api/preguntas/{idPregunta}/respuestasPosibles/{idRespuesta}` | Eliminar respuesta posible (AdminGeneral, Empleado)   |

---

## 🏷 Tipo de Pregunta

| Método | Ruta                      | Descripción                                          |
|--------|---------------------------|------------------------------------------------------|
| GET    | `/api/tipo-pregunta`      | Listar todos los tipos de pregunta                   |
| GET    | `/api/tipo-pregunta/{id}` | Obtener tipo de pregunta por ID                      |
| POST   | `/api/tipo-pregunta`      | Crear tipo de pregunta (AdminGeneral, Empleado)      |
| PUT    | `/api/tipo-pregunta/{id}` | Actualizar tipo de pregunta (AdminGeneral, Empleado) |
| DELETE | `/api/tipo-pregunta/{id}` | Eliminar tipo de pregunta (AdminGeneral, Empleado)   |

---

## 👤 Usuarios

| Método | Ruta                            | Descripción                                        |
|--------|---------------------------------|----------------------------------------------------|
| GET    | `/api/usuarios`                 | Listar todos los usuarios (AdminGeneral, Empleado) |
| GET    | `/api/usuarios/{id}`            | Obtener usuario por ID (AdminGeneral, Empleado)    |
| GET    | `/api/usuarios/correo/{correo}` | Obtener usuario por correo                         |
| POST   | `/api/usuarios`                 | Crear usuario (AdminGeneral)                       |
| PUT    | `/api/usuarios/{id}`            | Actualizar usuario (AdminGeneral)                  |
| DELETE | `/api/usuarios/{id}`            | Eliminar usuario (AdminGeneral)                    |
| POST   | `/api/usuarios/login`           | Login de usuario, retorna JWT                      |

---

## ⚡ Demo / Ping

| Método | Ruta        | Descripción                                            |
|--------|-------------|--------------------------------------------------------|
| GET    | `/api/ping` | Endpoint de prueba para verificar conexión del backend |

---