package com.gestorcerveja.repository;
import com.gestorcerveja.model.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RoleRepository {
    private final JdbcTemplate jdbc;
    public RoleRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }
    public List<Role> findAll() {
        return jdbc.query("SELECT * FROM Role", (rs, n) -> new Role(rs.getInt("idrole"), rs.getString("nome")));
    }
}
