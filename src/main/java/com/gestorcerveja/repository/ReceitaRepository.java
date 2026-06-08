package com.gestorcerveja.repository;

import com.gestorcerveja.model.Receita;
import com.gestorcerveja.model.ReceitaPreco;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ReceitaRepository {
    private final JdbcTemplate jdbc;
    public ReceitaRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Receita>     rm  = (rs, n) -> new Receita(rs.getInt("idreceita"), rs.getString("nome"), rs.getString("descricao"));
    private final RowMapper<ReceitaPreco> rpm = (rs, n) -> new ReceitaPreco(rs.getInt("idpreco"), rs.getInt("idreceita"), rs.getDouble("preco_por_litro"), rs.getDate("data_inicio").toLocalDate());

    public List<Receita>      findAll()         { return jdbc.query("SELECT * FROM Receita", rm); }
    public Optional<Receita>  findById(int id)  { return jdbc.query("SELECT * FROM Receita WHERE idreceita=?", rm, id).stream().findFirst(); }
    public Optional<ReceitaPreco> findActivePrice(int idreceita) {
        return jdbc.query("SELECT * FROM ReceitaPreco WHERE idreceita=? AND ativo=true ORDER BY data_inicio DESC LIMIT 1", rpm, idreceita).stream().findFirst();
    }

    public void insert(String nome, String descricao) {
        jdbc.update("INSERT INTO Receita (nome,descricao) VALUES (?,?)", nome, descricao);
    }
    public void update(int id, String nome, String descricao) {
        jdbc.update("UPDATE Receita SET nome=?,descricao=? WHERE idreceita=?", nome, descricao, id);
    }
    public void setPrice(int idreceita, double preco) {
        jdbc.update("UPDATE ReceitaPreco SET ativo=false WHERE idreceita=?", idreceita);
        jdbc.update("INSERT INTO ReceitaPreco (idreceita,preco_por_litro,data_inicio,ativo) VALUES (?,?,CURRENT_DATE,true)", idreceita, preco);
    }
    public void delete(int id) { jdbc.update("DELETE FROM Receita WHERE idreceita=?", id); }
}
