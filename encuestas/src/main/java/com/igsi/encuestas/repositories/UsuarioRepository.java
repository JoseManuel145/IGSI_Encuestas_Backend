package com.igsi.encuestas.repositories;

import com.igsi.encuestas.models.UsuarioModel;
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
public class UsuarioRepository {
    private final JdbcTemplate jdbcTemplate;
    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
// Mapper para convertir ResultSet a Usuario
    private final RowMapper<UsuarioModel> usuarioRowMapper = (rs, rowNum) -> new UsuarioModel(
            rs.getObject("id_usuario", Long.class),
            rs.getString("nombre"),
            rs.getString("correo"),
            rs.getString("password"),
            rs.getString("rol"),
            rs.getObject("id_departamento", Long.class)
    );
//  Listar todos los usuarios
    public List<UsuarioModel> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM Usuarios",
                usuarioRowMapper
        );
    }
//  Buscar por ID
    public Optional<UsuarioModel> getById(Long id) {
        return jdbcTemplate.query(
                "SELECT * FROM Usuarios WHERE id_usuario = ?",
                usuarioRowMapper,
                id
        ).stream().findFirst();
    }
//  Buscar por Correo
    public Optional<UsuarioModel> getByCorreo(String correo) {
        return jdbcTemplate.query(
                "SELECT * FROM Usuarios WHERE correo = ?",
                usuarioRowMapper,
                correo
        ).stream().findFirst();
    }
//  Registrar un Usuario
    public Long saveUser(UsuarioModel usuario) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Usuarios(nombre, correo, password, rol, id_departamento) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol());
            ps.setLong(5, usuario.getIdDepartamento());

            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
//  Actualizar un Usuario
    public int updateUser(UsuarioModel usuario) {
        return jdbcTemplate.update(
                "UPDATE Usuarios SET nombre=?,correo=?, password=?, rol=?, id_departamento=? WHERE id_usuario=?",
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getPassword(),
                usuario.getRol(),
                usuario.getIdDepartamento(),
                usuario.getIdUsuario()
        );
    }
//  Eliminar un Usuario
    public int delete(Long id) {
        return jdbcTemplate.update(
                "DELETE FROM Usuarios WHERE id_usuario=?",
                id
        );
    }
}