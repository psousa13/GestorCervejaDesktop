package com.gestorcerveja.repository;

import com.gestorcerveja.db.DBConnection;
import com.gestorcerveja.model.ReceitaPreco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceitaPrecoRepository {

    public List<ReceitaPreco> findByReceita(int idreceita) throws SQLException {
        List<ReceitaPreco> list = new ArrayList<>();
        String sql = "SELECT * FROM ReceitaPreco WHERE idreceita = ? ORDER BY data_vigencia DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idreceita);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public ReceitaPreco findActivePrice(int idreceita) throws SQLException {
        String sql = "SELECT * FROM ReceitaPreco WHERE idreceita = ? AND data_vigencia <= CURRENT_DATE ORDER BY data_vigencia DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idreceita);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public void insert(ReceitaPreco rp) throws SQLException {
        String sql = "INSERT INTO ReceitaPreco (idreceita, preco_por_litro, data_vigencia) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rp.getIdreceita());
            ps.setDouble(2, rp.getPrecoPorLitro());
            ps.setDate(3, Date.valueOf(rp.getDataVigencia()));
            ps.executeUpdate();
        }
    }

    private ReceitaPreco map(ResultSet rs) throws SQLException {
        return new ReceitaPreco(
                rs.getInt("idpreco"),
                rs.getInt("idreceita"),
                rs.getDouble("preco_por_litro"),
                rs.getDate("data_vigencia").toLocalDate()
        );
    }
}