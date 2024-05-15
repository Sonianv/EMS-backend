package com.ems.service;

import com.ems.error.ResourceNotFoundException;
import com.ems.model.Role;
import com.ems.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

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

    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find role with id " + id));
    }

}
