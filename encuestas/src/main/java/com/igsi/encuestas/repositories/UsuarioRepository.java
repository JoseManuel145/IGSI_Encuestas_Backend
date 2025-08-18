package com.igsi.encuestas.repositories;

import com.igsi.encuestas.models.UsuarioModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
                usuarioRowMapper);
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
    public void saveUser(UsuarioModel usuario) {
        jdbcTemplate.update(
                "INSERT INTO Usuarios(correo, password, rol, id_departamento) VALUES (?, ?, ?, ?)",
                usuario.getCorreo(),
                usuario.getPassword(),
                usuario.getRol(),
                usuario.getIdDepartamento()
        );
    }
//  Actualizar un Usuario
    public int updateUser(UsuarioModel usuario) {
        return jdbcTemplate.update(
                "UPDATE Usuarios SET correo=?, password=?, rol=?, id_departamento=? WHERE id_usuario=?",
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