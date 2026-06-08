package com.gestorcerveja.repository;

import com.gestorcerveja.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {
    private final JdbcTemplate jdbc;
    public UsuarioRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Usuario> mapper = (rs, n) -> new Usuario(
            rs.getInt("idusuario"), rs.getString("nome"),
            rs.getString("senha"), rs.getInt("idrole"));

    public List<Usuario>     findAll()             { return jdbc.query("SELECT * FROM Usuario", mapper); }
    public Optional<Usuario> findById(int id)      { return jdbc.query("SELECT * FROM Usuario WHERE idusuario=?", mapper, id).stream().findFirst(); }
    public Optional<Usuario> findByNome(String nm) { return jdbc.query("SELECT * FROM Usuario WHERE nome=?", mapper, nm).stream().findFirst(); }

    public void insert(Usuario u) {
        jdbc.update("INSERT INTO Usuario (nome,senha,idrole) VALUES (?,?,?)", u.getNome(), u.getSenha(), u.getIdrole());
    }
    public void update(int id, String novoNome, String novaSenha) {
        if (novaSenha == null || novaSenha.isBlank())
            jdbc.update("UPDATE Usuario SET nome=? WHERE idusuario=?", novoNome, id);
        else
            jdbc.update("UPDATE Usuario SET nome=?,senha=? WHERE idusuario=?", novoNome, novaSenha, id);
    }
    public void delete(int id) { jdbc.update("DELETE FROM Usuario WHERE idusuario=?", id); }
}
