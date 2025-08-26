package com.igsi.encuestas.repositories;

import com.igsi.encuestas.models.SeccionEncuestaModel;
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
public class SeccionEncuestaRepository {
    private final JdbcTemplate template;
    public SeccionEncuestaRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }
    private final RowMapper<SeccionEncuestaModel> seccionRowMapper = (rs, rowNum) -> new SeccionEncuestaModel(
            rs.getObject("id_seccion", Long.class),
            rs.getObject("id_encuesta", Long.class),
            rs.getString("titulo"),
            rs.getString("descripcion"),
            rs.getObject("orden", Integer.class)
    );
// Listar todas las secciones de una encuesta
    public List<SeccionEncuestaModel> getAll(Long idEncuesta) {
        return template.query(
                "SELECT * FROM Secciones_Encuesta WHERE id_encuesta = ?",
                seccionRowMapper,
                idEncuesta
        );
    }
// Buscar una sección
    public Optional<SeccionEncuestaModel> getById(Long idEncuesta, Long idSeccion) {
        return template.query(
                "SELECT * FROM Secciones_Encuesta WHERE id_seccion = ? AND id_encuesta = ?",
                seccionRowMapper,
                idSeccion,
                idEncuesta
        ).stream().findFirst();
    }
// Crear una sección
    public Long save(SeccionEncuestaModel seccion) {
        KeyHolder holder = new GeneratedKeyHolder();

        template.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Secciones_Encuesta(id_encuesta, titulo, descripcion, orden) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setObject(1, seccion.getIdEncuesta());
            ps.setString(2, seccion.getTitulo());
            ps.setString(3, seccion.getDescripcion());
            ps.setObject(4, seccion.getOrden());
            return ps;
        }, holder);

        return holder.getKey().longValue();
    }
// Actualizar sección
    public int update(Long idSeccion, SeccionEncuestaModel seccion) {
        return template.update(
                "UPDATE Secciones_Encuesta " +
                        "SET titulo = ?, descripcion = ?, orden = ? " +
                        "WHERE id_seccion = ?",
                seccion.getTitulo(),
                seccion.getDescripcion(),
                seccion.getOrden(),
                idSeccion
        );
    }
// Eliminar sección
    public int delete(Long idSeccion) {
        return template.update(
                "DELETE FROM Secciones_Encuesta WHERE id_seccion = ?",
                idSeccion
        );
    }
}