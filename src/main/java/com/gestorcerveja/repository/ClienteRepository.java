package repository;

import db.DBConnection;
import model.Cliente;
import model.ClienteParticular;
import model.ClienteRevendedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    public List<Cliente> findAll() throws SQLException {
        List<Cliente> list = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapBase(rs));
        }
        return list;
    }

    public Cliente findById(int id) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE idcliente = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapBase(rs);
        }
        return null;
    }

    public ClienteParticular findParticular(int id) throws SQLException {
        String sql = "SELECT c.*, cp.nome_completo, cp.nif FROM Cliente c JOIN ClienteParticular cp ON c.idcliente = cp.idcliente WHERE c.idcliente = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new ClienteParticular(
                    rs.getInt("idcliente"),
                    rs.getString("email"),
                    rs.getString("telefone"),
                    rs.getDate("data_registo").toLocalDate(),
                    rs.getString("nome_completo"),
                    rs.getString("nif")
            );
        }
        return null;
    }

    public ClienteRevendedor findRevendedor(int id) throws SQLException {
        String sql = "SELECT c.*, cr.nome_empresa, cr.vat_empresa, cr.contacto_principal, cr.departamento, cr.telefone_empresa, cr.nota_interna FROM Cliente c JOIN ClienteRevendedor cr ON c.idcliente = cr.idcliente WHERE c.idcliente = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new ClienteRevendedor(
                    rs.getInt("idcliente"),
                    rs.getString("email"),
                    rs.getString("telefone"),
                    rs.getDate("data_registo").toLocalDate(),
                    rs.getString("nome_empresa"),
                    rs.getString("vat_empresa"),
                    rs.getString("contacto_principal"),
                    rs.getString("departamento"),
                    rs.getString("telefone_empresa"),
                    rs.getString("nota_interna")
            );
        }
        return null;
    }

    public int insert(Cliente c) throws SQLException {
        String sql = "INSERT INTO Cliente (tipo_cliente, email, telefone) VALUES (?, ?, ?) RETURNING idcliente";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getTipoCliente());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefone());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public void insertParticular(int idcliente, ClienteParticular cp) throws SQLException {
        String sql = "INSERT INTO ClienteParticular (idcliente, nome_completo, nif) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idcliente);
            ps.setString(2, cp.getNomeCompleto());
            ps.setString(3, cp.getNif());
            ps.executeUpdate();
        }
    }

    public void insertRevendedor(int idcliente, ClienteRevendedor cr) throws SQLException {
        String sql = "INSERT INTO ClienteRevendedor (idcliente, nome_empresa, vat_empresa, contacto_principal, departamento, telefone_empresa, nota_interna) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idcliente);
            ps.setString(2, cr.getNomeEmpresa());
            ps.setString(3, cr.getVatEmpresa());
            ps.setString(4, cr.getContactoPrincipal());
            ps.setString(5, cr.getDepartamento());
            ps.setString(6, cr.getTelefoneEmpresa());
            ps.setString(7, cr.getNotaInterna());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE idcliente = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Cliente mapBase(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("idcliente"),
                rs.getString("tipo_cliente"),
                rs.getString("email"),
                rs.getString("telefone"),
                rs.getDate("data_registo").toLocalDate()
        );
    }
}