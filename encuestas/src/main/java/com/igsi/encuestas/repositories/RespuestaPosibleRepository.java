package com.igsi.encuestas.repositories;

import com.igsi.encuestas.models.RespuestaPosibleModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RespuestaPosibleRepository {
    private final JdbcTemplate template;

    public RespuestaPosibleRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    private final RowMapper<RespuestaPosibleModel> rowMapper = (rs, rn) -> new RespuestaPosibleModel(
            rs.getObject("id_respuesta_posible", Long.class),
            rs.getObject("id_pregunta", Long.class),
            rs.getString("texto_respuesta"),
            rs.getInt("puntaje"),
            rs.getBoolean("es_correcta")
    );
//  Listar todas las repuestas posibles de una pregunta
    public List<RespuestaPosibleModel> getAll(Long idPregunta) {
        return template.query(
                "SELECT * FROM Respuestas_Posibles WHERE id_pregunta = ?",
                rowMapper,
                idPregunta
        );
    }
//  Crear una respuesta de una pregunta
    public Long saveRespuesta(RespuestaPosibleModel respuestaPosible) {
        KeyHolder holder = new GeneratedKeyHolder();

        template.update(con -> {
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO Respuestas_Posibles(id_pregunta, texto_respuesta, puntaje, es_correcta) " +
                            "VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setObject(1, respuestaPosible.getIdPregunta());
            statement.setString(2, respuestaPosible.getTextoRespuesta());
            statement.setInt(3, respuestaPosible.getPuntaje());
            statement.setBoolean(4, respuestaPosible.getEsCorrecta());

            return statement;
        }, holder);
        return holder.getKey().longValue();
    }

    // Actualizar una respuesta posible de una pregunta
    public int updateRespuesta(Long idRespuestaPosible, RespuestaPosibleModel respuesta) {
        return template.update(
                "UPDATE Respuestas_Posibles " +
                        "SET id_pregunta = ?, texto_respuesta = ?, puntaje = ?, es_correcta = ? " +
                        "WHERE id_respuesta_posible = ?",
                respuesta.getIdPregunta(),
                respuesta.getTextoRespuesta(),
                respuesta.getPuntaje(),
                respuesta.getEsCorrecta(),
                idRespuestaPosible
        );
    }

    //  Eliminar una respuesta posible de una pregunta
    public int delete(Long idRespuestaPosible) {
        return template.update(
                "DELETE FROM Respuestas_Posibles WHERE id_respuesta_posible = ?",
                idRespuestaPosible
        );
    }
}
