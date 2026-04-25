package com.gestorcerveja.repository;

import com.gestorcerveja.db.DBConnection;
import com.gestorcerveja.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    public List<Usuario> findAll() throws SQLException {
        List<Usuario> list = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Usuario findById(int id) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE idusuario = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public Usuario findByNome(String nome) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE nome = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public void insert(Usuario u) throws SQLException {
        String sql = "INSERT INTO Usuario (nome, senha, idrole) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getSenha());
            ps.setInt(3, u.getIdrole());
            ps.executeUpdate();
        }
    }

    /**
     * Atualiza nome e/ou senha de um utilizador.
     * Se {@code novaSenha} for null ou vazia, a senha não é alterada.
     */
    public void update(int id, String novoNome, String novaSenha) throws SQLException {
        if (novaSenha == null || novaSenha.isBlank()) {
            String sql = "UPDATE Usuario SET nome = ? WHERE idusuario = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, novoNome);
                ps.setInt(2, id);
                ps.executeUpdate();
            }
        } else {
            String sql = "UPDATE Usuario SET nome = ?, senha = ? WHERE idusuario = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, novoNome);
                ps.setString(2, novaSenha);
                ps.setInt(3, id);
                ps.executeUpdate();
            }
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE idusuario = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Usuario map(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("idusuario"),
                rs.getString("nome"),
                rs.getString("senha"),
                rs.getInt("idrole")
        );
    }
}
