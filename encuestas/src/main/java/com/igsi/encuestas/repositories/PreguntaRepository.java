package com.igsi.encuestas.repositories;

import com.igsi.encuestas.models.PreguntaModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class PreguntaRepository {
    private final JdbcTemplate template;

    public PreguntaRepository(JdbcTemplate template) {
        this.template = template;
    }

    private final RowMapper<PreguntaModel> preguntaRowMapper = (rs, rowNum) -> new PreguntaModel(
            rs.getObject("id_pregunta", Long.class),
            rs.getObject("id_seccion", Long.class),
            rs.getString("texto_pregunta"),
            rs.getObject("id_tipo_pregunta", Long.class),
            rs.getInt("orden"),
            rs.getString("ayuda"),
            rs.getInt("puntaje")
    );

    // Listar todas las preguntas de una sección
    public List<PreguntaModel> getAll(Long idSeccion) {
        return template.query(
                "SELECT * FROM Preguntas WHERE id_seccion = ?",
                preguntaRowMapper,
                idSeccion
        );
    }

    // Obtener una pregunta específica de una sección
    public Optional<PreguntaModel> getById(Long idSeccion, Long idPregunta) {
        return template.query(
                "SELECT * FROM Preguntas WHERE id_seccion = ? AND id_pregunta = ?",
                preguntaRowMapper,
                idSeccion,
                idPregunta
        ).stream().findFirst();
    }

    // Crear una nueva pregunta en una sección
    public Long savePregunta(PreguntaModel pregunta) {
        KeyHolder key = new GeneratedKeyHolder();

        template.update(con -> {
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO Preguntas(id_seccion, texto_pregunta, id_tipo_pregunta, orden, ayuda, puntaje) VALUES (?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setObject(1, pregunta.getIdSeccion());
            statement.setString(2, pregunta.getTextoPregunta());
            statement.setObject(3, pregunta.getIdTipoPregunta());
            statement.setInt(4, pregunta.getOrden());
            statement.setString(5, pregunta.getAyuda());
            statement.setInt(6, pregunta.getPuntaje());
            return statement;
        }, key);

        return key.getKey().longValue();
    }

    // Actualizar una pregunta existente
    public int updatePregunta(Long idPregunta, PreguntaModel pregunta) {
        return template.update(
                "UPDATE Preguntas " +
                        "SET id_seccion = ?, texto_pregunta = ?, id_tipo_pregunta = ?, orden = ?, ayuda = ?, puntaje = ? " +
                        "WHERE id_pregunta = ?",
                pregunta.getIdSeccion(),
                pregunta.getTextoPregunta(),
                pregunta.getIdTipoPregunta(),
                pregunta.getOrden(),
                pregunta.getAyuda(),
                pregunta.getPuntaje(),
                idPregunta
        );
    }

    // Eliminar una pregunta de una sección
    public int delete(Long idPregunta) {
        return template.update(
                "DELETE FROM Preguntas WHERE id_pregunta = ?",
                idPregunta
        );
    }
}
