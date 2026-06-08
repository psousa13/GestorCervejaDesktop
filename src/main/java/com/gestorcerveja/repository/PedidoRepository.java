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

    private final RowMapper<Pedido> mapper = (rs, n) -> {
        int id = rs.getInt("idpedido");
        int idcliente = rs.getInt("idcliente");
        LocalDate dataPedido = rs.getDate("data_pedido").toLocalDate();
        String estado = rs.getString("estado");
        LocalDate dataEstimadaConclusao = null;
        if (rs.getDate("data_estimada_conclusao") != null) {
            dataEstimadaConclusao = rs.getDate("data_estimada_conclusao").toLocalDate();
        }
        double totalLitros = rs.getDouble("total_litros");
        int totalGrade = rs.getInt("total_grade");
        return new Pedido(id, idcliente, dataPedido, estado, dataEstimadaConclusao, totalLitros, totalGrade);
    };

    public List<Pedido>    findAll()        { return jdbc.query("SELECT * FROM Pedido", mapper); }
    public Optional<Pedido> findById(int id){ return jdbc.query("SELECT * FROM Pedido WHERE idpedido=?", mapper, id).stream().findFirst(); }

    public void insert(int idcliente, LocalDate dataEstimada) {
        jdbc.update("INSERT INTO Pedido (idcliente,data_pedido,estado,data_estimada_conclusao) VALUES (?,CURRENT_DATE,'Pendente',?)",
                idcliente, java.sql.Date.valueOf(dataEstimada));
    }
    public void updateEstado(int id, String estado) { jdbc.update("UPDATE Pedido SET estado=? WHERE idpedido=?", estado, id); }
    public void delete(int id)                      { jdbc.update("DELETE FROM Pedido WHERE idpedido=?", id); }
}
