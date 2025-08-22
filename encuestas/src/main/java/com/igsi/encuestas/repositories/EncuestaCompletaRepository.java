package com.igsi.encuestas.repositories;

import com.igsi.encuestas.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EncuestaCompletaRepository {

    private final JdbcTemplate jdbcTemplate;

    public EncuestaCompletaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMappers
    private final RowMapper<EncuestaModel> encuestaRowMapper = (rs, rowNum) -> new EncuestaModel(
            rs.getObject("id_encuesta", Long.class),
            rs.getString("titulo"),
            rs.getString("descripcion"),
            rs.getObject("id_departamento", Long.class),
            rs.getDate("fecha_inicio").toLocalDate(),
            rs.getDate("fecha_fin").toLocalDate(),
            rs.getString("estado"),
            rs.getBoolean("deleted")
    );

    private final RowMapper<SeccionEncuestaModel> seccionRowMapper = (rs, rowNum) -> new SeccionEncuestaModel(
            rs.getObject("id_seccion", Long.class),
            rs.getObject("id_encuesta", Long.class),
            rs.getString("titulo"),
            rs.getString("descripcion"),
            rs.getInt("orden")
    );

    private final RowMapper<PreguntaModel> preguntaRowMapper = (rs, rowNum) -> new PreguntaModel(
            rs.getObject("id_pregunta", Long.class),
            rs.getObject("id_seccion", Long.class),
            rs.getString("texto_pregunta"),
            rs.getObject("id_tipo_pregunta", Long.class),
            rs.getInt("orden"),
            rs.getString("ayuda"),
            rs.getInt("puntaje")
    );

    private final RowMapper<RespuestaPosibleModel> respuestaRowMapper = (rs, rowNum) -> new RespuestaPosibleModel(
            rs.getObject("id_respuesta_posible", Long.class),
            rs.getObject("id_pregunta", Long.class),
            rs.getString("texto_respuesta"),
            rs.getInt("puntaje"),
            rs.getBoolean("es_correcta")
    );

    // Métodos para listar
    public EncuestaModel getEncuesta(Long idEncuesta) {
        return jdbcTemplate.query(
                "SELECT * FROM Encuestas WHERE id_encuesta = ?",
                encuestaRowMapper,
                idEncuesta
        ).stream().findFirst().orElse(null);
    }

    public List<SeccionEncuestaModel> getSecciones(Long idEncuesta) {
        return jdbcTemplate.query(
                "SELECT * FROM Secciones_Encuesta WHERE id_encuesta = ? ORDER BY orden",
                seccionRowMapper,
                idEncuesta
        );
    }

    public List<PreguntaModel> getPreguntas(Long idSeccion) {
        return jdbcTemplate.query(
                "SELECT * FROM Preguntas WHERE id_seccion = ? ORDER BY orden",
                preguntaRowMapper,
                idSeccion
        );
    }

    public List<RespuestaPosibleModel> getRespuestas(Long idPregunta) {
        return jdbcTemplate.query(
                "SELECT * FROM Respuestas_Posibles WHERE id_pregunta = ?",
                respuestaRowMapper,
                idPregunta
        );
    }
}
