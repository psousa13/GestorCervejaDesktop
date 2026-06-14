package com.gestorcerveja.repository;
import com.gestorcerveja.model.Ingrediente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List; import java.util.Optional;

@Repository
public class IngredienteRepository {
    private final JdbcTemplate jdbc;
    public IngredienteRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Ingrediente> mapper = (rs, n) -> new Ingrediente(
            rs.getInt("idingrediente"), rs.getString("nome"),
            rs.getString("unidade"), rs.getDouble("stockatual"), rs.getDouble("stockminimo"));

    public List<Ingrediente>     findAll()           { return jdbc.query("SELECT * FROM Ingrediente", mapper); }
    public Optional<Ingrediente> findById(int id)    { return jdbc.query("SELECT * FROM Ingrediente WHERE idingrediente=?", mapper, id).stream().findFirst(); }
    public List<Ingrediente>     findBelowMinStock() { return jdbc.query("SELECT * FROM Ingrediente WHERE stockatual < stockminimo", mapper); }
    public void insert(Ingrediente i) { jdbc.update("INSERT INTO Ingrediente (nome,unidade,stockatual,stockminimo) VALUES (?,?,?,?)", i.getNome(), i.getUnidade(), i.getStockAtual(), i.getStockMinimo()); }
    public void update(int id, String nome, String unidade, double sa, double sm) { jdbc.update("UPDATE Ingrediente SET nome=?,unidade=?,stockatual=?,stockminimo=? WHERE idingrediente=?", nome, unidade, sa, sm, id); }
    public void updateStock(int id, double s) { jdbc.update("UPDATE Ingrediente SET stockatual=? WHERE idingrediente=?", s, id); }
    public void delete(int id) { jdbc.update("DELETE FROM Ingrediente WHERE idingrediente=?", id); }
}
