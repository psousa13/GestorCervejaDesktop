package com.gestorcerveja.repository;

import com.gestorcerveja.db.DBConnection;
import com.gestorcerveja.model.Fatura;
import com.gestorcerveja.model.FaturaItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FaturaRepository {

    public Fatura findById(int id) throws SQLException {
        String sql = "SELECT * FROM Fatura WHERE idfatura = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public Fatura findByPedido(int idpedido) throws SQLException {
        String sql = "SELECT * FROM Fatura WHERE idpedido = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idpedido);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public int insert(Fatura f) throws SQLException {
        String sql = "INSERT INTO Fatura (idpedido, data_emissao, valor_total, estado) VALUES (?, ?, ?, ?) RETURNING idfatura";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, f.getIdpedido());
            ps.setDate(2, Date.valueOf(f.getDataEmissao()));
            ps.setDouble(3, f.getValorTotal());
            ps.setString(4, f.getEstado());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public void updateEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE Fatura SET estado = ? WHERE idfatura = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public List<FaturaItem> findItemsByFatura(int idfatura) throws SQLException {
        List<FaturaItem> list = new ArrayList<>();
        String sql = "SELECT * FROM FaturaItem WHERE idfatura = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idfatura);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapItem(rs));
        }
        return list;
    }

    public void insertItem(FaturaItem item) throws SQLException {
        String sql = "INSERT INTO FaturaItem (idfatura, idreceita, quantidade_litros, preco_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getIdfatura());
            ps.setInt(2, item.getIdreceita());
            ps.setDouble(3, item.getQuantidadeLitros());
            ps.setDouble(4, item.getPrecoUnitario());
            ps.setDouble(5, item.getSubtotal());
            ps.executeUpdate();
        }
    }

    private Fatura map(ResultSet rs) throws SQLException {
        return new Fatura(
                rs.getInt("idfatura"),
                rs.getInt("idpedido"),
                rs.getDate("data_emissao").toLocalDate(),
                rs.getDouble("valor_total"),
                rs.getString("estado")
        );
    }

    private FaturaItem mapItem(ResultSet rs) throws SQLException {
        return new FaturaItem(
                rs.getInt("iditem"),
                rs.getInt("idfatura"),
                rs.getInt("idreceita"),
                rs.getDouble("quantidade_litros"),
                rs.getDouble("preco_unitario"),
                rs.getDouble("subtotal")
        );
    }
}