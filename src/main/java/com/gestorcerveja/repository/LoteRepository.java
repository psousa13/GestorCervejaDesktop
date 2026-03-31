package repository;

import db.DBConnection;
import model.Lote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoteRepository {

    public List<Lote> findAll() throws SQLException {
        List<Lote> list = new ArrayList<>();
        String sql = "SELECT * FROM Lote";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Lote> findByPedido(int idpedido) throws SQLException {
        List<Lote> list = new ArrayList<>();
        String sql = "SELECT * FROM Lote WHERE idpedido = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idpedido);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Lote findById(int id) throws SQLException {
        String sql = "SELECT * FROM Lote WHERE idlote = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public int insert(Lote l) throws SQLException {
        String sql = "INSERT INTO Lote (idpedido, idreceita, litros, data_producao, idveiculo, idrequest_producao) VALUES (?, ?, ?, ?, ?, ?) RETURNING idlote";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, l.getIdpedido());
            ps.setInt(2, l.getIdreceita());
            ps.setDouble(3, l.getLitros());
            ps.setDate(4, l.getDataProducao() != null ? Date.valueOf(l.getDataProducao()) : null);
            ps.setInt(5, l.getIdveiculo());
            ps.setInt(6, l.getIdrequestProducao());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public void updateVeiculo(int idlote, int idveiculo) throws SQLException {
        String sql = "UPDATE Lote SET idveiculo = ? WHERE idlote = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idveiculo);
            ps.setInt(2, idlote);
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Lote WHERE idlote = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Lote map(ResultSet rs) throws SQLException {
        Date dp = rs.getDate("data_producao");
        return new Lote(
                rs.getInt("idlote"),
                rs.getInt("idpedido"),
                rs.getInt("idreceita"),
                rs.getDouble("litros"),
                dp != null ? dp.toLocalDate() : null,
                rs.getInt("idveiculo"),
                rs.getInt("idrequest_producao")
        );
    }
}