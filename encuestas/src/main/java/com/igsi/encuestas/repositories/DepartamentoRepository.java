package com.igsi.encuestas.repositories;

import com.igsi.encuestas.models.DepartamentoModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
                "SELECT * FROM Departamentos",
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
    public void saveDepartamento(DepartamentoModel departamento) {
        jdbcTemplate.update(
                "INSERT INTO Departamentos(nombre, descripcion, deleted) VALUES (?, ?, ?)",
                departamento.getNombre(),
                departamento.getDescripcion(),
                departamento.getDeleted()
        );
    }
//  Actualizar un departamento
    public int updateDepartamento(DepartamentoModel departamento) {
        return jdbcTemplate.update(
                "UPDATE Departamentos SET nombre = ?, descripcion = ?, deleted = ? WHERE id_departamento = ?",
                departamento.getNombre(),
                departamento.getDescripcion(),
                departamento.getDeleted()
        );
    }
//  Eliminar un departamento
    public int delete(Long id) {
        return jdbcTemplate.update(
                "DELETE FROM Departamentos WHERE id_departamento = ?",
                id
        );
    }
}
