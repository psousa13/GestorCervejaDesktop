package com.gestorcerveja.service;

import com.gestorcerveja.model.Role;
import com.gestorcerveja.repository.RoleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepository repo;
    public RoleService(RoleRepository repo) { this.repo = repo; }
    public List<Role> getAll() { return repo.findAll(); }
}
