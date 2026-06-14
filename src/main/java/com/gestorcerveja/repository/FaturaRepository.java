package com.gestorcerveja.repository;
import com.gestorcerveja.model.Fatura;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List; import java.util.Optional;

@Repository
public class FaturaRepository {
    private final JdbcTemplate jdbc;
    public FaturaRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Fatura> mapper = (rs, n) -> new Fatura(
            rs.getInt("idfatura"),
            rs.getInt("idpedido"),
            rs.getDouble("valor_total"),
            rs.getDate("data_emissao").toLocalDate(),
            rs.getString("estado"));

    public List<Fatura>    findAll()              { return jdbc.query("SELECT * FROM Fatura", mapper); }
    public Optional<Fatura> findByPedido(int idp) { return jdbc.query("SELECT * FROM Fatura WHERE idpedido=?", mapper, idp).stream().findFirst(); }
}
