package br.com.sorveteria.sistema_sorveteria.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "SEGREDO_SUPER_SECRETO_123456";
    private static final long EXPIRATION = 1000 * 60 * 60 * 4; // 4h

    public String gerarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        return !getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
