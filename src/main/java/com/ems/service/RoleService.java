package com.ems.service;

import com.ems.model.Role;
import com.ems.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    @Transactional
    private void initializeRoles() {
        EnumSet.allOf(Role.ERole.class).forEach(roleName -> roleRepository.findByName(roleName).orElseGet(() -> saveRole(roleName)));
    }

    private Role saveRole(Role.ERole roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }
}
