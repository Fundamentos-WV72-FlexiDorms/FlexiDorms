package com.techartistry.accountservice.user.infrastructure.repositories;

import com.techartistry.accountservice.user.domain.entities.Role;
import com.techartistry.accountservice.user.domain.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    /**
     * Busca un rol por su nombre
     * @param name Nombre del rol
     * @return Rol encontrado (si existe)
     */
    Optional<Role> findByName(ERole name);

    /**
     * Verifica si existe un rol por su nombre
     * @param name Nombre del rol
     * @return true si existe, false si no
     */
    boolean existsByName(ERole name);
}
