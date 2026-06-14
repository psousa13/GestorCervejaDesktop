package com.gestorcerveja.repository;
import com.gestorcerveja.model.Pedido;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class PedidoRepository {
    private final JdbcTemplate jdbc;
    public PedidoRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Pedido> mapper = (rs, n) -> new Pedido(
            rs.getInt("idpedido"), rs.getInt("idcliente"),
            rs.getDate("data_pedido").toLocalDate(),
            rs.getString("estado"),
            rs.getDate("data_estimada_conclusao") != null
                    ? rs.getDate("data_estimada_conclusao").toLocalDate() : null);

    public List<Pedido>     findAll()        { return jdbc.query("SELECT * FROM Pedido ORDER BY idpedido DESC", mapper); }
    public Optional<Pedido> findById(int id) { return jdbc.query("SELECT * FROM Pedido WHERE idpedido=?", mapper, id).stream().findFirst(); }

    /** Devolve pedidos cujo cliente tem o email indicado. */
    public List<Pedido> findByEmail(String email) {
        return jdbc.query(
            "SELECT p.* FROM Pedido p JOIN Cliente c ON p.idcliente = c.idcliente WHERE c.email = ? ORDER BY p.idpedido DESC",
            mapper, email);
    }

    public int insert(int idcliente, LocalDate dataEstimada) {
        return jdbc.queryForObject(
                "INSERT INTO Pedido (idcliente,data_pedido,estado,data_estimada_conclusao) VALUES (?,CURRENT_DATE,'Pendente',?) RETURNING idpedido",
                Integer.class, idcliente, dataEstimada != null ? java.sql.Date.valueOf(dataEstimada) : null);
    }
    public void updateEstado(int id, String estado) { jdbc.update("UPDATE Pedido SET estado=? WHERE idpedido=?", estado, id); }
    public void delete(int id) { jdbc.update("DELETE FROM Pedido WHERE idpedido=?", id); }
}
