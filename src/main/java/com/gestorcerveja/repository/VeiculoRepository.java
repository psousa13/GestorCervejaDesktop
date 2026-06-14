package com.gestorcerveja.repository;
import com.gestorcerveja.model.Veiculo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List; import java.util.Optional;

@Repository
public class VeiculoRepository {
    private final JdbcTemplate jdbc;
    public VeiculoRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Veiculo> mapper = (rs, n) -> new Veiculo(
            rs.getInt("idveiculo"), rs.getString("matricula"), rs.getString("marca"),
            rs.getString("cor"), rs.getString("nome"), rs.getDouble("capacidade"),
            rs.getDouble("ocupacao_atual"), rs.getString("tipo"));

    public List<Veiculo>     findAll()        { return jdbc.query("SELECT * FROM Veiculo", mapper); }
    public Optional<Veiculo> findById(int id) { return jdbc.query("SELECT * FROM Veiculo WHERE idveiculo=?", mapper, id).stream().findFirst(); }
    public void insert(Veiculo v) { jdbc.update("INSERT INTO Veiculo (matricula,marca,cor,nome,capacidade,ocupacao_atual,tipo) VALUES (?,?,?,?,?,?,?)", v.getMatricula(), v.getMarca(), v.getCor(), v.getNome(), v.getCapacidade(), v.getOcupacaoAtual(), v.getTipo()); }
    public void update(int id, String nome, String tipo, String marca, String cor, double cap) { jdbc.update("UPDATE Veiculo SET nome=?,tipo=?,marca=?,cor=?,capacidade=? WHERE idveiculo=?", nome, tipo, marca, cor, cap, id); }
    public void updateOcupacao(int id, double o) { jdbc.update("UPDATE Veiculo SET ocupacao_atual=? WHERE idveiculo=?", o, id); }
    public void delete(int id) { jdbc.update("DELETE FROM Veiculo WHERE idveiculo=?", id); }
}
