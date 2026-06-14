package com.gestorcerveja.repository;
import com.gestorcerveja.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepository {
    private final JdbcTemplate jdbc;
    public ClienteRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public Optional<Cliente> findByEmail(String email) {
        return jdbc.query("SELECT * FROM Cliente WHERE LOWER(email) = LOWER(?)",
                (rs, n) -> new Cliente(rs.getInt("idcliente"), rs.getString("tipo_cliente"),
                        rs.getString("email"), rs.getString("telefone"),
                        rs.getDate("data_registo").toLocalDate()), email)
                .stream().findFirst();
    }

    public List<Cliente> findAll() {
        return jdbc.query("SELECT * FROM Cliente", (rs, n) -> new Cliente(
                rs.getInt("idcliente"), rs.getString("tipo_cliente"), rs.getString("email"),
                rs.getString("telefone"), rs.getDate("data_registo").toLocalDate()));
    }
    public Optional<Cliente> findById(int id) {
        return jdbc.query("SELECT * FROM Cliente WHERE idcliente=?",
                (rs, n) -> new Cliente(rs.getInt("idcliente"), rs.getString("tipo_cliente"),
                        rs.getString("email"), rs.getString("telefone"), rs.getDate("data_registo").toLocalDate()), id)
                .stream().findFirst();
    }
    public Optional<ClienteParticular> findParticular(int id) {
        return jdbc.query("SELECT c.*,cp.nome_completo,cp.nif FROM Cliente c JOIN ClienteParticular cp ON c.idcliente=cp.idcliente WHERE c.idcliente=?",
                (rs, n) -> new ClienteParticular(rs.getInt("idcliente"), rs.getString("email"),
                        rs.getString("telefone"), rs.getDate("data_registo").toLocalDate(),
                        rs.getString("nome_completo"), rs.getString("nif")), id)
                .stream().findFirst();
    }
    public Optional<ClienteRevendedor> findRevendedor(int id) {
        return jdbc.query("SELECT c.*,cr.nome_empresa,cr.vat_empresa,cr.contacto_principal,cr.departamento,cr.telefone_empresa,cr.nota_interna FROM Cliente c JOIN ClienteRevendedor cr ON c.idcliente=cr.idcliente WHERE c.idcliente=?",
                (rs, n) -> new ClienteRevendedor(rs.getInt("idcliente"), rs.getString("email"),
                        rs.getString("telefone"), rs.getDate("data_registo").toLocalDate(),
                        rs.getString("nome_empresa"), rs.getString("vat_empresa"),
                        rs.getString("contacto_principal"), rs.getString("departamento"),
                        rs.getString("telefone_empresa"), rs.getString("nota_interna")), id)
                .stream().findFirst();
    }
    public int insertBase(Cliente c) {
        return jdbc.queryForObject("INSERT INTO Cliente (tipo_cliente,email,telefone) VALUES (?,?,?) RETURNING idcliente",
                Integer.class, c.getTipoCliente(), c.getEmail(), c.getTelefone());
    }
    public void insertParticular(int id, ClienteParticular cp) {
        jdbc.update("INSERT INTO ClienteParticular (idcliente,nome_completo,nif) VALUES (?,?,?)", id, cp.getNomeCompleto(), cp.getNif());
    }
    public void insertRevendedor(int id, ClienteRevendedor cr) {
        jdbc.update("INSERT INTO ClienteRevendedor (idcliente,nome_empresa,vat_empresa,contacto_principal,departamento,telefone_empresa,nota_interna) VALUES (?,?,?,?,?,?,?)",
                id, cr.getNomeEmpresa(), cr.getVatEmpresa(), cr.getContactoPrincipal(),
                cr.getDepartamento(), cr.getTelefoneEmpresa(), cr.getNotaInterna());
    }
    @Transactional
    public void updateParticular(int id, String email, String telefone, String nomeCompleto, String nif) {
        jdbc.update("UPDATE Cliente SET email=?,telefone=? WHERE idcliente=?", email, telefone, id);
        jdbc.update("UPDATE ClienteParticular SET nome_completo=?,nif=? WHERE idcliente=?", nomeCompleto, nif, id);
    }
    public void delete(int id) { jdbc.update("DELETE FROM Cliente WHERE idcliente=?", id); }

    public void updateRevendedorDetails(int id, String nomeEmpresa, String vat, String contactoPrincipal) {
        jdbc.update(
                "UPDATE ClienteRevendedor SET nome_empresa=?, vat_empresa=?, contacto_principal=? WHERE idcliente=?",
                nomeEmpresa, vat, contactoPrincipal, id);
    }

    /** Encontra cliente pelo email, ou cria um Particular novo. */
    @Transactional
    public int findOrCreateParticular(String email, String telefone, String nomeCompleto) {
        List<Cliente> existing = jdbc.query("SELECT * FROM Cliente WHERE email=?",
                (rs, n) -> new Cliente(rs.getInt("idcliente"), rs.getString("tipo_cliente"),
                        rs.getString("email"), rs.getString("telefone"),
                        rs.getDate("data_registo").toLocalDate()), email);
        if (!existing.isEmpty()) return existing.get(0).getId();
        int newId = insertBase(new Cliente(0, "Particular", email, telefone, null));
        jdbc.update("INSERT INTO ClienteParticular (idcliente,nome_completo,nif) VALUES (?,?,'')", newId, nomeCompleto);
        return newId;
    }

    /** Encontra cliente pelo email, ou cria um Revendedor novo. */
    @Transactional
    public int findOrCreateRevendedor(String email, String telefone, String nomeEmpresa, String vat) {
        List<Cliente> existing = jdbc.query("SELECT * FROM Cliente WHERE email=?",
                (rs, n) -> new Cliente(rs.getInt("idcliente"), rs.getString("tipo_cliente"),
                        rs.getString("email"), rs.getString("telefone"),
                        rs.getDate("data_registo").toLocalDate()), email);
        if (!existing.isEmpty()) return existing.get(0).getId();
        int newId = insertBase(new Cliente(0, "Revendedor", email, telefone, null));
        jdbc.update("INSERT INTO ClienteRevendedor (idcliente,nome_empresa,vat_empresa,contacto_principal,departamento,telefone_empresa,nota_interna) VALUES (?,?,?,?,?,?,?)",
                newId, nomeEmpresa != null ? nomeEmpresa : email, vat, null, null, null, null);
        return newId;
    }
}
