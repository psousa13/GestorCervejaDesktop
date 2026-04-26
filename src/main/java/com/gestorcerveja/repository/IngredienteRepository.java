package com.gestorcerveja.repository;

import com.gestorcerveja.db.DBConnection;
import com.gestorcerveja.model.Ingrediente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredienteRepository {

    public List<Ingrediente> findAll() throws SQLException {
        List<Ingrediente> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Ingrediente")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Ingrediente findById(int id) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Ingrediente WHERE idingrediente = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public List<Ingrediente> findBelowMinStock() throws SQLException {
        List<Ingrediente> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Ingrediente WHERE stockatual < stockminimo")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public void insert(Ingrediente i) throws SQLException {
        String sql = "INSERT INTO Ingrediente (nome, unidade, stockatual, stockminimo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, i.getNome());
            ps.setString(2, i.getUnidade());
            ps.setDouble(3, i.getStockAtual());
            ps.setDouble(4, i.getStockMinimo());
            ps.executeUpdate();
        }
    }

    public void update(int id, String nome, String unidade, double stockAtual, double stockMinimo) throws SQLException {
        String sql = "UPDATE Ingrediente SET nome=?, unidade=?, stockatual=?, stockminimo=? WHERE idingrediente=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, unidade);
            ps.setDouble(3, stockAtual);
            ps.setDouble(4, stockMinimo);
            ps.setInt(5, id);
            ps.executeUpdate();
        }
    }

    public void updateStock(int id, double novoStock) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Ingrediente SET stockatual=? WHERE idingrediente=?")) {
            ps.setDouble(1, novoStock);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Ingrediente WHERE idingrediente=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Ingrediente map(ResultSet rs) throws SQLException {
        return new Ingrediente(rs.getInt("idingrediente"), rs.getString("nome"),
                rs.getString("unidade"), rs.getDouble("stockatual"), rs.getDouble("stockminimo"));
    }
}
