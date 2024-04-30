package com.techartistry.accountservice.security.infrastructure.util;

import com.techartistry.accountservice.user.domain.entities.Role;
import com.techartistry.accountservice.security.domain.model.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * Clase de utilidades para la seguridad y autenticación de usuarios
 */
public class Util {
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * Obtiene el token del header Authorization
     * @param request Solicitud http
     * @return Token obtenido
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        //obtiene el token JWT desde el header
        String jwtFromHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(jwtFromHeader) && jwtFromHeader.startsWith(BEARER_PREFIX)) {
            //obtiene solo el token (lo "recorta")
            return jwtFromHeader.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    /**
     * Obtiene los roles del usuario autenticado
     * @param principal Usuario autenticado
     * @return Lista de roles
     */
    public static List<String> getRoles(UserPrincipal principal) {
        return principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    /**
     * Mapea los roles a una lista de GrantedAuthority
     * @param roles Roles a mapear
     * @return Lista de GrantedAuthority
     */
    static public List<SimpleGrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();
    }
}
