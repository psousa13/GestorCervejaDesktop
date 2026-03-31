package repository;

import db.DBConnection;
import model.Pedido;
import model.PedidoItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {

    public List<Pedido> findAll() throws SQLException {
        List<Pedido> list = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Pedido findById(int id) throws SQLException {
        String sql = "SELECT * FROM Pedido WHERE idpedido = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public List<Pedido> findByCliente(int idcliente) throws SQLException {
        List<Pedido> list = new ArrayList<>();
        String sql = "SELECT * FROM Pedido WHERE idcliente = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idcliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public int insert(Pedido p) throws SQLException {
        String sql = "INSERT INTO Pedido (idcliente, data_pedido, estado, data_estimada_conclusao, total_litros, total_grade) VALUES (?, ?, ?, ?, ?, ?) RETURNING idpedido";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdcliente());
            ps.setDate(2, Date.valueOf(p.getDataPedido()));
            ps.setString(3, p.getEstado());
            ps.setDate(4, p.getDataEstimadaConclusao() != null ? Date.valueOf(p.getDataEstimadaConclusao()) : null);
            ps.setDouble(5, p.getTotalLitros());
            ps.setInt(6, p.getTotalGrade());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public void updateEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE Pedido SET estado = ? WHERE idpedido = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Pedido WHERE idpedido = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<PedidoItem> findItemsByPedido(int idpedido) throws SQLException {
        List<PedidoItem> list = new ArrayList<>();
        String sql = "SELECT * FROM PedidoItem WHERE idpedido = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idpedido);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapItem(rs));
        }
        return list;
    }

    public void insertItem(PedidoItem item) throws SQLException {
        String sql = "INSERT INTO PedidoItem (idpedido, idreceita, quantidade_litros, quantidade_grades) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getIdpedido());
            ps.setInt(2, item.getIdreceita());
            ps.setDouble(3, item.getQuantidadeLitros());
            ps.setInt(4, item.getQuantidadeGrades());
            ps.executeUpdate();
        }
    }

    private Pedido map(ResultSet rs) throws SQLException {
        Date estimada = rs.getDate("data_estimada_conclusao");
        return new Pedido(
                rs.getInt("idpedido"),
                rs.getInt("idcliente"),
                rs.getDate("data_pedido").toLocalDate(),
                rs.getString("estado"),
                estimada != null ? estimada.toLocalDate() : null,
                rs.getDouble("total_litros"),
                rs.getInt("total_grade")
        );
    }

    private PedidoItem mapItem(ResultSet rs) throws SQLException {
        return new PedidoItem(
                rs.getInt("iditem"),
                rs.getInt("idpedido"),
                rs.getInt("idreceita"),
                rs.getDouble("quantidade_litros"),
                rs.getInt("quantidade_grades")
        );
    }
}