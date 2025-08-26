package com.igsi.encuestas.repositories;

import com.igsi.encuestas.models.RespuestaModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RespuestaRepository {
    private final JdbcTemplate template;

    public RespuestaRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    private final RowMapper<RespuestaModel> rowMapper = (rs, rowNum) -> new RespuestaModel(
            rs.getObject("id_respuesta_alumno", Long.class),
            rs.getObject("id_alumno", Long.class),
            rs.getObject("id_pregunta", Long.class),
            rs.getObject("id_respuesta_posible", Long.class),
            rs.getString("respuesta_abierta")
    );

    // CREATE
    public Long saveRespuesta(RespuestaModel respuestaPosible) {
        KeyHolder holder = new GeneratedKeyHolder();

        template.update(con -> {
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO Respuestas_Alumnos(id_pregunta,id_alumno, id_respuesta_posible, respuesta_abierta) " +
                            "VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setObject(1, respuestaPosible.getId_pregunta());
            statement.setObject(2, respuestaPosible.getId_alumno());
            statement.setObject(3, respuestaPosible.getId_respuesta_posible());
            statement.setString(4, respuestaPosible.getRespuesta_abierta());

            return statement;
        }, holder);
        return holder.getKey().longValue();
    }

    // READ
    public List<Map<String, Object>> getRespuestasAlumnoEncuesta(Long idAlumno, Long idEncuesta) {
        String sql = """
        SELECT
            e.id_encuesta,
            e.titulo AS encuesta,
            s.id_seccion,
            s.titulo AS seccion,
            p.id_pregunta,
            p.texto_pregunta,
            rp.id_respuesta_posible,
            rp.texto_respuesta AS opcion_elegida,
            ra.respuesta_abierta
        FROM Respuestas_Alumnos ra
        JOIN Preguntas p ON ra.id_pregunta = p.id_pregunta
        JOIN Secciones_Encuesta s ON p.id_seccion = s.id_seccion
        JOIN Encuestas e ON s.id_encuesta = e.id_encuesta
        LEFT JOIN Respuestas_Posibles rp ON ra.id_respuesta_posible = rp.id_respuesta_posible
        WHERE ra.id_alumno = ? AND e.id_encuesta = ?
        ORDER BY s.orden, p.orden
        """;
        return template.queryForList(sql, idAlumno, idEncuesta);
    }

    public Optional<RespuestaModel> getById(Long idRespuesta) {
        return template.query(
                "SELECT * FROM Respuestas_Alumnos WHERE id_respuesta_alumno = ?",
                rowMapper,
                idRespuesta
        ).stream().findFirst();
    }
    // UPDATE
    public int updateRespuesta(Long id,RespuestaModel respuesta) {
        String sql = """
            UPDATE Respuestas_Alumnos
            SET id_alumno = ?, id_respuesta_posible = ?, respuesta_abierta = ?
            WHERE id_respuesta_alumno = ?
        """;
        return template.update(
                sql,
                respuesta.getId_alumno(),
                respuesta.getId_respuesta_posible(),
                respuesta.getRespuesta_abierta(),
                id
        );
    }

    // DELETE
    public int deleteRespuesta(Long idRespuestaAlumno) {
        String sql = "DELETE FROM Respuestas_Alumnos WHERE id_respuesta_alumno = ?";
        return template.update(sql, idRespuestaAlumno);
    }

    // COMPLETAR ENCUESTA Y GUARDAR EL REGISTRO
    public Long completarEncuesta(Long idEncuesta, Long idAlumno) {
        KeyHolder holder = new GeneratedKeyHolder();

        template.update(con -> {
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO encuestas_completadas(id_encuesta, id_alumno) " +
                            "VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setObject(1, idEncuesta);
            statement.setObject(2, idAlumno);
            return statement;
        }, holder);
        return holder.getKey().longValue();
    }
}