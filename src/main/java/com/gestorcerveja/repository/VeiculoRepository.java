package repository;

import db.DBConnection;
import model.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoRepository {

    public List<Veiculo> findAll() throws SQLException {
        List<Veiculo> list = new ArrayList<>();
        String sql = "SELECT * FROM Veiculo";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Veiculo findById(int id) throws SQLException {
        String sql = "SELECT * FROM Veiculo WHERE idveiculo = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public void insert(Veiculo v) throws SQLException {
        String sql = "INSERT INTO Veiculo (matricula, marca, cor, nome, capacidade, ocupacao_atual, tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getMatricula());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getCor());
            ps.setString(4, v.getNome());
            ps.setDouble(5, v.getCapacidade());
            ps.setDouble(6, v.getOcupacaoAtual());
            ps.setString(7, v.getTipo());
            ps.executeUpdate();
        }
    }

    public void updateOcupacao(int id, double ocupacao) throws SQLException {
        String sql = "UPDATE Veiculo SET ocupacao_atual = ? WHERE idveiculo = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, ocupacao);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Veiculo WHERE idveiculo = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Veiculo map(ResultSet rs) throws SQLException {
        return new Veiculo(
                rs.getInt("idveiculo"),
                rs.getString("matricula"),
                rs.getString("marca"),
                rs.getString("cor"),
                rs.getString("nome"),
                rs.getDouble("capacidade"),
                rs.getDouble("ocupacao_atual"),
                rs.getString("tipo")
        );
    }
}