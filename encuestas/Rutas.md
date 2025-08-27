# RUTAS IGSI

---

## 👨‍🎓 Alumnos
| Método | Ruta                           | Descripción                  |
|--------|--------------------------------|------------------------------|
| GET    | `/api/alumnos`                 | Listar todos los alumnos     |
| GET    | `/api/alumnos/{id}`            | Obtener un alumno por ID     |
| GET    | `/api/alumnos/nombre/{nombre}` | Obtener un alumno por nombre |
| POST   | `/api/alumnos`                 | Registrar un alumno          |
| DELETE | `/api/alumnos/{id}`            | Eliminar un alumno           |
| POST   | `/api/alumnos/login`           | Login de alumno              |

---

## 🏢 Departamentos
| Método | Ruta                                  | Descripción                                                |
|--------|---------------------------------------|------------------------------------------------------------|
| GET    | `/api/departamentos`                  | Listar departamentos                                       |
| GET    | `/api/departamentos/{id}`             | Obtener un departamento por ID                             |
| POST   | `/api/departamentos`                  | Crear departamento (**solo AdminGeneral**)                 |
| PUT    | `/api/departamentos/{id}`             | Actualizar departamento (**solo AdminGeneral**)            |
| DELETE | `/api/departamentos/{id}`             | Eliminar departamento (hard delete, **solo AdminGeneral**) |
| PATCH  | `/api/departamentos/{id}/soft-delete` | Soft delete de un departamento (**solo AdminGeneral**)     |
| PATCH  | `/api/departamentos/{id}/restaurar`   | Restaura un departamento (**solo AdminGeneral**)           |

---

## 👥 Usuarios
| Método | Ruta                            | Descripción                                |
|--------|---------------------------------|--------------------------------------------|
| GET    | `/api/usuarios`                 | Listar usuarios (**Admin/Empleado**)       |
| GET    | `/api/usuarios/{id}`            | Obtener un usuario por ID (**Admin/Empleado**) |
| GET    | `/api/usuarios/correo/{correo}` | Obtener un usuario por correo             |
| POST   | `/api/usuarios`                 | Crear usuario (**solo AdminGeneral**)      |
| PUT    | `/api/usuarios/{id}`            | Actualizar usuario (**solo AdminGeneral**) |
| DELETE | `/api/usuarios/{id}`            | Eliminar usuario (**solo AdminGeneral**)   |
| POST   | `/api/usuarios/login`           | Login de usuario                           |

---

## ❓ Tipos de Preguntas
| Método | Ruta                                    | Descripción                      |
|--------|-----------------------------------------|----------------------------------|
| GET    | `/api/tipo-pregunta`                    | Listar todos los tipos           |
| GET    | `/api/tipo-pregunta/{id}`               | Obtener un tipo por ID           |
| POST   | `/api/tipo-pregunta`                    | Crear un tipo de pregunta        |
| PUT    | `/api/tipo-pregunta/{id}`               | Actualizar un tipo de pregunta   |
| DELETE | `/api/tipo-pregunta/{id}`               | Eliminar un tipo de pregunta     |

---

## 📊 Encuestas
| Método | Ruta                               | Descripción                                |
|--------|------------------------------------|--------------------------------------------|
| GET    | `/api/encuestas/master`            | Listar todas las encuestas (master)       |
| GET    | `/api/encuestas/alumnos`           | Listar encuestas disponibles a alumnos    |
| GET    | `/api/encuestas/{id}`              | Obtener encuesta por ID                    |
| GET    | `/api/encuestas/{id}/completa`     | Obtener encuesta completa                  |
| GET    | `/api/encuestas/departamento/{id}` | Obtener encuestas por departamento        |
| POST   | `/api/encuestas`                   | Crear encuesta                             |
| PUT    | `/api/encuestas/{id}`              | Actualizar encuesta                        |
| DELETE | `/api/encuestas/{id}`              | Eliminar encuesta (hard delete)           |
| PATCH  | `/api/encuestas/{id}/soft-delete`  | Soft delete de encuesta                     |
| PATCH  | `/api/encuestas/{id}/restaurar`    | Restaurar encuesta                          |

---

## 📂 Secciones
| Método | Ruta                                                | Descripción                  |
|--------|-----------------------------------------------------|------------------------------|
| GET    | `/api/encuestas/{idEncuesta}/secciones`             | Listar secciones de encuesta |
| GET    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}` | Obtener una sección          |
| POST   | `/api/encuestas/{idEncuesta}/secciones`             | Crear sección                |
| PUT    | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}` | Actualizar sección           |
| DELETE | `/api/encuestas/{idEncuesta}/secciones/{idSeccion}` | Eliminar sección             |

---

## 📝 Preguntas
| Método | Ruta                                       | Descripción                           |
|--------|--------------------------------------------|---------------------------------------|
| GET    | `/api/secciones/{idSeccion}/preguntas`      | Listar todas las preguntas de sección |
| GET    | `/api/secciones/{idSeccion}/preguntas/{idPregunta}` | Obtener una pregunta específica       |
| POST   | `/api/secciones/{idSeccion}/preguntas`     | Crear pregunta en sección             |
| PUT    | `/api/secciones/{idSeccion}/preguntas/{idPregunta}` | Actualizar pregunta                   |
| DELETE | `/api/secciones/{idSeccion}/preguntas/{idPregunta}` | Eliminar pregunta                     |

---

## ✅ Respuestas Posibles
| Método | Ruta                                                      | Descripción                                          |
|--------|-----------------------------------------------------------|------------------------------------------------------|
| GET    | `/api/preguntas/{idPregunta}/respuestasPosibles`          | Listar todas las respuestas posibles de una pregunta |
| POST   | `/api/preguntas/{idPregunta}/respuestasPosibles`          | Crear una nueva respuesta posible                    |
| PUT    | `/api/preguntas/{idPregunta}/respuestasPosibles/{idRespuesta}` | Actualizar una respuesta existente                  |
| DELETE | `/api/preguntas/{idPregunta}/respuestasPosibles/{idRespuesta}` | Eliminar una respuesta posible                       |
