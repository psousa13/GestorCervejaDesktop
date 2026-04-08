package com.gestorcerveja.repository;

import com.gestorcerveja.db.DBConnection;
import com.gestorcerveja.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {

    public List<Role> findAll() throws SQLException {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT * FROM Role";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Role findById(int id) throws SQLException {
        String sql = "SELECT * FROM Role WHERE idrole = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public void insert(Role r) throws SQLException {
        String sql = "INSERT INTO Role (nome, descricao) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getNome());
            ps.setString(2, r.getDescricao());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Role WHERE idrole = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Role map(ResultSet rs) throws SQLException {
        return new Role(
                rs.getInt("idrole"),
                rs.getString("nome"),
                rs.getString("descricao")
        );
    }
}