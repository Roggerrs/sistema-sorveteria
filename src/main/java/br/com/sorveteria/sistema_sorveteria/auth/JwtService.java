package br.com.sorveteria.sistema_sorveteria.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "SEGREDO_SUPER_SECRETO_SEGREDO_SUPER_SECRETO_123456";

    private static final long EXPIRATION = 1000 * 60 * 60 * 4; // 4 horas

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 🔹 GERA TOKEN
    public String gerarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔹 EXTRAI USUÁRIO
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    // 🔹 VALIDA TOKEN
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 🔹 CLAIMS
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
