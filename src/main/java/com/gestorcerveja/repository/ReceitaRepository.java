package com.gestorcerveja.repository;
import com.gestorcerveja.model.Receita; import com.gestorcerveja.model.ReceitaPreco;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List; import java.util.Optional;

@Repository
public class ReceitaRepository {
    private final JdbcTemplate jdbc;
    
    public ReceitaRepository(JdbcTemplate jdbc) { 
        this.jdbc = jdbc; 
    }

    public List<Receita> findAll() { 
        return jdbc.query("SELECT * FROM Receita", (rs, n) -> new Receita(
            rs.getInt("idreceita"), 
            rs.getString("nome"), 
            rs.getString("descricao")
        )); 
    }
    
    public Optional<Receita> findById(int id) { 
        return jdbc.query("SELECT * FROM Receita WHERE idreceita=?", (rs, n) -> new Receita(
            rs.getInt("idreceita"), 
            rs.getString("nome"), 
            rs.getString("descricao")
        ), id).stream().findFirst(); 
    }
    
    public Optional<ReceitaPreco> findActivePrice(int id) {
    String sql = "SELECT * FROM ReceitaPreco WHERE idreceita=?";
    
    return jdbc.query(sql, (rs, n) -> new ReceitaPreco(
            rs.getInt("idpreco"), 
            rs.getInt("idreceita"), 
            rs.getDouble("precopor_litro"),
            rs.getDate("datavigencia") != null ? rs.getDate("datavigencia").toLocalDate() : null
    ), id).stream().findFirst();
}
    
    public void insert(String nome, String descricao) { 
        jdbc.update("INSERT INTO Receita (nome,descricao) VALUES (?,?)", nome, descricao); 
    }
    
    public void update(int id, String nome, String descricao) { 
        jdbc.update("UPDATE Receita SET nome=?,descricao=? WHERE idreceita=?", nome, descricao, id); 
    }
    
    public void setPrice(int idreceita, double preco) {
        // CORREÇÃO: Remove o UPDATE antigo que desativava os preços e remove o campo 'ativo' do INSERT
        jdbc.update("INSERT INTO ReceitaPreco (idreceita,preco_por_litro,data_inicio) VALUES (?,?,CURRENT_DATE)", idreceita, preco);
    }
    
    public void delete(int id) { 
        jdbc.update("DELETE FROM Receita WHERE idreceita=?", id); 
    }
}

