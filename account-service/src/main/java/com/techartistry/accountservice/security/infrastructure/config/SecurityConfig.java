package com.techartistry.accountservice.security.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techartistry.accountservice.security.application.services.ITokenService;
import com.techartistry.accountservice.security.application.services.impl.UserDetailsServiceImpl;
import com.techartistry.accountservice.security.domain.model.enums.ETokenType;
import com.techartistry.accountservice.security.infrastructure.filter.TokenAuthenticationFilter;
import com.techartistry.accountservice.security.infrastructure.util.Util;
import com.techartistry.accountservice.shared.model.dto.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

@Slf4j
@Configuration
@EnableWebSecurity //habilita la seguridad a nivel de la aplicación
@EnableMethodSecurity //habilita la seguridad a nivel de los métodos
public class SecurityConfig {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final ITokenService tokenService;

    public SecurityConfig(
            TokenAuthenticationFilter tokenAuthenticationFilter,
            UserDetailsServiceImpl userDetailsServiceImpl,
            ITokenService tokenService
    ) {
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.tokenService = tokenService;
    }

    /**
     * Bean que se encarga de manejar la configuración de la seguridad
     * @param http Objeto HttpSecurity
     * @return Objeto SecurityFilterChain
     * @throws Exception Excepción
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    ApiResponse<Object> apiResponse = new ApiResponse<>("Auth required", false, null);
                    new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    ApiResponse<Object> apiResponse = new ApiResponse<>("Access denied", false, null);
                    new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
                })
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> {
                authorize
                    .requestMatchers(
                        "/swagger-ui/**",
                        "/api/auth/**",
                        "/oauth2/**",
                        "/v3/api-docs/**",
                        "/error",
                        "/favicon.ico",
                        "/actuator/**"
                    )
                    .permitAll();
                authorize
                    .anyRequest()
                    .authenticated();
            })
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .addLogoutHandler((request, response, authentication) -> {
                    var token = Util.getTokenFromRequest(request);
                    if (StringUtils.hasText(token)) {
                        var storedToken = tokenService.findByTokenAndTokenType(token, ETokenType.REFRESH_TOKEN)
                                .orElse(null);

                        if (storedToken != null) {
                            tokenService.revokeToken(storedToken.getTokenId(), ETokenType.REFRESH_TOKEN);
                            SecurityContextHolder.clearContext();
                        }
                    }
                })
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
