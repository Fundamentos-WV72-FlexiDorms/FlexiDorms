package com.techartistry.accountservice.user.infrastructure.repositories;

import com.techartistry.accountservice.user.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    /**
     * Verifica si existe un usuario con el email dado
     * @param email Email
     * @return Si existe o no
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si existe un usuario con el número de celular dado
     * @param phoneNumber El número de celular
     * @return Si existe o no
     */
    boolean existsByPhoneNumber(String phoneNumber);

    /**
     * Obtiene un usuario con el email dado
     * @param email Email
     * @return El usuario encontrado
     */
    Optional<User> findByEmail(String email);
}
