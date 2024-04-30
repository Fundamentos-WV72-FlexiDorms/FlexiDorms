package com.techartistry.accountservice.shared.util;

import com.techartistry.accountservice.user.domain.entities.Role;
import com.techartistry.accountservice.user.domain.enums.ERole;
import com.techartistry.accountservice.user.infrastructure.repositories.IRoleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utilities {
    static public void insertRoleIfNotFound(IRoleRepository roleRepository, ERole roleName) {
        if (!roleRepository.existsByName(roleName)) {
            var newRole = new Role();
            newRole.setName(roleName);
            roleRepository.save(newRole);
            log.info("Role {} inserted", roleName);
        }
    }
}
