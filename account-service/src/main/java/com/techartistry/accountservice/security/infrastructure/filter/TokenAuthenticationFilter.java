package com.techartistry.accountservice.security.infrastructure.filter;

import com.techartistry.accountservice.security.application.services.IJwtService;
import com.techartistry.accountservice.security.application.services.impl.UserDetailsServiceImpl;
import com.techartistry.accountservice.security.infrastructure.util.Util;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Clase que se encarga de obtener, validar el token y los filtros del usuario por cada petición que se realiza
 */
@Slf4j
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final IJwtService jwtService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public TokenAuthenticationFilter(IJwtService jwtService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtService = jwtService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    /**
     * Realiza el filtro de autenticación (se ejecuta por cada petición)
     * @param request Solicitud http
     * @param response Respuesta http
     * @param filterChain Cadena de filtros (se ejecuta el siguiente filtro)
     * @throws ServletException Excepción de servlet
     * @throws IOException Excepción de entrada/salida
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var token = Util.getTokenFromRequest(request);

            //verifica si el token no es nulo y si es de tipo JWT
            if (StringUtils.hasText(token)) {
                //se obtiene el email del usuario
                var userEmail = jwtService.getSubject(token);

                //verifica si no hay una autenticación establecida en el contexto de seguridad
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    //carga el usuario asociado al token
                    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userEmail);

                    //se valida el token
                    if (jwtService.isTokenValid(token, userDetails)) {
                        //se comprueba si el usuario está habilitado
                        verifyUserStatus(userDetails);

                        //crea un objeto que representa la autenticación del usuario
                        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        //establece los detalles de autenticación adicionales
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        //establece la seguridad
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception ex) {
            log.warn("[!] - Couldn't set authentication obj to SecurityContext -> " + ex.getMessage());
        }

        //se ejecuta el siguiente filtro
        filterChain.doFilter(request, response);
    }

    /**
     * Verifica si el usuario está habilitado
     * @param userDetails Detalles del usuario
     */
    private void verifyUserStatus(UserDetails userDetails) {
        if (!userDetails.isEnabled()) {
            throw new DisabledException("User is disabled, verify your account");
        }
    }
}
