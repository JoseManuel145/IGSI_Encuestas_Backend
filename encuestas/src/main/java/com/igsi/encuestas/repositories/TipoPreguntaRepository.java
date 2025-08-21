package com.igsi.encuestas.repositories;

import com.igsi.encuestas.models.TipoPreguntaModel;
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
public class TipoPreguntaRepository {

    private final JdbcTemplate jdbcTemplate;

    public TipoPreguntaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<TipoPreguntaModel> rowMapper = (rs, rowNum) -> new TipoPreguntaModel(
            rs.getObject("id_tipo", Long.class),
            rs.getString("nombre"),
            rs.getString("descripcion")
    );

    // Listar todos
    public List<TipoPreguntaModel> getAll() {
        return jdbcTemplate.query("SELECT * FROM Tipos_Pregunta", rowMapper);
    }

    // Obtener por ID
    public Optional<TipoPreguntaModel> getById(Long id) {
        return jdbcTemplate.query("SELECT * FROM Tipos_Pregunta WHERE id_tipo = ?", rowMapper, id)
                .stream().findFirst();
    }

    // Guardar
    public Long save(TipoPreguntaModel tipoPregunta) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Tipos_Pregunta(nombre, descripcion) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tipoPregunta.getNombre());
            ps.setString(2, tipoPregunta.getDescripcion());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    // Actualizar
    public int update(TipoPreguntaModel tipoPregunta) {
        return jdbcTemplate.update(
                "UPDATE Tipos_Pregunta SET nombre = ?, descripcion = ? WHERE id_tipo = ?",
                tipoPregunta.getNombre(),
                tipoPregunta.getDescripcion(),
                tipoPregunta.getIdTipo()
        );
    }

    // Eliminar
    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM Tipos_Pregunta WHERE id_tipo = ?", id);
    }
}
