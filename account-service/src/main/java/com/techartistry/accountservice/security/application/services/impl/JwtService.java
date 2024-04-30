package com.techartistry.accountservice.security.application.services.impl;

import com.techartistry.accountservice.security.application.services.IJwtService;
import com.techartistry.accountservice.security.domain.model.UserPrincipal;
import com.techartistry.accountservice.security.infrastructure.util.Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

/**
 * Clase que genera el token, obtiene los claims, valida el token
 */
@Slf4j
@Service
public class JwtService implements IJwtService {
    @Value("${app.jwt.secret-key}")
    private String secretKey;
    @Value("${app.jwt.expiration-min}")
    private long expirationInMin;

    @Override
    public String generateAccessToken(UserPrincipal userPrincipal) {
        var minutesInMillis = expirationInMin * 60_000;
        var expiryDate = new Date(new Date().getTime() + minutesInMillis);

        return Jwts.builder()
                .subject(userPrincipal.getUsername()) //se usa el username (email) como subject
                .claim("roles", Util.getRoles(userPrincipal)) //agrega los roles
                .issuedAt(new Date()) //fecha de emisión
                .expiration(expiryDate) //fecha de expiración
                .signWith(getSignKey()) //firma el token
                .compact(); //construye el token
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String userEmail = getSubject(token);
            return (userEmail.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (JwtException ex) {
            log.warn("Error al validar el token: {}", ex.getMessage());
        }

        return false;
    }

    @Override
    public boolean isTokenExpired(String token) {
        //obtiene la fecha de expiración del token
        var tokenExpiration = extractClaim(token, Claims::getExpiration);

        //compara la fecha de expiración con la fecha actual
        return tokenExpiration.before(new Date());
    }

    @Override
    public String getSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims del token
     * @param token Token a procesar
     * @return Claims extraídos
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey()) //verifica la firma
                .build() //construye el parser
                .parseSignedClaims(token) //parsea el token
                .getPayload();
    }

    /**
     * Generar una clave secreta a partir del secret que se utilizará para firmar y verificar los tokens
     * @return Clave secreta HMAC
     */
    private SecretKey getSignKey() {
        byte[] secretBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}