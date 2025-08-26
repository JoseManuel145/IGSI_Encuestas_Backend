package com.igsi.encuestas.repositories;

import com.igsi.encuestas.models.DepartamentoModel;
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
public class DepartamentoRepository {
    private final JdbcTemplate jdbcTemplate;

    public DepartamentoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//  Mapper para convertir ResultSet a Departamento
    private final RowMapper<DepartamentoModel> departamentoRowMapper = (rs, rowNum) -> new DepartamentoModel(
        rs.getObject("id_departamento", Long.class),
        rs.getString("nombre"),
        rs.getString("descripcion"),
        rs.getBoolean("deleted")
    );
//  Listar todos los departamentos
    public List<DepartamentoModel> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM Departamentos WHERE deleted = FALSE",
                departamentoRowMapper
        );
    }
//  Buscar por ID
    public Optional<DepartamentoModel> getById(Long id) {
        return jdbcTemplate.query(
                "SELECT * FROM Departamentos WHERE id_departamento = ?",
                departamentoRowMapper,
                id
        ).stream().findFirst();
    }
//  Crear un departamento
    public Long save(DepartamentoModel departamento) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Departamentos(nombre, descripcion) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, departamento.getNombre());
            ps.setString(2, departamento.getDescripcion());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
//  Actualizar un departamento
    public int update(DepartamentoModel departamento) {
        return jdbcTemplate.update(
                "UPDATE Departamentos SET nombre = ?, descripcion = ? WHERE id_departamento = ?",
                departamento.getNombre(),
                departamento.getDescripcion(),
                departamento.getIdDepartamento()
        );
    }
//  Eliminar un departamento
    public int delete(Long id) {
        return jdbcTemplate.update(
                "DELETE FROM Departamentos WHERE id_departamento = ?",
                id
        );
    }
    //  Eliminar un departamento logicamente
    public int softDelete(Long id) {
        return jdbcTemplate.update(
                "UPDATE Departamentos SET deleted = TRUE WHERE id_departamento = ?",
                id
        );
    }
}
