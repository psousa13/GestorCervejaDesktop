package com.gestorcerveja.repository;
import com.gestorcerveja.model.RequestProducao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List; import java.util.Optional;

@Repository
public class RequestProducaoRepository {
    private final JdbcTemplate jdbc;
    public RequestProducaoRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<RequestProducao> mapper = (rs, n) -> new RequestProducao(
            rs.getInt("idrequest_producao"),
            rs.getInt("idusuario"),
            rs.getString("estado"),
            rs.getTimestamp("data_criacao") != null  ? rs.getTimestamp("data_criacao").toLocalDateTime()  : null,
            rs.getTimestamp("data_conclusao") != null ? rs.getTimestamp("data_conclusao").toLocalDateTime() : null);

    public List<RequestProducao>     findAll()        { return jdbc.query("SELECT * FROM RequestProducao", mapper); }
    public Optional<RequestProducao> findById(int id) { return jdbc.query("SELECT * FROM RequestProducao WHERE idrequest_producao=?", mapper, id).stream().findFirst(); }
    public void insert(int idusuario) { jdbc.update("INSERT INTO RequestProducao (idusuario,estado,data_criacao) VALUES (?,'Pendente',NOW())", idusuario); }
    public void concluir(int id)      { jdbc.update("UPDATE RequestProducao SET estado='Concluido',data_conclusao=NOW() WHERE idrequest_producao=?", id); }
    public void updateEstado(int id, String estado) { jdbc.update("UPDATE RequestProducao SET estado=? WHERE idrequest_producao=?", estado, id); }
}
