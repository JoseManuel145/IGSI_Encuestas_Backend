package com.igsi.encuestas.repositories;

import com.igsi.encuestas.models.AlumnoModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AlumnoRepository {
    private final JdbcTemplate jdbcTemplate;

    public AlumnoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<AlumnoModel> alumnoRowMapper = (rs, rowNum) -> new AlumnoModel(
            rs.getObject("id_alumno", Long.class),
            rs.getString("nombre"),
            rs.getString("password")
    );

//  LISTAR TODOS LOS ALUMNOS
    public List<AlumnoModel> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM Alumnos",
                alumnoRowMapper
        );
    }
//  OBTENER POR NOMBRE
    public Optional<AlumnoModel> getByNombre(String nombre) {
        return jdbcTemplate.query(
                "SELECT * FROM Alumnos WHERE nombre = ?",
                alumnoRowMapper,
                nombre
        ).stream().findFirst();
    }
//  OBTENER POR NOMBRE
    public Optional<AlumnoModel> getById(Long id) {
        return jdbcTemplate.query(
                "SELECT * FROM Alumnos WHERE id_alumno = ?",
                alumnoRowMapper,
                id
        ).stream().findFirst();
    }

//  REGISTRAR UN ALUMNO
    public void saveAlumno(AlumnoModel alumno) {
        jdbcTemplate.update(
                "INSERT INTO Alumnos(nombre,password) VALUES (?, ?)",
                alumno.getNombre(),
                alumno.getPassword()
        );
    }
//  ELIMINAR UN ALUMNO
    public int delete(Long id) {
        return jdbcTemplate.update(
                "DELETE FROM Alumnos WHERE id_alumno = ?",
                id
        );
    }
}