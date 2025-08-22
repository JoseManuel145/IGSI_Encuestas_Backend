# RUTAS IGSI

---

## 👨‍🎓 Alumnos
| Método | Ruta                                    | Descripción                 |
|--------|-----------------------------------------|-----------------------------|
| GET    | `/api/alumnos`                          | Listar todos los alumnos    |
| GET    | `/api/alumnos/{id}`                     | Obtener un alumno por ID    |
| GET    | `/api/alumnos/nombre/{nombre}`          | Obtener un alumno por nombre|
| POST   | `/api/alumnos`                          | Registrar un alumno         |
| DELETE | `/api/alumnos/{id}`                     | Eliminar un alumno          |
| POST   | `/api/alumnos/login`                    | Login de alumno             |

---

## 🏢 Departamentos
| Método | Ruta                                    | Descripción                              |
|--------|-----------------------------------------|------------------------------------------|
| GET    | `/api/departamentos`                    | Listar departamentos                      |
| GET    | `/api/departamentos/{id}`               | Obtener un departamento por ID            |
| POST   | `/api/departamentos`                    | Crear departamento (**solo AdminGeneral**)|
| PUT    | `/api/departamentos/{id}`               | Actualizar departamento (**solo AdminGeneral**) |
| DELETE | `/api/departamentos/{id}`               | Eliminar departamento (hard delete, **solo AdminGeneral**) |
| PATCH  | `/api/departamentos/{id}/soft-delete`   | Soft delete de un departamento (**solo AdminGeneral**) |

---

## 👥 Usuarios
| Método | Ruta                                    | Descripción                              |
|--------|-----------------------------------------|------------------------------------------|
| GET    | `/api/usuarios`                         | Listar usuarios                          |
| GET    | `/api/usuarios/{id}`                    | Obtener un usuario por ID                 |
| GET    | `/api/usuarios/correo/{correo}`         | Obtener un usuario por correo             |
| POST   | `/api/usuarios`                         | Crear usuario (**solo AdminGeneral**)     |
| PUT    | `/api/usuarios/{id}`                    | Actualizar usuario (**solo AdminGeneral**)|
| DELETE | `/api/usuarios/{id}`                    | Eliminar usuario (**solo AdminGeneral**)  |
| POST   | `/api/usuarios/login`                   | Login de usuario                         |

---

## ❓ Tipos de Preguntas
| Método | Ruta                                    | Descripción               |
|--------|-----------------------------------------|---------------------------|
| GET    | `/api/tipo-pregunta`                    | Listar todos los tipos    |
| GET    | `/api/tipo-pregunta/{id}`               | Obtener un tipo por ID    |
| POST   | `/api/tipo-pregunta`                    | Crear un tipo de pregunta |
| PUT    | `/api/tipo-pregunta/{id}`               | Actualizar un tipo        |
| DELETE | `/api/tipo-pregunta/{id}`               | Eliminar un tipo          |

---

## 📊 Encuestas
| Método | Ruta                                    | Descripción             |
|--------|-----------------------------------------|-------------------------|
| GET    | `/api/encuestas`                        | Listar todas            |
| GET    | `/api/encuestas/{id}`                   | Obtener por ID          |
| GET    | `/api/encuestas/departamento/{id}`      | Obtener por departamento|
| POST   | `/api/encuestas`                        | Crear                   |
| PUT    | `/api/encuestas/{id}`                   | Actualizar              |
| DELETE | `/api/encuestas/{id}`                   | Eliminar (hard delete)  |
| PATCH  | `/api/encuestas/{id}/soft-delete`       | Soft delete             |
| PATCH  | `/api/encuestas/{id}/restaurar`         | Restaurar encuesta      |

---

## 📂 Secciones
| Método | Ruta                                                                 | Descripción                 |
|--------|----------------------------------------------------------------------|-----------------------------|
| GET    | `/api/encuestas/{idEncuesta}/secciones`                              | Listar secciones de encuesta|
| GET    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}`                  | Obtener una sección         |
| POST   | `/api/encuestas/{idEncuesta}/secciones`                              | Crear sección               |
| PUT    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}`                  | Actualizar sección          |
| DELETE | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}`                  | Eliminar sección            |

---

## 📝 Preguntas
| Método | Ruta                                                                 | Descripción                          |
|--------|----------------------------------------------------------------------|--------------------------------------|
| GET    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}/preguntas`        | Listar todas las preguntas de sección|
| GET    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}/preguntas/{idPregunta}` | Obtener una pregunta específica       |
| POST   | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}/preguntas`        | Crear pregunta en sección            |
| PUT    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}/preguntas/{idPregunta}` | Actualizar pregunta                  |
| DELETE | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}/preguntas/{idPregunta}` | Eliminar pregunta                    |

## ✅ Respuestas Posibles
| Método | Ruta                                                                                                           | Descripción                           |
|--------|----------------------------------------------------------------------------------------------------------------|---------------------------------------|
| GET    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}/preguntas/{idPregunta}/respuestas`                         | Listar todas las respuestas posibles de una pregunta |
| POST   | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}/preguntas/{idPregunta}/respuestas`                         | Crear una nueva respuesta posible     |
| PUT    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}/preguntas/{idPregunta}/respuestas/{idRespuesta}`           | Actualizar una respuesta existente   |
| DELETE | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}/preguntas/{idPregunta}/respuestas/{idRespuesta}`           | Eliminar una respuesta posible        |
