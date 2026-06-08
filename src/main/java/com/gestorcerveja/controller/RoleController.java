package com.gestorcerveja.controller;

import com.gestorcerveja.model.Role;
import com.gestorcerveja.service.RoleService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RoleController {
    private final RoleService service;
    public RoleController(RoleService service) { this.service = service; }
    public List<Role> listAll() { return service.getAll(); }
}
