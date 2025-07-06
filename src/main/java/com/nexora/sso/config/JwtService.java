package com.nexora.sso.config;

import com.nexora.config.enums.MensajeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    // Secreto seguro
    @Value("${jwt.secret-key}")
    private String secretKey;

    // Duración del token en milisegundos (por defecto 1 hora)
    @Value("${jwt.expiration-ms:3600000}")
    private long expirationMs;

    // Emisor del token
    @Value("${jwt.issuer:nexora-sso}")
    private String issuer;


    public String generateToken(String subject, List<String> authorities) {
        Objects.requireNonNull(subject, MensajeEnum.SUBJECT_NULL.getValue());
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        Map<String, Object> claims = new HashMap<>();
        List<String> safeAuthorities = (authorities != null) ? authorities : Collections.emptyList();
        claims.put("authorities", safeAuthorities);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean isTokenValid(String token, String expectedSubject) {
        try {
            String subject = extractClaim(token, Claims::getSubject);
            return (subject.equals(expectedSubject)) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    /**
     * Obtiene la clave secreta
     */
    private Key getSignInKey() {
        if (secretKey == null || secretKey.isBlank()) {
            throw new IllegalStateException("La clave secreta JWT no está configurada.");
        }
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("La clave JWT debe tener al menos 256 bits (32 bytes).");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extrae username del token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae los roles del token.
     */
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object roles = claims.get("authorities");
        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream().map(Object::toString).toList();
        }
        return Collections.emptyList();
    }
}
