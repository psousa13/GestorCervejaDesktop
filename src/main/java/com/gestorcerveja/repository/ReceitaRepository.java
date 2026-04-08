package com.gestorcerveja.repository;

import com.gestorcerveja.db.DBConnection;
import com.gestorcerveja.model.Receita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceitaRepository {

    public List<Receita> findAll() throws SQLException {
        List<Receita> list = new ArrayList<>();
        String sql = "SELECT * FROM Receita";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Receita findById(int id) throws SQLException {
        String sql = "SELECT * FROM Receita WHERE idreceita = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public void insert(Receita r) throws SQLException {
        String sql = "INSERT INTO Receita (nome, descricao) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getNome());
            ps.setString(2, r.getDescricao());
            ps.executeUpdate();
        }
    }

    public void update(Receita r) throws SQLException {
        String sql = "UPDATE Receita SET nome = ?, descricao = ? WHERE idreceita = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getNome());
            ps.setString(2, r.getDescricao());
            ps.setInt(3, r.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Receita WHERE idreceita = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Receita map(ResultSet rs) throws SQLException {
        return new Receita(
                rs.getInt("idreceita"),
                rs.getString("nome"),
                rs.getString("descricao")
        );
    }
}