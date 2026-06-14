package com.gestorcerveja.controller;
import com.gestorcerveja.model.Role; import com.gestorcerveja.service.RoleService;
import org.springframework.stereotype.Component; import java.util.List;
@Component public class RoleController {
    private final RoleService s; public RoleController(RoleService s){this.s=s;}
    public List<Role> listAll() { return s.getAll(); }
}
