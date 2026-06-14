package com.gestorcerveja.repository;
import com.gestorcerveja.model.Lote;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List; import java.util.Optional;

@Repository
public class LoteRepository {
    private final JdbcTemplate jdbc;
    public LoteRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Lote> mapper = (rs, n) -> new Lote(
            rs.getInt("idlote"), rs.getInt("idpedido"), rs.getInt("idreceita"),
            rs.getDouble("litros"),
            rs.getDate("data_producao") != null ? rs.getDate("data_producao").toLocalDate() : null,
            rs.getInt("idveiculo"), rs.getInt("idrequest_producao"));

    public List<Lote>     findAll()              { return jdbc.query("SELECT * FROM Lote", mapper); }
    public Optional<Lote> findById(int id)        { return jdbc.query("SELECT * FROM Lote WHERE idlote=?", mapper, id).stream().findFirst(); }
    public List<Lote>     findByPedido(int idped) { return jdbc.query("SELECT * FROM Lote WHERE idpedido=?", mapper, idped); }

    public int insert(Lote l) {
        return jdbc.queryForObject(
                "INSERT INTO Lote (idpedido,idreceita,litros,data_producao,idveiculo,idrequest_producao) VALUES (?,?,?,?,?,?) RETURNING idlote",
                Integer.class, l.getIdPedido(), l.getIdReceita(), l.getLitros(),
                l.getDataProducao() != null ? java.sql.Date.valueOf(l.getDataProducao()) : null,
                l.getIdVeiculo(), l.getIdRequestProducao());
    }
    public void updateVeiculo(int idlote, int idveiculo) { jdbc.update("UPDATE Lote SET idveiculo=? WHERE idlote=?", idveiculo, idlote); }
    public void delete(int id) { jdbc.update("DELETE FROM Lote WHERE idlote=?", id); }
}
