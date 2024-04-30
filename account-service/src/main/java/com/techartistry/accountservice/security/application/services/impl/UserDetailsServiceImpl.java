package com.techartistry.accountservice.security.application.services.impl;

import com.techartistry.accountservice.user.infrastructure.repositories.IUserRepository;
import com.techartistry.accountservice.security.domain.model.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de la interfaz UserDetailsService que carga los detalles del usuario desde el repositorio de usuarios.
 * Esta clase se utiliza en la configuración de Spring Security para cargar los detalles del usuario (identidad)
 * durante el proceso de autenticación.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository userRepo;

    public UserDetailsServiceImpl(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Carga los detalles del usuario basados en el correo electrónico proporcionado.
     * @param email Correo electrónico del usuario.
     * @return Una instancia de UserDetails que contiene la identidad del usuario.
     * @throws UsernameNotFoundException Si no se encuentra el usuario con el correo electrónico proporcionado.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepo.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("No se encontró al usuario con el email: " + email)
        );

        return new UserPrincipal(user);
    }
}
