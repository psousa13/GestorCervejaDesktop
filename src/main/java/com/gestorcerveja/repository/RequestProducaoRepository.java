package com.gestorcerveja.repository;

import com.gestorcerveja.db.DBConnection;
import com.gestorcerveja.model.RequestProducao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestProducaoRepository {

    public List<RequestProducao> findAll() throws SQLException {
        List<RequestProducao> list = new ArrayList<>();
        String sql = "SELECT * FROM RequestProducao";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public RequestProducao findById(int id) throws SQLException {
        String sql = "SELECT * FROM RequestProducao WHERE idrequest_producao = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public int insert(RequestProducao r) throws SQLException {
        String sql = "INSERT INTO RequestProducao (idusuario, estado) VALUES (?, ?) RETURNING idrequest_producao";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getIdusuario());
            ps.setString(2, r.getEstado());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public void updateEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE RequestProducao SET estado = ?, data_conclusao = CURRENT_TIMESTAMP WHERE idrequest_producao = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    private RequestProducao map(ResultSet rs) throws SQLException {
        Timestamp conclusao = rs.getTimestamp("data_conclusao");
        return new RequestProducao(
                rs.getInt("idrequest_producao"),
                rs.getInt("idusuario"),
                rs.getString("estado"),
                rs.getTimestamp("data_criacao").toLocalDateTime(),
                conclusao != null ? conclusao.toLocalDateTime() : null
        );
    }
}